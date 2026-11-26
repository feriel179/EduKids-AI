package tn.esprit.nermineprojet.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Date dateParticipation;

    @Enumerated(EnumType.STRING)
    StatutParticipation statut;

    @ManyToOne
    Eleve eleve;

    @ManyToOne
    Evenement evenement;
}