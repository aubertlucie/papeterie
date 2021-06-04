package fr.eni.papeterie.bll;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DAOFactory;
import fr.eni.papeterie.dal.jdbc.ArticleDAO;

import java.util.List;

public class CatalogueManager {

    private ArticleDAO articleDAO = DAOFactory.getArticleDAO(); // on le met en attribut d'instance vu qu'on s'en sert tout le temps

    //le singleton : on créé l'instance + constructeur vide + getInstance (on veut qu'il n'y ait qu'une instance de manager)
    private static CatalogueManager instance;


    private CatalogueManager() {
    }

    public static CatalogueManager getInstance() {
        if (instance == null) {
            instance = new CatalogueManager();
        }
        return instance;
    }

    public List<Article> getCatalogue() throws BLLException {
        return this.articleDAO.selectAll();
    }


    public void addArticle(Article a) throws BLLException {
        validerArticle(a);
        this.articleDAO.insert(a);
    }

    public void updateArticle(Article a) throws BLLException {
        validerArticle(a);
        this.articleDAO.update(a);
    }

    public void removeArticle(int index) throws BLLException {
        try {
            this.articleDAO.delete(index);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    public Article getArticle(int index) throws BLLException {
        return this.articleDAO.selectById(index);
    }

    private void validerArticle(Article a) throws BLLException {
        if (a == null) {
            throw new BLLException("L'article est null");
        }
        if (a instanceof Ramette && ((Ramette) a).getGrammage() <= 0) { //le grammage n'est pas dans article mais dans ramette, on vérifie d'abord si c'est une ramette
            throw new BLLException("Son grammage n'est pas valide");
        }
        if (a instanceof Stylo && (((Stylo) a).getCouleur() == null || ((Stylo) a).getCouleur().trim().length() == 0)) {
            throw new BLLException("La couleur n'est pas valide");
        }

        if (a.getReference() == null || a.getReference().trim().length() == 0) {
            throw new BLLException("La référence est obligatoire");
        }
    }


}



