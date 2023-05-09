/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bhk
 */
public class Jeux {
    private int id  ; 
    
    private String nom , description;
    private String image,genre,color;
    private Double tournois ; 

    public Jeux() {
    }

    public Jeux(int id, String nom, String description, String image, String genre, String color) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.genre = genre;
        this.color = color;
    }

    public Jeux(String nom, String description, String image, String genre, String color) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.genre = genre;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getTournois() {
        return tournois;
    }

    public void setTournois(Double tournois) {
        this.tournois = tournois;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.image);
        hash = 97 * hash + Objects.hashCode(this.genre);
        hash = 97 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jeux other = (Jeux) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Jeux{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", image=" + image + ", genre=" + genre + ", color=" + color + ", tournois=" + tournois + '}';
    }

  

    
    
   

    

   
   

    
   
    
}
