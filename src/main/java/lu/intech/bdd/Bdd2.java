package lu.intech.bdd;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import migrate.MigrationManager;

/**
 * Servlet implementation class Bdd2
 */
public class Bdd2 extends HttpServlet {
	
	public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/jsp/step2.jsp";
 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        

    	MigrationManager migrate = new  MigrationManager();
		migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
    	migrate.migrateTo("2");
    	/* Initialisation de l'objet Java et récupération des messages */
      
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    } 
 
}
