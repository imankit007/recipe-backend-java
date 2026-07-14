package com.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.net.URL;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${s3.bucket:recipe-media}")
    private String bucket;

    public URL presignPutUrl(String key, String contentType, Duration expires) {
        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(por)
                .signatureDuration(expires)
                .build();
        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);
        return presigned.url();
    }

    public URL presignGetUrl(String key, Duration expires) {
        GetObjectRequest gor = GetObjectRequest.builder().bucket(bucket).key(key).build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(gor)
                .signatureDuration(expires)
                .build();
        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
        return presigned.url();
    }

    public boolean objectExists(String key) {
        try {
            HeadObjectRequest req = HeadObjectRequest.builder().bucket(bucket).key(key).build();
            s3Client.headObject(req);
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    public ObjectMeta headObject(String key) {
        try {
            HeadObjectRequest req = HeadObjectRequest.builder().bucket(bucket).key(key).build();
            HeadObjectResponse resp = s3Client.headObject(req);
            return new ObjectMeta(resp.contentLength(), resp.contentType(), resp.eTag());
        } catch (S3Exception e) {
            return null;
        }
    }

    public String copyObject(String sourceKey, String destKey) {
        String copySource = String.format("%s/%s", bucket, sourceKey);
        CopyObjectRequest copyReq = CopyObjectRequest.builder()
                .copySource(copySource)
                .destinationBucket(bucket)
                .destinationKey(destKey)
                .build();
        s3Client.copyObject(copyReq);
        return destKey;
    }

    public void deleteObject(String key) {
        DeleteObjectRequest req = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.deleteObject(req);
    }

    /**
     * Move object from sourceKey to a new avatars/{userId}/{uuid}{ext} key.
     * Validates content type and size limits.
     */
    public String promoteTempToAvatar(String sourceKey, long userId, long maxSizeBytes, String expectedEtag) {
        ObjectMeta meta = headObject(sourceKey);
        if (meta == null) throw new IllegalArgumentException("source object not found");

        // ETag verification if provided
        if (expectedEtag != null && !expectedEtag.isBlank()) {
            String actual = meta.eTag();
            if (actual == null || !actual.replaceAll("\"", "").equals(expectedEtag.replaceAll("\"", ""))) {
                throw new IllegalArgumentException("etag mismatch");
            }
        }

        String ct = meta.contentType();
        if (ct == null || !ct.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("unsupported content type: " + ct);
        }

        if (meta.contentLength() > maxSizeBytes) {
            throw new IllegalArgumentException("file too large");
        }

        String ext = "";
        if (sourceKey.contains(".")) {
            ext = sourceKey.substring(sourceKey.lastIndexOf('.'));
        } else if (ct.equals("image/jpeg")) {
            ext = ".jpg";
        } else if (ct.equals("image/png")) {
            ext = ".png";
        }

        String destKey = String.format("avatars/%d/%s%s", userId, UUID.randomUUID(), ext);
        copyObject(sourceKey, destKey);
        deleteObject(sourceKey);
        return destKey;
    }

    public static record ObjectMeta(long contentLength, String contentType, String eTag) {}
}