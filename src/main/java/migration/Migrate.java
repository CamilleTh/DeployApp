package migration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationVersion;

/**
 * Servlet implementation class Migrate
 */
public class Migrate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Migrate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  // Create the Flyway instance
        String version = request.getParameter("V");
		System.out.println("Version : "+version);
		
		Flyway flyway = new Flyway();
        // Point it to the database
        flyway.setDataSource("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
        
        if (Integer.parseInt(version)==1){
        	System.out.println("CLEAN");
        	flyway.clean();
        }
        
        
	    flyway.setTarget(new MigrationVersion(version));
	    
	    // Start the migration
	    flyway.migrate();
	    
	    // Duplicate adresse data
	    if (Integer.parseInt(version)==2){
	    	System.out.println("DATA COPY");
	    	datacopy();
	    }
	    
	    this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/migrate.jsp" ).forward( request, response );
	}

	private void datacopy() {
		 try {
	          
	            Class.forName( "com.mysql.jdbc.Driver" );
	        } catch ( ClassNotFoundException e ) {
	            System.out.println( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
	                    + e.getMessage() );
	        }
	     
	        /* Connexion à la base de données */
	        String url = "jdbc:mysql://mysql1.alwaysdata.com/40853_intech";
	        String utilisateur = "40853_2";
	        String motDePasse = "intech";
	        Connection connexion = null;
	        Statement statement = null;
	        Statement statement2 = null;
	        ResultSet resultat = null;
	        try {
	            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
	     
	            /* Création de l'objet gérant les requêtes */
	            statement = connexion.createStatement();
	            statement2 = connexion.createStatement();
	            /* Exécution d'une requête de lecture */
	            resultat = statement.executeQuery( "SELECT idPersonne, adresse FROM Personne;" );
	            
	            /* Récupération des données du résultat de la requête de lecture */
	            while ( resultat.next() ) {
	                int idPersonne = resultat.getInt( "idPersonne");
	                String adresse = resultat.getString( "adresse" );

	                String[] tabAdresse = adresse.split("-");
	                for(String s : tabAdresse) System.out.print(s + "/");
	                System.out.println(); 
	                
	                System.out.println("1"); 
	                statement2.execute("INSERT INTO `Adresse` (`idAdresse`, `num`, `rue`, `code`, `ville`, `pays`) VALUES (NULL, '"+tabAdresse[0]+"', '"+tabAdresse[1]+"', '"+tabAdresse[2]+"', '"+tabAdresse[3]+"', '"+tabAdresse[4]+"');");
	                System.out.println("2");
	                ResultSet resMax = statement2.executeQuery("SELECT MAX(  `idAdresse` ) AS idAdresseNext FROM Adresse");
	                System.out.println("3");
	                resMax.next();
	                int idAdresseNext = resMax.getInt("idAdresseNext");
	                
	                if ( resMax != null ) {
		                try {
		                	resMax.close();
		                } catch ( SQLException ignore ) {
		                }
		            }
	                
	                statement2.executeUpdate("UPDATE  `Personne` SET  `Adresse_idAdresse` =  '"+idAdresseNext+"' WHERE  `Personne`.`idPersonne` ="+idPersonne+";");
	                System.out.println("4"); 
	            }
	            
	        } catch ( SQLException e ) {
	        	System.out.println(e);
	        	System.out.println("message :"+ e.getMessage());
	        } finally {
	            if ( resultat != null ) {
	              /* try {
	                    resultat.close();
	                } catch ( SQLException ignore ) {
	                }*/
	            }
	            if ( statement != null ) {
	                try {
	                    statement.close();
	                } catch ( SQLException ignore ) {
	                }
	            }
	            if ( connexion != null ) {
	                try {
	                    connexion.close();
	                } catch ( SQLException ignore ) {
	                }
	            }
	        }
	        
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
