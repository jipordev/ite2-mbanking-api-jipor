package co.istad.mbanking.features.media;

import co.istad.mbanking.features.media.dto.MediaResponse;
import co.istad.mbanking.util.MediaUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements  MediaService{

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {

        // Generate new unique file name
        String newName = UUID.randomUUID().toString();

        // Extract extension from file upload
        // Assume profile.png

         newName += "."+ MediaUtil.extractExtension(file.getName());

        Path path = Paths.get(serverPath + folderName + "\\" + newName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage()
            );
        }
        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(MediaUtil.extractExtension(newName))
                .size(file.getSize())
                .uri(String.format("%s%s/%s", baseUri, folderName, newName ))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {

        // Create empty array list, wait for adding uploaded file
        List<MediaResponse> mediaResponses = new ArrayList<>();

        files.forEach(file -> {
            MediaResponse mediaResponse = this.uploadSingle(file, folderName);
            mediaResponses.add(mediaResponse);
        });

        return mediaResponses;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName, String folderName) {

        // Create absolute path
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {

            Resource resource = new UrlResource(path.toUri());
            log.info("Load resource: {}", resource.getFilename());

            if (!resource.exists()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media has not been found!"
                );
            }

            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType(Files.probeContentType(path))
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(resource.contentLength())
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName ))
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage()
            );
        }
    }

    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {

        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            long size = Files.size(path);
            if (!Files.deleteIfExists(path)){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media has not been found."
                );

            }
            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType(Files.probeContentType(path))
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(size)
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName ))
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Media path %s cannot be deleted", e.getLocalizedMessage())
            );
        }
    }

    @Override
    public List<MediaResponse> loadAllMedias(String folderName) {
        Path path = Paths.get(serverPath + folderName + "\\");

        try {
            List<MediaResponse> mediaResponses = new ArrayList<>();
            Files.list(path).forEach(mediaPath -> {
                try {
                    String mediaName = mediaPath.getFileName().toString();
                    Resource resource = new UrlResource(mediaPath.toUri());
                    if (resource.exists()) {
                        MediaResponse mediaResponse = MediaResponse.builder()
                                .name(mediaName)
                                .contentType(Files.probeContentType(mediaPath))
                                .extension(MediaUtil.extractExtension(mediaName))
                                .size(resource.contentLength())
                                .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                                .build();
                        mediaResponses.add(mediaResponse);
                    }
                } catch (IOException e) {
                    log.error("Error loading media: {}", e.getMessage());
                }
            });
            return mediaResponses;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Error loading media files from %s: %s", folderName, e.getMessage())
            );
        }
    }

    public ResponseEntity<Resource> downloadMediaByName(String fileName, String folderName) {
        try {
            Path path = Paths.get(serverPath+folderName+"\\"+fileName);
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media file not found");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error downloading media file", e);
        }
    }



}
