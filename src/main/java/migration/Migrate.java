package migration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationVersion;

/**
 * Servlet implementation class Migrate
 */
public class Migrate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Migrate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  // Create the Flyway instance
        String version = null;
		request.setAttribute("V", version);
		System.out.println("Version : "+version);
			
			Flyway flyway = new Flyway();
	        // Point it to the database
	        flyway.setDataSource("jdbc:mysql://mysql1.alwaysdata.com/40853_intech", "40853_2", "intech");
	        flyway.clean();
	        
	        flyway.setTarget(new MigrationVersion(version));
	        // Start the migration
	        flyway.migrate();
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
