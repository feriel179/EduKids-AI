
package com.edukids.cours.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private UUID enseignantId;

    private String statut;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    public Cours() {}

    public UUID getId() { return id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getEnseignantId() { return enseignantId; }
    public void setEnseignantId(UUID enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Niveau getNiveau() { return niveau; }
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}
