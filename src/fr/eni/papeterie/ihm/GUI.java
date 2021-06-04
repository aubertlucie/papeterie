package fr.eni.papeterie.ihm;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Couleur;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel panneauPrincipal;
    //les labels
    private JLabel referenceLbl;
    private JLabel designationLbl;
    private JLabel marqueLbl;
    private JLabel stockLbl;
    private JLabel prixLbl;
    private JLabel typeLbl;
    private JLabel grammageLbl;
    private JLabel couleurLbl;

    //les champs texte
    private JTextField referenceTxt;
    private JTextField designationTxt;
    private JTextField marqueTxt;
    private JTextField stockTxt;
    private JTextField prixTxt;

    //les panneaux
    private JPanel typePanneau;
    private JPanel grammagePanneau;
    private JPanel boutonsPanneau;

    //les boutons
    private JRadioButton rametteRadio;
    private JRadioButton styloRadio;
    private JCheckBox quatreVingtGrammes;
    private JCheckBox centGrammes;
    private JComboBox couleurBox;
    private JButton backButton;
    private JButton nouveauButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton forwardButton;

    //liste article
    private List<Article> listeDarticles;
    private int index = 0;
    private Article articleAafficher;


    //constructeur vide pour y ajouter les éléments du GUI
    public GUI() {
        this.setTitle("Papeterie");
        this.setSize(500, 400); //on indique taille de la fenêtre en px
        this.setLocationRelativeTo(null); // ça centre la fenêtre
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //éteindre quand on clique sur la fermer
        //le panneau principal
        this.setContentPane(getPanneauPrincipal()); //je colle le panneau principal sur le support en bois
        //this.pack();
        //je remplis ma liste d'articles
        listeDarticles = new ArrayList<>();
        CatalogueManager catalogueManager = CatalogueManager.getInstance();
        try {
            listeDarticles = catalogueManager.getCatalogue();
        } catch (BLLException e) {
            System.out.println(e.getMessage());
        }
        //fin du remplissage de la liste
        if (!listeDarticles.isEmpty()) { //si la liste n'est pas vide
            this.articleAafficher = listeDarticles.get(index);// on récupère le 1er article (index initialisé à 1) de la liste (on veut modifier cet index pour aller au précédent ou suivant)
            getReferenceTxt().setText(articleAafficher.getReference());
            getDesignationTxt().setText(articleAafficher.getDesignation());
            getMarqueTxt().setText(articleAafficher.getMarque());
            getStockTxt().setText(String.valueOf(articleAafficher.getQteStock()));
            getPrixTxt().setText(String.valueOf(articleAafficher.getPrixUnitaire()));
            if (articleAafficher instanceof Ramette) {
                getRametteRadio().setSelected(true);
                if (((Ramette) articleAafficher).getGrammage() == 80){
                    getQuatreVingt().setSelected(true);
                } else {
                    getCent().setSelected(true);
                }
            }
            if (articleAafficher instanceof Stylo) {
                getStyloRadio().setSelected(true);
                getCouleurBox().setSelectedItem(Couleur.valueOf(((Stylo) articleAafficher).getCouleur())); //on dit que la couleur chopé dans l'article (string)
            }



        }
        this.setContentPane(getPanneauPrincipal());
        this.setVisible(true);
    }

    //singleton du panneau principal
    public JPanel getPanneauPrincipal() {
        if (panneauPrincipal == null) {
            panneauPrincipal = new JPanel(); //je créé le panneau principal
            panneauPrincipal.setLayout(new GridBagLayout()); //gridbaglayout en positionnement
            GridBagConstraints gbc = new GridBagConstraints(); //on créé le gbc
            gbc.gridx = 0;
            gbc.gridy = 0;
            //gbc.insets=new Insets(5,5,5,5);
            panneauPrincipal.add(getReferenceLbl(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            panneauPrincipal.add(getDesignationLbl(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            panneauPrincipal.add(getMarqueLbl(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            panneauPrincipal.add(getStockLbl(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            panneauPrincipal.add(getPrixLbl(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            panneauPrincipal.add(getReferenceTxt(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            panneauPrincipal.add(getDesignationTxt(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            panneauPrincipal.add(getMarqueTxt(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panneauPrincipal.add(getStockTxt(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            panneauPrincipal.add(getPrixTxt(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            panneauPrincipal.add(getTypePanneau(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            panneauPrincipal.add(getGrammagePanneau(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            panneauPrincipal.add(getCouleurLbl(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 7;
            panneauPrincipal.add(getCouleurBox(), gbc);
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.gridwidth = 2;
            panneauPrincipal.add(getBoutonsPanneau(), gbc);


        }
        return panneauPrincipal;
    }

    //singleton du panneau secondaire type
    public JPanel getTypePanneau() {
        if (typePanneau == null) {
            typePanneau = new JPanel(); //je créé le panneau secondaire type
            typePanneau.setLayout(new GridBagLayout()); //gridbaglayout en positionnement
            GridBagConstraints gbc = new GridBagConstraints(); //on créé le gbc
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            typePanneau.add(getTypeLbl(), gbc);
            gbc.gridheight = 1;
            ButtonGroup bg = new ButtonGroup();
            bg.add(getRametteRadio());
            bg.add(getStyloRadio());
            gbc.gridx = 1;
            gbc.gridy = 0;
            typePanneau.add(getRametteRadio(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            typePanneau.add(getStyloRadio(), gbc);
        }
        return typePanneau;
    }

    //singleton du panneau secondaire grammage
    public JPanel getGrammagePanneau() {
        if (grammagePanneau == null) {
            grammagePanneau = new JPanel(); //je créé le panneau secondaire grammage
            grammagePanneau.setLayout(new GridBagLayout()); //gridbaglayout en positionnement
            GridBagConstraints gbc = new GridBagConstraints(); //on créé le gbc
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            grammagePanneau.add(getGrammageLbl(), gbc);
            gbc.gridheight = 1;
            ButtonGroup bg = new ButtonGroup();
            bg.add(getQuatreVingt());
            bg.add(getCent());
            gbc.gridx = 1;
            gbc.gridy = 0;
            grammagePanneau.add(getQuatreVingt(), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            grammagePanneau.add(getCent(), gbc);

        }
        return grammagePanneau;
    }

    public JPanel getBoutonsPanneau() {
        if (boutonsPanneau == null) {
            boutonsPanneau = new JPanel(); //je créé le panneau secondaire boutons
            boutonsPanneau.add(getBackButton());
            boutonsPanneau.add(getNouveauButton());
            boutonsPanneau.add(getSaveButton());
            boutonsPanneau.add(getDeleteButton());
            boutonsPanneau.add(getForwardButton());

        }
        return boutonsPanneau;
    }


    //singleton label reference
    public JLabel getReferenceLbl() {
        if (referenceLbl == null) {
            referenceLbl = new JLabel("Référence");
        }
        return referenceLbl;
    }

    //singleton label designation
    public JLabel getDesignationLbl() {
        if (designationLbl == null) {
            designationLbl = new JLabel("Désignation");
        }
        return designationLbl;
    }

    //singleton label marque
    public JLabel getMarqueLbl() {
        if (marqueLbl == null) {
            marqueLbl = new JLabel("Marque");
        }
        return marqueLbl;
    }

    //singleton label stock
    public JLabel getStockLbl() {
        if (stockLbl == null) {
            stockLbl = new JLabel("Stock");
        }
        return stockLbl;
    }

    //singleton label prix
    public JLabel getPrixLbl() {
        if (prixLbl == null) {
            prixLbl = new JLabel("Prix");
        }
        return prixLbl;
    }

    //singleton label type
    public JLabel getTypeLbl() {
        if (typeLbl == null) {
            typeLbl = new JLabel("Type");
        }
        return typeLbl;
    }

    //singleton label type
    public JLabel getGrammageLbl() {
        if (grammageLbl == null) {
            grammageLbl = new JLabel("Grammage");
        }
        return grammageLbl;
    }

    //singleton label couleur
    public JLabel getCouleurLbl() {
        if (couleurLbl == null) {
            couleurLbl = new JLabel("Couleur");
        }
        return couleurLbl;
    }

    //singleton texte reference
    public JTextField getReferenceTxt() {
        if (referenceTxt == null) {
            referenceTxt = new JTextField();
            referenceTxt.setColumns(25);
        }
        return referenceTxt;
    }

    //singleton texte designation
    public JTextField getDesignationTxt() {
        if (designationTxt == null) {
            designationTxt = new JTextField(25);

        }
        return designationTxt;
    }

    //singleton texte marque
    public JTextField getMarqueTxt() {
        if (marqueTxt == null) {
            marqueTxt = new JTextField(25);

        }
        return marqueTxt;
    }

    //singleton texte stock
    public JTextField getStockTxt() {
        if (stockTxt == null) {
            stockTxt = new JTextField(25);

        }
        return stockTxt;
    }

    //singleton texte prix
    public JTextField getPrixTxt() {
        if (prixTxt == null) {
            prixTxt = new JTextField();
            prixTxt.setColumns(25);
        }
        return prixTxt;
    }

    //singleton bouton radio ramette
    public JRadioButton getRametteRadio() {
        if (rametteRadio == null) {
            rametteRadio = new JRadioButton("Ramette");
            rametteRadio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getCouleurBox().setEnabled(false); //on ne peut plus accéder aux couleurs si on clique sur ramette (vu que pas stylo)
                    getQuatreVingt().setEnabled(true);
                    getCent().setEnabled(true);
                    getQuatreVingt().doClick();
                }
            });
        }
        return rametteRadio;
    }

    //singleton bouton radio stylo
    public JRadioButton getStyloRadio() {
        if (styloRadio == null) {
            styloRadio = new JRadioButton("Stylo");
            styloRadio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    getQuatreVingt().setEnabled(false);
                    getCent().setEnabled(false);
                    getCouleurBox().setEnabled(true);
                }
            });
        }
        return styloRadio;
    }

    //singleton bouton radio 80
    public JCheckBox getQuatreVingt() {
        if (quatreVingtGrammes == null) {
            quatreVingtGrammes = new JCheckBox("80 grammes");
        }
        return quatreVingtGrammes;
    }

    //singleton bouton radio 100
    public JCheckBox getCent() {
        if (centGrammes == null) {
            centGrammes = new JCheckBox("100 grammes");
        }
        return centGrammes;
    }

    //singleton bouton combo couleur
    public JComboBox<Couleur> getCouleurBox() {
        if (couleurBox == null) {
            couleurBox = new JComboBox<>(Couleur.values());
        }
        return couleurBox;
    }

    //singleton bouton back
    public JButton getBackButton() {
        if (backButton == null) {
            backButton = new JButton(new ImageIcon("img/Back24.gif"));
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index <= 0) {                 //on vérifie que l'index =0
                        index = listeDarticles.size() - 1;
                    } else {
                        index--;
                    }
                    articleAafficher = listeDarticles.get(index);
                    getReferenceTxt().setText(articleAafficher.getReference());
                    getDesignationTxt().setText(articleAafficher.getDesignation());
                    getMarqueTxt().setText(articleAafficher.getMarque());
                    getStockTxt().setText(String.valueOf(articleAafficher.getQteStock()));
                    getPrixTxt().setText(String.valueOf(articleAafficher.getPrixUnitaire()));

                }
            });
        }
        return backButton;
    }

    //singleton bouton nouveau
    public JButton getNouveauButton() {
        if (nouveauButton == null) {
            nouveauButton = new JButton(new ImageIcon("img/New24.gif"));
            nouveauButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //vide les champs textes
                    getReferenceTxt().setText(null);
                    getDesignationTxt().setText(null);
                    getMarqueTxt().setText(null);
                    getStockTxt().setText(null);
                    getPrixTxt().setText(null);
                    articleAafficher = null;
                    }
            });
        }
        return nouveauButton;
    }

    //singleton bouton save
    public JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton(new ImageIcon("img/Save24.gif"));
            saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CatalogueManager cm;
                        //Appel du CatalogueManager
                        cm = CatalogueManager.getInstance();
                        //si nouvel article
                        if (articleAafficher == null) {
                            //construction de l'article selon ramette ou stylo
                            ConstruireArticle();
                            //ajout de l'article
                            try {
                                cm.addArticle(articleAafficher);
                            } catch (BLLException bllException) {

                            }
                            //si article à modifier
                        } else {
                            try {
                                //modifie article
                                cm.updateArticle(articleAafficher);
                            } catch (BLLException bllException) {

                            }
                            }
                    }
                });
        }
        return saveButton;
    }

    /**
     * Méthode pour construire un article
     */
    private void ConstruireArticle() {
        //Si ramette
        if (rametteRadio.isSelected()) {
            articleAafficher = new Ramette();
            //récupération des données des champs
            articleAafficher.setReference(getReferenceTxt().getText());
            articleAafficher.setDesignation(getDesignationTxt().getText());
            articleAafficher.setMarque(getMarqueTxt().getText());
            articleAafficher.setQteStock(Integer.parseInt(getStockTxt().getText()));
            articleAafficher.setPrixUnitaire(Float.parseFloat(getPrixTxt().getText()));
            //si 80 de grammage
            if (quatreVingtGrammes.isSelected()) {
                ((Ramette) articleAafficher).setGrammage(80);
            }
            //si 100 de grammage
            else {
                ((Ramette) articleAafficher).setGrammage(100);
            }
        }
        //si stylo
        else {
            articleAafficher = new Stylo();
            articleAafficher.setReference(getReferenceTxt().getText());
            articleAafficher.setDesignation(getDesignationTxt().getText());
            articleAafficher.setMarque(getMarqueTxt().getText());
            articleAafficher.setQteStock(Integer.parseInt(getStockTxt().getText()));
            articleAafficher.setPrixUnitaire(Float.parseFloat(getPrixTxt().getText()));
            //prendre la couleur sélectionnée de la ComboBox
            ((Stylo) articleAafficher).setCouleur(couleurBox.getSelectedItem().toString());
        }
    }

    //singleton bouton delete
    public JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton(new ImageIcon("img/Delete24.gif"));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CatalogueManager cm = CatalogueManager.getInstance();
                    try {
                        cm.removeArticle(articleAafficher.getIdArticle());
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                    listeDarticles.remove(articleAafficher); // on l'enlève de la liste d'article
                    getForwardButton().doClick(); //quand on supprime on affiche l'article suivant
                }
            });
        }
        return deleteButton;
    }

    //singleton bouton forward
    public JButton getForwardButton() {
        if (forwardButton == null) {
            forwardButton = new JButton(new ImageIcon("img/Forward24.gif"));
            forwardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index >= listeDarticles.size() - 1) {
                        index = 0;
                    } else {
                        index++;
                    }
                    articleAafficher = listeDarticles.get(index);
                    getReferenceTxt().setText(articleAafficher.getReference());
                    getDesignationTxt().setText(articleAafficher.getDesignation());
                    getMarqueTxt().setText(articleAafficher.getMarque());
                    getStockTxt().setText(String.valueOf(articleAafficher.getQteStock()));
                    getPrixTxt().setText(String.valueOf(articleAafficher.getPrixUnitaire()));
                    if (articleAafficher instanceof Ramette) {
                        getRametteRadio().setSelected(true);
                        if (((Ramette) articleAafficher).getGrammage() == 80){
                            getQuatreVingt().setSelected(true);
                        } else {
                            getCent().setSelected(true);
                        }
                    }
                    if (articleAafficher instanceof Stylo) {
                        getStyloRadio().setSelected(true);
                        getCouleurBox().setSelectedItem(Couleur.valueOf(((Stylo) articleAafficher).getCouleur())); //on dit que la couleur chopé dans l'article (string)
                    }

                }
            });
        }
        return forwardButton;
    }

}