package lu.intech.bdd.singleton;

import lu.intech.bdd.migrate.migrate.MigrationManager;

public final class MigrationManagerSingleton {
        
		private static volatile MigrationManager instance = null;
 
        private MigrationManagerSingleton(){
        	
        }
 
        public static MigrationManager getInstance() {
                if (instance == null) {
                        synchronized (MigrationManagerSingleton .class){
                                if (instance == null) {
                                        instance = new MigrationManager();
                                }
                      }
                }
                return instance;
        }
}