<?php

function conectar()
{
    $server = "localhost";
    $user = "root";
    $pass = "";
    $bd = "agr_precision";
    $conexion = mysqli_connect($server, $user, $pass, $bd) or die("Ha sucedido un error inexperado en la conexion de la base de datos");
    return $conexion;
}

function cerrarConexion($conexion)
{
    $close = mysqli_close($conexion) or die("Ha sucedido un error inexperado en la desconexion de la base de datos");
    return $close;
}

function sha256($pwd)
{
    return hash('SHA256', $pwd);
}

function verificado($usuarioRecuperado, $pwdRecuperada)
{
    $conexion = conectar();
    $instruccion = "SELECT id, password FROM agricultores WHERE dni = \"$usuarioRecuperado\" OR email = \"$usuarioRecuperado\";";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consultaaaaa");
    $resultado = mysqli_num_rows($consulta);
    $datosUsuario = mysqli_fetch_array($consulta);
    $idUsuario = $datosUsuario[0];
    print $datosUsuario[1];

    if ($datosUsuario[1] == sha256($pwdRecuperada)) {
        $instruccion = "SELECT id, nombre, apellido, dni, email FROM agricultores WHERE dni = \"$usuarioRecuperado\" OR email = \"$usuarioRecuperado\";";
        $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
        $datosUsuario = mysqli_fetch_array($consulta);
        $idUsuario = $datosUsuario[0];
        $_SESSION["usuarioActual"] = $datosUsuario;
        $instruccion = "SELECT agricultores.nombre, roles.nombreRol FROM agricultores, roles, rolesagricultores WHERE agricultores.id = $idUsuario AND roles.idRol = rolesagricultores.idRol AND agricultores.id = rolesagricultores.idAgricultor";
        $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
        $columnas = mysqli_num_rows($consulta);
        if ($columnas > 0) {
            $roles = array();
            for ($i = 0; $i < $columnas; $i++) {
                $resultado = mysqli_fetch_array($consulta);
                array_push($roles, $resultado[1]);
            }
            $_SESSION["listaRoles[]"] = $roles;
        }
        cerrarConexion($conexion);
        return true;
    } else {
        cerrarConexion($conexion);
        return false;
    }
}

function obtenerUsuarios()
{
    $conexion = conectar();
    $instruccion = "SELECT id, nombre, apellido, dni, email FROM agricultores;";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    $usuarios = array();
    if ($columnas > 0) {
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($usuarios, array($resultado[0], $resultado[1], $resultado[2], $resultado[3], $resultado[4]));
        }
        cerrarConexion($conexion);
        return $usuarios;
    }
}

function obtenerRolesTexto($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT roles.nombreRol FROM agricultores, roles, rolesagricultores WHERE agricultores.id = $idUsuario AND roles.idRol = rolesagricultores.idRol AND agricultores.id = rolesagricultores.idAgricultor";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    $rolesTexto = "";
    if ($columnas > 0) {
        $roles = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($roles, $resultado[0]);
            $rolesTexto .= $resultado[0] . " ";
        }
        cerrarConexion($conexion);
        return $rolesTexto;
    }
}

function obtenerRolesArray($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT roles.nombreRol FROM agricultores, roles, rolesagricultores WHERE agricultores.id = $idUsuario AND roles.idRol = rolesagricultores.idRol AND agricultores.id = rolesagricultores.idAgricultor";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $roles = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($roles, $resultado[0]);
        }
        cerrarConexion($conexion);
        return $roles;
    }
}

function obtenerRolesNoRepetidos($arrayRoles)
{
    $conexion = conectar();
    $instruccion = "SELECT nombreRol FROM roles";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    $roles = array();
    $rolesNoRepetidos = array();
    if ($columnas > 0) {
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($roles, $resultado[0]);
        }
        cerrarConexion($conexion);
        for ($i = 0; $i < count($roles); $i++) {
            if (!in_array($roles[$i], $arrayRoles)) {
                array_push($rolesNoRepetidos, $roles[$i]);
            }
        }
        return $rolesNoRepetidos;
    }
}

function obtenerRolesRepetidos($arrayRoles)
{
    $conexion = conectar();
    $instruccion = "SELECT nombreRol FROM roles";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    $roles = array();
    $rolesRepetidos = array();
    if ($columnas > 0) {
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($roles, $resultado[0]);
        }
        cerrarConexion($conexion);
        for ($i = 0; $i < count($roles); $i++) {
            if (in_array($roles[$i], $arrayRoles)) {
                array_push($rolesRepetidos, $roles[$i]);
            }
        }
        return $rolesRepetidos;
    }
}

