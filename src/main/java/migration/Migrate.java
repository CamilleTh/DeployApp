package migration;

import java.awt.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	    	System.out.println("DATA COPY ADRESSE");
	    	datacopyAdresse();
	    }
	    else if (Integer.parseInt(version)==4){
	    	System.out.println("DATA COPY VILLE");
	    	datacopyVille();
	    }
	    else if (Integer.parseInt(version)==6){
	    	System.out.println("DATA COPY ROLE");
	    	datacopyRole();
	    }
	    this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/migrate.jsp" ).forward( request, response );
	}

	private void datacopyVille() {
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
	        Statement statement3 = null;
	        Statement statement4 = null;
	        Statement statement5 = null;
	        
	        ResultSet resultat = null;
	        try {
	            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
	     
	            /* Création de l'objet gérant les requêtes */
	            statement = connexion.createStatement();
	            statement2 = connexion.createStatement();
	            statement3 = connexion.createStatement();
	            statement4 = connexion.createStatement();
	            statement5 = connexion.createStatement();	            
	            
	            /* Exécution d'une requête de lecture */
	            resultat = statement.executeQuery( "SELECT idAdresse, code, ville FROM Adresse;" );
	            
	            Set<ArrayList> villeSet = new HashSet<ArrayList>();
	            ArrayList< ArrayList<String>> AdresseToVille = new ArrayList<ArrayList<String>>();
	            
	            /* Récupération des données du résultat de la requête de lecture */
	            while ( resultat.next() ) {
	                String idAdresse = resultat.getString( "idAdresse");
	                String code = resultat.getString( "code" );
	                String ville = resultat.getString( "ville" );
	                
	                ArrayList<String> villeInfo = new ArrayList<String>();

	                villeInfo.add(ville);
	                villeInfo.add(code);
	                
	                System.out.println("SELECT COUNT(*) AS exist FROM Ville WHERE nom = '"+ville+"'; ");
	                ResultSet resultat4 = statement4.executeQuery( "SELECT COUNT(*) AS exist FROM Ville WHERE nom = '"+ville+"'; " );
	                resultat4.next();
	                
	                int exist = resultat4.getInt("exist");
	                
	                System.out.println("exist:"+exist);
	                
	                if(exist == 0 ) villeSet.add(villeInfo);
	                
	                ArrayList<String> villeLink = new ArrayList<String>();
	                villeLink.add(idAdresse);
	                villeLink.add(ville);

	                AdresseToVille.add(villeLink);
	            }
	            
	            System.out.println("VilleSet:"+villeSet);
	            System.out.println("AdresseToVille:"+AdresseToVille);
	            
	            
	            for(ArrayList villeInfo : villeSet){
	            	 statement2.execute("INSERT INTO `Ville` ( `nom`, `code`) VALUES ( '"+((String) villeInfo.get(0)).trim()+"', '"+((String) villeInfo.get(1))+"');");
	            }
	            
	            System.out.println("OK 1");
	            ResultSet resultat2;
	            for(ArrayList villeLink : AdresseToVille){
		            System.out.println("OK 2");
		           System.out.println("SELECT idVille FROM Ville WHERE nom = '"+villeLink.get(1)+"';");
		            resultat2  = statement3.executeQuery( "SELECT idVille FROM Ville WHERE nom = '"+villeLink.get(1)+"';" );
	 	            System.out.println("OK 3");

	            	 resultat2.next();
		             String idVille = resultat2.getString( "idVille" );
	            	 statement5.execute("UPDATE `Adresse` SET `Ville_idVille` = '"+idVille+"' WHERE `idAdresse` = '"+villeLink.get(0)+"' ");
	 	            System.out.println("OK 4");

	            	 
	            }
	            
	            System.out.println("OK 5");

	            
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

	private void datacopyRole() {
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
	        Statement statement3 = null;
	        Statement statement4 = null;
	        Statement statement5 = null;
	        
	        ResultSet resultat = null;
	        try {
	            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
	     
	            /* Création de l'objet gérant les requêtes */
	            statement = connexion.createStatement();
	            statement2 = connexion.createStatement();
	            statement3 = connexion.createStatement();
	            statement4 = connexion.createStatement();
	            statement5 = connexion.createStatement();	            
	            
	            /* Exécution d'une requête de lecture */
	            resultat = statement.executeQuery( "SELECT idPersonne, role FROM Personne;" );	            
	            
	            /* Récupération des données du résultat de la requête de lecture */
	            while ( resultat.next() ) {
	                String role = resultat.getString( "role");
	                String idPersonne = resultat.getString( "idPersonne");
	                // AJOUT DU NEW ROLE
		                System.out.println("SELECT COUNT(*) AS exist FROM Role WHERE nom = '"+role+"'; ");
		                ResultSet resultat4 = statement4.executeQuery( "SELECT COUNT(*) AS exist FROM Role WHERE nom = '"+role+"';" );
		                resultat4.next();
		                
		                int exist = resultat4.getInt("exist");
		                
		                System.out.println("exist:"+exist);
		                
		                if(exist == 0 ) statement4.execute( "INSERT INTO Role (nom) VALUES ('"+role+"')" ); 
		            // FIN AJOUT DU NEW ROLE   
		                
		            // AJOUT LIAISON
		                ResultSet resultat5 = statement4.executeQuery("SELECT idRole FROM Role WHERE nom='"+role+"'" );
		                resultat5.next();
		                String idRole = resultat5.getString("idRole");
		                
		                statement4.execute( "INSERT IGNORE INTO `Role_has_Personne` (`Role_idRole`, `Personne_idPersonne`) VALUES ('"+idRole+"', '"+idPersonne+"');" ); 
			        // FIN AJOUT LIAISON
  
	            }

	            System.out.println("OK 5");

	            
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

	private void datacopyAdresse() {
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
	                statement2.execute("INSERT INTO `Adresse` (`idAdresse`, `num`, `rue`, `code`, `ville`, `pays`) VALUES (NULL, '"+tabAdresse[0].trim()+"', '"+tabAdresse[1].trim()+"', '"+tabAdresse[2].trim()+"', '"+tabAdresse[3].trim()+"', '"+tabAdresse[4].trim()+"');");
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
