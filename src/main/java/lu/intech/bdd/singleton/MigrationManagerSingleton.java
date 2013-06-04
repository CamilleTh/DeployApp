package lu.intech.bdd.singleton;

import migrate.MigrationManager;

public class MigrationManagerSingleton {
        
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