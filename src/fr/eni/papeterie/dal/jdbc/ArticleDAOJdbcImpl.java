package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.jdbc.ArticleDAO;
import fr.eni.papeterie.dal.jdbc.JdbcTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class permettant de faire appel à des instructions SQL
 */
public class ArticleDAOJdbcImpl implements ArticleDAO {
    //Constantes des requetes sql
    private final static String reqAll ="SELECT * FROM Articles;";
    private final static String reqById ="SELECT * FROM Articles WHERE idArticle = ?;";
    private final static String reqUpdate ="UPDATE Articles SET reference =?, marque=?, designation=?, prixUnitaire=?, qteStock=?, grammage=?, couleur=? WHERE idArticle=? ;";
    private final static String reqDelete ="DELETE FROM Articles WHERE idArticle = ?;";
    private final static String reqInsert ="INSERT INTO Articles (reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";


    @Override
    /**
     * Methode permettant d'obtenir une liste d'article
     * @return une liste d'articles comprenant des stylos et des ramettes
     */
    public List<Article> selectAll(){
        List<Article> listArticle = new ArrayList<>();
        try(Connection connection = JdbcTools.getConnection()){
            PreparedStatement reqSql = connection.prepareStatement(reqAll);
            ResultSet rs = reqSql.executeQuery();
            //On boucle tant qu'il y a une ligne à lire
            while(rs.next()){
                //Si l'article est un stylo
                if(rs.getString("couleur") == null){
                    listArticle.add(new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("Reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")));
                }
                //Sinon c'est une ramette
                if(rs.getString("grammage") == null){
                    listArticle.add(new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")));
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        //La methode retourne une liste d'article
        return listArticle;
    }

    @Override
    /**
     * Methode permettant d'obtenir un id en fonction de son id
     * @param i correspondant à l'id de l'article voulu
     * @return l'article voulu
     */
    public Article selectById(int id) {
        Article article = null;
        try (Connection connection = JdbcTools.getConnection())
        {
            PreparedStatement reqSql = connection.prepareStatement(reqById);
            reqSql.setInt(1,id);
            ResultSet rs = reqSql.executeQuery();
            if (rs.next()){
                //Si l'article est un stylo alors
                if (rs.getString("type").trim().equalsIgnoreCase("stylo"))
                {
                    article = new Stylo(rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur"));
                }
                //Si l'article est une ramette
                else if (rs.getString("type").trim().equalsIgnoreCase("ramette"))
                {
                    article = new Ramette(rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("Reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage"));
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return article;
    }

    @Override
    /**
     * Methode permettant de mettre à jour l'article dans la BDD
     * @param a Article
     */
    public void update(Article a){
        try(Connection connection = JdbcTools.getConnection() ) {
            PreparedStatement reqSql = connection.prepareStatement(reqUpdate);
            reqSql.setString(1,a.getReference());
            reqSql.setString(2,a.getMarque());
            reqSql.setString(3,a.getDesignation());
            reqSql.setFloat(4, a.getPrixUnitaire());
            reqSql.setInt(5,a.getQteStock());
            reqSql.setInt(8, a.getIdArticle());
            //Si l'article est un stylo
            if (a instanceof Stylo){
                reqSql.setString(7, ((Stylo) a).getCouleur());
            }
            //Si l'article est une ramette
            if (a instanceof Ramette){
                reqSql.setInt(6, ((Ramette) a).getGrammage());
            }
            reqSql.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    /**
     * Methode permettant de supprimer un article de la BDD en fonction de son id
     * @param i Integer indiquant l'id de l'article
     */
    public void delete(int id){
        try(Connection connection = JdbcTools.getConnection()) {
            PreparedStatement reqSql = connection.prepareStatement(reqDelete);
            reqSql.setInt(1,id);
            //On execute la commande SQL DELETE
            reqSql.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    /**
     * Methode permettant d'inserer un article dans la BDD
     * @param a Article
     */
    public void insert(Article article){
        try (Connection connection = JdbcTools.getConnection()){
            //Récupération des données de l'article commun à stylo et ramette
            PreparedStatement reqSql = connection.prepareStatement(reqInsert);
            reqSql.setString(1,article.getReference());
            reqSql.setString(2,article.getMarque());
            reqSql.setString(3,article.getDesignation());
            reqSql.setInt(4, (int) article.getPrixUnitaire());
            reqSql.setInt(5,article.getQteStock());
            //Si l'article est une ramette
            if (article instanceof Ramette){
                reqSql.setInt(6, ((Ramette) article).getGrammage());
                reqSql.setString(8,"Ramette");
            }
            //Si l'article est un stylo
            else if (article instanceof Stylo){
                reqSql.setString(7, ((Stylo) article).getCouleur());
                reqSql.setString(8,"Stylo");
            }
            reqSql.executeUpdate();
            //Recuperation de l'auto-incrementation
            ResultSet rs = reqSql.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                article.setIdArticle(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }
