package lu.intech.bdd.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lu.intech.bdd.migrate.migrate.MigrationManager;
import lu.intech.bdd.singleton.MigrationManagerSingleton;

/**
 * Servlet implementation class Bdd3
 */
public class ExpansionDataAdresse extends HttpServlet {
	public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/jsp/step3.jsp";
 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        MigrationManager migrate = MigrationManagerSingleton.getInstance();
        migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");

		copyDataAdresse(migrate);
		
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    private static void copyDataAdresse(MigrationManager migrate) {

    	Connection connection = null;
    	Statement statementSelect = null;
    	Statement statementInsert = null;
		ResultSet resultat = null;
		ResultSet resMax = null;
		try {
			 connection = (Connection) migrate.getConnection();
		    
	    	
	
		
			statementSelect = connection.createStatement();
	    	statementInsert = connection.createStatement();
	    	
			/* Exécution d'une requête de lecture */
			resultat = statementSelect.executeQuery( "SELECT idPersonne, adresse FROM Personne;" );


			/* Récupération des données du résultat de la requête de lecture */
			while ( resultat.next() ) {
				int idPersonne = resultat.getInt( "idPersonne");
				String adresse = resultat.getString( "adresse" );

				String[] tabAdresse = adresse.split("-");

				// REQUETE INSERTION ADRESSE
				String sqlInsertAdresse = "INSERT INTO `Adresse` (`idAdresse`, `num`, `rue`, `code`, `ville`, `pays`) VALUES (NULL, ?, ?, ?, ?, ?)";
				PreparedStatement prestInsert = ((Connection) migrate.getConnection()).prepareStatement(sqlInsertAdresse);
				
				prestInsert.setString(1, tabAdresse[0]);
				prestInsert.setString(2, tabAdresse[1]);
				prestInsert.setString(3, tabAdresse[2]);
				prestInsert.setString(4, tabAdresse[3]);
				prestInsert.setString(5, tabAdresse[4]);
				
				
				
				prestInsert.execute();

				resMax = statementInsert.executeQuery("SELECT MAX(  `idAdresse` ) AS idAdresseNext FROM Adresse");
				resMax.next();
				int idAdresseNext = resMax.getInt("idAdresseNext");

				

				// REQUETE MAJ DE LA CLE 
				String sqlUpdateKey = "UPDATE  `Personne` SET  `Adresse_idAdresse` =  ? WHERE  `Personne`.`idPersonne` = ? ;";
				PreparedStatement prestUpdate = ((Connection) migrate.getConnection()).prepareStatement(sqlUpdateKey);
				
				prestUpdate.setInt(1, idAdresseNext);
				prestUpdate.setInt(2, idPersonne);
				
				prestUpdate.execute();
					
			}

		} catch (SQLException e) {

		
		}finally{
			if ( resultat != null ) {
				try {
					resultat.close();
				} catch ( SQLException ignore ) {
				
				}
			}
			if ( resMax != null ) {
				try {
					resMax.close();
				} catch ( SQLException ignore ) {
				}
			}
			if ( statementInsert != null ) {
				try {
					statementInsert.close();
				} catch ( SQLException ignore ) {
				
				}
			}
			if ( statementSelect != null ) {
				try {
					statementSelect.close();
				} catch ( SQLException ignore ) {
				
				}
			}
			if ( connection != null ) {
				try {
					connection.close();
				} catch ( SQLException ignore ) {
				
				}
			}
			
		}
	}
}
