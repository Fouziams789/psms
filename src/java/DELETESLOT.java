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
 * @author KHSCI5MCA17025
 */
/*
---------------------------------------------------------------------------------------------------------------------------------
-                                          SERVLET TO INACTIVATE THE SLOT                                                       -
---------------------------------------------------------------------------------------------------------------------------------
*/

public class DELETESLOT extends HttpServlet {

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
            out.println("<title>Servlet DELETESLOT</title>");            
            out.println("</head>");
            out.println("<body>");
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    PreparedStatement ps;
                    String pid = request.getParameter("pid");       //PARK ID TO BE INACTIVATED
                    int f=0;                                        //FLAG TO CHECK UPDATES
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM park WHERE pid="+pid+";";
                    ResultSet rs = stmt.executeQuery(query);
                    
                    //LOOP FOR ACTIVATING SLOT
                    while(rs.next())
                    {
                        f=1;                                         //SETTING FLAG TO 1 ENSURE SUCCESSFULL UPDATE
                        query = "UPDATE park SET rn='NOT AVAILABLE' WHERE pid=?";
                        ps = con.prepareStatement(query);
                        ps.setString(1,pid);
                        ps.executeUpdate(); 
                        ps.close();
                    }
                    
                    //CHECK WHETHER SLOT IS INACTIVATED OR NOT
                    if(f==1)
                    {
                        out.println("deleted successfully");
                        request.getRequestDispatcher("ADMIN").include(request, response);
                    }
                    else
                    {
                        out.println("<h1 style='color:red;'>ERROR IN UPDATING"+pid+"</h1>");
                        //out.println(pid);
                        request.getRequestDispatcher("ADMIN").include(request, response);
                    }
                    
                } 
            }
            catch (InstantiationException | IllegalAccessException | SQLException ex) 
            {
                out.println(ex);
            }
            
            
            //out.println("<h1>Servlet DELETESLOT at " + request.getContextPath() + "</h1>");
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
            Logger.getLogger(DELETESLOT.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DELETESLOT.class.getName()).log(Level.SEVERE, null, ex);
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
