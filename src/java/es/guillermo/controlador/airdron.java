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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lib.utilidades;
import modelo.Agricultor;
import modelo.Dron;
import modelo.Parcela;
import modelo.Rol;

/**
 *
 * @author guill
 */
public class airdron extends HttpServlet {

    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = null;
        String come = "";
        ConectorBD bdActual = new ConectorBD("localhost", "agr_precision", "root", "");
        System.out.println("Parametro de inicio direccionBD " + (String) getInitParameter("direccionBD"));
        System.out.println("----------------------------------------");
        come = request.getParameter("come");
        System.out.println("----------------------------------------");
        Connection conn;

        System.out.println("Come vale: " + come);
        if (come == null) {
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            HttpSession session = request.getSession(true);
            rd.forward(request, response);
            System.out.println("He entrado al if con come valor null ");
        } else if (come.equals("index")) {
            String inicioSesion = (String) request.getParameter("inSesion");
            String registro = (String) request.getParameter("registrarse");
            if (inicioSesion != null) {
                if (inicioSesion.equals("Iniciar sesion")) {
                    System.out.println("InicioSesion vale: " + inicioSesion);
                    String usuario = request.getParameter("login");
                    String pwd = request.getParameter("pwd");
                    System.out.println("El usuario es: " + usuario + " y la contrase??a es " + pwd);
                    AgricultorDAO usuarioLogueando = new AgricultorDAO();
                    conn = bdActual.getConexion();
                    usuarioLogueando.setConn(conn);
                    Agricultor comprobacionLogueo = usuarioLogueando.verificar(usuario, pwd);
                    if (comprobacionLogueo == null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("error", "<p class=\"error\">La combinacion de usuario y contrase??a no existe. </p>");
                        System.out.println(session.getAttribute("error"));
                        rd = getServletContext().getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                    } else {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("agricultorLogueado", comprobacionLogueo);
                        RolDAO conseguirRoles = new RolDAO();
                        conn = bdActual.getConexion();
                        conseguirRoles.setConn(conn);
                        DronDAO recuperarDrones = new DronDAO();
                        recuperarDrones.setConn(conn);
                        ParcelaDAO recuperarParcelas = new ParcelaDAO();
                        recuperarParcelas.setConn(conn);
                        ArrayList<Rol> rolesUsuario = conseguirRoles.verRolesUsuario(comprobacionLogueo);
                        ArrayList<Dron> dronesUsuario = recuperarDrones.recuperarDrones(comprobacionLogueo);
                        ArrayList<Parcela> parcelasUsuario = recuperarParcelas.recuperarParcelas(comprobacionLogueo);
                        session.setAttribute("rolesLogueado", rolesUsuario);
                        session.setAttribute("dronesUsuario", dronesUsuario);
                        session.setAttribute("parcelasUsuario", parcelasUsuario);
                        System.out.println("El agricultor logueado actualmente es: " + (Agricultor) session.getAttribute("agricultorLogueado"));
                        System.out.println("Los roles del agricultor logueado son: " + session.getAttribute("rolesLogueado"));
                        System.out.println("Los drones del piloto logueado son: " + session.getAttribute("dronesUsuario"));
                        System.out.println("Las parcelas del agricultor logueado son: " + session.getAttribute("parcelasUsuario"));
                        usuarioLogueando.cerrarConexion();
                        conseguirRoles.cerrarConexion();
                        recuperarDrones.cerrarConexion();
                        rd = getServletContext().getRequestDispatcher("/menuPrincipal.jsp");
                        rd.forward(request, response);
                    }
                }
            } else if (registro != null) {
                if (registro.equals("Registrarse")) {
                    rd = getServletContext().getRequestDispatcher("/registro.jsp");
                    rd.forward(request, response);
                }
            }
        } else if (come.equals("paginaRegistro")) {
            String btnRegistro = (String) request.getParameter("registrarse");
            String btnCancelar = (String) request.getParameter("cancelar");
            System.out.println("Valor del boton de registro: " + btnRegistro);
            System.out.println("Valor del boton cancelar: " + btnCancelar);
            if (btnRegistro != null) {
                System.out.println("btnRegistro no es null");
                if (btnRegistro.equals("Registrarse")) {
                    System.out.println("He entrado a registrarse");
                    conn = bdActual.getConexion();
                    AgricultorDAO nuevoUsuario = new AgricultorDAO();
                    nuevoUsuario.setConn(conn);
                    nuevoUsuario.crearAgricultor(new Agricultor((String) request.getParameter("nombre"), (String) request.getParameter("apellido"), (String) request.getParameter("dni"), utilidades.convertirSHA256((String) request.getParameter("pwdConfirmada")), (String) request.getParameter("email")));
                    nuevoUsuario.cerrarConexion();
                    HttpSession session = request.getSession(true);
                    session.setAttribute("mensaje", "<p class=\"success\">Usuario creado correctamente.</p>");
                    rd = getServletContext().getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                }
            }
            if (btnCancelar != null) {
                System.out.println("btnCancelar no es null");
                if (btnCancelar.equals("Cancelar")) {
                    System.out.println("He entrado a cancelar");
                    rd = getServletContext().getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                }
            }
        } else if (come.equals("cerrarSesion")) {
            HttpSession session = request.getSession(true);
            session.invalidate();
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if (come.equals("cambiarDatos")) {
            conn = bdActual.getConexion();
            AgricultorDAO nuevoUsuario = new AgricultorDAO();
            nuevoUsuario.setConn(conn);
            HttpSession session = request.getSession(true);
            nuevoUsuario.modificarAgricultor((Agricultor) session.getAttribute("agricultorLogueado"), new Agricultor(request.getParameter("nuevoNombre"), request.getParameter("nuevoApellido"), request.getParameter("nuevoDNI"), request.getParameter("nuevoCorreo")));
            System.out.println("Los datos modificados son: " + session.getAttribute("nuevoNombre") + session.getAttribute("nuevoApellido") + request.getParameter("nuevoDNI") + request.getParameter("nuevoCorreo"));
            rd = getServletContext().getRequestDispatcher("/menuPrincipal.jsp");
            rd.forward(request, response);
        } else if (come.equals("crearDron")) {
            conn = bdActual.getConexion();
            DronDAO nuevoDron = new DronDAO();
            nuevoDron.setConn(conn);
            HttpSession session = request.getSession(true);
            nuevoDron.crearDron(new Dron(request.getParameter("modeloDron"), request.getParameter("marcaDron")), (Agricultor) session.getAttribute("agricultorLogueado"));
            ArrayList<Dron> actualizarDrones = nuevoDron.recuperarDrones((Agricultor) session.getAttribute("agricultorLogueado"));
            session.setAttribute("dronesUsuario", actualizarDrones);
            nuevoDron.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarDrones.jsp");
            rd.forward(request, response);
        } else if (come.equals("borrarDron")) {
            int idDron = Integer.valueOf(request.getParameter("idDr"));
            conn = bdActual.getConexion();
            DronDAO borrarDron = new DronDAO();
            borrarDron.setConn(conn);
            HttpSession session = request.getSession(true);
            borrarDron.borrarDron(idDron);
            ArrayList<Dron> actualizarDrones = borrarDron.recuperarDrones((Agricultor) session.getAttribute("agricultorLogueado"));
            session.setAttribute("dronesUsuario", actualizarDrones);
            borrarDron.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarDrones.jsp");
            rd.forward(request, response);
        } else if (come.equals("anadirRol")) {
            int idRol = Integer.valueOf(request.getParameter("rolSeleccionado"));
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            conn = bdActual.getConexion();
            RolAgricultorDAO darRol = new RolAgricultorDAO();
            darRol.setConn(conn);
            darRol.otogarRol(idRol, idUsuario);
            darRol.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarRoles.jsp");
            rd.forward(request, response);
        } else if (come.equals("borrarRol")) {
            int idRol = Integer.valueOf(request.getParameter("rolBorrado"));
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            conn = bdActual.getConexion();
            RolAgricultorDAO darRol = new RolAgricultorDAO();
            darRol.setConn(conn);
            darRol.quitarRol(idRol, idUsuario);
            darRol.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarRoles.jsp");
            rd.forward(request, response);
        } else if (come.equals("realizarTrabajo")) {
            int idTrabajo = Integer.valueOf(request.getParameter("elegirTrabajo"));
            int idDron = Integer.valueOf(request.getParameter("elegirDron"));
            TrabajoDAO tdao = new TrabajoDAO();
            conn = bdActual.getConexion();
            tdao.setConn(conn);
            tdao.realizarTrabajo(idTrabajo, idDron);
            tdao.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarTrabajos.jsp");
            rd.forward(request, response);
        } else if (come.equals("registroTrabajo")) {
            int idParcela = Integer.valueOf(request.getParameter("parcela"));
            int pilotoSeleccionado = Integer.valueOf(request.getParameter("piloto"));
            String tipoTrabajo = request.getParameter("tipo");
            HttpSession session = request.getSession(true);
            TrabajoDAO tdao = new TrabajoDAO();
            Agricultor agricultorActual = (Agricultor) session.getAttribute("agricultorLogueado");
            System.out.println("Agricultor actual en come registro trabajo " + agricultorActual + "----------------------------------------------");
            conn = bdActual.getConexion();
            tdao.setConn(conn);
            tdao.crearTrabajo(idParcela, pilotoSeleccionado, agricultorActual.getId(), tipoTrabajo);
            tdao.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarTrabajos.jsp");
            rd.forward(request, response);
        } else if (come.equals("borrarTarea")) {
            int idTarea = Integer.valueOf(request.getParameter("idTarea"));
            TrabajoDAO tdao = new TrabajoDAO();
            conn = bdActual.getConexion();
            tdao.setConn(conn);
            tdao.borrarTrabajo(idTarea);
            tdao.cerrarConexion();
            rd = getServletContext().getRequestDispatcher("/gestionarTrabajos.jsp");
            rd.forward(request, response);
        } else if (come.equals("crearParcela")){
            
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
        } catch (SQLException ex) {
            Logger.getLogger(airdron.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(airdron.class.getName()).log(Level.SEVERE, null, ex);
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
