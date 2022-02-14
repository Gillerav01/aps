<?php

$server = "localhost";
$user = "root";
$pass = "";
$bd = "agr_precision";
$conexion = mysqli_connect($server, $user, $pass, $bd) or die("Ha sucedido un error inexperado en la conexion de la base de datos");

$sql = "SELECT puntos FROM parcelas WHERE idParcela = " . $_REQUEST['idParcela'];
$result = mysqli_query($conexion, $sql);
$rows = mysqli_num_rows($result);
$datos = mysqli_fetch_array($result);
$puntos = "";

if ($rows > 0) {
    $puntos = $datos[0];
}

mysqli_close($conexion);
echo $puntos;   
?>