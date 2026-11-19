package tn.esprit.autoloc.webdistribue.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "reponse_eleve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReponseEleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    /** Choix coché (null pour une question TEXTE_LIBRE). Une ligne par choix coché en QCM_MULTIPLE. */
    private Long choixId;

    private String texteLibre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tentative_id", nullable = false)
    private Tentative tentative;
}
