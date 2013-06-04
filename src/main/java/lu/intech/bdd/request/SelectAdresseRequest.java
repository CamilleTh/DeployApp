package lu.intech.bdd.request;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
 
public class SelectAdresseRequest {
    /* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();

	public List<String> requestfillOff(List<String> messages, Connection connexion) throws SQLException {
		

		
        /* Exécution d'une requête de lecture */
		Statement statement = connexion.createStatement();
        ResultSet resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, adresse FROM Personne;" );
  
        /* Récupération des données du résultat de la requête de lecture */
        while ( resultat.next() ) {
            int idPersonne = resultat.getInt( "idPersonne" );
            String prenom = resultat.getString( "prenom" );
            String nom = resultat.getString( "nom" );
            String age = resultat.getString( "age" );
            String adresse = resultat.getString( "adresse" );

            /* Formatage des données pour affichage dans la JSP finale. */
            
            messages.add( "id = " + idPersonne + ", prenom = " + prenom
                    + ", nom = "
                    + nom + ", age = " + age + ", adresse = " + adresse + "." );
        }	
        return messages;
	}
	
	public List<String> requestfillOn(List<String> messages, Connection connexion) throws SQLException {
		
		 
		/* Exécution d'une requête de lecture */
		Statement statement = connexion.createStatement();
        ResultSet resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, num, rue, code,ville, pays FROM Personne,Adresse WHERE Personne.`Adresse_idAdresse` = Adresse.`idAdresse`" );
  
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
            messages.add( "id = " + idPersonne + ", prenom = " + prenom
                    + ", nom = "
                    + nom + ", age = " + age + ", adresse = " + num  + " - " + rue + " - " + code + " - " + ville + " - " + pays +"." );
        }	
        return messages;
	}
}