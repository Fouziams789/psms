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
//import java.util.Date;
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

/*
---------------------------------------------------------------------------------------------------------------------------------
-                                        SERVLET FOR USERS FOR SLOT BOOKING OR UPDATE                                           -
---------------------------------------------------------------------------------------------------------------------------------
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
                                                                                //FETCHINGVALUES FORM 
            String rn = request.getParameter("no");
            String at = request.getParameter("at");
            String dt = request.getParameter("dt");
            String vt = request.getParameter("vt");
            
            int f=0;                                                            //FLAG SETTER
            PreparedStatement ps;
            ResultSet rs;
            String query;
            String vtchk;
            
            HttpSession session = request.getSession();                         //GET SESSION
            session.getAttribute("usr");
            
            try 
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/psms", "root", "")) 
                {
                    query = "SELECT * FROM park WHERE rn IS NULL;";
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    
                    //CREATE TABLE park(pid integer AUTO_INCREMENT PRIMARY KEY NOT NULL,rn varchar(30),vt varchar(15) NOT NULL,sno integer(10));
                    
                    //checking if slots are available
                    if(rs.next()== false)
                    {
                        out.println("<h3>OOPSS!!! SORRY NO SLOTS ARE AVAILABLE!!</h3>");
                        request.getRequestDispatcher("success.html").forward(request, response);
                    }
                    else
                    {
                        //THE USERS REGISTER NUMBER ARE SET TO THE PARK TABLE ONCE THEY HAVE BOOKED THEIR SLOT
                        query = "SELECT * FROM park WHERE rn IS NULL;";
                        rs = stmt.executeQuery(query);
                        while(rs.next()==true)
                        {
                            vtchk = rs.getString("vt");
                            //ALLOCATING SLOT IF TWO WHEELER
                            if(vtchk.equals(vt) && vtchk.equals("two-wheeler"))
                            {
                                f=1;
                                query = " UPDATE park SET rn=? WHERE rn IS NULL and vt=? and pid=?;";
                                ps = con.prepareStatement(query);
                                
                                ps.setString(1,rn);
                                ps.setString(2,vtchk);
                                ps.setInt(3,rs.getInt("pid"));
                                ps.executeUpdate();
                                ps.close();
                                out.println("<h3>successfully booked your slot!!</h3>");
                                out.println("<h3>your parking ID is "+rs.getInt("pid")+"</h3>");
                                out.println("<h3>your slot number is "+rs.getInt("sno")+"</h3>");
                                out.println("<h3>your arrival time is "+at+"\n your departure time is "+dt+"</h3>");
                                request.getRequestDispatcher("success.html").include(request, response);
                                break;
                            }
                            //ALLOCATING SLOT IF THREE WHEELER
                            else if(vtchk.equals(vt) && vtchk.equals("three-wheeler"))
                            {
                                f=1;
                                query = " UPDATE park SET rn=? WHERE rn IS NULL and vt=? and pid=?;";
                                ps = con.prepareStatement(query);
                                
                                ps.setString(1,rn);
                                ps.setString(2,vtchk);
                                ps.setInt(3,rs.getInt("pid"));
                                ps.executeUpdate();
                                ps.close();
                                
                                out.println("<h3>successfully booked your slot!!</h3>");
                                out.println("<h3>your parking ID is</h3>"+rs.getInt("pid"));
                                out.println("<h3>your slot number is</h3>"+rs.getInt("sno"));
                                out.println("<h3>your arrival time is "+at+"\n your departure time is "+dt+"</h3>");
                                request.getRequestDispatcher("success.html").include(request, response);
                                break;
                            }
                            //ALLOCATING SLOT IF FOUR WHEELER
                            else if(vtchk.equals(vt) && vtchk.equals("four-wheeler"))
                            {
                                f=1;
                                query = " UPDATE park SET rn=? WHERE rn IS NULL and vt=? and pid=?;";
                                ps = con.prepareStatement(query);
                                
                                ps.setString(1,rn);
                                ps.setString(2,vtchk);
                                ps.setInt(3,rs.getInt("pid"));
                                ps.executeUpdate();
                                ps.close();
                                
                                out.println("<h3>successfully booked your slot!!</h3>");
                                out.println("<h3>your parking ID is</h3>"+rs.getInt("pid"));
                                out.println("<h3>your slot number is</h3>"+rs.getInt("sno"));
                                out.println("<h3>your arrival time is "+at+"\n your departure time is "+dt+"</h3>");
                                request.getRequestDispatcher("success.html").include(request, response);
                                break;
                            }
                        }
                        
                        if(f==0)                                                //CHECKING FLAG (SLOT PRESENT / NOT)
                        {
                           out.println("<h3 style='color:black;'>OOPSS!!! SORRY NO SLOTS ARE AVAILABLE!!</h3>");
                           request.getRequestDispatcher("usr.html").include(request, response);
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
