/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bhk
 */
public class Formulaire{
    private int id,code,resultat_quiz;
    private String Contact,Type;
    private Date date_de_creation;

    public Formulaire(int id, int code, int resultat_quiz, String Contact, String Type, Date date_de_creation) {
        this.id = id;
        this.code = code;
        this.resultat_quiz = resultat_quiz;
        this.Contact = Contact;
        this.Type = Type;
        this.date_de_creation = date_de_creation;
    }

    public Formulaire() {
        
    }
    

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getResultat_quiz() {
        return resultat_quiz;
    }

    public void setResultat_quiz(int resultat_quiz) {
        this.resultat_quiz = resultat_quiz;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + this.code;
        hash = 53 * hash + this.resultat_quiz;
        hash = 53 * hash + Objects.hashCode(this.Contact);
        hash = 53 * hash + Objects.hashCode(this.Type);
        hash = 53 * hash + Objects.hashCode(this.date_de_creation);
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
        final Formulaire other = (Formulaire) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.code != other.code) {
            return false;
        }
        if (this.resultat_quiz != other.resultat_quiz) {
            return false;
        }
        if (!Objects.equals(this.Contact, other.Contact)) {
            return false;
        }
        if (!Objects.equals(this.Type, other.Type)) {
            return false;
        }
        if (!Objects.equals(this.date_de_creation, other.date_de_creation)) {
            return false;
        }
        return true;
    }

    
    

   
   

    
   
    
}
