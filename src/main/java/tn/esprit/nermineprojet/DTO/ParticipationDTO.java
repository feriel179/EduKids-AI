package tn.esprit.nermineprojet.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.nermineprojet.Entities.StatutParticipation;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {

    private Date dateParticipation;

    private StatutParticipation statut;

    private Long eleveId;

    private Long evenementId;

}