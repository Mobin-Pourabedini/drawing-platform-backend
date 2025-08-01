package ir.sharif.louie.controllers;

import ir.sharif.louie.models.db.Painting;
import ir.sharif.louie.models.UserPrincipal;
import ir.sharif.louie.models.dto.painting.SavePaintingRequest;
import ir.sharif.louie.models.dto.painting.SavePaintingResponse;
import ir.sharif.louie.services.PaintingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {

    private final PaintingService paintingService;
    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> savePainting(@RequestBody SavePaintingRequest request,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Painting painting = paintingService.savePainting(
                userPrincipal.getUsername(),
                request.getCanvasData()
        );

        return ResponseEntity.ok("Painting saved");
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPainting(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Optional<Painting> painting = paintingService.getPaintingByUserUsername(userPrincipal.getUsername());

        if (painting.isPresent()) {
            SavePaintingResponse response = new SavePaintingResponse(painting.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok("Painting not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyPainting(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        paintingService.deletePaintingByUserUsername(userPrincipal.getUsername());

        return ResponseEntity.ok("Painting deleted");
    }
}
