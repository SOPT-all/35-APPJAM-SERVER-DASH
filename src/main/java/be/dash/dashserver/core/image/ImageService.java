package be.dash.dashserver.core.image;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import be.dash.dashserver.core.exception.BadGatewayException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageUploader imageUploader;

    public String upload(MultipartFile file) {
        try {
            return imageUploader.uploadImage(file);
        } catch (IOException e) {
            throw new BadGatewayException("이미지 업로드 중에 오류가 발생했습니다.");
        }
    }
}
