//Compteur de véhicules
/*var nbcar;
var nbvehiculesecours;
var nbfourgonpompe;
var nbcamionciterne;
var nbcamionechelle;
var nbcamion;*/


//Fire
var HauteIntensite = new L.LayerGroup();
var FaibleIntensite = new L.LayerGroup();
var GrandeEtendue = new L.LayerGroup();
var FaibleEtendue = new L.LayerGroup();
var catA = new L.LayerGroup();
var catB_Gasoline = new L.LayerGroup();
var catB_Alcohol = new L.LayerGroup();
var catB_Plastics = new L.LayerGroup();
var catC_Flammable_Gases = new L.LayerGroup();
var catD_Metals = new L.LayerGroup();
var catE_Electric = new L.LayerGroup();

//Vehicle type

var CAR = new L.LayerGroup();
var FIRE_ENGINE = new L.LayerGroup();
var PUMPER_TRUCK = new L.LayerGroup();
var WATER_TENDERS = new L.LayerGroup();
var TURNTABLE_LADDER_TRUCK = new L.LayerGroup();
var TRUCK = new L.LayerGroup();


var map = L.map('map',{layers :[HauteIntensite,FaibleIntensite,GrandeEtendue,FaibleEtendue,catA,catB_Alcohol,catB_Gasoline,catB_Plastics,catC_Flammable_Gases, catD_Metals, catE_Electric, CAR, FIRE_ENGINE, PUMPER_TRUCK, WATER_TENDERS, TURNTABLE_LADDER_TRUCK, TRUCK]}).setView([45.7542,4.8375], 12); 


var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', { 
    attribution: '© OpenStreetMap contributors',
    maxZoom: 19
});
map.addLayer(osmLayer);


var fireIcon = L.icon({
    iconUrl : 'fire.png',
    iconAnchor : [30,37],

});
var pompierIcon = L.icon({
    iconUrl : 'camion.png',
    iconAnchor : [25,28],
});

var caserneIcon = L.icon({
    iconUrl : 'caserne.jpeg',
    iconAnchor : [25,45],
});

var carIcon = L.icon({
    iconUrl : 'voiture.png',
    iconAnchor : [12,14],
});

var command = L.control({position: 'topright'});
command.onAdd = function (map) {
    var div = L.DomUtil.create('div', 'command');
    div.innerHTML += '<div style="text-align:center;"><span style="font-size:18px;">Affichage des feux <br> en fonction de leurs caracteristiques</span></div>';

    return div;
};
command.addTo(map);

var commande = L.control({position: 'topleft'});
commande.onAdd = function (map) {
    var div = L.DomUtil.create('div', 'command');
    div.innerHTML += '<div style="text-align:center;"><span style="font-size:18px;">Affichage des engins de pompier <br> en fonction de leur type</span></div>';

    return div;
};
commande.addTo(map);
L.marker([45.747389,4.828066], {icon: caserneIcon}).addTo(map).bindPopup("Caserne 1");
L.marker([45.77900662392688,4.8782091997044645], {icon: caserneIcon}).addTo(map).bindPopup("Caserne 2");
L.marker([45.76074861168169,4.924973158699562], {icon: caserneIcon}).addTo(map).bindPopup("Caserne 3");
L.marker([45.7872386,4.7137926], {icon: caserneIcon}).addTo(map).bindPopup("Caserne 4");


/*var lat = (e.latlng.lat);
var lng = (e.latlng.lng);
marker.setLatLng([lat, lng]).update();*/

async function getFire(){

    HauteIntensite.clearLayers();
    FaibleIntensite.clearLayers();
    GrandeEtendue.clearLayers();
    FaibleEtendue.clearLayers();
    catA.clearLayers();
    catB_Gasoline.clearLayers();
    catB_Alcohol.clearLayers();
    catB_Plastics.clearLayers();
    catC_Flammable_Gases.clearLayers();
    catD_Metals.clearLayers();
    catE_Electric.clearLayers();


    await fetch("http://localhost:8081/fire")
        .then(function(res) {
            if (res.ok) {
                return res.json();
            }
    
        })
        .then(function(value) {
            for(let elt in value){
        //console.log(value[elt]);
                if (value[elt]["intensity"] > 30){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(HauteIntensite).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["intensity"] < 30){
                   L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(FaibleIntensite).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["range"] > 30){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(GrandeEtendue).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["range"] < 30){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(FaibleEtendue).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "A"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catA).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "B_Gasoline"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catB_Gasoline).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "B_Alcohol"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catB_Alcohol).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "B_Plastics"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catB_Plastics).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "C_Flammable_Gases"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catC_Flammable_Gases).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "D_Metals"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catD_Metals).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
                if (value[elt]["type"] == "E_Electric"){
                    L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: fireIcon}).addTo(catE_Electric).bindPopup("Intensite : " + value[elt]["intensity"]  +  "<br>Type : " +value[elt]["type"] + "<br>Etendue : " + value[elt]["range"]) ;
                }
            }
        })
    setTimeout(getFire,3000);       //Permet d'appeler cette fonction toute les 3*1seconde afin de mettre à jour l'affichage s'il y a de nouveaux feux sans mettre à jour la page
}
getFire();

