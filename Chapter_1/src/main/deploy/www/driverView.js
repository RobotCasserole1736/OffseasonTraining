


//Note - this PORT string must be aligned with the port the webserver is served on.
var port = "5805";
var hostname = window.location.hostname + ":" + port;

var dataSocket = new WebSocket("ws://" + hostname + "/driverviewstream")
var numTransmissions = 0;
var display_objs = {};

//Class for a dial

var casseroleDial = function (elementID_in, min_in, max_in, min_acceptable_in, max_acceptable_in, step_in, name_in) {
    this.min = min_in;
    this.max = max_in;
    this.min_acceptable = min_acceptable_in;
    this.max_acceptable = max_acceptable_in;
    this.step = step_in;
    this.name = name_in;
    this.value = min_in;
    this.elementID = elementID_in;

    this.handcolor = "#333";
    this.bgcolor = "white";
    this.init();

}

casseroleDial.prototype.init = function () {
    this.canvas = document.getElementById(this.elementID);
    this.ctx = this.canvas.getContext("2d");
    this.radius = this.canvas.height / 2;
    this.ctx.translate(this.radius, this.radius);
    this.radius = this.radius * 0.90;
    this.drawFullDial();
}

casseroleDial.prototype.convValToAngle = function (num_in) {
    return ((num_in - this.min) / (this.max - this.min)) * (1.5 * Math.PI) + 1.25 * Math.PI;
}

casseroleDial.prototype.setValue = function (new_value) {

    //draw-over background This resets to a blank dial.
    this.drawFace();

    this.value = new_value;
    this.drawHand();
    this.drawValue();
    this.drawNumbers(); //Just in case the hand goes over the numbers
    this.drawName();
}

casseroleDial.prototype.drawFullDial = function () {
    //draw constant parts of the dial
    this.drawFace();
    this.drawNumbers();
    this.drawName();


    //draw the variable parts of the dial
    this.drawHand();
    this.drawValue();
}

casseroleDial.prototype.drawFace = function () {
    var grad;

    min_acc_ang = this.convValToAngle(this.min_acceptable) - (0.5 * Math.PI);
    max_acc_ang = this.convValToAngle(this.max_acceptable) - (0.5 * Math.PI);


    //min to min acceptable
    this.ctx.beginPath();
    this.ctx.moveTo(0, 0);
    this.ctx.arc(0, 0, this.radius, 0.75 * Math.PI, min_acc_ang);
    grad1 = this.ctx.createRadialGradient(0, 0, this.radius * 0.95, 0, 0, this.radius * 1.05);
    grad1.addColorStop(0, this.handcolor);
    grad1.addColorStop(0.5, "red");
    grad1.addColorStop(1, this.handcolor);
    this.ctx.strokeStyle = grad1;
    this.ctx.lineWidth = this.radius * 0.1;
    this.ctx.stroke();

    //max acceptable to max 
    this.ctx.beginPath();
    this.ctx.moveTo(0, 0);
    this.ctx.arc(0, 0, this.radius, max_acc_ang, 2.25 * Math.PI);
    grad3 = this.ctx.createRadialGradient(0, 0, this.radius * 0.95, 0, 0, this.radius * 1.05);
    grad3.addColorStop(0, this.handcolor);
    grad3.addColorStop(0.5, "red");
    grad3.addColorStop(1, this.handcolor);
    this.ctx.strokeStyle = grad3;
    this.ctx.lineWidth = this.radius * 0.1;
    this.ctx.stroke();

    //min acceptable to max acceptable
    this.ctx.beginPath();
    this.ctx.moveTo(0, 0);
    this.ctx.arc(0, 0, this.radius, min_acc_ang, max_acc_ang);
    grad2 = this.ctx.createRadialGradient(0, 0, this.radius * 0.95, 0, 0, this.radius * 1.05);
    grad2.addColorStop(0, this.handcolor);
    grad2.addColorStop(0.5, "green");
    grad2.addColorStop(1, this.handcolor);
    this.ctx.strokeStyle = grad2;
    this.ctx.lineWidth = this.radius * 0.1;
    this.ctx.stroke();

    //White background
    this.ctx.beginPath();
    this.ctx.moveTo(0, 0);
    this.ctx.arc(0, 0, this.radius * 0.94, 0.0 * Math.PI, 2 * Math.PI);
    this.ctx.fillStyle = this.bgcolor;
    this.ctx.fill();

}

