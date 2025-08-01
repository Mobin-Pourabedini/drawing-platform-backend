package ir.sharif.louie.models.dto.painting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SavePaintingRequest {
    private String title;

    @JsonProperty("canvas_data")
    private String canvasData;
}
