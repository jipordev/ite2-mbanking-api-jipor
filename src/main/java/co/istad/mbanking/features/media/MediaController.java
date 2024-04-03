package co.istad.mbanking.features.media;

import co.istad.mbanking.features.media.dto.MediaResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Negative;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file){
        return mediaService.uploadSingle(file, "IMAGE");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files){
        return mediaService.uploadMultiple(files, "IMAGE");
    }

    @GetMapping("/{mediaName}")
    MediaResponse loadMediaByName(@PathVariable String mediaName){
        return mediaService.loadMediaByName(mediaName, "IMAGE");
    }

    @DeleteMapping("/{mediaName}")
    MediaResponse deleteByName(@PathVariable String mediaName){
        return mediaService.deleteMediaByName(mediaName, "IMAGE");
    }

    @GetMapping
    List<MediaResponse> loadAllMedias() {
        return mediaService.loadAllMedias("IMAGE");
    }

    @GetMapping("/download/{mediaName}")
    public ResponseEntity<?> downloadMedia(@PathVariable String mediaName) {
        return ResponseEntity.ok().body(
                Map.of(
                        "media",mediaService.downloadMediaByName(mediaName, "IMAGE")
                )
        );
    }

}
