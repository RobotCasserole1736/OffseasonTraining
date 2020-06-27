
//Note - this PORT string must be aligned with the port the Data Server is served on.
var port = "5806";
var hostname = window.location.hostname+":"+port;

var dataSocket = new WebSocket("ws://"+hostname+"/ds")
var numTransmissions = 0;

var filterSpec = "";
var idToName = {};
filterChangeHandler = function(filterspec_in){
  filterSpec = filterspec_in.toLowerCase();
}


dataSocket.onopen = function (event) {
  document.getElementById("id01").innerHTML = "Socket Open";

  // Send the command to get the list of all signals
  dataSocket.send(JSON.stringify({cmd: "getSig"}));

  //Nothing else to do on init, at least till the server responds with the signal list
  
};

dataSocket.onmessage = function (event) {
  serverMsg = JSON.parse(event.data);

  var daq_request_cmd = {};

  if(serverMsg.type == "sig_list") {
    // When the server sends us a signal list, we respond by requesting a single DAQ List with every signal
    daq_request_cmd.cmd = "addDaq";
    daq_request_cmd.id = "main";
    daq_request_cmd.tx_period_ms = "100"; //Sets the frequency of packet transmit from RIO to this client
    daq_request_cmd.samp_period_ms = "100"; //Sets the decimation of the data expected. 0 = return all data, non-zero = decimate data prior to send.
    daq_request_cmd.sig_id_list = [];

    var i = 0;
    for(i = 0; i < serverMsg.signals.length; i++){
      idToName[serverMsg.signals[i].id] = serverMsg.signals[i].display_name; //Record a mapping of all displayed signal names to their underlying id's
      daq_request_cmd.sig_id_list[i] = serverMsg.signals[i].id;
    }

    var sendVal = JSON.stringify(daq_request_cmd);
    dataSocket.send(sendVal);

    genInitTable(serverMsg.signals.sort(function(a,b){return a.id.localeCompare(b.id)}));

    var sendVal = JSON.stringify({cmd: "start"});
    dataSocket.send(sendVal);

  } else if(serverMsg.type == "daq_update") {
    if(serverMsg.daq_id == "main"){
      updateTable(serverMsg);
    }
  }
  
  numTransmissions = numTransmissions + 1;
  document.getElementById("id01").innerHTML = "COM Status: Socket Open. RX Count:" + numTransmissions; 
};

dataSocket.onerror = function (error) {
  document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
  alert("ERROR from Present State: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
};

dataSocket.onclose = function (error) {
  document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
  alert("ERROR from Present State: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
};

function genInitTable(arr) {
    var i;
    var out = "<table>";

    for(i = 0; i < arr.length; i++) {
        out += "<tr id=\"elem_row_id_" + arr[i].id  + "\"><td>" +
        arr[i].display_name +
        "</td><td>" +
        arr[i].units +
        "</td><td id=\"elem_disp_id_" + arr[i].id  + "\" style=\"width: 200px;\">" +
        "??" +
        "</td></tr>";
    }
    out += "</table>";
    document.getElementById("id02").innerHTML = out;
}

function updateTable(updObj) {
  var sig_idx = 0;

  if(updObj.daq_id == "main" && updObj.type == "daq_update"){
    for(sig_idx = 0; sig_idx < updObj.signals.length; sig_idx++){
      var signal = updObj.signals[sig_idx];

      if(signal.samples.length > 0){
        document.getElementById("elem_disp_id_" + signal.id).innerHTML = signal.samples[signal.samples.length-1].val;
      }

      if(checkName(idToName[signal.id])){
        document.getElementById("elem_row_id_" + signal.id).style.visibility = "visible";
      } else {
        document.getElementById("elem_row_id_" + signal.id).style.visibility = "collapse";
      }
    }
  }
}

function checkName(name){

  if(filterSpec.length == 0){
    return true;
  } else {
    if(name.toLowerCase().includes(filterSpec)){
      return true;
    } else {
      return false;
    }
  }
}