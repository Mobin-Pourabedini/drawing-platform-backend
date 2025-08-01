package ir.sharif.louie.models.dto.painting;

import lombok.Data;

@Data
public class SavePaintingRequest {
    private String title;
    private String canvasData;
}
