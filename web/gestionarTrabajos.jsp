<%-- 
    Document   : gestionarDrones
    Created on : 31-ene-2022, 17:41:01
    Author     : DAW209
--%>

<%@page import="DB.ParcelaDAO"%>
<%@page import="modelo.Trabajo"%>
<%@page import="DB.TrabajoDAO"%>
<%@page import="modelo.Dron"%>
<%@page import="DB.AgricultorDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="DB.ConectorBD"%>
<%@page import="lib.utilidades"%>
<%@page import="modelo.Parcela"%>
<%@page import="modelo.Parcela"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Rol"%>
<%@page import="modelo.Agricultor"%>

<%
    Agricultor actual = (Agricultor) session.getAttribute("agricultorLogueado");
    ArrayList<Rol> rolesActuales = (ArrayList<Rol>) session.getAttribute("rolesLogueado");
    ArrayList<Parcela> parcelasUsuario = (ArrayList<Parcela>) session.getAttribute("parcelasUsuario");
    ArrayList<Dron> dronesPiloto = (ArrayList<Dron>) session.getAttribute("dronesUsuario");
    ConectorBD bdActual = new ConectorBD("localhost", "agr_precision", "root", "");
    Connection conn;
    conn = bdActual.getConexion();
    AgricultorDAO adao = new AgricultorDAO();
    adao.setConn(conn);
    ArrayList<Agricultor> pilotos = adao.recuperarPilotos();
    TrabajoDAO tdao = new TrabajoDAO();
    tdao.setConn(conn);
    System.out.println("Usuario actual: " + actual);
    ArrayList<Trabajo> trabajos = tdao.verTrabajosPilotoPendientes(actual.getId());
    System.out.println("Trabajos pendientes piloto " + trabajos);
    ArrayList<Trabajo> trabajosPendientes = tdao.verTrabajosAgricultorPendiente(actual);
    System.out.println("Trabajos pendientes agricultor" + trabajosPendientes);
    ArrayList<Trabajo> trabajosFinalizados = tdao.verTrabajosAgricultorFinalizado(actual);
    System.out.println("Trabajos finalizados agricultor " + trabajosFinalizados);
    ArrayList<Trabajo> trabajosPilotoFinalizados = tdao.verTrabajosPilotoFinalizado(actual);
    System.out.println("Trabajos pendientes piloto " + trabajosPilotoFinalizados);
    adao.cerrarConexion();

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú principal</title>
        <!--        <link rel="stylesheet" href="css/style.css">-->
        <script src="js/main.js"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Varela+Round&display=swap" rel="stylesheet">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
                        <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
                        <link rel="stylesheet" type="text/css" href="css/style.css" />
                        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" integrity="sha512-xodZBNTC5n17Xt2atTPuE1HxjVMSvLVW9ocqUKLsCC5CXdbqCmblAshOMAS6/keqq/sMZMZ19scR4PsZChSR7A==" crossorigin="" />
                        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js" integrity="sha512-XQoYMqMTK8LvdxXYG3nZ448hOEQiglfqkJs1NOQV44cWnUrBc8PkAOcXy20w0vlaXaVUearIOBhiXZ5V3ynxwA==" crossorigin=""></script>
                        </head>
                        <%        if (actual != null) {
                                for (Rol roles : rolesActuales) {
                                    if (roles.getNombreRol().equals("Piloto") || roles.getNombreRol().equals("Agricultor")) {
                        %>
                        <body class="d-flex flex-column" style="background-image: url('img/fondo.jpg'); overflow-x: hidden; background-repeat: no-repeat; background-size: cover;">
                            <header>
                                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                                    <div class="container-fluid">
                                        <a class="navbar-brand" href="menuPrincipal.jsp">DronAir
                                            <a class="navbar-brand" href="menuPrincipal.jsp">
                                                <img src="img/dronLogo.png" alt="" width="70px">
                                            </a></a>
                                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                                            <span class="navbar-toggler-icon"></span>
                                        </button>
                                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                            <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-flex">
                                                <!-- Todos -->
                                                <li class="nav-item">
                                                    <a class="nav-link active" aria-current="page" href="menuPrincipal.jsp">Inicio <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home">
                                                            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                                            <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                                        </svg></a>
                                                </li>

                                                <!-- Agricultor -->
                                                <%
                                                    for (Rol rol : rolesActuales) {
                                                        if (rol.getNombreRol().equals("Agricultor")) {
                                                %>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="gestionarParcelas.jsp">Gestionar parcelas <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map">
                                                            <polygon points="1 6 1 22 8 18 16 22 23 18 23 2 16 6 8 2 1 6"></polygon>
                                                            <line x1="8" y1="2" x2="8" y2="18"></line>
                                                            <line x1="16" y1="6" x2="16" y2="22"></line>
                                                        </svg></a>
                                                </li>
                                                <%
                                                            break;
                                                        }
                                                    }
                                                %>
                                                <!-- Administrador -->
                                                <%
                                                    for (Rol rol : rolesActuales) {
                                                        if (rol.getNombreRol().equals("Administrador")) {
                                                %>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="gestionarRoles.jsp">Gestionar roles <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-sliders">
                                                            <line x1="4" y1="21" x2="4" y2="14"></line>
                                                            <line x1="4" y1="10" x2="4" y2="3"></line>
                                                            <line x1="12" y1="21" x2="12" y2="12"></line>
                                                            <line x1="12" y1="8" x2="12" y2="3"></line>
                                                            <line x1="20" y1="21" x2="20" y2="16"></line>
                                                            <line x1="20" y1="12" x2="20" y2="3"></line>
                                                            <line x1="1" y1="14" x2="7" y2="14"></line>
                                                            <line x1="9" y1="8" x2="15" y2="8"></line>
                                                            <line x1="17" y1="16" x2="23" y2="16"></line>
                                                        </svg></a>
                                                </li>
                                                <%
                                                            break;
                                                        }
                                                    }
                                                %>
                                                <!-- Agricultor y piloto -->
                                                <%
                                                    for (Rol rol : rolesActuales) {
                                                        if (rol.getNombreRol().equals("Agricultor") || rol.getNombreRol().equals("Piloto")) {
                                                %>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="gestionarTrabajos.jsp">Gestionar trabajos <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-briefcase">
                                                            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                                                            <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
                                                        </svg></a>
                                                </li>
                                                <%
                                                            break;
                                                        }
                                                    }
                                                %>
                                                <!-- Piloto -->
                                                <%
                                                    for (Rol rol : rolesActuales) {
                                                        if (rol.getNombreRol().equals("Piloto")) {
                                                %>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="gestionarDrones.jsp">Gestionar drones <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-command">
                                                            <path d="M18 3a3 3 0 0 0-3 3v12a3 3 0 0 0 3 3 3 3 0 0 0 3-3 3 3 0 0 0-3-3H6a3 3 0 0 0-3 3 3 3 0 0 0 3 3 3 3 0 0 0 3-3V6a3 3 0 0 0-3-3 3 3 0 0 0-3 3 3 3 0 0 0 3 3h12a3 3 0 0 0 3-3 3 3 0 0 0-3-3z"></path>
                                                        </svg></a>
                                                </li>
                                                <%
                                                            break;
                                                        }
                                                    }
                                                %>
                                            </ul>

                                            <!-- Todos -->

                                            <section>
                                                <a class="nav-link" href="airdron?come=cerrarSesion" style="text-align: center; color: white;">Cerrar sesion <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-log-out">
                                                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                                                        <polyline points="16 17 21 12 16 7"></polyline>
                                                        <line x1="21" y1="12" x2="9" y2="12"></line>
                                                    </svg></a>
                                            </section>
                                        </div>
                                    </div>
                                </nav>
                            </header>
                            <main class="row d-flex">
                                <%
                                    for (Rol rol : rolesActuales) {
                                        if (rol.getNombreRol().equals("Piloto")) {
                                %>
                                <section class="container col-12 col-xl-5 d-flex flex-column p-1 rounded" style="background-color: white; margin-top: 10vh; margin-bottom: 10vh">
                                    <h1 style="color: black;">Gestion trabajos - Zona agricultor</h1>
                                    <section class="container col-12 col-xl-12 d-flex flex-column p-1 rounded" style="background-color: white; margin-bottom: 10vh">
                                        <h2 style="color: black; text-align: center;">Registro de trabajo</h2>
                                        <form action="airdron" class="container col-8 col-xl-8 d-flex flex-column">
                                            <label for="parcela">Parcela: </label>
                                            <select id="parcela" name="parcela">
                                                <%
                                                    for (Parcela parcela : parcelasUsuario) {
                                                %>
                                                <option value="<%=parcela.getId()%>">
                                                    <%=parcela.getNomParcela()%>
                                                </option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                            <label for="piloto">Piloto: </label>
                                            <select id="piloto" name="piloto">
                                                <%
                                                    for (Agricultor piloto : pilotos) {
                                                %>
                                                <option value="<%=piloto.getId()%>">
                                                    <%=piloto.getDni()%>, <%=piloto.getNombre()%> <%=piloto.getApellido()%>
                                                </option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                            <label for="tipo">Tipo de tarea: </label>
                                            <select name="tipo" id="tipo">
                                                <option value="abonado">Abonado</option>
                                                <option value="fumigacion">Fumigacion</option>
                                            </select>
                                            <input type="hidden" value="registroTrabajo" name="come">
                                                <input type="submit" value="Registrar trabajo" name="registrarTrabajo" class="btn btn-primary" style="margin-top: 4vh;">
                                                    </form>
                                                    </section>
                                                    <section class="container col-12 col-xl-12 d-flex flex-column p-1 rounded" style="background-color: white; margin-bottom: 10vh">
                                                        <h1 style="color: black;">Ver trabajos en proceso</h1>
                                                        <table class="table table-dark table-hover">
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col">Nombre parcela</th>
                                                                    <th scope="col">Tipo tarea</th>
                                                                    <th scope="col">Fecha de registro</th>
                                                                    <th scope="col"></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (Trabajo trabajoPendiente : trabajosPendientes) {
                                                                %>
                                                                <tr>
                                                                    <th scope="row"><%=ParcelaDAO.recuperarParcela(trabajoPendiente.getIdParcela())%></th>
                                                                    <td><%=trabajoPendiente.getTipoTrabajo()%></td>
                                                                    <td><%=trabajoPendiente.getFechaRegistro()%></td>
                                                                    <td><a href="airdron?come=borrarTarea&idTarea=<%=trabajoPendiente.getIdTrabajo()%>" class="btn btn-danger">Borrar trabajo pendiente</a></td>
                                                                    <tr>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        </tbody>
                                                                        </table>
                                                                        </section>
                                                                        <section class="container col-12 col-xl-12 d-flex flex-column p-1 rounded" style="background-color: white; margin-bottom: 10vh">
                                                                            <h1 style="color: black;">Ver trabajos terminados</h1>
                                                                            <table class="table table-dark table-hover">
                                                                                <h1 class="text-center text-white">Usuarios sin roles</h1>
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th scope="col">Nombre parcela</th>
                                                                                        <th scope="col">Tipo tarea</th>
                                                                                        <th scope="col">Fecha de registro</th>
                                                                                        <th scope="col">Fecha de realización</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <%
                                                                                        for (Trabajo trabajoPendiente : trabajosFinalizados) {
                                                                                    %>
                                                                                    <tr>
                                                                                        <th scope="row"><%=ParcelaDAO.recuperarParcela(trabajoPendiente.getIdParcela())%></th>
                                                                                        <td><%=trabajoPendiente.getTipoTrabajo()%></td>
                                                                                        <td><%=trabajoPendiente.getFechaRegistro()%></td>
                                                                                        <td><%=trabajoPendiente.getFechaRealizacion()%></td>
                                                                                        <tr>
                                                                                            <%
                                                                                                }
                                                                                            %>
                                                                                            </tbody>
                                                                                            </table>
                                                                                            </section>
                                                                                            </section>
                                                                                            <%                }
                                                                                                if (rol.getNombreRol().equals("Agricultor")) {
                                                                                            %>
                                                                                            <section class="container col-12 col-xl-5 d-flex flex-column p-1 rounded" style="background-color: white; margin-top: 10vh; margin-bottom: 10vh">
                                                                                                <h1 style="color: black;">Gestion trabajos - Zona piloto</h1>
                                                                                                <section class="container col-12 col-xl-12 d-flex flex-column p-1 rounded" style="background-color: white; margin-bottom: 10vh">
                                                                                                    <h2 style="text-align: center;">Realizar trabajo</h2>
                                                                                                    <form name="gestionarTrabajos" id="gestionarTrabajos" action="airdron" method="GET" class="container col-8 col-xl-8 d-flex flex-column">
                                                                                                        <label for="elegirTrabajo" class="align-self-center">Elige un trabajo: </label>
                                                                                                        <select name="elegirTrabajo" id="elegirTrabajo">
                                                                                                            <%
                                                                                                                for (Trabajo trabajo : trabajos) {
                                                                                                            %>
                                                                                                            <option value="<%=trabajo.getIdTrabajo()%>"><%=trabajo.getTipoTrabajo()%> en la parcela <%=ParcelaDAO.recuperarParcela(trabajo.getIdParcela())%></option>
                                                                                                            <%
                                                                                                                }
                                                                                                            %>
                                                                                                        </select>
                                                                                                        <label for="elegirDron" class="align-self-center">Elige un dron para realizar la tarea: </label>
                                                                                                        <select name="elegirDron" id="elegirDron">
                                                                                                            <%
                                                                                                                for (Dron dronesPilotoActual : dronesPiloto) {
                                                                                                            %>
                                                                                                            <option value="<%=dronesPilotoActual.getId()%>"><%=dronesPilotoActual.getModeloDron()%>, <%=dronesPilotoActual.getMarcaDron()%></option>
                                                                                                            <%
                                                                                                                }
                                                                                                            %>
                                                                                                        </select>
                                                                                                        <input type="hidden" value="realizarTrabajo" name="come">
                                                                                                            <input type="submit" name="submit" value="Enviar trabajo" class="btn btn-primary" style="margin-top: 1vh;">
                                                                                                                </form>
                                                                                                                </section>
                                                                                                                <section class="container col-12 col-xl-12 d-flex flex-column p-1 rounded" style="background-color: white; margin-bottom: 10vh">
                                                                                                                    <h1 style="color: black;">Ver trabajos terminados</h1>
                                                                                                                    <table class="table table-dark table-hover">
                                                                                                                        <h1 class="text-center text-white">Usuarios sin roles</h1>
                                                                                                                        <thead>
                                                                                                                            <tr>
                                                                                                                                <th scope="col">Nombre parcela</th>
                                                                                                                                <th scope="col">Tipo tarea</th>
                                                                                                                                <th scope="col">Fecha de registro</th>
                                                                                                                                <th scope="col">Fecha de realización</th>
                                                                                                                            </tr>
                                                                                                                        </thead>
                                                                                                                        <tbody>
                                                                                                                            <%
                                                                                                                                for (Trabajo trabajoPendiente : trabajosPilotoFinalizados) {
                                                                                                                            %>
                                                                                                                            <tr>
                                                                                                                                <th scope="row"><%=ParcelaDAO.recuperarParcela(trabajoPendiente.getIdParcela())%></th>
                                                                                                                                <td><%=trabajoPendiente.getTipoTrabajo()%></td>
                                                                                                                                <td><%=trabajoPendiente.getFechaRegistro()%></td>
                                                                                                                                <td><%=trabajoPendiente.getFechaRealizacion()%></td>
                                                                                                                                <tr>
                                                                                                                                    <%
                                                                                                                                        }
                                                                                                                                    %>
                                                                                                                                    </tbody>
                                                                                                                                    </table>
                                                                                                                                    </section>
                                                                                                                                    </section>
                                                                                                                                    <%
                                                                                                                                        }
                                                                                                                                    %>

                                                                                                                                    <%
                                                                                                                                        }
                                                                                                                                    %>
                                                                                                                                    </main>
                                                                                                                                    <footer class="text-center text-lg-start text-white" style="background-color: #28242c; width: 100%;">
                                                                                                                                        <hr class="mb-4" />
                                                                                                                                        <section class="mb-4 text-center">
                                                                                                                                            <a class="btn btn-outline-light btn-floating m-1" href="https://www.facebook.com/guillermo.illera/" role="button"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-facebook">
                                                                                                                                                    <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                                                                                                                                                </svg></a>
                                                                                                                                            <a class="btn btn-outline-light btn-floating m-1" href="https://www.instagram.com/itsguillermo97/" role="button"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-instagram">
                                                                                                                                                    <rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect>
                                                                                                                                                    <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path>
                                                                                                                                                    <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line>
                                                                                                                                                </svg></a>
                                                                                                                                            <a class="btn btn-outline-light btn-floating m-1" href="https://www.linkedin.com/in/gillerav/" role="button"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-linkedin">
                                                                                                                                                    <path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z"></path>
                                                                                                                                                    <rect x="2" y="9" width="4" height="12"></rect>
                                                                                                                                                    <circle cx="4" cy="4" r="2"></circle>
                                                                                                                                                </svg></a>
                                                                                                                                            <a class="btn btn-outline-light btn-floating m-1" href="https://github.com/Gillerav01" role="button"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-github">
                                                                                                                                                    <path d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"></path>
                                                                                                                                                </svg></a>
                                                                                                                                        </section>
                                                                                                                                        </div>
                                                                                                                                        <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2)">
                                                                                                                                            © 2021 - 2022 Copyright:
                                                                                                                                            <a class="text-white" href="https://www.instagram.com/itsguillermo97/">Guillermo Illera</a>
                                                                                                                                        </div>
                                                                                                                                    </footer>
                                                                                                                                    </body>
                                                                                                                                    <%
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    %>
                                                                                                                                    </html>