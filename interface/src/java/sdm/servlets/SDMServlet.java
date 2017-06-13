package sdm.servlets;

import java.io.*;

/**
 * Die Beschreibung des Typs hier eingeben. Erstellungsdatum: (17.03.2004
 * 13:44:17)
 *
 * @author:
 */
public class SDMServlet extends javax.servlet.http.HttpServlet {

  private java.sql.Connection connection_;

  /**
   * Process incoming HTTP GET requests
   *
   * @param request Object that encapsulates the request to the servlet
   * @param response Object that encapsulates the response from the servlet
   */
  public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    String action = request.getParameter("action");
    if (action == null || action.trim().length() == 0) {
      return; //No Action specified
    }
    PrintWriter pw = null;
    try {
      pw = response.getWriter();
      if (action.equalsIgnoreCase("libstring.lib")) {
        try {
          java.sql.Statement stmt = connection_.createStatement();
          stmt.execute("SELECT matnr,lastname,firstname FROM students;");
          java.sql.ResultSet rs = stmt.getResultSet();
          if (rs.first()) {
            do {
              String matnr = rs.getString(1);
              String lastname = rs.getString(2);
              String firstname = rs.getString(3);
              pw.println(matnr + "," + lastname + " " + firstname);
            } while (rs.next());
          }
          pw.flush();
        } catch (java.sql.SQLException sqle) {
          System.err.println("Die Query konnte nicht durchgeführt werden.");
          sqle.printStackTrace();
        }
      } else {
        try {
          if (action.endsWith(".lib")) {
            action = action.substring(0, action.length() - 4);
          }

          java.sql.Statement stmt = connection_.createStatement();
          stmt.execute("SELECT lastname,firstname FROM students WHERE matnr = '" + action + "';");
          java.sql.ResultSet rs = stmt.getResultSet();
          String result = action + ",";
          if (rs.first()) {
            result += rs.getString("lastname") + " " + rs.getString("firstname");
            stmt.execute("SELECT lvnr FROM courses;");
            rs = stmt.getResultSet();
            if (rs.first()) {
              do {
                result += ",";
                java.sql.Statement stmt2 = connection_.createStatement();
                stmt2.execute("SELECT mark FROM marks WHERE matnr = '" + action + "' AND lvnr = '" + rs.getString(1) + "';");
                java.sql.ResultSet rs2 = stmt2.getResultSet();
                if (rs2.first()) {
                  result += rs2.getString("mark");
                }
              } while (rs.next());
            }
          }
          pw.println(result);
        } catch (java.sql.SQLException sqle) {
          System.err.println("Die Query konnte nicht durchgeführt werden.");
          sqle.printStackTrace();
        }
      }
    } finally {
      try {
        pw.close();
      } catch (Exception e) {
      }
    }
  }

  /**
   * Initializes the servlet.
   */
  public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection_ = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/datamanagement", "student", "student");
    } catch (Exception e) {
      throw new javax.servlet.ServletException(e);
    }
  }
}