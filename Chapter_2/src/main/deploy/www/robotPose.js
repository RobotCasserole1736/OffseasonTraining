
//Note - this PORT string must be aligned with the port the webserver is served on.
var port = "5806";
var hostname = window.location.hostname + ":" + port;

//Config - adjust this year to year
var ROBOT_W_FT = 2;
var ROBOT_L_FT = 2.5;
var FIELDPOLY_FT =
    [[0, 0],[13.5, 0],[13.5, 54],[-13.5, 54],[-13.5, 0],[0, 0]];  


//Render Constants
var PX_PER_FOOT = 15;
var FIELD_COLOR = '#534F4D';
var FIELD_BG_PX_PER_FOOT = 1306.0/27.0;
var BOT_COLOR = '#d22';
var RED_FIELD_ELEMENT_COLOR = '#FF2D00';
var BLUE_FIELD_ELEMENT_COLOR = '#004CFF';
var TAPE_COLOR = '#FFFFFF';
var CANVAS_MARGIN_PX = 20;

var ROBOT_W_PX = 0;
var ROBOT_L_PX = 0;

var dataSocket = null;

//Websocket variables
window.onload = function() {
    dataSocket = new WebSocket("ws://" + hostname + "/ds")
    numTransmissions = 0;

    dataSocket.onopen = function (event) {
        document.getElementById("id01").innerHTML = "Socket Open";
    
        // Send the command to get the list of all signals
        dataSocket.send(JSON.stringify({ cmd: "getSig" }));
    };
    
    dataSocket.onmessage = function (event) {
        procData(event.data);
        numTransmissions = numTransmissions + 1;
        document.getElementById("id01").innerHTML = "COM Status: Socket Open. RX Count:" + numTransmissions;
    };
    
    dataSocket.onerror = function (error) {
        document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
        alert("ERROR from Robot PoseView: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
    };
    
    dataSocket.onclose = function (error) {
        document.getElementById("id01").innerHTML = "COM Status: Error with socket. Reconnect to robot, open driver station, then refresh this page.";
        alert("ERROR from Robot PoseView: Robot Disconnected!!!\n\nAfter connecting to the robot, open the driver station, then refresh this page.");
    };
    
}
var numTransmissions = 0;
var botDesPoseXSignalName = "botDesPoseX";
var botDesPoseYSignalName = "botDesPoseY";
var botDesPoseTSignalName = "botDesPoseT";
var botActPoseXSignalName = "botActPoseX";
var botActPoseYSignalName = "botActPoseY";
var botActPoseTSignalName = "botActPoseT";

var botDesPoseXSignalID = "";
var botDesPoseYSignalID = "";
var botDesPoseTSignalID = "";
var botActPoseXSignalID = "";
var botActPoseYSignalID = "";
var botActPoseTSignalID = "";

var botPrevDesPoseX = -1; 
var botPrevDesPoseY = -1;
var botPrevActPoseX = -1;
var botPrevActPoseY = -1;


function procData(json_data) {

    //Parse incoming websocket packet as JSON
    var data = JSON.parse(json_data);

    //Grab a reference to the canvases
    this.canvas = document.getElementById("field_bg_canvas");
    this.ctx = this.canvas.getContext("2d");

    this.canvas_robot = document.getElementById("robot_canvas");
    this.ctx_robot = this.canvas_robot.getContext("2d");

    this.canvas_path = document.getElementById("path_canvas");
    this.ctx_path = this.canvas_path.getContext("2d");

    if (data.type == "sig_list") {

        var daq_request_cmd = {};

        var desPoseXFound = false;
        var desPoseYFound = false;
        var desPoseTFound = false;
        var actPoseXFound = false;
        var actPoseYFound = false;
        var actPoseTFound = false;

        for (i = 0; i < data.signals.length; i++) {
            if (data.signals[i].display_name == botDesPoseXSignalName) {
                desPoseXFound = true;
                botDesPoseXSignalID = data.signals[i].id;
            } else if (data.signals[i].display_name == botDesPoseYSignalName) {
                desPoseYFound = true;
                botDesPoseYSignalID = data.signals[i].id;
            } else if (data.signals[i].display_name == botDesPoseTSignalName) {
                desPoseTFound = true;
                botDesPoseTSignalID = data.signals[i].id;
            } else if (data.signals[i].display_name == botActPoseXSignalName) {
                actPoseXFound = true;
                botActPoseXSignalID = data.signals[i].id;
            } else if (data.signals[i].display_name == botActPoseYSignalName) {
                actPoseYFound = true;
                botActPoseYSignalID = data.signals[i].id;
            } else if (data.signals[i].display_name == botActPoseTSignalName) {
                actPoseTFound = true;
                botActPoseTSignalID = data.signals[i].id;
            }
        }

        if (desPoseXFound == false ||
            desPoseYFound == false ||
            desPoseTFound == false ||
            actPoseXFound == false ||
            actPoseYFound == false ||
            actPoseTFound == false ) {
            alert("ERROR from Robot PoseView: Could not find all required signals to drive robot. Not starting.");
            document.getElementById("id01").innerHTML = "COM Status: Socket Open, but signals not found.";

        } else {

            //Handle view init information

            //Get extrema of the described shape and set canvas size
            max_x_px = 0;
            min_x_px = 0;
            max_y_px = 0;
            min_y_px = 0;
            for (i = 0; i < FIELDPOLY_FT.length; i++) {
                x_px = FIELDPOLY_FT[i][0] * PX_PER_FOOT;
                y_px = FIELDPOLY_FT[i][1] * PX_PER_FOOT;

                max_x_px = Math.max(x_px, max_x_px);
                min_x_px = Math.min(x_px, min_x_px);
                max_y_px = Math.max(y_px, max_y_px);
                min_y_px = Math.min(y_px, min_y_px);
            }

            //Adjust width/height of everything based on the field dimensions requested.
            const image = document.getElementById('source');
            bg_image_width_px = image.width / FIELD_BG_PX_PER_FOOT * PX_PER_FOOT;
            bg_image_height_px = image.height / FIELD_BG_PX_PER_FOOT * PX_PER_FOOT;
            this.ctx.canvas.height = bg_image_height_px;
            this.ctx.canvas.width = bg_image_width_px;
            this.ctx_robot.canvas.height = this.ctx.canvas.height;
            this.ctx_robot.canvas.width = this.ctx.canvas.width;
            this.ctx_path.canvas.height = this.ctx.canvas.height;
            this.ctx_path.canvas.width = this.ctx.canvas.width;
            document.getElementById("container").style.height = this.ctx.canvas.height.toString() + "px";
            document.getElementById("container").style.width = this.ctx.canvas.width.toString() + "px";

            this.bot_origin_offset_x = -1 * min_x_px;
            this.bot_origin_offset_y = -1 * min_y_px;

            //Configure the appearance 
            this.ctx.fillStyle = FIELD_COLOR;
            //Draw polygon based on specified points 
            this.ctx.beginPath();
            for (i = 0; i < FIELDPOLY_FT.length; i++) {
                x_px = FIELDPOLY_FT[i][0] * PX_PER_FOOT + this.bot_origin_offset_x;
                y_px = this.ctx.canvas.height - (FIELDPOLY_FT[i][1] * PX_PER_FOOT) + this.bot_origin_offset_y; //transform from software refrence frame to html/js canvas reference frame.

                if (i == 0) {
                    this.ctx.moveTo(x_px, y_px);
                } else {
                    this.ctx.lineTo(x_px, y_px);
                }
            }
            
            this.ctx.closePath();
            this.ctx.fill();

            this.ctx.drawImage(image,0,0,bg_image_width_px, bg_image_height_px);

            
            //Save robot dimensions
            ROBOT_W_PX = ROBOT_W_FT * PX_PER_FOOT;
            ROBOT_L_PX = ROBOT_L_FT * PX_PER_FOOT;

            //Fire up a new DAQ for the robot

            daq_request_cmd.cmd = "addDaq";
            daq_request_cmd.id = "main";
            daq_request_cmd.tx_period_ms = "50"; //Sets the frequency of packet transmit from RIO to this client
            daq_request_cmd.samp_period_ms = "0";
            daq_request_cmd.sig_id_list = [botDesPoseXSignalID, botDesPoseYSignalID, botDesPoseTSignalID,
                                           botActPoseXSignalID, botActPoseYSignalID, botActPoseTSignalID   ];

            //Request data from robot
            var sendVal = JSON.stringify(daq_request_cmd);
            dataSocket.send(sendVal);

            var sendVal = JSON.stringify({ cmd: "start" });
            dataSocket.send(sendVal);

        }

    } else if (data.type == "daq_update") {
        if (data.daq_id == "main") {

            var desPoseXFound = false;
            var desPoseYFound = false;
            var desPoseTFound = false;

            var desPoseX = 0;
            var desPoseY = 0;
            var desPoseT = 0;
			
            var actPoseXFound = false;
            var actPoseYFound = false;
            var actPoseTFound = false;

            var actPoseX = 0;
            var actPoseY = 0;
            var actPoseT = 0;

            for (i = 0; i < data.signals.length; i++) {
                var signal = data.signals[i];
                if (signal.samples.length > 0) {
                    if (signal.id == botDesPoseXSignalID) {
                        desPoseXFound = true;
                        desPoseX = signal.samples[signal.samples.length - 1].val;
                    } else if (signal.id == botDesPoseYSignalID) {
                        desPoseYFound = true;
                        desPoseY = signal.samples[signal.samples.length - 1].val;
                    } else if (signal.id == botDesPoseTSignalID) {
                        desPoseTFound = true;
                        desPoseT = signal.samples[signal.samples.length - 1].val;
                    } else if (signal.id == botActPoseXSignalID) {
                        actPoseXFound = true;
                        actPoseX = signal.samples[signal.samples.length - 1].val;
                    } else if (signal.id == botActPoseYSignalID) {
                        actPoseYFound = true;
                        actPoseY = signal.samples[signal.samples.length - 1].val;
                    } else if (signal.id == botActPoseTSignalID) {
                        actPoseTFound = true;
                        actPoseT = signal.samples[signal.samples.length - 1].val;
                    }
                }
            }

            this.ctx_robot.clearRect(0, 0, this.canvas_robot.width, this.canvas_robot.height);

            if (actPoseXFound == true &&
                actPoseYFound == true &&
                actPoseTFound == true) {
                //Handle robot pose update
                poseX_px  = actPoseX * PX_PER_FOOT + this.bot_origin_offset_x;
                poseY_px  = (ctx.canvas.height - actPoseY * PX_PER_FOOT) + this.bot_origin_offset_y;
                drawRobot(this.ctx_robot, poseX_px, poseY_px, actPoseT, true);
                //Draw new line segment
                drawPathSegment(this.ctx_path, poseX_px, poseY_px,botPrevActPoseX,botPrevActPoseY,true);
                botPrevActPoseX = poseX_px;
                botPrevActPoseY = poseY_px;
            }

            if (desPoseXFound == true &&
                desPoseYFound == true &&
                desPoseTFound == true) {
                //Handle robot pose update
                poseX_px  = desPoseX * PX_PER_FOOT + this.bot_origin_offset_x;
                poseY_px  = (ctx.canvas.height - desPoseY * PX_PER_FOOT) + this.bot_origin_offset_y;
                drawRobot(this.ctx_robot, poseX_px, poseY_px, desPoseT, false);
                //draw new line segment
                drawPathSegment(this.ctx_path, poseX_px, poseY_px,botPrevDesPoseX,botPrevDesPoseY,false);
                botPrevDesPoseX = poseX_px;
                botPrevDesPoseY = poseY_px;
            }
        }
    }
}

drawRobot = function (ctx_in, x_pos_px, y_pos_px, rotation_deg, isActual) {

    //Draw the robot itself

    //Tweak rotation to match the javascript canvas draw angle
    rotation_deg -= 90;
    rotation_deg *= -1;

    //Rotate to robot reference frame
    ctx_in.translate(x_pos_px, y_pos_px);
    ctx_in.rotate(rotation_deg * Math.PI / 180);

    //Draw robot body
	if(isActual){
        //Solid filled in red robot is for Actual
        ctx_in.beginPath();
        ctx_in.strokeStyle = "black";
        ctx_in.lineWidth = "1";
        ctx_in.rect(-ROBOT_W_PX / 2, -ROBOT_L_PX / 2, ROBOT_W_PX, ROBOT_L_PX);
        ctx_in.closePath();
        ctx_in.stroke();
        ctx_in.fillStyle = "red";
        ctx_in.fillRect(-ROBOT_W_PX / 2, -ROBOT_L_PX / 2, ROBOT_W_PX, ROBOT_L_PX);
        
	} else {
        //Outlined blue robot is for Desired
        ctx_in.beginPath();
        ctx_in.strokeStyle = "blue";
        ctx_in.lineWidth = "3";
        ctx_in.rect(-ROBOT_W_PX / 2, -ROBOT_L_PX / 2, ROBOT_W_PX, ROBOT_L_PX);
        ctx_in.closePath();
        ctx_in.stroke();

        ctx_in.beginPath();
        ctx_in.strokeStyle = "black";
        ctx_in.lineWidth = "1";
        ctx_in.rect(-ROBOT_W_PX / 2, -ROBOT_L_PX / 2, ROBOT_W_PX, ROBOT_L_PX);
        ctx_in.closePath();
        ctx_in.stroke();
    }
    
    //Draw front-of-robot arrowhead
    drawArrowhead(ctx_in, 0, ROBOT_L_PX / 2, 0, -ROBOT_L_PX / 3, 8);
    
    //Undo rotation
    ctx_in.rotate(-1 * rotation_deg * Math.PI / 180);
    ctx_in.translate(-x_pos_px, -y_pos_px);

    //Draw robot centroid marker
    ctx_in.beginPath();
    ctx_in.strokeStyle = "black";
    ctx_in.lineWidth = "1";
    ctx_in.moveTo(x_pos_px-5, y_pos_px);
    ctx_in.lineTo(x_pos_px+5, y_pos_px);
    ctx_in.moveTo(x_pos_px, y_pos_px-5);
    ctx_in.lineTo(x_pos_px, y_pos_px+5);
    ctx_in.closePath();
    ctx_in.stroke(); 
}

drawPathSegment = function(ctx_in, x_start_px, y_start_px, x_end_px, y_end_px, isActual){
    
    if(x_end_px >= 0 && y_end_px >= 0){
        //Draw the line segment for the most recent path taken
        ctx_in.beginPath();
        if(isActual){
            ctx_in.strokeStyle = "red";
            ctx_in.lineWidth = "1";
        } else {
            ctx_in.strokeStyle = "blue";
            ctx_in.lineWidth = "1";
        }
        ctx_in.moveTo(x_start_px, y_start_px);
        ctx_in.lineTo(x_end_px, y_end_px);
        ctx_in.closePath();
        ctx_in.stroke(); 
    }
}

function handlePathClearBtnClick() {
    this.ctx_path.clearRect(0, 0, this.canvas_path.width, this.canvas_path.height);
}


function drawArrowhead(context_in, from_x, from_y, to_x, to_y, radius) {
    var x_center = to_x;
    var y_center = to_y;

    var angle;
    var x;
    var y;

    context_in.beginPath();
    context_in.strokeStyle = "white";
    context_in.lineWidth = "-4";
    context_in.fillStyle = '#000';

    angle = Math.atan2(to_y - from_y, to_x - from_x)
    x = radius * Math.cos(angle) + x_center;
    y = radius * Math.sin(angle) + y_center;

    context_in.moveTo(x, y);

    angle += (1.0 / 3.0) * (2 * Math.PI)
    x = radius * Math.cos(angle) + x_center;
    y = radius * Math.sin(angle) + y_center;

    context_in.lineTo(x, y);

    angle += (1.0 / 3.0) * (2 * Math.PI)
    x = radius * Math.cos(angle) + x_center;
    y = radius * Math.sin(angle) + y_center;

    context_in.lineTo(x, y);

    context_in.closePath();

    context_in.fill();
}