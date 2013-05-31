package lu.intech.databaseMigration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import migrate.MigrationManager;

/**
 * Hello world!
 *
 */
public class App 
{

	private static MigrationManager migrate;


	public static void main( String[] args )
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Saisir \"Entrée\" pour démarrer");
		sc.nextLine();

		phase1();
		
		System.out.println("Phase 1 OK : Initialisation");
		
		System.out.println("Saisir \"Entrée\" pour démarrer");
		sc.nextLine();

		phase2();	

		System.out.println("Phase 2 OK : Transition");
		
		System.out.println("Saisir \"Entrée\" pour commencer la phase de duplication des données");
		sc.nextLine();

		phase3();

		System.out.println("Données copiées");
		System.out.println("Saisir \"Entrée\" pour commencer la phase 3");
		sc.nextLine();

		phase4();

		System.out.println("Données copiées");	

	}
	
	private static void phase1() {
		
		// Creation de l'objet
		migrate = new  MigrationManager();
		System.out.println("Objet Migrate instancié");

		// Lien avec la base et connection ..
		System.out.print("Connection à la base de données...");
		migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
		System.out.println(" OK");

		// Creation du répertoire pour déposer ces fichiers de migration, creation de la table pour la gestion du flipping
		migrate.init();
		System.out.println("Initialisation");
		System.out.println("Création de la table de gestion du flipping");

		// Initialisation de la base de données, creation du répertoire pour déposer ces fichiers de migration, creation de la table pour la gestion du flipping
		//m.initAndReset();
		//System.out.println("Initialisation et reset de la base");

		// Creation d'un boolean de flipping : test
		migrate.createFlipBoolean("test");
		System.out.println("Creation du boolean de flipping ");

		// migration vers la V1 initialisation
		migrate.migrateTo("1");

	}


	private static void phase2() {
		
		migrate.migrateTo("2");
		
	}

	private static void phase3() {
		
		Statement statement = migrate.getStatement();
		Statement statement2 = migrate.getStatement();

		copyDataAdresse(statement, statement2);

	}
	
	private static void phase4() {
		
		migrate.migrateTo("3");
		
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
