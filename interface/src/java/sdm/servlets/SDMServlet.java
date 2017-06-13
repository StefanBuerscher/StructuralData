package sdm.servlets;

import com.google.gson.Gson;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sdm.model.Politican;

/**
 * Die Beschreibung des Typs hier eingeben. Erstellungsdatum: (17.03.2004
 * 13:44:17)
 *
 * @author:
 */
public class SDMServlet extends HttpServlet {

  private enum Action {
    getPoliticans, postPolitican
  };

  private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private java.sql.Connection connection_;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String actionString = request.getParameter("action");
    Action action = null;
    try {
      action = Action.valueOf(actionString);
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("Specify action parameter! Possible: " + Arrays.asList(Action.values()));
    }

    switch (action) {
      case getPoliticans:
        getPoliticans(response);
        break;
      case postPolitican:
        break;
    }

  }

  private void getPoliticans(HttpServletResponse response) throws IOException {
    response.setContentType("application/json");

    try (PrintWriter pw = response.getWriter()) {
      java.sql.Statement stmt = connection_.createStatement();
      stmt.execute("select Id, Forename, Surname, Party, DOB from politician");

      java.sql.ResultSet rs = stmt.getResultSet();
      List<Politican> politicans = new ArrayList<>();
      while (rs.next()) {
        int id = rs.getInt(1);
        String forename = rs.getString(2);
        String surname = rs.getString(3);
        String party = rs.getString(4);
        Date dob = rs.getDate(5);
        Politican p = new Politican(id, forename, surname, party, dob);
        politicans.add(p);
      }
      Gson gson = new Gson();
      String json = gson.toJson(politicans);
      pw.print(json);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Initializes the servlet.
   *
   * @param config
   * @throws javax.servlet.ServletException
   */
  @Override
  public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection_ = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/sdm", "sdm", "sdm");
    } catch (ClassNotFoundException | SQLException e) {
      throw new javax.servlet.ServletException(e);
    }
  }
}
