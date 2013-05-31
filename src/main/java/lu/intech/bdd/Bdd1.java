package lu.intech.bdd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import migrate.MigrationManager;

public class Bdd1 extends HttpServlet {
	public static final String ATT_MESSAGES = "messages";
	public static final String VUE          = "/WEB-INF/jsp/step1.jsp";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		/* Initialisation de l'objet Java et récupération des messages */
		 List<String> messages = new ArrayList<String>();
		 
		 
		// Creation de l'objet
		MigrationManager migrate = new  MigrationManager();
		messages.add("Objet Migrate instancié");

		// Lien avec la base et connexion ...
		messages.add("Lié de la base avec le databaseManager");
		migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");

		// Creation du répertoire pour déposer ces fichiers de migration, creation de la table pour la gestion du flipping
		migrate.init();
		messages.add("Initialisation");
		messages.add("Création de la table de gestion du flipping");

		// Initialisation de la base de données, creation du répertoire pour déposer ces fichiers de migration, creation de la table pour la gestion du flipping
		//m.initAndReset();
		//System.out.println("Initialisation et reset de la base");

		// Creation d'un boolean de flipping : test
		migrate.createFlipBoolean("test");
		messages.add("Creation du boolean de flipping ");

		// migration vers la V1 initialisation
		migrate.migrateTo("1");
		
		Req1 test = new Req1();
		messages.addAll(test.executerTests( request ));

		/* Enregistrement de la liste des messages dans l'objet requête */
		 request.setAttribute( ATT_MESSAGES, messages );


		/* Transmission vers la page en charge de l'affichage des résultats */
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}
