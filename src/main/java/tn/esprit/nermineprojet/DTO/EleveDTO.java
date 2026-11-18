package tn.esprit.nermineprojet.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EleveDTO {

    Long id;
    String nom;
    String prenom;
    String niveau;
}