package lu.intech.bdd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
 
public class Req2 {
    /* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();
    
    public List<String> executerTests( HttpServletRequest request ) {
        /* Chargement du driver JDBC pour MySQL */
        System.out.println("ENTER2");

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
     
            if(flipperOn(connexion)){
            	System.out.println("ON");
            	messages = requestfillOn(messages,connexion);
            }else{
            	System.out.println("OFF");
            	messages = requestfillOff(messages,connexion);
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

	private boolean flipperOn(Connection connexion) throws SQLException {
        System.out.println("1"); 

		Statement statement = connexion.createStatement();
        System.out.println("2"); 

        ResultSet resultat = statement.executeQuery( "SELECT flip FROM Flipping;" );
        System.out.println("3"); 
        resultat.next();
        System.out.println(Boolean.parseBoolean(resultat.getString( "flip" )));
        String flip = resultat.getString( "flip" );
        
        if(flip.compareTo("1")==0){
        	return true;
        }else return false;
	}

	private List<String> requestfillOff(List<String> messages, Connection connexion) throws SQLException {
		
		 messages.add("FLIP OFF ");		 

		
        /* Exécution d'une requête de lecture */
		Statement statement = connexion.createStatement();
        ResultSet resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, adresse FROM Personne;" );
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
        return messages;
	}
	
	private List<String> requestfillOn(List<String> messages, Connection connexion) throws SQLException {
		
		 messages.add("FLIP ON ");		 
		 
		/* Exécution d'une requête de lecture */
		Statement statement = connexion.createStatement();
        ResultSet resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, num, rue, code,ville, pays FROM Personne,Adresse WHERE Personne.`Adresse_idAdresse` = Adresse.`idAdresse`" );
        messages.add( "Requête \"SELECT idPersonne, prenom, nom, age, num, rue, code,ville, pays FROM Personne,Adresse WHERE Personne.`Adresse_idAdresse` = Adresse.`idAdresse`;\" effectuée !" );
  
        /* Récupération des données du résultat de la requête de lecture */
        while ( resultat.next() ) {
            int idPersonne = resultat.getInt( "idPersonne" );
            String prenom = resultat.getString( "prenom" );
            String nom = resultat.getString( "nom" );
            String age = resultat.getString( "age" );
            String num = resultat.getString( "num" );
            String rue = resultat.getString( "rue" );
            String code = resultat.getString( "code" );
            String ville = resultat.getString( "ville" );
            String pays = resultat.getString( "pays" );

            /* Formatage des données pour affichage dans la JSP finale. */
            messages.add( "Données retournées par la requête : id = " + idPersonne + ", prenom = " + prenom
                    + ", nom = "
                    + nom + ", age = " + age + ", adresse = " + num  + " - " + rue + " - " + code + " - " + ville + " - " + pays +"." );
        }	
        return messages;
	}
}