casseroleDial.prototype.drawNumbers = function () {
    var ang;
    var num;
    this.ctx.font = "bold " + this.radius * 0.15 + "px arial";
    this.ctx.textBaseline = "middle";
    this.ctx.textAlign = "center";
    this.ctx.fillStyle = "black";
    this.ctx.beginPath();
    this.ctx.moveTo(0, 0);
    for (num = this.min; num <= this.max; num += this.step) {
        ang = this.convValToAngle(num);
        this.ctx.rotate(ang);
        this.ctx.translate(0, -this.radius * 0.82);
        this.ctx.rotate(-ang);
        this.ctx.fillText(num.toString(), 0, 0);
        this.ctx.rotate(ang);
        this.ctx.translate(0, this.radius * 0.82);
        this.ctx.rotate(-ang);
    }
}

casseroleDial.prototype.drawName = function () {
    this.ctx.rect(-this.radius * 0.9, this.radius * 0.7, this.radius * 0.9 * 2, this.radius * 0.3);
    this.ctx.fillStyle = "#cccccc";
    this.ctx.fill();
    this.ctx.translate(0, this.radius * 0.85);
    this.ctx.font = this.radius * 0.18 + "px arial";
    this.ctx.fillStyle = "#EE0000";
    this.ctx.fillText(this.name, 0, 0);
    this.ctx.translate(0, -this.radius * 0.85);

}

casseroleDial.prototype.drawValue = function () {
    this.ctx.translate(0, this.radius * 0.4);
    this.ctx.font = "bold " + this.radius * 0.20 + "px arial";
    this.ctx.fillStyle = "#008877";
    this.ctx.fillText(this.value.toString(), 0, 0);
    this.ctx.translate(0, -this.radius * 0.4);

}

casseroleDial.prototype.drawHand = function () {
    var ang = this.convValToAngle(this.value);
    this.ctx.strokeStyle = this.handcolor;
    this.ctx.beginPath();
    this.ctx.lineWidth = this.radius * 0.08;
    this.ctx.lineCap = "round";
    this.ctx.moveTo(0, 0);
    this.ctx.rotate(ang);
    this.ctx.lineTo(0, -this.radius * 0.7);
    this.ctx.stroke();
    this.ctx.rotate(-ang);

    this.ctx.beginPath();
    this.ctx.arc(0, 0, this.radius * 0.1, 0, 2 * Math.PI);
    this.ctx.fillStyle = this.handcolor;
    this.ctx.fill();
}

//END Dial Class


//START String Box Display class
var casseroleStringBox = function (elementID_in, name_in) {

    //initial input values
    this.name = name_in;
    this.value = "N/A"; //init to something meaningful
    this.elementID = elementID_in;

    //Initialize data
    this.init();

    //Do initial draw
    this.drawName();
    this.drawValBox();
    this.drawVal();

}

casseroleStringBox.prototype.init = function () {
    this.canvas = document.getElementById(this.elementID);
    this.ctx = this.canvas.getContext("2d");

    //Appearance tune params
    this.valueBoxX = this.canvas.width * 0.025;
    this.valueBoxY = this.canvas.height * 0.1;
    this.valueBoxWidth = this.canvas.width * 0.95;
    this.valueBoxHeight = this.canvas.height * 0.4;
    this.valueTextY = this.valueBoxY + (this.valueBoxHeight * 0.85);
    this.valueTextX = this.valueBoxX * 2;

    this.nameBoxX = this.canvas.width * 0.025;
    this.nameBoxY = this.canvas.height * 0.550;
    this.nameBoxWidth = this.canvas.width * 0.95;
    this.nameBoxHeight = this.canvas.height * 0.4;
    this.nameTextY = this.nameBoxY + (this.nameBoxHeight * 0.85);
    this.nameTextX = this.nameBoxX * 2;

    this.textcolor = "#333";
    this.bgcolor = "white";

}

