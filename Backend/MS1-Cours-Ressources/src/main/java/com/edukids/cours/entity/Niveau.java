
package com.edukids.cours.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "niveaux")
public class Niveau {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String libelle;

    public Niveau() {}

    public UUID getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
