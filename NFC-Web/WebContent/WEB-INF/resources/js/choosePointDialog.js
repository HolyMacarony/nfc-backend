var currentDate;
var currentInvoker;

// jQuery(document).ready(function(){
// });


function setCurrentDate(xhr, status, args) {
    if (typeof args.date !== 'undefined') {
        currentDate = args.date;
    }
}

function setCurrentInvoker(xhr, status, args) {
    if (typeof args.invoker !== 'undefined') {
        currentInvoker = args.invoker;
    }
}


//get current year
function getHikesectionDateJS(xhr, status, args) {
    if (args && !args.validationFailed) {
        alert("good " + args);
    } else {
        alert("shit")
    }
}


function choosePointDialog() {
    //restrict max date to today
    PF('datepicker').jqEl.datepicker("option", "maxDate", +0);

    var topPosOfDialog;
    document.getElementById('weathermap').innerHTML = "<div id='map' style='width: 100%; height: 100%;'></div>";
    var map = new L.Map('map', {crs: L.CRS.EPSG4326, minZoom: 13, maxZoom: 18}).setView([48.12, 11.54], 18);
    var popup = L.popup();
    var marker;


    let DefaultIcon = L.icon({
        iconUrl: '/images/marker-icon.png',
        iconRetinaUrl: '/images/marker-icon-2x.png',
        shadowUrl: '/images/marker-shadow.png',

        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        tooltipAnchor: [16, -28],
        shadowSize: [41, 41]
    });

    L.Marker.prototype.options.icon = DefaultIcon;

    // handle marker position and backing bean update with position
    function onMapClick(e) {
        if (typeof marker !== 'undefined') {
            map.removeLayer(marker)
        }
        marker = L.marker(e.latlng, {draggable: true}).addTo(map);

        //update backing bean
        setLocation([{
            name: 'latitude',
            value: e.latlng.lat
        }, {
            name: 'longitude',
            value: e.latlng.lng
        }, {
            name: 'invoker',
            value: currentInvoker
        }]);
        currentInvoker = null;

    }

    //find initial position of client
    function onLocationFound(e) {
        var radius = Math.round(e.accuracy / 2);
        L.marker(e.latlng).addTo(map)
            .bindPopup("Sie sind im Umkreis von " + radius + " Metern um diesen Punkt").openPopup();
        L.circle(e.latlng, radius).addTo(map);
    }

    function onLocationError(e) {
        console.log(e.message);
    }


    map.on('click', onMapClick);
    map.locate({setView: true});
    map.on('locationfound', onLocationFound);
    map.on('locationerror', onLocationError);

    //basic
    // L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    //     attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    // }).addTo(map);

    // var wmsLayer = L.tileLayer.wms('http://ohm.f4.htw-berlin.de:8181/geoserver/ohdm_t/wms', {
    //     layers: 'ohdm_t:building_points,ohdm_t:geological_polygons,ohdm_t:Highways',//todo all layers from ohdm v2
    //     transparent: true,
    //     format: 'image/png',
    //     date: "2012-01-01",
    // }).addTo(map);

    //load via WMS plugin
// Default usage (uses L.WMS.Overlay)

    //add WMS layers of OHDM geoserver
    var MySource = L.WMS.Source.extend({

        'ajax': function (url, callback) {
            $.ajax(url, {
                'context': this,
                'success': function (result) {
                    callback.call(this, result);
                }
            });
        },
        'showFeatureInfo': function (latlng, info) {
            // popup
            //     .setLatLng(latlng)
            //     .setContent("Sie haben hier geklickt: " + latlng.toString() + " mit folgendem Objekt: " + info.toString())
            //     .openOn(map);
            // $('.output').html(info);
            // alert(info);
        }
    });
    // getHikesectionDateJSF();
    var options = {day: 'numeric', month: 'numeric', year: 'numeric'};
    var thedate = PF('datepicker').getDate();
    //TODO get Date from datepicker
    var cqlTemplateTimeFilter = "\'{{DATE}}\' >= valid_since AND \'{{DATE}}\' <= valid_until";
    // thedate.toLocaleDateString("de-DE",options)
    // thedate = typeof thedate !== 'undefined' ? thedate.toLocaleDateString("de-DE",options) : new Date().toLocaleDateString("de-DE",options);
    var datestring = thedate.getFullYear() + "-" + thedate.getMonth() + "-" + thedate.getDate();
    var cqlTimeFilter = cqlTemplateTimeFilter.replace(new RegExp('{{DATE}}', 'g'), datestring);
    var options = {
        'maxZoom': 18,
        'maxNativeZoom': 18,
        'transparent': true,
        'tiled': true,
        'identify': true,
        'version': '1.3.0',
        format: 'image/png',
        'cql_filter': cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter+";"+cqlTimeFilter
    };
//"'2015-12-15' >= valid_since AND '2015-12-15' <= valid_until"

    var source = new MySource("http://ohm.f4.htw-berlin.de:8181/geoserver/ohdm_t/wms", options);
    var boundaries_admin_2 = source.getLayer('ohdm_t:boundaries_admin_2');
    var boundaries_admin_4 = source.getLayer('ohdm_t:boundaries_admin_4');
    var boundaries_admin_6 = source.getLayer('ohdm_t:boundaries_admin_6');
    var boundaries_admin_9 = source.getLayer('ohdm_t:boundaries_admin_9');
    var highway_small_lines = source.getLayer('ohdm_t:highway_small_lines');
    var highway_tertiary_lines = source.getLayer('ohdm_t:highway_tertiary_lines');
    var highway_huge_lines = source.getLayer('ohdm_t:highway_huge_lines');
    var highway_secondary_lines = source.getLayer('ohdm_t:highway_secondary_lines');
    var highway_primary_lines = source.getLayer('ohdm_t:highway_primary_lines');
    var highway_path_lines = source.getLayer('ohdm_t:highway_path_lines');
    var railway_lines = source.getLayer('ohdm_p:railway_lines');
    var shop_points = source.getLayer('ohdm_t:shop_points');
    var public_transport_points = source.getLayer('ohdm_t:ohdm_t_public_transport_points');
    var aeroway_points = source.getLayer('ohdm_t:aeroway_points');
    var craft_polygons = source.getLayer('ohdm_t:craft_polygons');
    var building_polygons = source.getLayer('ohdm_t:building_polygons');
    var natural_polygons = source.getLayer('ohdm_t:natural_polygons');
    var military_polygons = source.getLayer('ohdm_t:military_polygons');
    var waterway_polygons = source.getLayer('ohdm_t:waterway_polygons');
    var geological_polygons = source.getLayer('ohdm_t:geological_polygons');
    var aeroway_polygons = source.getLayer('ohdm_t:aeroway_polygons');
    var emergency_polygons = source.getLayer('ohdm_t:emergency_polygons');
    var landuse_brown = source.getLayer('ohdm_t:landuse_brown');
    var landuse_commercialetc = source.getLayer('ohdm_t:landuse_commercialetc');
    var landuse_freegreenandwood = source.getLayer('ohdm_t:landuse_freegreenandwood');
    var landuse_gardeningandfarm = source.getLayer('ohdm_t:landuse_gardeningandfarm');
    var landuse_industrial = source.getLayer('ohdm_t:landuse_industrial');
    var layers = L.layerGroup([
        boundaries_admin_2,
        boundaries_admin_4,
        boundaries_admin_6,
        boundaries_admin_9,
        highway_tertiary_lines,
        highway_huge_lines,
        highway_secondary_lines,
        highway_primary_lines,
        highway_small_lines,
        // highway_path_lines,
        // shop_points,
        // public_transport_points,
        // aeroway_points,
        // railway_lines,
        craft_polygons,
        building_polygons,
        natural_polygons,
        military_polygons,
        waterway_polygons,
        geological_polygons,
        aeroway_polygons,
        emergency_polygons,
        landuse_brown,
        landuse_commercialetc,
        landuse_freegreenandwood,
        landuse_gardeningandfarm,
        landuse_industrial
    ]);

    // var control = L.control.layers(null,{
    //     // 'all': layers.addTo(map)
    // });
    layers.addTo(map);
    // source.addSubLayer('DIE POLYGONE');
    // source.addTo(map);

// #########################################################

// Default usage (uses L.WMS.Overlay)
//     var source = L.WMS.source("http://ohm.f4.htw-berlin.de:8080/geoserver/ohdm_t/wms", {
//         'transparent': true
//     });
//     source.getLayer("ohdm_t:building_points").addTo(map);
//     source.getLayer("ohdm_t:Highways").addTo(map);

    var control = L.control.layers(null, null).addTo(map);
    // var control;
    // var overlayMaps = null;
    // render geometries on top of map
    map.on('moveend', function (e) {


// //todo JAHRESZAHL EINFÜGEN....  -_-'
        const wfstPointOptions = {
            crs: L.CRS.EPSG4326,
            showExisting: false,
            geometryField: 'polygon',
            url: 'http://ohdm.f4.htw-berlin.de:8181/geoserver/wfs',
            typeNS: 'test',
            typeName: 'building_polygons',
            maxFeatures: 10000,
            opacity: 1,
            filter: new L.Filter.BBox('polygon', map.getBounds(), L.CRS.EPSG4326),
            // bbox: map.getBounds().toBBoxString(),
            style: function (layer) {
                // you can use if statemt etc
                return {
                    color: 'green',
                    weight: 1
                }
            },
        };
        var wfstPoint = new L.WFST(wfstPointOptions, new L.Format.GeoJSON({crs: L.CRS.EPSG4326}));
        wfstPoint.addTo(map);
        // control.removeLayer(wfstPoint);
        // control.addOverlay(wfstPoint, "voll die building polygone");

        //  control = L.control.layers(null,{
        //     "Buildings": wfstPoint});
        // var overlayMaps = {
        //     "buildings": wfstPoint
        // };
        // var overlays = {
        //     "Buildings": wfstPoint
        // };
        // Whenever you want to remove all overlays:
        // for (var name in overlays) {
        //     map.removeLayer("buildings");
        // }
        // control.layers(null, overlayMaps);
        // map.addLayer(overlayMaps);


        // map.removeLayer(wfstPoint);
        // overlayMaps = L.layerGroup([
        //     wfstPoint
        // ]);
        //
        // map.addLayer(overlayMaps);


        // var control = L.control.layers(null,{
        //     'buldings': wfstPoint.addTo(map)
        // });


        // wfstPoint

    });
    // control.addOverlay(overlayMaps, 'Polygons');
    // var control = L.control.layers(null,{
    //     // 'all': layers.addTo(map)
    // });
    // control.addTo(map);
    // control.addTo(map);
    // });


// ################################################

    // var map = L.map('map', {editable: true}).setView([0, 0], 2);

    // add an OpenStreetMap tile layer
    // L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    //     attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    // }).addTo(map);
    //
    // var wfst = new L.WFST({
    //     url: 'http://ohdm.f4.htw-berlin.de:8080/geoserver/wfs',
    //     typeNS: 'ics',
    //     typeName: 'Highways',
    //     maxFeatures: 100,
    //     crs: L.CRS.EPSG4326,
    //
    //     geometryField: 'polygon',
    //     style: {
    //         color: 'blue',
    //         weight: 3
    //     }
    // }, new L.Format.GeoJSON({crs: L.CRS.EPSG4326}))
    //     .addTo(map)
    //     .once('load', function () {
    //         map.fitBounds(wfst);
    //     });

// ################################################
    // var boundaries = new L.WFS({
    //     url: 'http://ohdm.f4.htw-berlin.de:8080/geoserver/wfs',
    //     typeNS: 'topp',
    //     typeName: 'Highways',
    //     crs: L.CRS.EPSG4326,
    //     style: {
    //         color: 'blue',
    //         weight: 2
    //     }
    // }).addTo(map)
    //     .on('load', function () {
    //         map.fitBounds(boundaries);
    //     })
// --------------------------------
//     var owsrootUrl = 'http://ohdm.f4.htw-berlin.de:8080/geoserver/wfs';
//
//     var defaultParameters = {
//         service : 'WFS',
//         version : '2.0',
//         request : 'GetFeature',
//         typeName : 'Highways',
//         outputFormat : 'text/javascript',
//         format_options : 'callback:getJson',
//         SrsName : 'EPSG:4326'
//     };
//
//     var parameters = L.Util.extend(defaultParameters);
//     var URL = owsrootUrl + L.Util.getParamString(parameters);
//
//     var WFSLayer = null;
//     var ajax = $.ajax({
//         url : URL,
//         dataType : 'jsonp',
//         jsonpCallback : 'getJson',
//         success : function (response) {
//             WFSLayer = L.geoJson(response, {
//                 style: function (feature) {
//                     return {
//                         stroke: false,
//                         fillColor: 'FFFFFF',
//                         fillOpacity: 0
//                     };
//                 },
//                 onEachFeature: function (feature, layer) {
//                     popupOptions = {maxWidth: 200};
//                     layer.bindPopup("Popup text, access attributes with feature.properties.ATTRIBUTE_NAME"
//                         ,popupOptions);
//                 }
//             }).addTo(map);
//         }
//     });
    // -----------------------------------------------------------------


    // var source = new MySource("http://ohm.f4.htw-berlin.de:8080/geoserver/ohdm_t/wms", {
    //     'transparent': true,
    //     'identify' :false
    // });
    // source.getLayer("ohdm_t:building_points").addTo(map);
    // source.getLayer("ohdm_t:Highways").addTo(map);


    var geocoder = L.Control.geocoder({
        position: "bottomleft",
        placeholder: "Suchen",
        collapsed: false,
        defaultMarkGeocode: false
    })
        .on('markgeocode', function (e) {
            var bbox = e.geocode.bbox;
            var poly = L.polygon([
                bbox.getSouthEast(),
                bbox.getNorthEast(),
                bbox.getNorthWest(),
                bbox.getSouthWest()
            ]);
            map.fitBounds(poly.getBounds());
        }).addTo(map);

    L.control.fullscreen({
        position: 'topleft', // change the position of the button can be topleft, topright, bottomright or bottomleft, defaut topleft
        title: 'Show me the fullscreen !', // change the title of the button, default Full Screen
        titleCancel: 'Exit fullscreen mode', // change the title of the button when fullscreen is on, default Exit Full Screen
        content: null, // change the content of the button, can be HTML, default null
        forceSeparateButton: true, // force seperate button to detach from zoom buttons, default false
        forcePseudoFullscreen: false, // force use of pseudo full screen even if full screen API is available, default false
        fullscreenElement: false // Dom element to render in full screen, false by default, fallback to map._container
    }).addTo(map);

// events are fired when entering or exiting fullscreen.
    map.on('enterFullscreen', function () {
        console.log('entered fullscreen');
        topPosOfDialog = $(map.getContainer()).parent().parent().parent().parent().position().top;
    });

    map.on('exitFullscreen', function () {
        console.log('exited fullscreen');
        $(map.getContainer()).parent().parent().parent().parent().css({position: 'fixed', top: topPosOfDialog});
    });


    $("#map input[type=text]").css('position', 'relative');
    $("#map input[type=text]").css('z-index', 3001);
};