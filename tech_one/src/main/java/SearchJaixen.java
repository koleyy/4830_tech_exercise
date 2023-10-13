import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchJaixen")
public class SearchJaixen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchJaixen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionJaixen.getDBConnection();
         connection = DBConnectionJaixen.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM techTable ORDER BY CAST(difficulty AS DECIMAL) ASC";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM techTable WHERE DIFFICULTY LIKE ? ORDER BY CAST(difficulty AS DECIMAL) ASC";
            String difficulty = keyword;
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, keyword);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String task = rs.getString("task").trim();
            String difficulty = rs.getString("difficulty").trim();

            if (keyword.isEmpty() || difficulty.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Difficulty: " + difficulty + ", ");
               out.println(task);
               out.println("<br>");
            }
         }
         out.println("<a href=/tech_one/search_jaixen.html>Search Data</a> <br>");
         out.println("<a href=/tech_one/insert_jaixen.html>Insert Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
