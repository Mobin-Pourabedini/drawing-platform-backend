package ir.sharif.louie.models.dto.painting;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.sharif.louie.models.db.Painting;
import lombok.Data;

@Data
public class SavePaintingResponse {
    private Long id;

    @JsonProperty("canvas_data")
    private String canvasData;

    public SavePaintingResponse(Painting painting) {
        this.id = painting.getId();
        this.canvasData = painting.getCanvasData();
    }
}