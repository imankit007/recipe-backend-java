package com.recipe.web.controller;


import com.recipe.core.security.AuthContextHolder;
import com.recipe.data.auth.repository.UserRepository;
import com.recipe.web.model.user.UpdateAvatarRequest;
import com.recipe.web.model.user.UpdateAvatarResponse;
import com.recipe.web.model.user.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user-related operations.
 * Requires JWT authentication via Bearer token.
 */
@Slf4j
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final com.recipe.service.S3Service s3Service;

    /**
     * Returns the current authenticated user's information.
     * User info is extracted from the JWT token in the Authorization header.
     *
     * @return ResponseEntity containing the authenticated user's information
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        Long userId = AuthContextHolder.getUserId();


        log.info("Context = {}", AuthContextHolder.getContext());


        log.info("Fetching current user info for userId: {}", userId);


        if (userId == null) {
            return ResponseEntity.notFound().build();
        }

        var user = userRepository.findById(userId);
        String finalThumbnailUrl = "";
        String profilePictureUrl = "";

        if (user.isPresent()) {
            var u = user.get();
            if (u.getAvatarUrl() != null && !u.getAvatarUrl().isEmpty()) {
                try {
                    java.net.URL presigned = s3Service.presignGetUrl(u.getAvatarUrl(), java.time.Duration.ofMinutes(15));
                    profilePictureUrl = presigned.toString();
                } catch (Exception e) {
                    log.warn("Failed to generate presigned GET URL for avatar", e);
                }
            }
        }

        String finalProfilePictureUrl = profilePictureUrl;
        return user.map(u -> new UserResponse(u.getId(), u.getEmail(), u.getDisplayName(), finalProfilePictureUrl, finalThumbnailUrl))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/avatar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateAvatarResponse> updateAvatar(@RequestBody UpdateAvatarRequest req) {
        Long userId = AuthContextHolder.getUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String sourceKey = req.objectKey();
        String expectedEtag = req.etag();
        var user = userOpt.get();

        try {
            String destKey;
            // If uploaded to temp/ prefix, promote; else assume it's already a final key
            if (sourceKey != null && sourceKey.startsWith("temp/")) {
                destKey = s3Service.promoteTempToAvatar(sourceKey, userId, 5L * 1024 * 1024, expectedEtag); // 5MB limit
            } else {
                destKey = sourceKey;
            }

            user.setAvatarUrl(destKey);
            userRepository.save(user);

            java.net.URL presigned = s3Service.presignGetUrl(destKey, java.time.Duration.ofMinutes(15));
            return ResponseEntity.ok(new com.recipe.web.model.user.UpdateAvatarResponse(presigned.toString(), destKey));
        } catch (IllegalArgumentException _) {
            return ResponseEntity.badRequest().body(new com.recipe.web.model.user.UpdateAvatarResponse(null, sourceKey));
        } catch (Exception e) {
            log.error("Failed to update avatar", e);
            return ResponseEntity.status(500).body(new com.recipe.web.model.user.UpdateAvatarResponse(null, sourceKey));
        }
    }

}
