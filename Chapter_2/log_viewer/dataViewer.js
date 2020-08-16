//******************************************************************************************
// Copyright (C) 2019 FRC Team 1736 Robot Casserole - www.robotcasserole.org
//******************************************************************************************
// 
// This software is released under the MIT Licence - see the license.txt
// 



var global_chart;

var numSignals = 0;

var allow_scroll_zoom = true;

var mouse_cursor_center = null;
var time_range_sec = 10.0;

var timeInSeconds = true;
var unitsPresent = false;

var signalNameList = [];

var dflt_options = {

    credits: {
        enabled: false
    },
    chart: {
        zoomType: 'x',
        renderTo: 'container',
        animation: false,
        ignoreHiddenSeries: true,
        resetZoomButton: {
            position: {
                align: 'left',
            },
            theme: {
                fill: '#822',
                stroke: '#999',
                r: 3,
                style: {
                    color: '#999'
                },
                states: {
                    hover: {
                        fill: '#782828',
                        style: {
                            color: '#ccc'
                        },
                    },
                },
            },
        },
        panning: true,
        panKey: 'shift',
        showAxes: true,
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
            stops: [
                [0, 'rgb(0, 0, 0)'], //Yes, both black. Just in case I decide to change back....
                [1, 'rgb(0, 0, 0)']
            ]
        },
    },
    title: {
        text: ''
    },

    xAxis: {
        type: 'linear',
        title: 'Time (sec)',
        lineColor: '#777',
        tickColor: '#444',
        gridLineColor: '#444',
        gridLineWidth: 1,
        labels: {
            style: {
                color: '#DDD',
                fontWeight: 'bold'
            },
        },
        title: {
            style: {
                color: '#D43',
            },
        },
    },

    yAxis: [{ //default Y axis
        title: {
            text: "Value",
            style: {
                color: '#DDD',
            },
        },
        showEmpty: false,
        lineColor: '#777',
        tickColor: '#444',
        gridLineColor: '#444',
        gridLineWidth: 1,
        labels: {
            style: {
                color: '#DDD',
                fontWeight: 'bold'
            },
        },

    }],

    legend: {
        enabled: false
    },

    exporting: {
        enabled: false
    },

    colors: ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF', 
             '#88FF00', '#8800FF', '#0088FF', '#FF8800', '#FF0088', '#00FF88',
             '#FF4444', '#44FF44', '#4444FF', '#FFFF44', '#FF44FF', '#44FFFF', 
             '#88FF44', '#8844FF', '#4488FF', '#FF8844', '#FF4488', '#44FF88',
    ],

    plotOptions: {
        line: {
            marker: {
                radius: 2
            },
            lineWidth: 1,
            threshold: null,
            animation: false,
        }
    },
    tooltip: {
        crosshairs: true,
        hideDelay: 0,
        shared: true,
        backgroundColor: null,
        snap: 30,
        borderWidth: 1,
        borderColor: '#FF0000',
        shadow: true,
        animation: true,
        useHTML: false,
        style: {
            padding: 0,
            color: '#D43',
        },
    },

    series: []
}



//sets mouse_cursor_center to an X value if possible, otherwise null
// This is used by the zoom function to determine where the zoom center should be.
function ChartMouseMoveHandler(e) {
    if (global_chart) {
        e = global_chart.pointer.normalize(e);
        if (global_chart.isInsidePlot(e.chartX - global_chart.plotLeft, e.chartY - global_chart.plotTop)) {
            mouse_cursor_center = global_chart.xAxis[0].toValue(e.chartX);
        } else {
            mouse_cursor_center = null;
        }

    }

}

