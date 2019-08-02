/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.DateFormat;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author KHSCI5MCA17025
 */
public class usr extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.InterruptedException
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet usr</title>");            
            out.println("</head>");
            out.println("<body>");
            String rn = request.getParameter("no");
            String at = request.getParameter("at");
            String dt = request.getParameter("dt");
            String vt = request.getParameter("vt");
            //Date now = new Date();
            String query;
            
            HttpSession session = request.getSession();
            session.getAttribute("usr");
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    query = "SELECT * FROM park WHERE rn IS NULL;";
                    Statement stmt = con.createStatement();
                    PreparedStatement ps;
                    //stmt.executeQuery(query);
                    ResultSet rs = stmt.executeQuery(query);
                    //CREATE TABLE park(pid integer AUTO_INCREMENT PRIMARY KEY NOT NULL,rn varchar(30),vt varchar(15) NOT NULL,sno integer(10));
                    if(rs.next()== false)
                    {
                        out.println("<h3>OOPSS!!! SORRY NO SLOTS ARE AVAILABLE!!</h3>");
                        request.getRequestDispatcher("success.html").forward(request, response);
                    }
                    else
                    {
                        query = "SELECT vt FROM park WHERE rn IS NULL;";
                        rs = stmt.executeQuery(query);
                        if(rs.getString("vt").equals(vt))
                        {
                            query = "INSERT INTO `park`(`rn`) VALUES (?);";
                            ps = con.prepareStatement(query);
                            ps.setString(1,rn);
                            ps.executeUpdate();
                            ps.close();
                            out.println("<h3>successfully booked your slot!!</h3>");
                            out.println("<h3>your parking ID is</h3>"+rs.getInt("pid"));
                            out.println("<h3>your slot number is</h3>"+rs.getInt("sno"));
                            request.getRequestDispatcher("success.html").forward(request, response);
                            /*DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                            Date a = dateFormat.parse(at);
                            Date d = dateFormat.parse(dt);
                            Thread.sleep(60*2);
                            request.getRequestDispatcher("login.html").forward(request, response);
                            out.println("<h3>session expired login again and book</h3>");*/
                        }
                        else
                        {
                            out.println("<h3>OOPSS!!! SORRY NO SLOTS ARE AVAILABLE!!</h3>");
                            request.getRequestDispatcher("success.html").forward(request, response);
                        }
                    }
                }
            } 
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)
            {
                out.println(e);
            }
            //out.println("<h1>Servlet usr at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException | ParseException ex) {
            Logger.getLogger(usr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException | ParseException ex) {
            Logger.getLogger(usr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
