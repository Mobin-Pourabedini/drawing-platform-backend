package ir.sharif.louie.services;

import ir.sharif.louie.models.db.Painting;
import ir.sharif.louie.models.db.User;
import ir.sharif.louie.repositories.PaintingRepository;
import ir.sharif.louie.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final UserRepository userRepository;

    public PaintingService(PaintingRepository paintingRepository, UserRepository userRepository) {
        this.paintingRepository = paintingRepository;
        this.userRepository = userRepository;
    }

    public Painting savePainting(String username, String canvasData) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));

        Painting p = paintingRepository.findPaintingByUser_Username(username).
                orElseGet(() -> {
                    return new Painting(canvasData, user);
                });
        p.setCanvasData(canvasData);
        return paintingRepository.save(p);
    }

    public Optional<Painting> getPaintingByUserUsername(String username) {
        return paintingRepository.findPaintingByUser_Username(username);
    }

    public void deletePaintingByUserUsername(String username) {
        paintingRepository.findPaintingByUser_Username(username)
                .ifPresent(paintingRepository::delete);
    }
}