var markersObject = new Map();

async function getVehicle(){

    CAR.clearLayers();
    FIRE_ENGINE.clearLayers();
    PUMPER_TRUCK.clearLayers();
    WATER_TENDERS.clearLayers();
    TURNTABLE_LADDER_TRUCK.clearLayers();
    TRUCK.clearLayers();


    await fetch("http://localhost:8081/vehicle")
        .then(function(res) {
            if (res.ok) {
                return res.json();
                
            }
        })
        .then(function(value) {
            for(let elt in value){
                if (value[elt]["type"] == "CAR"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: carIcon});
                    marker.addTo(CAR).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'quipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]) ;
                    
                }
                if (value[elt]["type"] == "FIRE_ENGINE"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: carIcon});
                    marker.addTo(FIRE_ENGINE).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'equipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]);
                    
                }
                if (value[elt]["type"] == "PUMPER_TRUCK"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: pompierIcon});
                    marker.addTo(PUMPER_TRUCK).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'equipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]) ;
                    
                }
                if (value[elt]["type"] == "WATER_TENDERS"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: pompierIcon});
                    marker.addTo(WATER_TENDERS).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'equipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]) ;
                   
                }
                if (value[elt]["type"] == "TURNTABLE_LADDER_TRUCK"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: pompierIcon});
                    marker.addTo(TURNTABLE_LADDER_TRUCK).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'equipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]);
                    
                }
                if (value[elt]["type"] == "TRUCK"){
                    var marker = L.marker([value[elt]["lat"],value[elt]["lon"]], {icon: pompierIcon});
                    marker.addTo(TRUCK).bindPopup("ID: " + value[elt]["id"] +"<br> Type de vehicule: " + value[elt]["type"]  +  "<br> Nombre d'equipiers : " +value[elt]["crewMember"] + "<br> Type de liquide : " + value[elt]["liquidType"] + "<br> Quantite de liquide restante : " + value[elt]["liquidQuantity"] + "<br> Essence : " + value[elt]["fuel"]);
                   
                }
            }
        })
    setTimeout(getVehicle,1000); 

}

getVehicle();





/*var lat = 45.747389;
var lon = 4.828066;
var newLatLng = new L.LatLng(lat, lon);
var marker = markersObject['5'];
marker.setLatLng(newLatLng);*/



//Compteur de véhicules
var idCar = [];
var idVehiculesecours = [];
var idFourgonpompe = [];
var idCamionciterne = [];
var idCamionechelle = [];
var idCamion = [];

function getIdVehicle(){
    fetch("http://localhost:8081/vehicle")
        .then(function(res) {
            if (res.ok) {
                return res.json();

            }
        
        })
        .then(function(value) {
            for(let elt in value){
                if (value[elt]["type"] == "CAR"){
                    idCar.push(value[elt]["id"]);
                }
                if (value[elt]["type"] == "FIRE_ENGINE"){
                    idVehiculesecours.push(value[elt]["id"]);
                }
                if (value[elt]["type"] == "PUMPER_TRUCK"){
                    idFourgonpompe.push(value[elt]["id"]);
                }
                if (value[elt]["type"] == "WATER_TENDERS"){
                    idCamionciterne.push(value[elt]["id"]);
                }
                if (value[elt]["type"] == "TURNTABLE_LADDER_TRUCK"){
                    idCamionechelle.push(value[elt]["id"]);
                }
                if (value[elt]["type"] == "TRUCK"){
                    idCamion.push(value[elt]["id"]);
                }
        
                }
        })
}
getIdVehicle();

function Choix(form) {

    i = form.VehiculeSupprime.selectedIndex;
    if (i == 0) {
        return;
    }
    switch (i) {
        case 1 : var txt = idCar; break;
        case 2 : var txt = idVehiculesecours; break;
        case 3 : var txt = idFourgonpompe; break;
        case 4 : var txt = idCamionciterne; break;
        case 5 : var txt = idCamionechelle; break;
        case 6 : var txt = idCamion; break;
    
    }

    for (i=0;i<12;i++) {
        form.Page.options[i+1].text=txt[i];
    }
} 

