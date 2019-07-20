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
import java.util.Date;
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            Date now = new Date();
            String query;
            
            HttpSession session = request.getSession();
            session.getAttribute("usr");
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) {
                    query = "SELECT * FROM park;";
                    Statement stmt = con.createStatement();
                    PreparedStatement ps;
                    //stmt.executeQuery(query);
                    ResultSet rs = stmt.executeQuery(query);
                    //CREATE TABLE park(pid number AUTOINCREMENT=100 PRIMARY KEY NOT NULL,rn varchar(30),vt varchar(15) NOT NULL,sno integer(10) AUTOINCREMENT);
                    if(rs.next()== false)
                    {
                       query = "INSERT INTO `park`(`pid`, `vt`,`rn`) VALUES (?,?,?);";
                        ps = con.prepareStatement(query);
                        ps.setString(1,rn);
                        ps.setString(2,vt);
                        ps.executeUpdate();
                        out.println("<h3>successfully booked your slot!!</h3>");
                        out.println("<h3>your slot number is</h3>"+rs.getInt("sno"));
                        request.getRequestDispatcher("success.html").forward(request, response);
                    }
                    else
                    {
                        do
                        {
                            dbUsername = rs.getString("usr");
                            if(dbUsername.equals(usr))
                                f=1;
                        }while(rs.next());
                    if(f==1)
                    {
                        out.println("<n3>UserName already exists !!!</h3>");
                        request.getRequestDispatcher("register.html").include(request, response);
                    }
                    else
                    {
                        query = "INSERT INTO `users`(`name`, `addr`, `phone`, `rn`, `vt`, `email`, `usr`, `psd`) VALUES (?,?,?,?,?,?,?,?);";
                        ps = con.prepareStatement(query);
                        ps.setString(1,n);
                        ps.setString(2,addr);
                        ps.setString(3,phn);
                        ps.setString(4,v);
                        ps.executeUpdate();
                        out.println("<h3>successfully registered!!</h3>");
                        out.println("<h3>remember ur username and password</h3>");
                        request.getRequestDispatcher("login.html").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
