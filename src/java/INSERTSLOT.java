/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ucmol
 */

/*
---------------------------------------------------------------------------------------------------------------------------------
-                                          SERVLET TO ACTIVATE THE SLOT                                                       -
---------------------------------------------------------------------------------------------------------------------------------
*/


public class INSERTSLOT extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet INSERTSLOT</title>");            
            out.println("</head>");
            out.println("<body>");
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    PreparedStatement ps;
                    String pid = request.getParameter("pid");              //ID OF SLOT TO BE ACTIVATED FROM ADMIN.JAVA
                    int f=0;                                               //FLAG TO CHECK UPDATE
                    String query = "UPDATE park SET rn = NULL WHERE pid=?";
                    ps = con.prepareStatement(query);
                    ps.setString(1,pid);
                    if(ps.executeUpdate()==1)                                    //REGNO OF SLOT WITH CORRESPONDING PARK ID IS SET NULL
                    {
                        f=1;
                    }
                    ps.close();                                            //i.e., AVAILABLE TO BOOKING
                    
                    //CHECKING WHETHER UPDATES SUCCESSFULLY OR NOT
                    if(f==1)
                    {
                        out.println("updated successfully");
                        request.getRequestDispatcher("ADMIN").include(request, response);
                    }
                    else
                    {   
                        out.println("<h1 style='color:red;'>ERROR IN UPDATING"+pid+"</h1>");
                        request.getRequestDispatcher("ADMIN").include(request, response);
                    }
                }
                    
            } 
            catch (InstantiationException | IllegalAccessException | SQLException ex) 
            {
                 out.println(ex);
            }
            //out.println("<h1>Servlet INSERTSLOT at " + request.getContextPath() + "</h1>");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(INSERTSLOT.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(INSERTSLOT.class.getName()).log(Level.SEVERE, null, ex);
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
