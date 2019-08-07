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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ucmol
 */

/*
---------------------------------------------------------------------------------------------------------------------------------
-                                        SERVLET FOR LOGIN CHECKING(ADMIN/USER)                                                 -
---------------------------------------------------------------------------------------------------------------------------------
*/
public class log extends HttpServlet {

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
            out.println("<title>Servlet log</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String query;
            String dbUsername, dbPassword;                                      //var to get username and paswd stored in db
            String username = request.getParameter("usr");                      //fetching usrname 
            String password = request.getParameter("psd");                      //fetching passwrd

        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "");
            
            Statement stmt = (Statement) con.createStatement();
            query = "SELECT usr,psd FROM users;";                               //query to get username and paswrd
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();
            HttpSession session;                                                //SESSION
            int f=0;                                                         //FLAG SETTING TO FIND THE USERS
            
            while(rs.next())
            {
                dbUsername = rs.getString("usr");
                dbPassword = rs.getString("psd");
                if(dbUsername.equals("ADMIN") && dbPassword.equals(password))   //CHECKING IF ADMIN
                {
                    f=2;                                                        //FLAG SET TO IDENTIFY USER AS ADMIN
                }
                else if(dbUsername.equals(username) && dbPassword.equals(password))
                {
                    f=1;                                                        //FLAG SET TO IDENTIFY OTHER USERS
                }
            }
            //CHECK FOR USERS    
            switch (f) 
            {
                        //USER LOGIN
                case 1:
                        session = request.getSession();                         //CREATE SESSION FOR USERS
                        session.setAttribute("usr",request.getParameter("usr"));
                        request.getRequestDispatcher("usr.html").forward(request, response);//GO TO USER PAGE
                        break;
                        
                        //ADMIN LOGIN
                case 2:                        
                        session = request.getSession();                         //CREATE SESSION FOR ADMIN
                        session.setAttribute("ADMIN",request.getParameter("usr"));
                        request.getRequestDispatcher("ADMIN").forward(request, response);   //GO TO ADMIN PAGE
                        break;
                        
                        //INVALID LOGIN
                default:
                        out.println("<center><h3 style='color: white;font-size:30px;'>Invalid password or username!!!!</h3></center>");
                        request.getRequestDispatcher("login.html").include(request, response);
                        break;
            }
        } 
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)
        {
            out.println(e);
        }
            //out.println("<h1>Servlet log at " + request.getContextPath() + "</h1>");
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
