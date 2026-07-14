package com.recipe.web.controller;

import com.recipe.core.security.AuthContextHolder;
import com.recipe.data.auth.repository.UserRepository;
import com.recipe.service.S3Service;
import com.recipe.web.model.user.PresignRequest;
import com.recipe.web.model.user.PresignResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user/avatar")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AvatarController {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @PostMapping("/presign")
    public ResponseEntity<PresignResponse> presignAvatar(@RequestBody PresignRequest req) {
        Long userId = AuthContextHolder.getUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String filename = req.filename();
        String contentType = req.contentType();

        String ext = "";
        if (filename != null && filename.contains(".")) {
            ext = filename.substring(filename.lastIndexOf('.'));
        }

        String objectKey = "temp/" + userId + "/" + UUID.randomUUID() + ext;
        String ct = (contentType == null || contentType.isEmpty()) ? "application/octet-stream" : contentType;

        URL uploadUrl = s3Service.presignPutUrl(objectKey, ct, Duration.ofMinutes(15));

        // Do not persist the objectKey here. Persist only after successful upload via /user/avatar
        PresignResponse resp = new PresignResponse(uploadUrl.toString(), objectKey, 15 * 60);

        return ResponseEntity.ok(resp);
    }
}
