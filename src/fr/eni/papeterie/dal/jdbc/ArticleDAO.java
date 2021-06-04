package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.dal.DALException;

import java.util.List;

public interface ArticleDAO {
    void delete (int id) throws DALException;
    void update(Article article);
    void insert(Article article);
    Article selectById(int id);
    public List<Article> selectAll();



}
