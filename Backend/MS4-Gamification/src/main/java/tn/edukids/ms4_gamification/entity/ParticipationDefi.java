package tn.edukids.ms4_gamification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "participation_defi", schema = "dbo")
@Getter @Setter
public class ParticipationDefi {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profil_gamification_id", nullable = false)
    private ProfilGamification profil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "defi_id", nullable = false)
    private Defi defi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutParticipation statut = StatutParticipation.EN_COURS;

    @Column(nullable = false)
    private int score;

    @Column(name = "points_gagnes", nullable = false)
    private int pointsGagnes;

    @Column(name = "date_participation", nullable = false)
    private LocalDate dateParticipation;

    @PrePersist
    void initDateParticipation() {
        if (dateParticipation == null) dateParticipation = LocalDate.now();
    }
}
