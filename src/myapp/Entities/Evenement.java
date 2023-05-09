/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Entities;



/**
 *
 * @author zeine
 */
public class Evenement {
      private int id;
      private String NomEvent, AfficheE,  DescriptionEvent,lieu;
    

    public Evenement() {
    }

    public Evenement(  String NomEvent, String lieu, String DescriptionEvent, String AfficheE) {        
        this.NomEvent = NomEvent;
        this.lieu = lieu;
        this.AfficheE = AfficheE;
        this.DescriptionEvent = DescriptionEvent;
       
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  
    public String getNomEvent() {
        return NomEvent;
    }

    public void setNomEvent(String NomEvent) {
        this.NomEvent = NomEvent;
    }

  
    public String getAfficheE() {
        return AfficheE;
    }

    public void setAfficheE(String AfficheE) {
        this.AfficheE = AfficheE;
    }

    public String getDescriptionEvent() {
        return DescriptionEvent;
    }

    public void setDescriptionEvent(String DescriptionEvent) {
        this.DescriptionEvent = DescriptionEvent;
    }

   

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", NomEvent=" + NomEvent + ", AfficheE=" + AfficheE + ", DescriptionEvent=" + DescriptionEvent + '}';
    }

          
    
    
}
