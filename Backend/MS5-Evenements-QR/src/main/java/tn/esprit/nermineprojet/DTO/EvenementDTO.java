package tn.esprit.nermineprojet.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.nermineprojet.Entities.StatutEvenement;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvenementDTO {

    Long id;
    String titre;
    String description;
    Date dateDebut;
    Date dateFin;
    String type;
    Integer capacite;
    StatutEvenement statut;
}