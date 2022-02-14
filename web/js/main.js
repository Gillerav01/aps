function validarRegistro() {
    var dni = document.getElementById("dni");
    var dniValor = document.getElementById("dni").value;
    var nombre = document.getElementById("nombre");
    var nombreValor = document.getElementById("nombre").value;
    var apellido = document.getElementById("apellido");
    var apellidoValor = document.getElementById("apellido").value;
    var pwd = document.getElementById("pwd");
    var pwdValor = document.getElementById("pwd").value;
    var pwdConfirmada = document.getElementById("pwdConfirmada");
    var pwdConfirmadaValor = document.getElementById("pwdConfirmada").value;
    var correo = document.getElementById("correo");
    var correoValor = document.getElementById("correo").value;
    var expresionDNI = /^[0-9]{8,8}[A-Za-z]$/g;
    var expresionCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    var expresionContrasena = /^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,30}$/;
    var errores = "";
    var seccion = document.getElementById("errores");
    if (dniValor == "") {
        console.info("El DNI no puede estar vacío.");
        errores += "El DNI no puede estar vacío. </br>";
    } else if (dniValor.search(expresionDNI) == -1) {
        console.info("El DNI está mal formado. (8 numeros y 1 letra.)");
        errores += "El DNI está mal formado. (8 numeros y 1 letra.) </br>";
    }
    if (nombreValor == "") {
        console.info("El Nombre no puede estar vacío.");
        errores += "El Nombre no puede estar vacío. </br>";
    } else if (nombreValor.length > 15) {
        console.info("El Nombre no puede tener mas de 15 caracteres.");
        errores += "El Nombre no puede tener mas de 15 caracteres. </br>";
    }
    if (apellidoValor == "") {
        console.info("El Apellido no puede estar vacío.");
        errores += "El Apellido no puede estar vacío. </br>";
    } else if (apellidoValor.length > 30) {
        console.info("El Apellido no puede tener mas de 30 caracteres.");
        errores += "El Apellido no puede tener mas de 30 caracteres. </br>";
    }
    if (pwdValor == "") {
        console.info("La contraseña no puede estar vacía.");
        errores += "La contraseña no puede estar vacía. </br>";
    } else if (pwdConfirmadaValor == "") {
        console.info("No has confirmado la contraseña.");
        errores += "No has confirmado la contraseña </br>";
    } else if (pwdValor != pwdConfirmadaValor) {
        console.info("Las contraseñas no coinciden.");
        errores += "Las contraseñas no coinciden </br>";
    } else if (pwdConfirmadaValor.search(expresionContrasena) == -1) {
        console.info("La contraseña necesita al menos un dígito, al menos una minúscula y al menos una mayúscula. Entre 8 y 30 carácteres.");
        errores += "La contraseña necesita al menos un dígito, al menos una minúscula y al menos una mayúscula. Entre 8 y 30 carácteres.<br>";
    }
    if (correoValor == "") {
        console.info("El correo no puede estar vacío.");
        errores += "El correo no puede estar vacío.<br>";
    } else if (correoValor.search(expresionCorreo) == -1) {
        console.info("El correo añadido no es correcto.");
        errores += "El correo añadido no es correcto. <br>";
    }

    if (errores == "") {
        return true;
    } else {
        seccion.innerHTML = errores;
        seccion.style.backgroundColor = "#ee1616";
        return false;
    }
}

function validarLogin() {
    var nombreValor = document.getElementById("campoNombre").value;
    var pwdValor = document.getElementById("pwd").value;
    var expresionDNI = /^[0-9]{8,8}[A-Za-z]$/g;
    var expresionCorreo = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    var erroresLogin = "";
    var seccion = document.getElementById("errores");

    if (nombreValor == "" || pwdValor == "") {
        erroresLogin += "El usuario no puede estar vacío. Introduzca su DNI o su correo.</br>";
    } else if (nombreValor.search(expresionDNI) == -1 && nombreValor.search(expresionCorreo) == -1) {
        erroresLogin += "Usuario incorrectamente formado </br>";
    }
    if (erroresLogin == "") {
        alert("si");
        return true;
    } else {
        seccion.innerHTML = erroresLogin;
        seccion.style.backgroundColor = "#ee1616";
        return false;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname == "/AgriculturaJardineriaPHP/registro.php") {
        document.getElementById("enviar").addEventListener("click", (e) => {
            if (!validarRegistro()) {
                e.preventDefault();
            }
        });
    }
});

function dibujarParcela(idParcela) {

    // Si existe un div con id map, lo elimino, si no, lo creo
    if (document.getElementById("map")) {
        document.getElementById("map").remove();
    }
    var divMap = document.createElement("div");
    divMap.setAttribute("id", "map");
    document.getElementById("map-container").appendChild(divMap);

    divMap.style.width = "100%";
    divMap.style.height = "250px";
    var puntazos = [];
    var map = L.map('map').setView([43.39161392884635, -3.7276212668677426], 18);
    var puntazos = [];
    var tiles = L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1
    }).addTo(map);
    tiles.addTo(map);
    fetch(`http://localhost/agriculturaPrecisionPHP/lib/recuperarPuntos.php?idParcela=${idParcela}`)
        .then(response => response.text())
        .then(data => {
            let buffer = data.split(";")
            let puntos = [];
            for (c of buffer) {
                let coordenadas = c.split(",");
                if (c != "") {
                    puntos.push([parseFloat(coordenadas[0]), parseFloat(coordenadas[1])]);
                }
            }
            puntazos = puntos;
            var p = L.polygon(puntazos, { color: `green` }).addTo(map);
            map.fitBounds(p.getBounds());
        });
}