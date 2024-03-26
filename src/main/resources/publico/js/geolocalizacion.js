let lat, lon;

window.onload = function ()  {
    console.log("geolocalizacion.js loaded");
    if(navigator.geolocation){
        console.log("Geolocalization api supported");

        navigator.geolocation.getCurrentPosition(function(position ){
            lat = position.coords.latitude;
            lon = position.coords.longitude;
            console.log("Latitude: "+lat);
            console.log("Longitude: "+lon);
        },
            function (error){
            console.log("error retrieving position", error);
        });
    }
    else{
        console.log("Geolocalization api not supported");
    }
}
function setCoodinates() {
    document.getElementById('latitud').value =lat;
    document.getElementById('longitud').value = lon;
}