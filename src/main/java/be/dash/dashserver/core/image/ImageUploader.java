package be.dash.dashserver.core.image;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String uploadImage(MultipartFile file) throws IOException;
}
