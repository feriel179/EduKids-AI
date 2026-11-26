package tn.edukids.ms4_gamification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profil_gamification", schema = "dbo")
@Getter @Setter
public class ProfilGamification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eleve_id", nullable = false, unique = true)
    private Long eleveId;

    @Column(name = "total_points", nullable = false)
    private int totalPoints;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Niveau niveau = Niveau.DEBUTANT;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDate dateCreation;

    @ManyToMany
    @JoinTable(name = "profil_gamification_badges", schema = "dbo",
            joinColumns = @JoinColumn(name = "profil_gamification_id"),
            inverseJoinColumns = @JoinColumn(name = "badges_id"))
    private Set<Badge> badges = new HashSet<>();

    @OneToMany(mappedBy = "profil")
    private Set<ParticipationDefi> participations = new HashSet<>();

    @PrePersist
    void initDateCreation() {
        if (dateCreation == null) dateCreation = LocalDate.now();
    }

    public void ajouterPoints(int pts) {
        if (pts < 0) throw new IllegalArgumentException("Les points doivent être positifs");
        totalPoints = Math.addExact(totalPoints, pts);
        niveau = calculerNiveau();
    }

    public Niveau calculerNiveau() {
        if (totalPoints >= 500) return Niveau.CHAMPION;
        if (totalPoints >= 100) return Niveau.EXPLORATEUR;
        return Niveau.DEBUTANT;
    }
}
