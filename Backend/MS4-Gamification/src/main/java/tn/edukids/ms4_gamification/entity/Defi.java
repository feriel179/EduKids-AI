package tn.edukids.ms4_gamification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "defi", schema = "dbo")
@Getter @Setter
public class Defi {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titre;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeDefi type;

    @Column(name = "points_recompense", nullable = false)
    private int pointsRecompense;

    @Column(name = "niveau_scolaire", nullable = false)
    private int niveauScolaire;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutDefi statut;

    @OneToMany(mappedBy = "defi")
    private Set<ParticipationDefi> participations = new HashSet<>();

    public boolean estActif() {
        LocalDate aujourdHui = LocalDate.now();
        return statut == StatutDefi.ACTIF && dateDebut != null && dateFin != null
                && !aujourdHui.isBefore(dateDebut) && !aujourdHui.isAfter(dateFin);
    }
}