casseroleStringBox.prototype.drawName = function () {
    this.ctx.fillStyle = this.bgcolor;
    this.ctx.fillRect(this.nameBoxX, this.nameBoxY, this.nameBoxWidth, this.nameBoxHeight);
    this.ctx.font = "bold " + this.nameBoxHeight * 0.50 + "px arial";
    this.ctx.fillStyle = this.textcolor;
    this.ctx.fillText(this.name.toString(), this.nameTextX, this.nameTextY);

}

casseroleStringBox.prototype.drawValBox = function () {
    this.ctx.fillStyle = this.bgcolor;
    this.ctx.fillRect(this.valueBoxX, this.valueBoxY, this.valueBoxWidth, this.valueBoxHeight);

}

casseroleStringBox.prototype.drawVal = function () {
    this.ctx.font = "bold " + this.valueBoxHeight * 0.50 + "px arial";
    this.ctx.fillStyle = this.textcolor;
    this.ctx.fillText(this.value.toString(), this.valueTextX, this.valueTextY);
}

casseroleStringBox.prototype.setValue = function (new_value) {
    this.value = new_value;
    this.drawValBox(); //draw over the existing value
    this.drawVal(); //draw new value

}
//END String Display class

//START Boolean Display class
var casseroleBooleanDisplay = function (elementID_in, name_in, color_in) {

    //initial input values
    this.name = name_in;
    this.color = color_in;
    this.value = false;
    this.elementID = elementID_in;

    //Initialize data
    this.init();

    //Do initial draw
    this.drawBgBox();
    this.drawInd();

}

casseroleBooleanDisplay.prototype.init = function () {
    this.canvas = document.getElementById(this.elementID);
    this.ctx = this.canvas.getContext("2d");

    //Appearance tune params
    this.borderSize = 0.05;

    this.bgBoxX = this.canvas.width * this.borderSize;
    this.bgBoxY = this.canvas.height * this.borderSize;
    this.bgBoxWidth = this.canvas.width * (1 - 2 * this.borderSize);
    this.bgBoxHeight = this.canvas.height * (1 - 2 * this.borderSize);

    this.indBoxX = this.canvas.width * this.borderSize * 2;
    this.indBoxY = this.canvas.height * this.borderSize * 2;
    this.indBoxWidth = this.canvas.width * (1 - 4 * this.borderSize);
    this.indBoxHeight = this.canvas.height * (1 - 4 * this.borderSize);

    this.textBoxX = this.canvas.width * this.borderSize * 3;
    this.textBoxY = this.canvas.height * this.borderSize * 3;
    this.textBoxWidth = this.canvas.width * (1 - 6 * this.borderSize);
    this.textBoxHeight = this.canvas.height * (1 - 6 * this.borderSize);


    if (this.color == "red") {
        this.indBoxOffColor = "#440000";
        this.indBoxOnColor = "#CC0000";
        this.textOffColor = "#774444";
        this.textOnColor = "#FFAAAA";
    } else if (this.color == "yellow") {
        this.indBoxOffColor = "#603300";
        this.indBoxOnColor = "#DDAA00";
        this.textOffColor = "#885500";
        this.textOnColor = "#FFEE99";
    } else { //presume green
        this.indBoxOffColor = "#003300";
        this.indBoxOnColor = "#30CC30";
        this.textOffColor = "#2A662A";
        this.textOnColor = "#A9FFA9";
    }

    this.bgcolor = "#BBBBBB";

}

casseroleBooleanDisplay.prototype.drawBgBox = function () {
    this.ctx.fillStyle = this.bgcolor;
    this.ctx.fillRect(this.bgBoxX, this.bgBoxY, this.bgBoxWidth, this.bgBoxHeight);
}

casseroleBooleanDisplay.prototype.drawInd = function () {
    if (this.value == true) {
        this.ctx.fillStyle = this.indBoxOnColor;
    } else {
        this.ctx.fillStyle = this.indBoxOffColor;
    }
    this.ctx.fillRect(this.indBoxX, this.indBoxY, this.indBoxWidth, this.indBoxHeight);

    if (this.value == true) {
        this.ctx.fillStyle = this.textOnColor;
    } else {
        this.ctx.fillStyle = this.textOffColor;
    }
    this.ctx.font = "bold " + this.textBoxHeight * 0.20 + "px arial";
    this.drawWrapText(this.name, this.textBoxX, this.textBoxY + this.textBoxHeight / 4, this.textBoxWidth, this.textBoxHeight / 4);
}


