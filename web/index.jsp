<%-- 
    Document   : index
    Created on : 31-ene-2022, 17:52:46
    Author     : DAW209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Inicio</title>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/main.js"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Varela+Round&display=swap" rel="stylesheet">
    </head>
    <body class="principal">
        <form action="airdron" method="POST" class="formularioLogueo" autocomplete="off">
            <h1>Gestion de drones y parcelas</h1>
            <input value="21141255T" type="text" placeholder="Introduzca su DNI o Correo" id="campoNombre" name="login" class="campoNombre campoTexto">
            <input value="Mu1234567."  type="password" placeholder="Introduzca su contraseña" name="pwd" id="pwd" class="campoContraseña campoTexto">
            <section class="botones">
                <input type="submit" value="Iniciar sesion" name="inSesion" id="iniciarSesion" class="boton">
                <input type="submit" value="Registrarse" name="registrarse" class="boton">
            </section>
            <input type="hidden" name="come" value="index">
            <section id="errores" class="errores"></section>
        </form>
    </body>
</html>