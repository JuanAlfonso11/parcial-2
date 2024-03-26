window.onload = function() {
    //sleep(2000);
    if(verificarConexion()) {
        var redireccionElement = document.getElementById("usertoken");

        if (redireccionElement) {
            localStorage.setItem("accessToken", document.getElementById("usertoken").getAttribute("datatemp").toString());
            window.location.href = document.getElementById("redireccion").getAttribute("datatemp");
        } else {


            if (localStorage.getItem('accessToken')) {

                var dataToSend = {
                    data: localStorage.getItem('accessToken')
                };

                if (dataToSend.data) {
                    $.ajax({
                        url: '/verify',
                        type: 'POST',
                        contentType: 'application/json', // Agrega esta línea
                        data: JSON.stringify({
                            dataToSend
                        }),
                        success: function(response) {

                            if(response == "true"){
                                console.log("Si se puede");
                                var encodedData = encodeURIComponent(localStorage.getItem('accessToken'));
                                window.location.href = document.getElementById("redireccion").getAttribute("datatemp")+"?data="+encodedData;

                            }else{
                                console.log("no se puede");
                                console.log("La respuesta: "+response);
                                window.location.href = "/login";
                            }

                        },
                        error: function(error) {
                            console.log(error);
                        }
                    });
                    // window.location.href = document.getElementById("redireccion").getAttribute("datatemp");
                }
            } else {
                window.location.href = "/login";
            }
        }
    }else{
        console.log("La conexion: "+verificarConexion());
    }
};
function verificarConexion() {
    return new Promise(function(resolve, reject) {
        $.ajax({
            url: "/index.html",
            method: "HEAD",
            timeout: 5000,
            success: function() {
                console.log("Conexión exitosa");
                resolve(true);
            },
            error: function() {
                console.log("No se pudo conectar al servidor");
                resolve(false);
            }
        });
    });
}
function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}