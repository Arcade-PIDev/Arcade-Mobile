/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Entities;

/**
 *
 * @author Amira
 */
public class Commande {
    private int id,prixTotal;

    public Commande() {
    }

    public Commande(int  prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

 @Override
    public String toString() {
        return "cart{" + "id=" + id  + ", prixTotal=" + prixTotal + "}";
    }
}
