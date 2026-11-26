package tn.esprit.autoloc.webdistribue.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tentative")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identité de l'élève gérée par Keycloak. */
    @Column(nullable = false)
    private Long eleveId;

    /** Note de la tentative, sur 20. */
    private Float score;

    @Column(nullable = false)
    private LocalDateTime dateSoumission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    /** Tentative 1 — 0..* ReponseEleve */
    @OneToMany(mappedBy = "tentative", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReponseEleve> reponses = new ArrayList<>();

    public void addReponse(ReponseEleve r) {
        r.setTentative(this);
        reponses.add(r);
    }
}
