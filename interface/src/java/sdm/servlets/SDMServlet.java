package sdm.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sdm.model.Bribery;
import sdm.model.Lobbyist;
import sdm.model.Politican;

public class SDMServlet extends HttpServlet {

  private enum GetAction {
    getPoliticans, getPoliticanById, getPoliticansWithMoney, getLobbyists
  };

  private enum PostAction {
    postPolitican, postBribery
  };

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String actionString = request.getParameter("action");
    GetAction action = null;
    try {
      action = GetAction.valueOf(actionString);
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("Specify GET action parameter! Possible: " + Arrays.asList(GetAction.values()));
    }

    switch (action) {
      case getPoliticans:
        getPoliticans(response);
        break;
      case getPoliticanById:
        getPoliticanById(request, response);
        break;
      case getPoliticansWithMoney:
        getPoliticansWithMoney(response);
        break;
      case getLobbyists:
        getLobbyists(response);
        break;
    }

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String actionString = request.getParameter("action");
    PostAction action = null;
    try {
      action = PostAction.valueOf(actionString);
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("Specify POST action parameter! Possible: " + Arrays.asList(PostAction.values()));
    }

    switch (action) {
      case postPolitican:
        postPolitican(request, response);
        break;
      case postBribery:
        postBribery(request, response);
        break;
    }
  }

  private void getPoliticanById(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    int politicanId = Integer.parseInt(request.getParameter("id"));

    try (PrintWriter pw = response.getWriter()) {
      connection = getDBConnection();
      stmt = connection.prepareStatement("select Id, Forename, Surname, Party, DOB from politician where Id = ?");
      stmt.setInt(1, politicanId);
      stmt.execute();

      rs = stmt.getResultSet();
      Politican p = null;
      while (rs.next()) {
        int id = rs.getInt(1);
        String forename = rs.getString(2);
        String surname = rs.getString(3);
        String party = rs.getString(4);
        Date dob = rs.getDate(5);
        p = new Politican(id, forename, surname, party, dob, 0);
      }
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      String json = gson.toJson(p);
      pw.print(json);
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }
  }

  private void getPoliticansWithMoney(HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    try (PrintWriter pw = response.getWriter()) {
      connection = getDBConnection();
      stmt = connection.createStatement();
      String sql = "select p.Id, p.Forename, p.Surname, p.Party, p.DOB, coalesce(SUM(b.Value),0) "
              + " from politician p                                                              "
              + " left outer join bribery b on p.id = b.FK_Politician                            "
              + " GROUP BY p.Id                                                                  ";
      stmt.execute(sql);

      rs = stmt.getResultSet();
      List<Politican> politicans = new ArrayList<>();
      int value;
      while (rs.next()) {
        int id = rs.getInt(1);
        String forename = rs.getString(2);
        String surname = rs.getString(3);
        String party = rs.getString(4);
        Date dob = rs.getDate(5);
        value = rs.getInt(6);
        Politican p = new Politican(id, forename, surname, party, dob, value);
        politicans.add(p);
      }
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      String json = gson.toJson(politicans);

      pw.print(json);
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }
  }

  private void getPoliticans(HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    try (PrintWriter pw = response.getWriter()) {
      connection = getDBConnection();
      stmt = connection.createStatement();
      stmt.execute("select Id, Forename, Surname, Party, DOB from politician");

      rs = stmt.getResultSet();
      List<Politican> politicans = new ArrayList<>();
      while (rs.next()) {
        int id = rs.getInt(1);
        String forename = rs.getString(2);
        String surname = rs.getString(3);
        String party = rs.getString(4);
        Date dob = rs.getDate(5);
        Politican p = new Politican(id, forename, surname, party, dob, 0);
        politicans.add(p);
      }
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      String json = gson.toJson(politicans);
      pw.print(json);
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }
  }

  private void postPolitican(HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuilder jb = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
      jb.append(line);
    }

    Gson gson = new Gson();
    Politican p = gson.fromJson(jb.toString(), Politican.class);

    Connection connection = null;
    PreparedStatement ps = null;
    String sql;
    if (p.getId() <= 0) {
      sql = "insert into politician"
              + " (Forename, Surname, Party, DOB) VALUES"
              + " (?, ?, ?, ?)";
    } else {
      sql = "update politician"
              + " set Forename = ?, Surname = ?, Party = ?, DOB = ?"
              + " where id = ?";
    }

    try {
      connection = getDBConnection();
      ps = connection.prepareStatement(sql);
      ps.setString(1, p.getForename());
      ps.setString(2, p.getSurname());
      ps.setString(3, p.getParty());
      ps.setDate(4, new java.sql.Date(p.getDob().getTime()));
      if (p.getId() > 0) {
        ps.setInt(5, p.getId());
      }
      ps.executeUpdate();
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }

  }

  private void postBribery(HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuilder jb = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
      jb.append(line);
    }

    Gson gson = new Gson();
    Bribery b = gson.fromJson(jb.toString(), Bribery.class);

    Connection connection = null;
    PreparedStatement ps = null;
    String sql = "insert into bribery"
            + " (FK_Politician, FK_Lobbyist, Value, Reason, Confirmed) VALUES"
            + " (?, ?, ?, ?, ?)";

    try {
      connection = getDBConnection();
      ps = connection.prepareStatement(sql);
      ps.setInt(1, b.getFkPolitician());
      ps.setInt(2, b.getFkLobbyist());
      ps.setInt(3, b.getValue());
      ps.setString(4, b.getReason());
      ps.setBoolean(5, b.isConfirmed());
      ps.executeUpdate();
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (ps != null) {
          ps.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }

  }

  private static Connection getDBConnection() throws SQLException {
    Connection connection = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/sdm", "sdm", "sdm");
    } catch (ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }

    return connection;
  }

  public class politicianAndCash {

    String g;
    int value;

    public politicianAndCash(Politican p, int v) {
      this.value = v;

      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      this.g = gson.toJson(p);
    }
  }

  private void getLobbyists(HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    try (PrintWriter pw = response.getWriter()) {
      connection = getDBConnection();
      stmt = connection.createStatement();
      stmt.execute("select Id, Forename, Surname, Company, DOB from lobbyist");

      rs = stmt.getResultSet();
      List<Lobbyist> lobbyists = new ArrayList<>();
      while (rs.next()) {
        int id = rs.getInt(1);
        String forename = rs.getString(2);
        String surname = rs.getString(3);
        String company = rs.getString(4);
        Date dob = rs.getDate(5);
        Lobbyist l = new Lobbyist(id, forename, surname, company, dob);
        lobbyists.add(l);
      }
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
      String json = gson.toJson(lobbyists);
      pw.print(json);
    } catch (SQLException ex) {
      throw new ServerException("SQL", ex);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException e) {
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
      }
    }
  }

}