function correoRegistro($nombreUsuario)
{
    $correo = '<!DOCTYPE html>
        <html lang="en" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:v="urn:schemas-microsoft-com:vml">
        <head>
        <title></title>
        <meta charset="utf-8"/>
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->
        <style>
                * {
                    box-sizing: border-box;
                }

                body {
                    margin: 0;
                    padding: 0;
                }

                a[x-apple-data-detectors] {
                    color: inherit !important;
                    text-decoration: inherit !important;
                }

                #MessageViewBody a {
                    color: inherit;
                    text-decoration: none;
                }

                p {
                    line-height: inherit
                }

                @media (max-width:620px) {
                    .icons-inner {
                        text-align: center;
                    }

                    .icons-inner td {
                        margin: 0 auto;
                    }

                    .fullMobileWidth,
                    .row-content {
                        width: 100% !important;
                    }

                    .image_block img.big {
                        width: auto !important;
                    }

                    .stack .column {
                        width: 100%;
                        display: block;
                    }
                }
            </style>
        </head>
        <body style="background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;">
        <table border="0" cellpadding="0" cellspacing="0" class="nl-container" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;" width="100%">
        <tbody>
        <tr>
        <td>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ff7d14; background-image: url("../img/fondo.jpg\"); background-position: top center; background-repeat: no-repeat;" width="100%">
        <tbody>
        <tr>
        <td>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;" width="600">
        <tbody>
        <tr>
        <td class="column" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
        <table border="0" cellpadding="0" cellspacing="0" class="image_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="padding-bottom:35px;padding-left:30px;padding-right:30px;padding-top:35px;width:100%;">
        <div align="center" style="line-height:10px"><img src="img/dronLogo.png" style="display: block; height: auto; border: 0; width: 210px; max-width: 100%;" width="210"/></div>
        </td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="image_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="width:100%;padding-right:0px;padding-left:0px;">
        <div align="center" style="line-height:10px"></div>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-2" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #000000;" width="100%">
        <tbody>
        <tr>
        <td>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 600px;" width="600">
        <tbody>
        <tr>
        <td class="column" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
        <table border="0" cellpadding="0" cellspacing="0" class="heading_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="padding-bottom:5px;padding-top:25px;width:100%;text-align:center;">
        <h1 style="margin: 0; color: #555555; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 36px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;"><strong>¡Gracias por registrarte!</strong></h1>
        </td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="text_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
        <tr>
        <td style="padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:20px;">
        <div style="font-family: sans-serif">
        <div style="font-size: 14px; mso-line-height-alt: 25.2px; color: #737487; line-height: 1.8; font-family: Arial, Helvetica Neue, Helvetica, sans-serif;">
        <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 32.4px;"><span style="font-size:18px;">Hola, ' . $nombreUsuario . ', tu cuenta ha sido creada con éxito.</span></p>
        <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 32.4px;"><span style="font-size:18px;">En estos momentos no tienes ningún rol, deberás de esperar a que un administrador te lo conceda. Se te enviará un email avisandote.</span></p>
        </div>
        </div>
        </td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="button_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:20px;text-align:center;">
        <div align="center">
        <!--[if mso]><v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" style="height:52px;width:222px;v-text-anchor:middle;" arcsize="8%" stroke="false" fillcolor="#ff7d14"><w:anchorlock/><v:textbox inset="0px,0px,0px,0px"><center style="color:#ffffff; font-family:Arial, sans-serif; font-size:16px"><![endif]-->
        <div style="text-decoration:none;display:inline-block;color:#ffffff;background-color:#ff7d14;border-radius:4px;width:auto;border-top:1px solid #ff7d14;border-right:1px solid #ff7d14;border-bottom:1px solid #ff7d14;border-left:1px solid #ff7d14;padding-top:10px;padding-bottom:10px;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;"><span style="padding-left:60px;padding-right:60px;font-size:16px;display:inline-block;letter-spacing:normal;"><span style="font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;"><a href="http://localhost/AgriculturaJardineriaPHP/index.php" style="text-decoration: none; color: white; font-size: 1.5rem; padding: 2rem;">Entra a la web</a></span></span></div>
        <!--[if mso]></center></v:textbox></v:roundrect><![endif]-->
        </div>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-3" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #000000;" width="100%">
        <tbody>
        <tr>
        <td>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-position: center top; color: #000000; width: 600px;" width="600">
        <tbody>
        <tr>
        <td class="column" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
        <table border="0" cellpadding="0" cellspacing="0" class="image_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="width:100%;padding-right:0px;padding-left:0px;">
        </td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="text_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
        <tr>
        <td style="padding-bottom:5px;padding-left:5px;padding-right:5px;padding-top:30px;">
        <div style="font-family: sans-serif">
        <div style="font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;">
        <p style="margin: 0; font-size: 14px; text-align: center;"><span style="font-size:12px;">© 2021 - 2022 DronAir | Calle Emperador, Portal 43, 1ºA</span></p>
        </div>
        </div>
        </td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="text_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
        <tr>
        <td style="padding-bottom:35px;padding-left:10px;padding-right:10px;padding-top:5px;">
        <div style="font-family: sans-serif">
        <div style="font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;">
        </div>
        </div>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-4" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tbody>
        <tr>
        <td>
        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;" width="600">
        <tbody>
        <tr>
        <td class="column" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
        <table border="0" cellpadding="0" cellspacing="0" class="icons_block" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="padding-bottom:5px;padding-top:5px;color:#9d9d9d;font-family:inherit;font-size:15px;text-align:center;">
        <table cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
        <tr>
        <td style="text-align:center;">
        <table cellpadding="0" cellspacing="0" class="icons-inner" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;">
        <tr>
        </tr>
        </table>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        </td>
        </tr>
        </tbody>
        </table>
        </td>
        </tr>
        </tbody>
        </table><!-- End -->
        </body>
        </html>';
    return $correo;
}

