


//Note - this PORT string must be aligned with the port the webserver is served on.
var port = "5805";
var hostname = window.location.hostname + ":" + port;

var dataSocket = new WebSocket("ws://" + hostname + "/driverviewstream")
var numTransmissions = 0;
var display_objs = {};



//Data socket handlers
dataSocket.onopen = function (event) {
    document.getElementById("id01").innerHTML = "COM Status: Socket Open";
};

dataSocket.onerror = function (error) {
    document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
    alert("ERROR from Driver View: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
};

dataSocket.onclose = function (error) {
    document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
    alert("ERROR from Driver View: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
};

dataSocket.onmessage = function (event) {
    var arr = JSON.parse(event.data);

    if (arr.step == "init") {
        //initial setup of the things on the page
        webcamTexts = "";

        //Part 1 - HTML Setup
        for (i = 0; i < arr.obj_array.length; i++) {
             if (arr.obj_array[i].type == "webcam") {
                var tgt_x_pct = arr.obj_array[i].marker_x;
                var tgt_y_pct = arr.obj_array[i].marker_y;
                //Draw webcam plus crosshairs overlaid
                webcamTexts += "<td><div id=\"outter\"align=\"center\" style=\"position:relative;width:100%;height:auto;\">";
                webcamTexts += "<img src=\"" + arr.obj_array[i].url + "\" style=\"width:100%;height:auto\"/>";
                webcamTexts += "<img id=\"crosshair_" + (arr.obj_array[i].name) + "\" src=\"/crosshair.png\" style=\"width:8%;height:auto;position:absolute;top:" + tgt_y_pct.toString() + "%;left:" + tgt_x_pct.toString() + "%;transform:translate(-50%, -50%)\">";
                webcamTexts += "</div></td>";
            }
        }

        //Part 2 - update the HTML on the page
        document.getElementById("webcams").innerHTML = webcamTexts;


    } else if (arr.step == "valUpdate") {
        for (i = 0; i < arr.obj_array.length; i++) {
            if (arr.obj_array[i].type == "webcam") {
               document.getElementById("crosshair_" + arr.obj_array[i].name).setAttribute("style", "width:8%;height:auto;position:absolute;top:" + arr.obj_array[i].marker_y + "%;left:" + arr.obj_array[i].marker_x + "%;transform:translate(-50%, -50%)");
            }
        }
    }
    //ignore other messages
};


document.addEventListener("fullscreenchange", function() {
    inFullScreen =   null != document.fullscreenElement || /* Standard syntax */
                     null != document.webkitFullscreenElement || /* Chrome, Safari and Opera syntax */
                     null != document.mozFullScreenElement ||/* Firefox syntax */
                     null != document.msFullscreenElement; /* IE/Edge syntax */
    if(inFullScreen){
        document.getElementById("statusContent").setAttribute("style", "display:none;");
    } else {
        document.getElementById("statusContent").setAttribute("style", "display:block;");
    }
  });


//Main Execution

var elem = document.getElementById("supercooltable");
function openFullscreen() {
  if (elem.requestFullscreen) {
    elem.requestFullscreen();
  } else if (elem.mozRequestFullScreen) { /* Firefox */
    elem.mozRequestFullScreen();
  } else if (elem.webkitRequestFullscreen) { /* Chrome, Safari & Opera */
    elem.webkitRequestFullscreen();
  } else if (elem.msRequestFullscreen) { /* IE/Edge */
    elem.msRequestFullscreen();
  }
}