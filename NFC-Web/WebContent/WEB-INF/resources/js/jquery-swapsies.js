/*!
* jQuery Swapsie Plugin
* Examples and documentation at: http://biostall.com/swap-and-re-order-divs-smoothly-using-jquery-swapsie-plugin
    * Copyright (c) 2010 Steve Marks - info@biostall.com
* Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
    * Version: 1 (09-JULY-2010)
*/
var swapping = false;
(function ($) {
    $.fn.extend({
        swap: function (options) { // Init
            var defaults = {
                target: "",
                speed: 1000,
                opacity: "1",
                callback: function () {}
            };
            swapping = options.swapping;
            options = $.extend(defaults, options);
            // Get the elements
            return this.each(function () {
                var source = $(this);
                if (options.target != "" && !swapping) {
                    var target = options.target;
                    swapping = true;
                    // Set positions for the elements
                    var attributes = new Array();
                    attributes['source'] = source.css("position");
                    attributes['target'] = target.css("position");
                    if (attributes['source'] != "relative" && attributes['source'] != "absolute") {
                        source.css("position", "relative");
                    }
                    if (attributes['target'] != "relative" && attributes['target'] != "absolute") {
                        target.css("position", "relative");
                    }
                    // Movement
                    var position = new Array();
                    var totals = new Array();
                    var totals_source = new Array();
                    var totals_target = new Array();
                    position['source'] = source.offset();
                    position['target'] = target.offset();
                    var height = new Array();
                    height['source'] = source.height();
                    height['target'] = target.height();
                    var width = new Array();
                    width['source'] = source.width();
                    width['target'] = target.width();
                    // y-axis movement
                    var direction_y = new Array();
                    direction_y['source'] = '-';
                    direction_y['target'] = '-';
                    if (position['source'].top <= position['target'].top) {
                        direction_y['source'] = '+';
                        totals['y'] = position['target'].top - position['source'].top;
                    } else {
                        totals['y'] = position['source'].top - position['target'].top;
                    }
                    if (direction_y['source'] == '-') {
                        direction_y['target'] = '+';
                    } else {
                        direction_y['target'] = '-';
                    }
                    // correct the values
                    totals_source['y'] = totals['y'];
                    totals_target['y'] = totals['y'];
                    if (height['source'] != height['target']) {
                        // set the difference
                        if (height['source'] > height['target']) {
                            var diff = height['source'] - height['target'];
                            if (direction_y['source'] == '-') {
                                totals_target['y'] = totals['y'] + diff;
                            } else {
                                totals_source['y'] = totals['y'] - diff;
                            }
                        } else {
                            var diff = height['target'] - height['source'];
                            if (direction_y['source'] == '-') {
                                totals_target['y'] = totals['y'] - diff;
                            } else {
                                totals_source['y'] = totals['y'] + diff;
                            }
                        }
                    }
                    // x-axis movement
                    var direction_x = new Array();
                    direction_x['source'] = '-';
                    direction_x['target'] = '-';
                    if (position['source'].left <= position['target'].left) {
                        direction_x['source'] = '+';
                        totals['x'] = position['target'].left - position['source'].left;
                    } else {
                        totals['x'] = position['source'].left - position['target'].left;
                    }
                    if (direction_x['source'] == '-') {
                        direction_x['target'] = '+';
                    } else {
                        direction_x['target'] = '-';
                    }
                    // correct the values
                    totals_source['x'] = totals['x'];
                    totals_target['x'] = totals['x'];
                    if (width['source'] != width['target']) {
                        // set the difference
                        if (width['source'] > width['target']) {
                            var diff = width['source'] - width['target'];
                            if (direction_y['source'] == '-') {
                                totals_target['x'] = totals['x'] + diff;
                            } else {
                                totals_source['x'] = totals['x'] - diff;
                            }
                        } else {
                            var diff = width['target'] - width['source'];
                            if (direction_y['source'] == '-') {
                                totals_target['x'] = totals['x'] - diff;
                            } else {
                                totals_source['x'] = totals['x'] + diff;
                            }
                        }
                    }
                    // Do the swapping
                    source.animate({
                        opacity: options.opacity
                    }, 100, function () {
                        source.animate({
                            top: direction_y['source'] + "=" + totals_source['y'] + "px",
                            left: direction_x['source'] + "=" + totals_source['x'] + "px"
                        }, options.speed, function () {
                            source.animate({
                                opacity: "1"
                            }, 100);
                        });
                    });
                    target.animate({
                        opacity: options.opacity
                    }, 100, function () {
                        target.animate({
                            top: direction_y['target'] + "=" + totals_target['y'] + "px",
                            left: direction_x['target'] + "=" + totals_target['x'] + "px"
                        }, options.speed, function () {
                            target.animate({
                                opacity: "1"
                            }, 100, function () {
                                swapping = false;
                                // call the callback and apply the scope:
                                options.callback.call(this);
                            });
                        });
                    });
                }
            });
        }
    });
})(jQuery);