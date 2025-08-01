package ir.sharif.louie.models.dto.painting;

import ir.sharif.louie.models.db.Painting;
import lombok.Data;

@Data
public class SavePaintingResponse {
    private Long id;
    private String canvasData;

    public SavePaintingResponse(Painting painting) {
        this.id = painting.getId();
        this.canvasData = painting.getCanvasData();
    }
}