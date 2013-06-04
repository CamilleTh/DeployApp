package lu.intech.bdd.request;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
 
 
public class SelectAdresseRequest {
    /* La liste qui contiendra tous les résultats de nos essais */

	public List<String> requestfillOff(List<String> messages, Connection connexion) {
		
		Statement statement = null;
		ResultSet resultat = null;
		try {
			/* Exécution d'une requête de lecture */
			statement = connexion.createStatement();
	        resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, adresse FROM Personne;" );
	  
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
		} catch (SQLException e) {
			// TODO: handle exception
		}finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {

				}
			}
			if(resultat != null){
				try {
					resultat.close();
				} catch (SQLException e) {

				}
			}
		}
        
        return messages;
	}
	
	public List<String> requestfillOn(List<String> messages, Connection connexion)  {
		
		 
		/* Exécution d'une requête de lecture */
		Statement statement = null;
        ResultSet resultat = null;
        
        try {
        	
        	statement = connexion.createStatement();
        	resultat = statement.executeQuery( "SELECT idPersonne, prenom, nom, age, num, rue, code,ville, pays FROM Personne,Adresse WHERE Personne.`Adresse_idAdresse` = Adresse.`idAdresse`" );
        	  
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
		} catch (SQLException e) {
			// TODO: handle exception
		}finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {

				}
			}
			if(resultat != null){
				try {
					resultat.close();
				} catch (SQLException e) {

				}
			}
		}
        
       
        return messages;
	}
}