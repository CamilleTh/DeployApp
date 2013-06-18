package lu.intech.bdd.migrate.databaseManager.databaseManagerImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import lu.intech.bdd.migrate.databaseManager.DatabaseManager;

public class DatabaseManagerSQL extends DatabaseManager {


	private String url, utilisateur, password;


	public String getUtilisateur() {
		return utilisateur;
	}
	public String getUrl() {
		return url;
	}
	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		
		Connection connection = null;
		try { 
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			System.out.println( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
					+ e.getMessage() );
		}

		try {

		connection = DriverManager.getConnection( url, utilisateur, password );
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return connection;
	}

	public DatabaseManagerSQL(String url, String utilisateur, String password) {
		this.url=url;
		this.utilisateur=utilisateur;
		this.password=password;
	}




	public void initFlipTable(){
		
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			statement.execute( "CREATE  TABLE IF NOT EXISTS `Feature` ( `idFeature` INT NOT NULL AUTO_INCREMENT ,  `description` VARCHAR(255) NULL ,  `name`  VARCHAR(255)  NULL , `value` BOOLEAN DEFAULT 0, PRIMARY KEY (`idFeature`) )"); 
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public boolean createFlipBoolean(String name){

		boolean success = false; 

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			success = statement.execute( "INSERT  INTO `Feature` (`name`, `value`) VALUES ('"+name+"', '0');" ); 
			
			statement.close();
			connection.close();
			
			// FIN AJOUT LIAISON
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;

	}


	public boolean flipping(String name){

		boolean success = false; 

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			success = statement.execute("UPDATE `Feature` SET `value` = !`value` WHERE `name` = '"+name+"'");
			
			statement.close();
			connection.close();
			// FIN AJOUT LIAISON
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean initDirectory(){
		return (new File("src/main/resources/db/migration")).mkdirs();
	}


}


