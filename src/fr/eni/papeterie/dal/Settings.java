package fr.eni.papeterie.dal;

import java.util.Properties;

public class Settings {
    private static Properties propriete;

    static {
        try{
          propriete = new Properties();
          propriete.load(Settings.class.getResourceAsStream("settings.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getPropriete (String cle){
        String parametre = propriete.getProperty(cle,null);
        return parametre;
    }
}
