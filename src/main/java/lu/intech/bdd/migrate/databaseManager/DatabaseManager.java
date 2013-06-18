package lu.intech.bdd.migrate.databaseManager;


public abstract class DatabaseManager {
	public abstract boolean createFlipBoolean(String name);

	
	public abstract boolean initDirectory();

	public abstract boolean flipping(String name);
	
	public abstract void initFlipTable();

}
