package lu.intech.bdd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import migrate.MigrationManager;

/**
 * Servlet implementation class Flip1
 */
public class Flip1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Flip1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Req2 req2 = new Req2();
		

		
		MigrationManager migrate = new  MigrationManager();
		migrate.setDataSourceSQL("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
		Connection connexion = (Connection) migrate.getConnection();
		ArrayList<String> messages = new ArrayList<String>();
		
		PrintWriter out = response.getWriter();
		
		out.write("<h3>Depuis l'attribut adresse de la table Personne</h3>");
		
		try {
			for(String s : req2.requestfillOff(messages, connexion)){
				out.write(s);
				out.write("<br>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
