package co.istad.mbanking.features.media.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record MediaResponse(
        String name, // unique
        String contentType, // PNG, JPEG, MP4
        String extension,
        String uri, // https://api.istad.co/media/image/c8c41751-3bc0-4f07-9658-7d95efbae692.png
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long size
) {
}
