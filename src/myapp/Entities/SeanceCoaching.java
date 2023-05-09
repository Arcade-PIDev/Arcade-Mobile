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
public class SeanceCoaching {
    private int id  ; 
    private Double  prixSeance ; 
    private String titreSeance , descriptionSeance;
    private String imageSeance;
    private Date dateDebutSeance,dateFinSeance ;

    public SeanceCoaching() {
    }

    public SeanceCoaching(int id, Double prixSeance, String titreSeance, String descriptionSeance, String imageSeance, Date dateDebutSeance, Date dateFinSeance) {
        this.id = id;
        this.prixSeance = prixSeance;
        this.titreSeance = titreSeance;
        this.descriptionSeance = descriptionSeance;
        this.imageSeance = imageSeance;
        this.dateDebutSeance = dateDebutSeance;
        this.dateFinSeance = dateFinSeance;
    }

    public SeanceCoaching(Double prixSeance, String titreSeance, String descriptionSeance, String imageSeance, Date dateDebutSeance, Date dateFinSeance) {
        this.prixSeance = prixSeance;
        this.titreSeance = titreSeance;
        this.descriptionSeance = descriptionSeance;
        this.imageSeance = imageSeance;
        this.dateDebutSeance = dateDebutSeance;
        this.dateFinSeance = dateFinSeance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrixSeance() {
        return prixSeance;
    }

    public void setPrixSeance(Double prixSeance) {
        this.prixSeance = prixSeance;
    }

    public String getTitreSeance() {
        return titreSeance;
    }

    public void setTitreSeance(String titreSeance) {
        this.titreSeance = titreSeance;
    }

    public String getDescriptionSeance() {
        return descriptionSeance;
    }

    public void setDescriptionSeance(String descriptionSeance) {
        this.descriptionSeance = descriptionSeance;
    }

    public String getImageSeance() {
        return imageSeance;
    }

    public void setImageSeance(String imageSeance) {
        this.imageSeance = imageSeance;
    }

    public Date getDateDebutSeance() {
        return dateDebutSeance;
    }

    public void setDateDebutSeance(Date dateDebutSeance) {
        this.dateDebutSeance = dateDebutSeance;
    }

    public Date getDateFinSeance() {
        return dateFinSeance;
    }

    public void setDateFinSeance(Date dateFinSeance) {
        this.dateFinSeance = dateFinSeance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.prixSeance);
        hash = 37 * hash + Objects.hashCode(this.titreSeance);
        hash = 37 * hash + Objects.hashCode(this.descriptionSeance);
        hash = 37 * hash + Objects.hashCode(this.imageSeance);
        hash = 37 * hash + Objects.hashCode(this.dateDebutSeance);
        hash = 37 * hash + Objects.hashCode(this.dateFinSeance);
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
        final SeanceCoaching other = (SeanceCoaching) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.titreSeance, other.titreSeance)) {
            return false;
        }
        if (!Objects.equals(this.descriptionSeance, other.descriptionSeance)) {
            return false;
        }
        if (!Objects.equals(this.imageSeance, other.imageSeance)) {
            return false;
        }
        if (!Objects.equals(this.prixSeance, other.prixSeance)) {
            return false;
        }
        if (!Objects.equals(this.dateDebutSeance, other.dateDebutSeance)) {
            return false;
        }
        if (!Objects.equals(this.dateFinSeance, other.dateFinSeance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeanceCoaching{" + "id=" + id + ", prixSeance=" + prixSeance + ", titreSeance=" + titreSeance + ", descriptionSeance=" + descriptionSeance + ", imageSeance=" + imageSeance + ", dateDebutSeance=" + dateDebutSeance + ", dateFinSeance=" + dateFinSeance + '}';
    }

    

    
   

    

   
   

    
   
    
}