function enviarCorreoRegistro($correoUsuario, $nombreUsuario)
{
    $to = $correoUsuario;
    $subject = 'Confirmacion de registro.';
    $message = correoRegistro($nombreUsuario);
    $cabeceras = 'MIME-Version: 1.0' . "\r\n";
    $cabeceras .= 'Content-type: text/html; charset=utf-8' . "\r\n";
    $cabeceras .= 'From: AirDron';
    mail($to, $subject, $message, $cabeceras);
}

function obtenerIdRol($idRol)
{
    $conexion = conectar();
    $instruccion = ("SELECT idRol FROM roles WHERE roles.nombreRol = \"$idRol\"");
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $resultado = mysqli_fetch_array($consulta);
    cerrarConexion($conexion);
    return $resultado[0];
}

function otorgarRol($idRol, $idUsuario)
{
    $conexion = conectar();
    $instruccion = "INSERT INTO `rolesagricultores` (`idAgricultor`, `idRol`) VALUES ($idUsuario, $idRol)";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function borrarRol($idRol, $idUsuario)
{
    $conexion = conectar();
    $instruccion = "DELETE FROM `rolesagricultores` WHERE `rolesagricultores`.`idAgricultor` = $idUsuario AND `rolesagricultores`.`idRol` = $idRol";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function obtenerRoles()
{
    $conexion = conectar();
    $instruccion = "SELECT roles.nombreRol FROM roles";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $roles = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($roles, $resultado[0]);
        }
        cerrarConexion($conexion);
        return $roles;
    }
}

function datosUsuario($idUsuario)
{
    $conexion = conectar();
    $instruccion = ("SELECT nombre, apellido, dni, email FROM agricultores WHERE agricultores.id = \"$idUsuario\"");
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $resultado = mysqli_fetch_array($consulta);
    cerrarConexion($conexion);
    return $resultado[0];
}

function modificarDatosUsuario($idUsuario, $nuevoNombre, $nuevoApellido, $nuevoDNI, $nuevoEmail, $nuevaContraseña)
{
    $conexion = conectar();

    if ($nuevoNombre != "") {
        $instruccion = ("UPDATE `agricultores` SET `nombre` = '$nuevoNombre' WHERE `agricultores`.`id` = $idUsuario;");
        mysqli_query($conexion, $instruccion) or die("Fallo en la consulta 1");
    }
    if ($nuevoApellido != "") {
        $instruccion = ("UPDATE `agricultores` SET `apellido` = '$nuevoApellido' WHERE `agricultores`.`id` = $idUsuario;");
        mysqli_query($conexion, $instruccion) or die("Fallo en la consulta 2");
    }
    if ($nuevoDNI != "") {
        $instruccion = ("UPDATE `agricultores` SET `dni` = '$nuevoDNI' WHERE `agricultores`.`id` = $idUsuario;");
        mysqli_query($conexion, $instruccion) or die("Fallo en la consulta 3");
    }
    if ($nuevoEmail != "") {
        $instruccion = ("UPDATE `agricultores` SET `email` = '$nuevoEmail' WHERE `agricultores`.`id` = $idUsuario;");
        mysqli_query($conexion, $instruccion) or die("Fallo en la consulta 4");
    }
    if ($nuevaContraseña != "") {
        $nuevaContraseña = sha256($nuevaContraseña);
        $instruccion = ("UPDATE `agricultores` SET `password` = '$nuevaContraseña' WHERE `agricultores`.`id` = $idUsuario;");
        mysqli_query($conexion, $instruccion) or die("Fallo en la consulta 5");
    }
    cerrarConexion($conexion);
}

