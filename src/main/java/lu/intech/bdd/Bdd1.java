package lu.intech.bdd;
 
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import lu.intech.bdd.Req1;
 
public class Bdd1 extends HttpServlet {
    public static final String ATT_MESSAGES = "messages";
    public static final String VUE          = "/WEB-INF/jsp/show.jsp";
 
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        System.out.println("ENTER");

    	/* Initialisation de l'objet Java et récupération des messages */
        Req1 test = new Req1();
        List<String> messages = test.executerTests( request );
 
        /* Enregistrement de la liste des messages dans l'objet requête */
        request.setAttribute( ATT_MESSAGES, messages );
 
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}