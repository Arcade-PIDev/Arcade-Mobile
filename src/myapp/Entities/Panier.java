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
public class Panier {
    private int id,quantite,commande;
    private Produit produit;

    public Panier(){};

    public Panier(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getCommande() {
        return commande;
    }

    public void setCommande(int commande) {
        this.commande = commande;
    }
    
    public void setProduct(Produit produit) {
        this.produit= produit;
    }

 @Override
    public String toString() {
        return "cart{" + "id=" + id + ",product=" + produit + ",quantite=" + quantite + "}";
    }
}
