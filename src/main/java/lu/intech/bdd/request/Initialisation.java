package lu.intech.bdd.request;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
 
public class Initialisation {
    /* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();
 
    public List<String> executerTests( HttpServletRequest request ) {
        /* Chargement du driver JDBC pour MySQL */
    	
    	 
        try {
            messages.add( "Chargement du driver..." );
            Class.forName( "com.mysql.jdbc.Driver" );
            messages.add( "Driver chargé !" );
        } catch ( ClassNotFoundException e ) {
            messages.add( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
                    + e.getMessage() );
        }
     
        /* Connexion à la base de données */
        String url = "jdbc:mysql://mysql1.alwaysdata.com/40853_intech";
        String utilisateur = "40853_2";
        String motDePasse = "intech";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            messages.add( "Connexion à la base de données..." );
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            messages.add( "Connexion réussie !" );
     
            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();
            messages.add( "Objet requête créé !" );
     
            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, adresse FROM Personne;" );
            messages.add( "Requête \"SELECT idPersonne, prenom, nom, age, adresse FROM Personne;\" effectuée !" );
      
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                int idPersonne = resultat.getInt( "idPersonne" );
                String prenom = resultat.getString( "prenom" );
                String nom = resultat.getString( "nom" );
                String age = resultat.getString( "age" );
                String adresse = resultat.getString( "adresse" );

                /* Formatage des données pour affichage dans la JSP finale. */
                messages.add( "Données retournées par la requête : id = " + idPersonne + ", prenom = " + prenom
                        + ", nom = "
                        + nom + ", age = " + age + ", adresse = " + adresse + "." );
            }
        } catch ( SQLException e ) {
            messages.add( "Erreur lors de la connexion : <br/>"
                    + e.getMessage() );
        } finally {
            messages.add( "Fermeture de l'objet ResultSet." );
            if ( resultat != null ) {
                try {
                    resultat.close();
                } catch ( SQLException ignore ) {
                }
            }
            messages.add( "Fermeture de l'objet Statement." );
            if ( statement != null ) {
                try {
                    statement.close();
                } catch ( SQLException ignore ) {
                }
            }
            messages.add( "Fermeture de l'objet Connection." );
            if ( connexion != null ) {
                try {
                    connexion.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
     
        return messages;
    }
}