casseroleBooleanDisplay.prototype.drawWrapText = function (text, x, y, maxWidth, lineHeight) {
    var words = text.split(' ');
    var line = '';

    for (var n = 0; n < words.length; n++) {
        var testLine = line + words[n] + ' ';
        var metrics = this.ctx.measureText(testLine);
        var testWidth = metrics.width;
        if (testWidth > maxWidth && n > 0) {
            this.ctx.fillText(line, x, y);
            line = words[n] + ' ';
            y += lineHeight;
        }
        else {
            line = testLine;
        }
    }
    this.ctx.fillText(line, x, y);
}

casseroleBooleanDisplay.prototype.setValue = function (new_value) {
    if (new_value == "False") {
        this.value = false;
    } else if (new_value == "True") {
        this.value = true;
    }
    //else, do nothing

    this.drawInd(); //draw over the existing box
}

//END Boolean Display class

//START Sound Widget class
var casseroleSoundWidget = function (elementID_in, name_in, file_in) {

    //initial input values
    this.name = name_in;
    this.file = file_in;
    this.value = false;
    this.prevValue = false;
    this.elementID = elementID_in;
    this.soundfile = null;

    try{
        this.soundfile = new Audio(this.file);
    } catch(e){
        console.error("Error while attempting to load audio file" + this.file + " for " + this.name);
        console.error(e);
    }
    

    //Nothing to draw
}

casseroleSoundWidget.prototype.setValue = function (new_value) {
    this.prevValue = this.value
    if (new_value == "False") {
        this.value = false;
    } else if (new_value == "True") {
        this.value = true;
    }
    //else, do nothing

    // try to play a sound, but don't try too hard.
    if(this.soundfile != null){
        if(this.value == true && this.prevValue == false){
            try{
                this.soundfile.play();
            } catch (e) {
                console.log(e);
            }
        }
    }
}

// END Sound Widget class



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

//Callback for when the auton selector changes state.
// Should read the switch dropdown and send a websocket message with the new value
autoSelUpdateWs = function (object) {
    id = object.id;
    console.log("Auto Select hanlder called");
    console.log(id);
    console.log(document.getElementById(id).value);
    dataSocket.send(id + ":" + document.getElementById(id).value);
}

autoSelSetCurrent = function (name, val) {
    document.getElementById(name + "_cur").innerHTML = val;
}

