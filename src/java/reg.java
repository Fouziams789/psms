/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author KHSCI5MCA17025
 */
public class reg extends HttpServlet {

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
            out.println("<title>Servlet reg</title>");            
            out.println("</head>");
            out.println("<body>");
            String query;
            String dbUsername;
            String usr = request.getParameter("usr");
            String psd = request.getParameter("psd");
            String n = request.getParameter("n");
            String v = request.getParameter("v");
            String vt  = request.getParameter("vt");
            String addr = request.getParameter("adr");
            String phn = request.getParameter("phn");
            String email = request.getParameter("email");
            int f=0;
            
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) {
                    query = "SELECT * FROM users;";
                    Statement stmt = con.createStatement();
                    PreparedStatement ps;
                    //stmt.executeQuery(query);
                    ResultSet rs = stmt.executeQuery(query);
                    //CREATE TABLE users(name varchar(20) NOT NULL,addr varchar(50) NOT NULL,phone varchar(30) NOT NULL,rn varchar(30) NOT NULL,vt varchar(15) NOT NULL,email varchar(50),usr varchar(40) PRIMARY KEY NOT NULL,psd varchar(18) NOT NULL);
                    if(rs.next()== false)
                    {
                       query = "INSERT INTO `users`(`name`, `addr`, `phone`, `rn`, `vt`, `email`, `usr`, `psd`) VALUES (?,?,?,?,?,?,?,?);";
                        ps = con.prepareStatement(query);
                        ps.setString(1,n);
                        ps.setString(2,addr);
                        ps.setString(3,phn);
                        ps.setString(4,v);
                        ps.setString(5,vt);
                        ps.setString(6,email);
                        ps.setString(7,usr);
                        ps.setString(8,psd);
                        ps.executeUpdate();
                        out.println("<h3>successfully registered!!</h3>");
                        out.println("<h3>remember ur username and password</h3>");
                        request.getRequestDispatcher("login.html").forward(request, response);
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
                        ps.setString(5,vt);
                        ps.setString(6,email);
                        ps.setString(7,usr);
                        ps.setString(8,psd);
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
            //out.println("<h1>Servlet reg at " + request.getContextPath() + "</h1>");
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
