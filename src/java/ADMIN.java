/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
//import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
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
-                                               SERVLET FOR ADMIN PAGE                                                          -
---------------------------------------------------------------------------------------------------------------------------------
*/

public class ADMIN extends HttpServlet {

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
            out.println("<title>ADMIN PORTAL</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div><H3 style='text-align:right;'><a href='LOGOUTSERVLET'>LOGOUT</a></H3></div>");
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    Statement stmt = con.createStatement();
                    ResultSet rs ;
                    rs= stmt.executeQuery("select * from users");
                    HttpSession session = request.getSession(); 
                    session.setAttribute("usr",request.getParameter("usr"));
                    
                    out.println("<div><table border=1 width=50% height=50%>");
                    out.println("<tr><th>USERS</th><th>ADDRESS</th><th>PHONE</th><th>REGISTERATION NUMBER</th><th>VEHICLE TYPE</th><th>EMAIL</th><th>USERNAME</th><th>PASSWORD</th><th>                             </th><tr>");
                    
                    int i=0;                                            //VALUE TO ADD INORDER TO SET UNIQUE ID's FOR HTML LINKS 
                    while (rs.next())
                    {
                        
                        i++;                                            //INCREMENTING ID VALUE
                        String s = rs.getString("usr");                 //FETCHING USERNAME TO PASS TO SERVLETS
                        
                        //PRINT EACH ROW
                        out.println("<tr id='user'+i><td>" + rs.getString("name") + "</td><td>" + rs.getString("addr") + "</td><td>" +
                            rs.getString("phone") + "</td><td>" + rs.getString("rn") + "</td><td>" + rs.getString("vt") + "</td><td>" +
                            rs.getString("email") + "</td><td>" + rs.getString("usr") + "</td><td>" + rs.getString("psd") + "</td>"+
                            "<td><a id='edit"+i+"' href='UPDATEUSER.html'>edit</a> <a href='DELETEUSER?usr="+s+"'>delete</a></td></tr>");
                                 //SETTING UNIQUE ID                               //PASSING VALUE TO DELETE USER
                                 
                    }
                    out.println("</table></div>");
                    
                    rs = stmt.executeQuery("select * from park");
                    
                    //PRINT PARK TABLE
                    out.println("<div><table border=1 width=50% height=50%>");
                    out.println("<tr><th>PARK ID</th><th>REG NO</th><th>VEHICLE TYPE</th><th>SLOT NO</th><th>                     </th><tr>");
                    
                    i=0;                                                //VALUE TO ADD INORDER TO SET UNIQUE ID's FOR HTML LINKS
                    while (rs.next())
                    {
                        //PRINT EACH ROW
                        i++;                                            //INCREMENTING ID VALUE 
                        int s = rs.getInt("pid");
                        out.println("<tr id='park'+i><td>" + rs.getString("pid") + "</td><td>" + rs.getString("rn") + "</td><td>" + rs.getString("vt") + "</td><td>" + rs.getString("sno") + "</td>");
                        if(rs.getString("rn")==null)
                        {
                            out.println("<td><a id='edit"+i+"' href='UPDATESLOT?pid="+s+"'>edit</a> <a id='delete"+i+"' href='DELETESLOT?pid="+s+"'>delete</a></td></tr>");
                        }
                        else
                        {
                            out.println("<td><a id='act"+i+"' href='INSERTSLOT?pid="+s+"'>activate</a> <a id='edit"+i+"' href='UPDATESLOT.html'>edit</a> <a id='delete"+i+"' href='DELETESLOT?usr="+s+"'>delete</a></td></tr>");
                            
                        }
                    }
                    out.println("</table></div>");   
                
                }
            } 
            catch (InstantiationException | IllegalAccessException | SQLException ex)
            {
                out.println(ex);
            }
            //out.println("<h1>Servlet ADMIN at " + request.getContextPath() + "</h1>");
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
            Logger.getLogger(ADMIN.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ADMIN.class.getName()).log(Level.SEVERE, null, ex);
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
