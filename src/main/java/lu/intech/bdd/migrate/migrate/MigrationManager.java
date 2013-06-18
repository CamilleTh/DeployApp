package lu.intech.bdd.migrate.migrate;

import lu.intech.bdd.migrate.databaseManager.DatabaseManager;
import lu.intech.bdd.migrate.databaseManager.databaseManagerImpl.DatabaseManagerSQL;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationVersion;

public class MigrationManager {

	DatabaseManager databaseManager;
	Flyway flyway;

	public MigrationManager() {
		this.flyway = new Flyway();
		
	}
	
	// Migration vers une version X
	public void migrateTo(String version){
		
		if(version.compareTo("1")==0) flyway.clean();
		flyway.setTarget(new MigrationVersion(version));
		flyway.migrate();
		
		

	}
	
	
	public void initAndReset(){
		
		flyway.clean();
		flyway.init();
		databaseManager.initDirectory(); // creation du dossier de fichier de migration SQL
		databaseManager.initFlipTable(); // creation de la table pour gérer le flipping
		
	}

	
	public void init(){
		databaseManager.initDirectory(); // creation du dossier de fichier de migration SQL
		databaseManager.initFlipTable(); // creation de la table pour gérer le flipping
	}


	
	/**
	 * Creation d'un boolean de flipping
	 * @param name
	 * @return
	 */
	public boolean createFlipBoolean(String name){
		return databaseManager.createFlipBoolean(name);
	}
	
	/**
	 *  Flippng d'un boolean
	 * @param name
	 * @return
	 */
	public boolean flipping(String name){
		return databaseManager.flipping(name);
	}
	
	/**
	 *  Connection à la base de données
	 * @param url
	 * @param user
	 * @param password
	 */
	public void setDataSourceSQL(String url, String user, String password) {
		
		flyway.setDataSource(url,user,password);
		databaseManager = new DatabaseManagerSQL(url, user, password);
		
	}
		
	public Object getConnection(){
		
		if (databaseManager instanceof DatabaseManagerSQL)
			return ((DatabaseManagerSQL) databaseManager).getConnection();
		return null;
		
	}

	}
