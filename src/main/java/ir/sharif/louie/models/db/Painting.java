package ir.sharif.louie.models.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String canvasData; // JSON string of canvas data

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Painting() {}

    public Painting(String canvasData, User user) {
        this.canvasData = canvasData;
        this.user = user;
    }
}
