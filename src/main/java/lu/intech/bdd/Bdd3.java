package lu.intech.bdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import migrate.MigrationManager;

/**
 * Servlet implementation class Bdd3
 */
public class Bdd3 extends HttpServlet {
	public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/jsp/step3.jsp";
 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        System.out.println("ENTER");

        MigrationManager migrate = new MigrationManager();
    	migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");

		copyDataAdresse(migrate);
		
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    private static void copyDataAdresse(MigrationManager migrate) {

    	Statement statementSelect = migrate.getStatement();
    	Statement statementInsert = migrate.getStatement();
    	
		/* Exécution d'une requête de lecture */
		ResultSet resultat;
		try {
			
			resultat = statementSelect.executeQuery( "SELECT idPersonne, adresse FROM Personne;" );


			/* Récupération des données du résultat de la requête de lecture */
			while ( resultat.next() ) {
				int idPersonne = resultat.getInt( "idPersonne");
				String adresse = resultat.getString( "adresse" );

				String[] tabAdresse = adresse.split("-");

				// REQUETE INSERTION ADRESE
				String sqlInsertAdresse = "INSERT INTO `Adresse` (`idAdresse`, `num`, `rue`, `code`, `ville`, `pays`) VALUES (NULL, ?, ?, ?, ?, ?)";
				PreparedStatement prestInsert = ((Connection) migrate.getConnection()).prepareStatement(sqlInsertAdresse);
				
				for(int i =0;i<5;i++){
					prestInsert.setString(i+1, tabAdresse[i]);
				}
				
				prestInsert.execute();

				ResultSet resMax = statementInsert.executeQuery("SELECT MAX(  `idAdresse` ) AS idAdresseNext FROM Adresse");
				resMax.next();
				int idAdresseNext = resMax.getInt("idAdresseNext");

				if ( resMax != null ) {
					try {
						resMax.close();
					} catch ( SQLException ignore ) {
					}
				}

				// REQUETE MAJ DE LA CLE 
				String sqlUpdateKey = "UPDATE  `Personne` SET  `Adresse_idAdresse` =  ? WHERE  `Personne`.`idPersonne` = ? ;";
				PreparedStatement prestUpdate = ((Connection) migrate.getConnection()).prepareStatement(sqlUpdateKey);
				
				prestUpdate.setInt(1, idAdresseNext);
				prestUpdate.setInt(2, idPersonne);
				
				prestUpdate.execute();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
