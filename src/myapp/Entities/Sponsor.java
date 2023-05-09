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
public class Sponsor {
    private int id , IDEventsFK;
    private String NomSponsor, NumTelSponsor ,EmailSponsor, DomaineSponsor, AdresseSponsor, logoSponsor;
    private float MontantSponsoring;
    
    
    public Sponsor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIDEventsFK() {
        return IDEventsFK;
    }

    public void setIDEventsFK(int IDEventsFK) {
        this.IDEventsFK = IDEventsFK;
    }

    public String getNomSponsor() {
        return NomSponsor;
    }

    public void setNomSponsor(String NomSponsor) {
        this.NomSponsor = NomSponsor;
    }

    public String getNumTelSponsor() {
        return NumTelSponsor;
    }

    public void setNumTelSponsor(String NumTelSponsor) {
        this.NumTelSponsor = NumTelSponsor;
    }

    public String getEmailSponsor() {
        return EmailSponsor;
    }

    public void setEmailSponsor(String EmailSponsor) {
        this.EmailSponsor = EmailSponsor;
    }

    public String getDomaineSponsor() {
        return DomaineSponsor;
    }

    public void setDomaineSponsor(String DomaineSponsor) {
        this.DomaineSponsor = DomaineSponsor;
    }

    public String getAdresseSponsor() {
        return AdresseSponsor;
    }

    public void setAdresseSponsor(String AdresseSponsor) {
        this.AdresseSponsor = AdresseSponsor;
    }

    public String getLogoSponsor() {
        return logoSponsor;
    }

    public void setLogoSponsor(String logoSponsor) {
        this.logoSponsor = logoSponsor;
    }

    public float getMontantSponsoring() {
        return MontantSponsoring;
    }

    public void setMontantSponsoring(float MontantSponsoring) {
        this.MontantSponsoring = MontantSponsoring;
    }

    @Override
    public String toString() {
        return "Sponsor{" + "id=" + id + ", IDEventsFK=" + IDEventsFK + ", NomSponsor=" + NomSponsor + ", NumTelSponsor=" + NumTelSponsor + ", EmailSponsor=" + EmailSponsor + ", DomaineSponsor=" + DomaineSponsor + ", AdresseSponsor=" + AdresseSponsor + ", logoSponsor=" + logoSponsor + ", MontantSponsoring=" + MontantSponsoring + '}';
    }
          
    
}
