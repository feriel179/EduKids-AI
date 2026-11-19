package tn.esprit.autoloc.webdistribue.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    /** Référence externe vers le chapitre (MS1). */
    @Column(nullable = false)
    private Long chapitreId;

    private Integer dureeMin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatutQuiz statut = StatutQuiz.BROUILLON;

    /** Quiz 1 — 1..* Question */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordre ASC")
    @Builder.Default
    private List<Question> questions = new ArrayList<>();

    /** Quiz 1 — 0..* Tentative */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Tentative> tentatives = new ArrayList<>();

    public void addQuestion(Question question) {
        question.setQuiz(this);
        questions.add(question);
    }
}