function devolverProvincia($numero)
{
    $provincias = array(
        '', 'Alava', 'Albacete', 'Alicante', 'Almería', 'Avila', 'Badajoz', 'Baleares', 'Barcelona', 'Burgos', 'Cáceres',
        'Cádiz', 'Castellón', 'Ciudad Real', 'Córdoba', 'La Coruña', 'Cuenca', 'Gerona', 'Granada', 'Guadalajara',
        'Guipúzcoa', 'Huelva', 'Huesca', 'Jaén', 'León', 'Lérida', 'La rioja', 'Lugo', 'Madrid', 'Málaga', 'Murcia', 'Navarra',
        'Orense', 'Asturias', 'Palencia', 'Las Palmas', 'Pontevedra', 'Salamanca', 'Tenerife', 'Cantabria', 'Segovia', 'Sevilla', 'Soria', 'Tarragona',
        'Teruel', 'Toledo', 'Valencia', 'Valladolid', 'Vizcaya', 'Zamora', 'Zaragoza', 'Ceuta', 'Melilla'
    );

    return $provincias[$numero];
}

function devolverComunidad($numero)
{
    $comunidades = array(
        "Andalucía", "Aragón", "Canarias", "Cantabria", "Castilla y León", 
        "Castilla-La Mancha", "Cataluña", "Ceuta", "Comunidad Valenciana", 
        "Comunidad de Madrid", "Extremadura", "Galicia", "Islas Baleares", 
        "La Rioja", "Melilla", "Navarra", "País Vasco", "Principado de Asturias", 
        "Región de Murcia");
    return $comunidades[$numero];
}

function obtenerParcelas($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM parcelas WHERE idAgricultor = $idUsuario";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $parcelas = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($parcelas, $resultado);
        }
        cerrarConexion($conexion);
        return $parcelas;
    }
}

function registrarParcela($direccionArchivo, $nombre, $idAgricultor)
{
    $resultadoXML = XMLParcelas($direccionArchivo);
    $conexion = conectar();
    $textoPuntos = "";
    for ($i = 0; $i < count($resultadoXML[0]); $i++) {
        $x = $resultadoXML[0][$i][0];
        $y = $resultadoXML[0][$i][1];
        $textoPuntos .= "$x" . "," . "$y" . ";";
    }
    $instruccion = "INSERT INTO `parcelas` (`idParcela`, `nomParcela`, `area`, `idAgricultor`, `direccionArchivo`, `provincia`, `municipio`, `puntos`) VALUES (NULL, '$nombre', '$resultadoXML[1]', '$idAgricultor','$direccionArchivo', '$resultadoXML[3]', '$resultadoXML[2]', '$textoPuntos');";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function borrarParcela($idUsuario, $idParcela)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM `trabajos` WHERE idAgricultor = $idUsuario AND idParcela = $idParcela";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta de comprobacoin de trabajos");
    $columnas = mysqli_num_rows($consulta);
    $instruccion = "DELETE FROM `parcelas` WHERE idAgricultor = $idUsuario AND idParcela = $idParcela";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta de borrado de parcela");
    cerrarConexion($conexion);
}

function verDrones($idPiloto)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM drones WHERE idPiloto = $idPiloto";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $drones = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($drones, $resultado);
        }
        cerrarConexion($conexion);
        return $drones;
    }
}

