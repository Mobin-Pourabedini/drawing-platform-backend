package ir.sharif.louie.repositories;

import ir.sharif.louie.models.db.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    Optional<Painting> findPaintingByUser_Username(String username);
}