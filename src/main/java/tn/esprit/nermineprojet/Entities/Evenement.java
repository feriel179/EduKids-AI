package tn.esprit.nermineprojet.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titre;
    String description;

    Date dateDebut;
    Date dateFin;

    String type;
    Integer capacite;

    @Enumerated(EnumType.STRING)
    StatutEvenement statut;
    @JsonIgnore
    @OneToMany(mappedBy = "evenement")
    List<Participation> participations;
}