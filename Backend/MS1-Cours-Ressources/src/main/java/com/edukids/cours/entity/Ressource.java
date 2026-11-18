
package com.edukids.cours.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "ressources")
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titre;
    private String type;
    private String url;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cours cours;

    public Ressource() {}

    public UUID getId() { return id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) {
        this.url = url;
    }

    public Cours getCours() { return cours; }
    public void setCours(Cours cours) {
        this.cours = cours;
    }
}
