/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.guillermo.controlador;

import DB.AgricultorDAO;
import DB.ConectorBD;
import DB.DronDAO;
import DB.ParcelaDAO;
import DB.RolAgricultorDAO;
import DB.RolDAO;
import DB.TrabajoDAO;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DAW209
 */
public class controlador extends HttpServlet {

    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        AgricultorDAO adao = new AgricultorDAO();
        DronDAO ddao = new DronDAO();
        ParcelaDAO pdao = new ParcelaDAO();
        RolAgricultorDAO radao = new RolAgricultorDAO();
        RolDAO rdao = new RolDAO();
        TrabajoDAO tdao = new TrabajoDAO();
        ConectorBD bdActual = new ConectorBD(getInitParameter("direccionBD"), getInitParameter("nombreBD"), getInitParameter("nombreUsu"), getInitParameter("pwdUsu"));
        Connection conn = bdActual.getConexion();
        adao.setConn(conn);
        ddao.setConn(conn);
        pdao.setConn(conn);
        radao.setConn(conn);
        rdao.setConn(conn);
        tdao.setConn(conn);
        

        System.out.println(tdao.getConn().toString());
    }

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
//        response.setContentType("text/html;charset=UTF-8");
//        RequestDispatcher rd = null;
//        String come = "";
//        come = request.getParameter("come");
//        if (come.equals("index")) {
////            String login = request.getParameter("user");
////            String password = request.getParameter("pwd");
////            if (verificador.validacion(login, password)) {
////                rd = getServletContext().getRequestDispatcher("/menu.jsp");
////                rd.forward(request, response);
////            } else {
////                rd = getServletContext().getRequestDispatcher("/registro.jsp");
////                rd.forward(request, response);
////            }
//        } else if (come.equals("registro")) {
//            request.setAttribute("message", "Registro correcto");
//            rd = getServletContext().getRequestDispatcher("/index.jsp");
//            rd.forward(request, response);
//        } else if ("Disconnect".equals(request.getParameter("cerrarSesion"))) {
//            rd = getServletContext().getRequestDispatcher("/index.jsp");
//            rd.forward(request, response);
//        }
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
