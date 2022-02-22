<%-- 
    Document   : registro
    Created on : 31-ene-2022, 17:52:55
    Author     : DAW209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

    <head>
        <meta charset="UTF-8">
        <title></title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Varela+Round&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/main.js"></script>
    </head>

    <body>
        <form action="airdron" method="post" id="formularioRegistro" class="formularioRegistro" autocomplete="off">
            <h1>REGISTRO</h1>
            <input type="text" placeholder="Introduzca su DNI" name="dni" id="dni" class="campoTexto">
            <input type="text" placeholder="Introduzca su Nombre" name="nombre" id="nombre" class="campoTexto">
            <input type="text" placeholder="Introduzca su Apellido" name="apellido" id="apellido" class="campoTexto">
            <input type="password" placeholder="Introduzca su contraseña" name="pwd" id="pwd" class="campoTexto">
            <input type="password" placeholder="Confirme su contraseña" name="pwdConfirmada" id="pwdConfirmada" class="campoTexto">
            <input type="hidden" value="paginaRegistro" name="come">
            <input type="text" placeholder="Introduzca su email" name="email" id="correo" class="campoTexto">
            <section class="botones">
                <input type="submit" value="Registrarse" name="registrarse" id="enviar" class="boton">
                <input type="submit" value="Cancelar" name="cancelar" id="cancelar" class="boton">
            </section>
            <section id="errores" class="errores"></section>
        </form>
    </body>

</html>