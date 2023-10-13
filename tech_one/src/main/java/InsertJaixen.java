
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class InsertJaixen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertJaixen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String task = request.getParameter("task");
      String difficulty = request.getParameter("difficulty").trim();

      Connection connection = null;
      
      
      
      
      /*
      // Check if 'difficulty' is a valid decimal number
      try {
          double difficultyValue = Double.parseDouble(difficulty);
      } catch (NumberFormatException e) {
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String errorMessage = "Error: 'Difficulty' must be a valid decimal number.";
          out.println(errorMessage);
          
       // Add a link to go back to the input page
          out.println("<p><a href=\"/tech_one/insert_jaixen.html\">Insert Data</a></p>");
          return; // Exit the method if the input is not valid.
      }
      */
      
      try {
    	    int difficultyValue = Integer.parseInt(difficulty);

    	    // Check if difficultyValue is in the range [0, 10]
    	    if (difficultyValue < 0 || difficultyValue > 10) {
    	        response.setContentType("text/html");
    	        PrintWriter out = response.getWriter();
    	        String errorMessage = "Error: 'Difficulty' must be an integer between 0 and 10.";
    	        out.println(errorMessage);

    	        // Add a link to go back to the input page
    	        out.println("<p><a href=\"/tech_one/insert_jaixen.html\">Insert Data</a></p>");
    	        return;
    	    }
    	} catch (NumberFormatException e) {
    	    // Input is not a valid integer.
    	    response.setContentType("text/html");
    	    PrintWriter out = response.getWriter();
    	    String errorMessage = "Error: 'Difficulty' must be a valid integer between 0 and 10.";
    	    out.println(errorMessage);

    	    // Add a link to go back to the input page
    	    out.println("<p><a href=\"/tech_one/insert_jaixen.html\">Insert Data</a></p>");
    	    return;
    	}

      
      
      
      
      
      
      
      
      String insertSql = " INSERT INTO techTable (id, TASK, DIFFICULTY) values (default, ?, ?)";

      try {
         DBConnectionJaixen.getDBConnection();
         connection = DBConnectionJaixen.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         
         preparedStmt.setString(1, task);
         preparedStmt.setString(2, difficulty);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Task</b>: " + task + "\n" + //
            "  <li><b>Difficulty</b>: " + difficulty + "\n" + //
            "</ul>\n");
      
      out.println("<a href=/tech_one/insert_jaixen.html>Insert More Data</a> <br>");
      out.println("<a href=/tech_one/search_jaixen.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
