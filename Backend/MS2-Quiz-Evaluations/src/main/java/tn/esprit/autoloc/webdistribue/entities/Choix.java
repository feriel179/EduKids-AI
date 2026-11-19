package tn.esprit.autoloc.webdistribue.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "choix")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texte;

    @Column(nullable = false)
    private Boolean estCorrect;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
