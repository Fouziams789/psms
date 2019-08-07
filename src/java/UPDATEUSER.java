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
-                                          SERVLET TO UPDATE USER DETAILS                                                       -
---------------------------------------------------------------------------------------------------------------------------------
*/


public class UPDATEUSER extends HttpServlet {

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
            out.println("<title>Servlet UPDATE</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //FETCHING DATA FROM UPDATEUSER.HTML
            
            String usr = request.getParameter("usr");                   //UNIQUE USERNAME
            String psd = request.getParameter("psd");                   //UNIQUE PASSWORD
            String n = request.getParameter("n");                       //NAME
            String v = request.getParameter("v");                       //REG NUMBER
            String vt  = request.getParameter("vt");                    //VEHICLE TYPE
            String addr = request.getParameter("adr");                  //ADDRESS
            String phn = request.getParameter("phn");                   //PHONE
            String email = request.getParameter("email");               //EMAIL
            ResultSet rs;
            Statement stmt;
            String query;
            int f=0;                                                    //FLAG SETTER
            String dbUsername;
            String dbPassword;
                    
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    stmt = (Statement) con.createStatement();
                    query = "SELECT usr,psd FROM users;";                               //query to get username and paswrd
                    stmt.executeQuery(query);
                    rs = stmt.getResultSet();
                    while(rs.next())
                    {
                        dbUsername = rs.getString("usr");
                        dbPassword = rs.getString("psd");
                        if(dbUsername.equals(usr) && dbPassword.equals(psd))            //CHECKING IF USER EXIST
                        {
                            f=1;                                                        //FLAG SET TO IDENTIFY USER AS ADMIN
                            break;
                        }
                    }
                    
                    if(f==1)
                    {
                        PreparedStatement ps;
                        query = " UPDATE users SET name=?, addr=?, phone=?, rn=?, vt=?, email=?  WHERE usr=? AND psd=?;";
                    
                        ps = con.prepareStatement(query);
                        ps.setString(1,n);
                        ps.setString(2,addr);
                        ps.setString(3,phn);
                        ps.setString(4,v);
                        ps.setString(5,vt);
                        ps.setString(6,email);
                        ps.setString(7,usr);
                        ps.setString(8,psd);
                        ps.executeUpdate();                         //UPDATING THE DB BASED ON USERNAME AND PASSWORD
                        ps.close();
                        
                        out.println("updated successfully");
                        if(usr.equals("ADMIN"))
                            request.getRequestDispatcher("ADMIN").include(request, response);
                        else
                            request.getRequestDispatcher("usr.html").include(request, response);
                    }
                    else
                    {
                        out.println("<h3>invalid username or password.....!!!</h3>");
                        request.getRequestDispatcher("UPDATEUSER.html").include(request, response);
                    }
                } 
            }
            catch (InstantiationException | IllegalAccessException | SQLException ex) 
            {
                out.println(ex);
            }
            //out.println("<h1>Servlet UPDATE at " + request.getContextPath() + "</h1>");
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
            Logger.getLogger(UPDATESLOT.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UPDATESLOT.class.getName()).log(Level.SEVERE, null, ex);
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
