package tn.edukids.ms4_gamification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "badge", schema = "dbo")
@Getter @Setter
public class Badge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String icone;

    @Column(name = "points_requis", nullable = false)
    private int pointsRequis;

    @ManyToMany(mappedBy = "badges")
    private Set<ProfilGamification> profils = new HashSet<>();
}
