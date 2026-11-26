package tn.edukids.ms4_gamification.mapper;

import org.springframework.stereotype.Component;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.entity.*;
import java.util.stream.Collectors;

@Component
public class GamificationMapper {
    public BadgeResponse toResponse(Badge badge) {
        return new BadgeResponse(badge.getId(), badge.getNom(), badge.getDescription(),
                badge.getIcone(), badge.getPointsRequis());
    }

    public void update(Badge badge, BadgeRequest request) {
        badge.setNom(request.nom());
        badge.setDescription(request.description());
        badge.setIcone(request.icone());
        badge.setPointsRequis(request.pointsRequis());
    }

    public DefiResponse toResponse(Defi defi) {
        return new DefiResponse(defi.getId(), defi.getTitre(), defi.getDescription(), defi.getType(),
                defi.getPointsRecompense(), defi.getNiveauScolaire(), defi.getDateDebut(),
                defi.getDateFin(), defi.getStatut(), defi.estActif());
    }

    public void update(Defi defi, DefiRequest request) {
        defi.setTitre(request.titre());
        defi.setDescription(request.description());
        defi.setType(request.type());
        defi.setPointsRecompense(request.pointsRecompense());
        defi.setNiveauScolaire(request.niveauScolaire());
        defi.setDateDebut(request.dateDebut());
        defi.setDateFin(request.dateFin());
        defi.setStatut(request.statut());
    }

    public ProfilResponse toResponse(ProfilGamification profil) {
        return new ProfilResponse(profil.getId(), profil.getEleveId(), profil.getTotalPoints(),
                profil.getNiveau(), profil.getDateCreation(),
                profil.getBadges().stream().map(Badge::getId).collect(Collectors.toSet()));
    }

    public ParticipationResponse toResponse(ParticipationDefi participation) {
        return new ParticipationResponse(participation.getId(), participation.getProfil().getId(),
                participation.getDefi().getId(), participation.getStatut(), participation.getScore(),
                participation.getPointsGagnes(), participation.getDateParticipation());
    }
}