function añadirDron($idPiloto, $modeloDron, $marcaDron)
{
    $conexion = conectar();
    if ($modeloDron == "" || $marcaDron == "") {
        return;
    }
    $instruccion = "INSERT INTO `drones` (`id`, `idPiloto`, `modeloDron`, `marcaDron`) VALUES (NULL, '$idPiloto', '$modeloDron', '$marcaDron');";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function borrarDron($idUsuario, $idDron)
{
    $conexion = conectar();
    $instruccion = "DELETE FROM `drones` WHERE idPiloto = $idUsuario AND id = $idDron";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function registrarTrabajo($idParcela, $idPiloto, $tipoTarea, $idAgricultor)
{
    $conexion = conectar();
    $instruccion = "INSERT INTO `trabajos` (`idTrabajo`, `idParcela`, `idPiloto`, `idAgricultor`, `idDron`, `tipoTarea`, `fechaRegistro`, `fechaRealizacion`) VALUES (NULL, '$idParcela', '$idPiloto', '$idAgricultor', NULL, '$tipoTarea', current_timestamp(), NULL);";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function verTrabajos($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM trabajos WHERE idAgricultor = $idUsuario";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $trabajos = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($trabajos, $resultado);
        }
        cerrarConexion($conexion);
        return $trabajos;
    }
}

function verTrabajosPendientes($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM trabajos WHERE idAgricultor = $idUsuario AND fechaRealizacion IS null";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $trabajosDisponibles = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($trabajosDisponibles, $resultado);
        }
        cerrarConexion($conexion);
        return $trabajosDisponibles;
    }
}

function verTrabajosTerminados($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM trabajos WHERE idAgricultor = $idUsuario AND fechaRealizacion IS NOT null";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $parcelas = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($parcelas, $resultado);
        }
        cerrarConexion($conexion);
        return $parcelas;
    }
}

function verTrabajosDisponibles($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM trabajos WHERE idPiloto = $idUsuario AND fechaRealizacion IS null";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $trabajosDisponibles = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($trabajosDisponibles, $resultado);
        }
        cerrarConexion($conexion);
        return $trabajosDisponibles;
    }
}
function verTrabajosRealizados($idUsuario)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM trabajos WHERE idPiloto = $idUsuario AND fechaRealizacion IS NOT null";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $parcelas = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($parcelas, $resultado);
        }
        cerrarConexion($conexion);
        return $parcelas;
    }
}


function borrarTrabajo($idUsuario, $idTrabajo)
{
    $conexion = conectar();
    $instruccion = "DELETE FROM `trabajos` WHERE idAgricultor = $idUsuario AND idTrabajo = $idTrabajo";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function realizarTrabajo($idTrabajo, $idDron)
{
    $conexion = conectar();
    $instruccion = "UPDATE `trabajos` SET `idDron` = '$idDron', `fechaRealizacion` = current_timestamp() WHERE `trabajos`.`idTrabajo` = $idTrabajo;";
    mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    cerrarConexion($conexion);
}

function seleccionarPilotos()
{
    $conexion = conectar();
    $instruccion = "SELECT agricultores.id, agricultores.nombre, agricultores.apellido FROM agricultores, rolesagricultores WHERE rolesagricultores.idRol = 3 AND rolesagricultores.idAgricultor = agricultores.id";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $columnas = mysqli_num_rows($consulta);
    if ($columnas > 0) {
        $pilotos = array();
        for ($i = 0; $i < $columnas; $i++) {
            $resultado = mysqli_fetch_array($consulta);
            array_push($pilotos, $resultado);
        }
        cerrarConexion($conexion);
        return $pilotos;
    }
}

function obtenerInformacionParcela($idParcela)
{
    $conexion = conectar();
    $instruccion = "SELECT * FROM parcelas WHERE idParcela = $idParcela";
    $consulta = mysqli_query($conexion, $instruccion) or die("Fallo en la consulta");
    $resultado = mysqli_fetch_array($consulta);
    return $resultado;
}

function XMLParcelas($nombreArchivo)
{
    $xml = simplexml_load_file("gml/" . $nombreArchivo);
    $stringPuntos = $xml->featureMember->Recinto->geometry->Polygon->exterior->LinearRing->posList;
    $arrayPuntos = explode(' ', $stringPuntos);
    $puntos = array();
    for ($i = 0; $i < count($arrayPuntos); $i += 2) {
        $lat = $arrayPuntos[$i];
        $long = $arrayPuntos[$i + 1];
        array_push($puntos, [$long, $lat]);
    }
    $stringArea = $xml->featureMember->Recinto->dn_surface[0];
    $stringMunicipio = $xml->featureMember->Recinto->municipio[0];
    $stringProvincia = $xml->featureMember->Recinto->provincia[0];
    return array($puntos, (string) $stringArea, (string) $stringMunicipio, (string) $stringProvincia);
}