//Handle mouse wheel ticks to change the zoom on the chart 
function ChartMouseWheelHandler(e) {
    // cross-browser wheel delta
    var e = window.event || e;
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));

    if (global_chart) {
        if (allow_scroll_zoom) {

            //We should adjust the view extents based on the read-in mouse wheel scroll action

            //Grab the current view level (min/max data range)
            old_max = global_chart.xAxis[0].getExtremes().max;
            old_min = global_chart.xAxis[0].getExtremes().min;

            if (old_max == NaN) {
                old_max = global_chart.xAxis[0].getExtremes().dataMax;
            }

            if (old_min == NaN) {
                old_min = global_chart.xAxis[0].getExtremes().dataMin;
            }

            //Calculate the zoom action center (presumed to be center of chart if not yet set)
            if (mouse_cursor_center != null) {
                center = mouse_cursor_center;
            } else {
                center = (old_max + old_min) / 2;
            }


            //Calculate the above/below center widths
            old_right_size = old_max - center;
            old_left_size = center - old_min;

            //Calculate a multiplicative factor (1.0 for no change, > 1.0 for more zoom, < 1.0 for less zoom)
            scaler = (1 - delta * 0.3);

            //Apply the factor to the above/below center widths
            new_right_size = old_right_size * scaler;
            new_left_size = old_left_size * scaler;

            //calculate the new extents
            new_max = center + new_right_size;
            new_min = center - new_left_size;

            //Limit the extents to the available data. Make sure to display the
            // "reset zoom" button if we're not yet looking at the whole chart.
            data_min = global_chart.xAxis[0].getExtremes().dataMin;
            data_max = global_chart.xAxis[0].getExtremes().dataMax;

            if(data_min != null && data_max != null){
                if (data_min > new_min) {
                    new_min = global_chart.xAxis[0].getExtremes().dataMin;
                }

                if (data_max < new_max) {
                    new_max = global_chart.xAxis[0].getExtremes().dataMax;
                }

                //Set the new extents
                global_chart.xAxis[0].setExtremes(new_min, new_max);
            }

        }
    }

    //Return false to prevent this mouse event we're handling here from scrolling the page
    if (e.preventDefault) e.preventDefault();
    return false;
}



function handleFileSelect(files_in) {

    var fileobj = files_in[0];
    var temp_series = [];
    var units_to_yaxis_index = [];
    var yaxis_index = 1;

    var checkboxHTMLString = ""


    //Destroy any existing chart.
    if (global_chart) {
        global_chart.destroy();
    }

    //deep-copy the default chart options
    var options = $.extend(true, {}, dflt_options)


    var reader = new FileReader();
    reader.readAsText(fileobj);
    reader.onload = function (evt) {

        var all_lines = evt.target.result + '';
        var lines = all_lines.split('\n');
        var timestamp = 0;
        var plotter_index = 0;

        var first_timestamp = 0;
        var last_timestamp = 0;


        // Iterate over the lines and add categories or series
        $.each(lines, function (lineNo, line) {
            var items = line.split(',');

            // first line contains signal names. Ignore Time column.
            if (lineNo == 0) {
                plotter_index = 0;
                $.each(items, function (itemNo, item) {
                    if(itemNo == 0){
                        //Parsing name of first column.
                        if(item == "Timestamp"){
                            //If the first row is named "Timestamp", parse the column as a full datetime stamp
                            timeInSeconds = false;
                        } else {
                            //Otherwise, treat it as a floating point number
                            timeInSeconds = true;
                        }
                    } else {
                        //Parsing name of other columns (assumed to be signals)
                        cleanedName = item.replace(/ /g, '').trim();
                        if (item.length > 0) { //skip empty elements
                            //Create a new series in the highCharts plot per signal
                            temp_series.push({
                                name: cleanedName,
                                data: [],
                                visible: false,
                                visibility_counter: 0,
                                yAxis: 0, //temp, will be updated once (or iff) the actual unit is read.
                                states: {
                                    hover: {
                                        enabled: false
                                    },
                                },
                            });
                            signalNameList.push(item)
                            checkboxHTMLString += "<div id=\"checkboxDiv_"+plotter_index+"\"><input type=\"checkbox\" id=\""+plotter_index+"\" onclick=\"checkboxHandler(this)\"> "+ item + "<br></div>"
                            plotter_index++;
                        }
                    }
                });
                numSignals = plotter_index;
                document.getElementById("signalCheckboxes").innerHTML = checkboxHTMLString;
            }

            // second line might contain units...
            else if (lineNo == 1) {
                plotter_index = 0;
                $.each(items, function (itemNo, item) {
                    if(itemNo == 0){
                        if(item == "sec"){
                            //If the first column literally has "sec" as the unit, assume this is a units column
                            unitsPresent = true;
                        } else {
                            //Otherwise, we should continue parsing this line as normal.
                            unitsPresent = false;
                        }

                    } else if (unitsPresent && itemNo > 0 && plotter_index < numSignals) {
                        var unit = item.replace(/ /g, '').trim();
                        temp_series[plotter_index].name = temp_series[plotter_index].name + ' (' + unit + ')';
                        if (!(unit in units_to_yaxis_index)) {
                            units_to_yaxis_index[unit] = yaxis_index;
                            options.yAxis.push({
                                title: {
                                    text: unit,
                                    style: {
                                        color: '#DDD',
                                    },
                                },
                                showEmpty: false,
                                lineColor: '#777',
                                tickColor: '#444',
                                gridLineColor: '#444',
                                gridLineWidth: 1,
                                labels: {
                                    style: {
                                        color: '#DDD',
                                        fontWeight: 'bold'
                                    },
                                },

                            });
                            yaxis_index++;
                        }
                        temp_series[plotter_index].yAxis = units_to_yaxis_index[unit];
                        plotter_index++;
                    }
                });
            }

            // the rest of the lines contain data with their name in the first 
            // position
            else {
                plotter_index = 0;
                $.each(items, function (itemNo, item) {
                    if (itemNo == 0) {

                        //Parse the timestamp down to seconds
                        if(timeInSeconds){
                            timestamp = parseFloat(item);
                        } else {
                            timestamp = Date.parse(item)/1000.0;
                        }

                        // record what the first timestamp had in it
                        if (lineNo == 2) {
                            first_timestamp = timestamp;
                        }
                        last_timestamp = timestamp;

                        //re-scale so plot always starts at zero
                        timestamp = timestamp - first_timestamp;

                    } else if (plotter_index < numSignals) {
                        val_str = item.trim();
                        if (val_str.length > 0) {
                            //Some data items might not have anything in them at a given timestamp
                            temp_series[plotter_index].data.push([timestamp, eval(val_str)]);
                        }
                        plotter_index++;
                    }
                });

            }



        });

        //Add all data to the chart
        $.each(temp_series, function (itemNo, element) {
            options.series.push(element);
        });

        //chart named after its source file
        options.title.text = fileobj.name;

        //Create dat chart
        global_chart = new Highcharts.Chart(options);
        rectifySize()

        //The following chunk of main code and handler functions are to add chart interaction
        // which I find useful, but which highcharts does not implement natively.
        // Namely, I want a mouse-wheel zoom, which centers around wherever the user's mouse
        // is sitting at.
        var sq = {};
        sq.e = document.getElementById("container");

        if (sq.e.addEventListener) {
            sq.e.addEventListener("mousewheel", ChartMouseWheelHandler, false);
            sq.e.addEventListener("DOMMouseScroll", ChartMouseWheelHandler, false);
        }
        else sq.e.attachEvent("onmousewheel", ChartMouseWheelHandler);
        
        sq.e.addEventListener('mousemove', ChartMouseMoveHandler, false);

    };
};

