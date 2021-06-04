package fr.eni.papeterie.bo;


import java.util.ArrayList;
import java.util.List;

public class Panier {
    private float montant;
    private List<Ligne> lignesPanier;


    // constructeur
    public Panier() {
        this.lignesPanier = new ArrayList<Ligne>();
    }


    //getters et setters
    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    //
    public List<Ligne> getLignesPanier() {
        return lignesPanier;
    }

    // ajouter une ligne au panier
    public void addLigne(Article article, int qte) {
        Ligne ligneAdding = new Ligne(article, qte);
        lignesPanier.add(ligneAdding);
    }

    public final Ligne getLigne(int index)    {
        return lignesPanier.get(index);
    }

    public void removeLigne(int index) {
        lignesPanier.remove(index);
    }

    //modifier la quantité placée dans le panier
    public void updateLigne(int index, int newQte) {
        this.getLigne(index).setQte(newQte);
    }
}