dataSocket.onmessage = function (event) {
    var arr = JSON.parse(event.data);

    if (arr.step == "init") {
        //initial setup of the things on the page
        dialCanvasTexts = "";
        stringboxCanvasTexts = "";
        booleansCanvasTexts = "";
        webcamTexts = "";
        autoSelText = "";

        //Part 1 - HTML Setup
        autoSelText += "<tr><th>Auto Mode</th><th>Select</th><th>Current</th></tr>"
        for (i = 0; i < arr.obj_array.length; i++) {
            if (arr.obj_array[i].type == "dial") {
                dialCanvasTexts += "<canvas id=\"obj" + (arr.obj_array[i].name) + "\" width=\"175\" height=\"175\" style=\"background-color:#333\"></canvas>"
            } else if (arr.obj_array[i].type == "stringbox") {
                stringboxCanvasTexts += "<canvas id=\"obj" + (arr.obj_array[i].name) + "\" width=\"150\" height=\"75\" style=\"background-color:#333\"></canvas>"
            } else if (arr.obj_array[i].type == "boolean") {
                booleansCanvasTexts += "<canvas id=\"obj" + (arr.obj_array[i].name) + "\" width=\"75\" height=\"75\" style=\"background-color:#333\"></canvas>"
            } else if (arr.obj_array[i].type == "webcam") {
                var tgt_x_pct = arr.obj_array[i].marker_x;
                var tgt_y_pct = arr.obj_array[i].marker_y;
                var rotation = arr.obj_array[i].rotation_deg;
                //Draw webcam plus crosshairs overlaid
                webcamTexts += "<td><div id=\"outter\" style=\"position:relative;width:300px;height:auto;\"><img src=\"" + arr.obj_array[i].url + "\" style=\"width:300px;height:auto;transform:rotate(" + rotation.toString() + "deg)\"/><div id=\"crosshair_vert" + (arr.obj_array[i].name) + "\" style=\"background:yellow;position:absolute;top:" + tgt_y_pct.toString() + "%;left:" + tgt_x_pct.toString() + "%;width:2px;height:30px;transform:translate(-50%, -50%)\"/><div id=\"crosshair_horiz" + (arr.obj_array[i].name) + "\" style=\"background:yellow;position:absolute;top:" + tgt_y_pct.toString() + "%;left:" + tgt_x_pct.toString() + "%;width:30px;height:2px;transform:translate(-50%, -50%)\"/></div></td>";
            } else if (arr.obj_array[i].type == "autosel") {
                autoSelText += "<tr>";
                autoSelText += "<td>" + arr.obj_array[i].displayName + "</td>";
                autoSelText += "<td><select id=\"" + arr.obj_array[i].name + "\" onChange=\"autoSelUpdateWs(this)\">";
                for (j = 0; j < arr.obj_array[i].options.length; j++) {
                    autoSelText += "<option value=\"" + arr.obj_array[i].options[j].id + "\">" + arr.obj_array[i].options[j].displayName + "</option>";
                }
                autoSelText += "</select></td>";
                autoSelText += "<td id=\"" + arr.obj_array[i].name + "_cur\">NOT SET</td>";
                autoSelText += "</tr>";
            }
        }

        //Part 2 - update the HTML on the page
        //document.getElementById("webcams").innerHTML = webcamTexts;
        document.getElementById("booleans").innerHTML = booleansCanvasTexts;
        document.getElementById("stringboxes").innerHTML = stringboxCanvasTexts;
        document.getElementById("dials").innerHTML = dialCanvasTexts;
        document.getElementById("autoSel").innerHTML = autoSelText;

        //Part 3 - init the data elements
        for (i = 0; i < arr.obj_array.length; i++) {
            if (arr.obj_array[i].type == "dial") {
                display_objs[arr.obj_array[i].name] = (new casseroleDial("obj" + (arr.obj_array[i].name), arr.obj_array[i].min, arr.obj_array[i].max, arr.obj_array[i].min_acceptable, arr.obj_array[i].max_acceptable, arr.obj_array[i].step, arr.obj_array[i].displayName));
            } else if (arr.obj_array[i].type == "stringbox") {
                display_objs[arr.obj_array[i].name] = (new casseroleStringBox("obj" + (arr.obj_array[i].name), arr.obj_array[i].displayName));
            } else if (arr.obj_array[i].type == "boolean") {
                display_objs[arr.obj_array[i].name] = (new casseroleBooleanDisplay("obj" + (arr.obj_array[i].name), arr.obj_array[i].displayName, arr.obj_array[i].color));
            } else if (arr.obj_array[i].type == "soundWidget") {
                display_objs[arr.obj_array[i].name] = (new casseroleSoundWidget("obj" + (arr.obj_array[i].name), arr.obj_array[i].displayName, arr.obj_array[i].file));
            }
            //ignore other types
        }


    } else if (arr.step == "valUpdate") {
        for (i = 0; i < arr.obj_array.length; i++) {
            if (arr.obj_array[i].type == "webcam") {
                //document.getElementById("crosshair_vert" + arr.obj_array[i].name).setAttribute("style", "background:red;position:absolute;top:" + arr.obj_array[i].marker_y + "%;left:" + arr.obj_array[i].marker_x + "%;width:2px;height:30px;transform:translate(-50%, -50%)");
                //document.getElementById("crosshair_horiz" + arr.obj_array[i].name).setAttribute("style", "background:white;position:absolute;top:" + arr.obj_array[i].marker_y + "%;left:" + arr.obj_array[i].marker_x + "%;width:30px;height:2px;transform:translate(-50%, -50%)");
            } else if (arr.obj_array[i].type == "autosel") {
                autoSelSetCurrent(arr.obj_array[i].name, arr.obj_array[i].val);
            } else {
                display_objs[arr.obj_array[i].name].setValue(arr.obj_array[i].value);
            }

        }
    }
    //ignore other messages


};



//Main Execution