function checkName(filterSpec, name){
    var result = true;

    if(filterSpec.length == 0){
        result = true;
    } else {
        re = new RegExp(filterSpec.toLowerCase().replace(/\*/g,'.*').replace(/\?/g,'.'));
        strMatch = re.test(name.toLowerCase());

        console.log(strMatch)

        if(strMatch){
            result = true;
        } else {
            result = false;
        }
    }

    return result;
}

function updateFilter(elem){
    searchString = elem.value;
    console.log(searchString)
    if(global_chart){
        for(var i = 0; i < numSignals; i++){
            signalName = signalNameList[i]
            elementName = "checkboxDiv_"+i
            if(checkName(searchString, signalName)){
                document.getElementById(elementName).style.display = "block";
            } else {
                document.getElementById(elementName).style.display = "none";
            }
        }
    }

}

function rectifySize(){
    //Need highcharts to flow vertically. Seems like it's stuck at 400px. Huh.
    if(global_chart){
        height = 0.8* window.innerHeight;
        global_chart.setSize(null, height);
        global_chart.reflow();
        global_chart.redraw();
    }
}

function handleClearBtnClick(){
    var i;
    checkBoxList=document.getElementById("signalCheckboxes")
    //Reset all checkboxes to unchecked.
    for(i = 0; i < checkBoxList.children.length; i++){
        checkbox = checkBoxList.children[i].children[0];
        checkbox.checked = false;
    }
    hideAll();
    rectifySize();
}

function checkboxHandler(elem){
    var itemNo = parseInt(elem.id); //This feels like a hack. Ah well.
    if(global_chart) {
        global_chart.series[itemNo].setVisible(elem.checked, false);
    }
    rectifySize();
}


function hideAll() {
    if (global_chart) {
        for (itemNo = 0; itemNo < global_chart.series.length; itemNo++) {
            global_chart.series[itemNo].setVisible(false, false);
        }
    }
}

function openNav() {
    document.getElementById("mySidenav").style.width = "350px";
    document.getElementById("main").style.marginLeft = "350px";
    rectifySize();
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
    rectifySize();
}

