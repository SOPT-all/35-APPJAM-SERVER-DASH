package be.dash.dashserver.external.s3;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import be.dash.dashserver.core.exception.BadRequestException;
import be.dash.dashserver.core.image.ImageUploader;
import be.dash.dashserver.external.config.s3.S3Config;
import be.dash.dashserver.external.config.s3.S3Properties;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {
    private final S3Properties s3Properties;
    private final S3Config s3Config;
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/webp", "image/heic", "image/heif");

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        final String key = generateImageFileName();
        final S3Client s3Client = s3Config.getS3Client();

        validateExtension(image);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Properties.s3BucketName())
                .key(key)
                .contentType(image.getContentType())
                .contentDisposition("inline")
                .build();

        RequestBody requestBody = RequestBody.fromBytes(image.getBytes());
        s3Client.putObject(request, requestBody);
        return s3Properties.s3Endpoint() + key;
    }

    private String generateImageFileName() {
        return UUID.randomUUID() + ".jpg";
    }


    private void validateExtension(MultipartFile image) {
        String contentType = image.getContentType();
        if (!IMAGE_EXTENSIONS.contains(contentType)) {
            throw new BadRequestException("이미지 확장자는 jpg, png, webp, heic, heif만 가능합니다.");
        }
    }
}
