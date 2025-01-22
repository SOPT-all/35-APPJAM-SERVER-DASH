package be.dash.dashserver.api.core.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import be.dash.dashserver.api.core.external.dto.ImagePostResponse;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.image.ImageService;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Trace
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Permission(role = {Role.MEMBER, Role.TEACHER})
    @PostMapping
    public ResponseEntity<ImagePostResponse> createStore(@RequestPart final MultipartFile image) {
        String uploadUrl = imageService.upload(image);
        return ResponseEntity.ok().body(new ImagePostResponse(uploadUrl));
    }
}