function deleteVehicle(){
    
    var myvar = document.getElementById("deleteid").value;
    let xhttp = new XMLHttpRequest();
    let url = 'http://localhost:8081/vehicle/'+ myvar;

    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var jsonParse = JSON.parse(xhttp.responseText);  
        }
    }
    xhttp.open('DELETE', url);
    xhttp.send();

}


var longitude;
var latitude;

function ChoixCaserne(form){
    i = form.VehiculePompier.selectedIndex;
    if (i == 0) {
        return;
    }
    switch (i) {
        case 1 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;
        case 2 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;
        case 3 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;
        case 4 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;
        case 5 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;
        case 6 : var txt = ['Caserne 1', 'Caserne 2', 'Caserne 3', 'Caserne 4']; break;

    }
    for (i=0;i<6;i++) {
        form.caserne.options[i+1].text=txt[i];
    }

}


function ChoixLiquide(form){
    i = form.caserne.selectedIndex;
    if (i == 0) {
        return;
    }
    switch (i) {
        case 1 : var txt = ['ALL', 'WATER', 'WATER_WITH_ADDITIVES', 'CARBON_DIOXIDE', 'POWDER']; break;
        case 2 : var txt = ['ALL', 'WATER', 'WATER_WITH_ADDITIVES', 'CARBON_DIOXIDE', 'POWDER']; break;
        case 3 : var txt = ['ALL', 'WATER', 'WATER_WITH_ADDITIVES', 'CARBON_DIOXIDE', 'POWDER']; break;
        case 4 : var txt = ['ALL', 'WATER', 'WATER_WITH_ADDITIVES', 'CARBON_DIOXIDE', 'POWDER']; break;
    }

    for (i=0;i<5;i++) {
        form.liquide.options[i+1].text=txt[i];
    }
}


function getSelectedOption(){
    //Choix de la caserne de départ

    if (document.getElementById("casernechoice").value == 'Caserne 1'){
        longitude = 4.828066;
        latitude = 45.747389;
    }
    else if (document.getElementById("casernechoice").value == 'Caserne 2'){
        longitude = 4.8782091997044645;
        latitude = 45.77900662392688;
    }
    else if (document.getElementById("casernechoice").value == 'Caserne 3'){
        longitude = 4.924973158699562;
        latitude = 45.76074861168169;
    }
    else if (document.getElementById("casernechoice").value == 'Caserne 4'){
        longitude = 4.7137926;
        latitude = 45.7872386;
    }
    

    let vehicle = {lon : longitude, lat : latitude, type : document.getElementById("vehicule").value, efficiency : 10.0, liquidType : document.getElementById("liquidechoice").value, liquidQuantity : 100.0, liquidConsumption : 1.0, fuel : 100.0, fuelConsumption : 10.0, crewMember : 8, crewMemberCapacity : 8, facilityrefID : 0}

    var data = JSON.stringify(vehicle);
    

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8081/vehicle";

    xhr.onload = () => {

    // print JSON response
        if (xhr.status >= 200 && xhr.status < 300) {
        // parse JSON
            const response = JSON.parse(xhr.responseText);
            console.log(response);
        } 
    };

    xhr.open("POST", url);

    xhr.setRequestHeader("Content-Type", "application/json");


    xhr.send(data);

    
}


var baseLayers = {};
var overlaysfire = {
"Haute intensite" : HauteIntensite,
"Faible intensite" : FaibleIntensite,
"Grande etendue" : GrandeEtendue,
"Faible etendue" : FaibleEtendue,
"Type A" : catA,
"Type B_Gasoline" : catB_Gasoline,
"Type B_Alcohol" : catB_Alcohol,
"Type B_Plastics" : catB_Plastics,
"Type C_Flammable_Gases" : catC_Flammable_Gases,
"Type D_Metals" : catD_Metals,
"Type E_Electric" : catE_Electric,
};

var overlaysvehicle = {
"Voiture" : CAR, 
"Vehicule de secours" : FIRE_ENGINE,
"Fourgon pompe" : PUMPER_TRUCK, 
"Camion citerne" : WATER_TENDERS, 
"Camion echelle" : TURNTABLE_LADDER_TRUCK, 
"Camion" : TRUCK,
};

L.control.layers(baseLayers, overlaysfire, {collapsed :false}).addTo(map);
L.control.layers(baseLayers, overlaysvehicle, {collapsed :false, position : "topleft"}).addTo(map);




