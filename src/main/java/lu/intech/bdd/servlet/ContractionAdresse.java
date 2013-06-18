package lu.intech.bdd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lu.intech.bdd.migrate.migrate.MigrationManager;
import lu.intech.bdd.singleton.MigrationManagerSingleton;

/**
 * Servlet implementation class Bdd4
 */
public class ContractionAdresse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/jsp/step4.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	MigrationManager migrate = MigrationManagerSingleton.getInstance();
		migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
    	migrate.migrateTo("3");
    	/* Initialisation de l'objet Java et récupération des messages */
      
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
