package lu.intech.bdd;

import java.io.IOException;
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
    	
    	Statement statement = migrate.getStatement();
		Statement statement2 = migrate.getStatement();

		copyDataAdresse(statement, statement2);
		
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    private static void copyDataAdresse(Statement statement, Statement statement2) {

		/* Exécution d'une requête de lecture */
		ResultSet resultat;
		try {
			resultat = statement.executeQuery( "SELECT idPersonne, adresse FROM Personne;" );


			/* Récupération des données du résultat de la requête de lecture */
			while ( resultat.next() ) {
				int idPersonne = resultat.getInt( "idPersonne");
				String adresse = resultat.getString( "adresse" );

				String[] tabAdresse = adresse.split("-");

				statement2.execute("INSERT INTO `Adresse` (`idAdresse`, `num`, `rue`, `code`, `ville`, `pays`) VALUES (NULL, '"+tabAdresse[0].trim()+"', '"+tabAdresse[1].trim()+"', '"+tabAdresse[2].trim()+"', '"+tabAdresse[3].trim()+"', '"+tabAdresse[4].trim()+"');");
				ResultSet resMax = statement2.executeQuery("SELECT MAX(  `idAdresse` ) AS idAdresseNext FROM Adresse");
				resMax.next();
				int idAdresseNext = resMax.getInt("idAdresseNext");

				if ( resMax != null ) {
					try {
						resMax.close();
					} catch ( SQLException ignore ) {
					}
				}

				statement2.executeUpdate("UPDATE  `Personne` SET  `Adresse_idAdresse` =  '"+idAdresseNext+"' WHERE  `Personne`.`idPersonne` ="+idPersonne+";");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
