package fr.eni.papeterie.bo;

public class Ligne {
    protected Article article;
    protected int qte;


    //constructeur
    public Ligne(Article article, int qte) {
        this.article = article;
        this.qte = qte;
    }
    // getters et setters
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
    //retourne le prix unitaire d'un article
    public float getPrix() {
        return article.getPrixUnitaire();
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "article=" + article +
                ", qte=" + qte +
                '}';
    }
}
