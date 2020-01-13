function swapElements(obj1, obj2) {
    // create marker element and insert it where obj1 is
    var temp = document.createElement("div");
    obj1.parentNode.insertBefore(temp, obj1);

    // move obj1 to right before obj2
    obj2.parentNode.insertBefore(obj1, obj2);

    // move obj2 to right before where obj1 used to be
    temp.parentNode.insertBefore(obj2, temp);

    // remove temporary marker node
    temp.parentNode.removeChild(temp);
}

$(document).ready(function () {
//     PF('myHikeWidget').updateRowKeys = function () {
//         console.log('UPDATE ROW KEYS');
//         // call the generic implementation:
//         // PrimeFaces.widget.VerticalTree.prototype.updateRowKeys();
//         PrimeFaces.widget.VerticalTree.prototype.updateRowKeys.call(this);
//     };


//     PF('myHikeWidget').init = function(cfg) {
//     console.log('node INIT');
//     // call the generic implementation:
//         PrimeFaces.widget.VerticalTree.prototype.init(cfg);
//     $("button.ui-button.ui-widget.ui-state-default[data-move-button]").click(function (event) {
//         var swapsource, swaptarget;
//
//         // swapsource = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement);
//         swapsource = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement);
//
//         if ($(event.currentTarget).attr('data-move-button') === 'down') {
//             // value : $(event.target).parent().nextAll('li[data-nodetype="section"]:first').not($(event.target).parent()).attr('data-rowkey')
//             // swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).nextAll('li[data-nodetype=' + swapsource.attr('data-nodetype') + ']:first');
//             swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).next('div:has(li[data-nodetype=' + swapsource.children(":first").attr('data-nodetype') + ']):first');
//         }
//         else {
//             // swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).prevAll('li[data-nodetype=' + swapsource.attr('data-nodetype') + ']:first');
//             swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).prev('div:has(li[data-nodetype=' + swapsource.children(":first").attr('data-nodetype') + ']):first');
//         }
//         if (typeof swaptarget[0] !== 'undefined') {
//             var index;
//             var sourceSwap, targetSwap;
//             for (index = 0; index < swapsource.length; ++index) {
//                 //check type
//                 if (typeof swaptarget[index].children[0].attributes['id'] !== 'undefined') {
//                     sourceSwap = $(swapsource[index]);
//                     targetSwap = $(swaptarget[index]);
//
//                     $(swapsource[index]).swap({
//                         target: $(swaptarget[index]), // Mandatory. The ID of the element we want to swap with
//                         opacity: "1", // Optional. If set will give the swapping elements a translucent effect while in motion
//                         speed: 1000, // Optional. The time taken in milliseconds for the animation to occur
//                         callback: function () { // swap elements in DOM
//                             // sourceSwap.removeAttr("style");
//                             // targetSwap.removeAttr("style");
//                             // var tmp = sourceSwap.html();
//                             // sourceSwap.empty().append(targetSwap.html());
//                             // targetSwap.empty().append(tmp);
//
//
//                             swapElements(swapsource[0], swaptarget[0]);
//
//                             console.log(sourceSwap);
//                             console.log(targetSwap);
//                             initTree();
//                         }
//                     });
//                 }
//                 // else {
//                 //     $(swapsource[index]).swap({
//                 //         target: $(swaptarget[index]), // Mandatory. The ID of the element we want to swap with
//                 //         opacity: "1", // Optional. If set will give the swapping elements a translucent effect while in motion
//                 //         swapping: false,
//                 //         speed: 1000, // Optional. The time taken in milliseconds for the animation to occur
//                 //         callback: function () { // Optional. Callback function once the swap is complete
//                 //             // alert("Swap Complete");
//                 //         }
//                 //     });
//                 // }
//             }
//         }
//     });
});


// //prevent dialogs from closing when form is invalid
// function hideDialogOnSuccess(args, dialogWidgetVar) {
//     if (args && !args.validationFailed) {
//         PF(dialogWidgetVar).hide();
//     }
//     else {
//         PF(dialogWidgetVar).jq.effect("shake", {
//             times: 6
//         }, 300);
//     }
// }

//my old sample
function handleLoginRequest(xhr, status, args) {
    if (args.validationFailed || !args.loggedIn) {
        PF('dlg').jq.effect("shake", {
            times: 5
        }, 500);
    } else {
        PF('dlg').hide();
        $('#loginLink').fadeOut();
    }
}

$(document).on("click", "button.ui-button.ui-widget.ui-state-default[data-move-button]", function (event) {
    var swapsource, swaptarget;

    // swapsource = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement);
    swapsource = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement);

    if ($(event.currentTarget).attr('data-move-button') === 'down') {
        // value : $(event.target).parent().nextAll('li[data-nodetype="section"]:first').not($(event.target).parent()).attr('data-rowkey')
        // swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).nextAll('li[data-nodetype=' + swapsource.attr('data-nodetype') + ']:first');
        swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).next('div:has(li[data-nodetype=' + swapsource.children(":first").attr('data-nodetype') + ']):first');
    }
    else {
        // swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).prevAll('li[data-nodetype=' + swapsource.attr('data-nodetype') + ']:first');
        swaptarget = $(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).prev('div:has(li[data-nodetype=' + swapsource.children(":first").attr('data-nodetype') + ']):first');
    }
    if (typeof swaptarget[0] !== 'undefined') {
        var index;
        var sourceSwap, targetSwap;
        for (index = 0; index < swapsource.length; ++index) {
            //check type
            if (typeof swaptarget[index].children[0].attributes['id'] !== 'undefined') {
                sourceSwap = $(swapsource[index]);
                targetSwap = $(swaptarget[index]);

                $(swapsource[index]).swap({
                    target: $(swaptarget[index]), // Mandatory. The ID of the element we want to swap with
                    opacity: "1", // Optional. If set will give the swapping elements a translucent effect while in motion
                    speed: 1000, // Optional. The time taken in milliseconds for the animation to occur
                    callback: function () { // swap elements in DOM
                        // sourceSwap.removeAttr("style");
                        // targetSwap.removeAttr("style");
                        // var tmp = sourceSwap.html();
                        // sourceSwap.empty().append(targetSwap.html());
                        // targetSwap.empty().append(tmp);


                        // swapElements(swapsource[0], swaptarget[0]);

                        console.log(sourceSwap);
                        console.log(targetSwap);
                        initTree();
                    }
                });
            }
            // else {
            //     $(swapsource[index]).swap({
            //         target: $(swaptarget[index]), // Mandatory. The ID of the element we want to swap with
            //         opacity: "1", // Optional. If set will give the swapping elements a translucent effect while in motion
            //         swapping: false,
            //         speed: 1000, // Optional. The time taken in milliseconds for the animation to occur
            //         callback: function () { // Optional. Callback function once the swap is complete
            //             // alert("Swap Complete");
            //         }
            //     });
            // }
        }
    }
});

// function markDeleteButton(xhr, status, args) {
//     var message = args.tests;
//     console.log(message);
//
// }

// function hideTooltips() {
//     for (var w in PrimeFaces.widgets) {
//         if (w.startsWith('eventToolTipVar') || w.startsWith('sectionToolTipVar') || w.startsWith('userToolTipVar')) {
//             PrimeFaces.widgets[w]._hide();
//         }
//     }
// }
//
// function removeMarkAndToolTip(event, terminator = false) {
//
//     function checkTimeout(timer1, timer2, timer3) {
//         clearTimeout(timer1);
//         clearTimeout(timer2);
//         clearTimeout(timer3);
//     }
//
//     if (typeof timer1 !== 'undefined' && typeof timer2 !== 'undefined' && typeof timer3 !== 'undefined') {
//         checkTimeout(timer1, timer2, timer3);
//     }
//     if (!terminator) {
//         timer1 = setTimeout(hideTooltips, 5500);
//         timer2 = setTimeout(clearToBeDeletedEventDelayed.bind(null, event), 5000);
//         timer3 = setTimeout(clearToBeDeletedSectionDelayed.bind(null, event), 5000);
//     }
// }

//
// function clearToBeDeletedEventDelayed(event) {
//     clearToBeDeletedEvent();
//     refreshPrimefacesWidget(event);
// }
//
// function clearToBeDeletedSectionDelayed(event) {
//     clearToBeDeletedSection();
//     refreshPrimefacesWidget(event);
// }
//

//
// function refreshPrimefacesWidget(event) {
//     updatePrimefacesWidget([{
//         name: 'toBeRefreshedWidgetID',
//         value: "widget_" + event.target.parentElement.id.replace(/:/g, "_")
//     }]);
// }


//https://github.com/primefaces/primefaces/blob/master/src/main/resources/META-INF/resources/primefaces/tree/tree.js
var MyApp = {};
/**
 * PrimeFaces Base Tree Widget
 */
PrimeFaces.widget.BaseTree = PrimeFaces.widget.BaseWidget.extend({
    init: function (cfg) {
        this._super(cfg);
        this.cfg.highlight = (this.cfg.highlight === false) ? false : true;
        this.focusedNode = null;

        if (!this.cfg.disabled) {
            if (this.cfg.selectionMode) {
                this.initSelection();
            }

            this.bindEvents();

            this.jq.data('widget', cfg.widgetVar);
        }
    },
    initSelection: function () {
        this.selectionHolder = $(this.jqId + '_selection');
        var selectionsValue = this.selectionHolder.val();
        this.selections = selectionsValue === '' ? [] : selectionsValue.split(',');

        if (this.cursorNode) {
            this.cursorNode = this.jq.find('.ui-treenode[data-rowkey="' + this.cursorNode.data('rowkey') + '"]');
        }

        if (this.isCheckboxSelection()) {
            this.preselectCheckbox();
        }
    },
    bindContextMenu: function (menuWidget, targetWidget, targetId, cfg) {
        var nodeContentSelector = targetId + ' .ui-tree-selectable',
            nodeEvent = cfg.nodeType ? cfg.event + '.treenode.' + cfg.nodeType : cfg.event + '.treenode',
            containerEvent = cfg.event + '.tree';

        $(document).off(nodeEvent, nodeContentSelector).on(nodeEvent, nodeContentSelector, null, function (e) {
            var nodeContent = $(this);

            if ($(e.target).is(':not(.ui-tree-toggler)') && (cfg.nodeType === undefined || nodeContent.parent().data('nodetype') === cfg.nodeType)) {
                targetWidget.nodeRightClick(e, nodeContent);
                menuWidget.show(e);
            }
        });

        $(document).off(containerEvent, this.jqTargetId).on(containerEvent, this.jqTargetId, null, function (e) {
            if (targetWidget.isEmpty()) {
                menuWidget.show(e);
            }
        });
    },
    expandNode: function (node) {
        var $this = this;

        if (this.cfg.dynamic) {
            if (this.cfg.cache && $this.getNodeChildrenContainer(node).children().length > 0) {
                this.showNodeChildren(node);

                return;
            }

            if (node.data('processing')) {
                PrimeFaces.debug('Node is already being expanded, ignoring expand event.');
                return;
            }

            node.data('processing', true);

            var options = {
                source: this.id,
                process: this.id,
                update: this.id,
                formId: this.cfg.formId,
                params: [
                    {name: this.id + '_expandNode', value: $this.getRowKey(node)}
                ],
                onsuccess: function (responseXML, status, xhr) {
                    PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                        widget: $this,
                        handle: function (content) {
                            var nodeChildrenContainer = this.getNodeChildrenContainer(node);
                            nodeChildrenContainer.append(content);

                            this.showNodeChildren(node);

                            if (this.cfg.draggable) {
                                this.makeDraggable(nodeChildrenContainer.find('span.ui-treenode-content'));
                            }

                            if (this.cfg.droppable) {
                                this.makeDropPoints(nodeChildrenContainer.find('li.ui-tree-droppoint'));
                                this.makeDropNodes(nodeChildrenContainer.find('span.ui-treenode-droppable'));
                            }
                        }
                    });

                    return true;
                },
                oncomplete: function () {
                    node.removeData('processing');
                }
            };

            if (this.hasBehavior('expand')) {
                var expandBehavior = this.cfg.behaviors['expand'];
                expandBehavior.call(this, options);
            }
            else {
                PrimeFaces.ajax.Request.handle(options);
            }
        }
        else {
            this.showNodeChildren(node);
            this.fireExpandEvent(node);
        }
    },
    fireExpandEvent: function (node) {
        if (this.cfg.behaviors) {
            var expandBehavior = this.cfg.behaviors['expand'];
            if (expandBehavior) {
                var ext = {
                    params: [
                        {name: this.id + '_expandNode', value: this.getRowKey(node)}
                    ]
                };

                expandBehavior.call(this, ext);
            }
        }
    },
    fireCollapseEvent: function (node) {
        if (this.cfg.behaviors) {
            var collapseBehavior = this.cfg.behaviors['collapse'];
            if (collapseBehavior) {
                var ext = {
                    params: [
                        {name: this.id + '_collapseNode', value: this.getRowKey(node)}
                    ]
                };

                collapseBehavior.call(this, ext);
            }
        }
    },
    getNodeChildrenContainer: function (node) {
        throw "Unsupported Operation";
    },
    showNodeChildren: function (node) {
        throw "Unsupported Operation";
    },
    writeSelections: function () {
        this.selectionHolder.val(this.selections.join(','));
    },
    fireNodeSelectEvent: function (node) {
        if (this.isCheckboxSelection() && this.cfg.dynamic) {
            var $this = this,
                options = {
                    source: this.id,
                    process: this.id
                };

            options.params = [
                {name: this.id + '_instantSelection', value: this.getRowKey(node)}
            ];

            options.oncomplete = function (xhr, status, args) {
                if (args.descendantRowKeys && args.descendantRowKeys !== '') {
                    var rowKeys = args.descendantRowKeys.split(',');
                    for (var i = 0; i < rowKeys.length; i++) {
                        $this.addToSelection(rowKeys[i]);
                    }
                    $this.writeSelections();
                }
            };

            if (this.hasBehavior('select')) {
                var selectBehavior = this.cfg.behaviors['select'];
                selectBehavior.call(this, options);
            }
            else {
                PrimeFaces.ajax.AjaxRequest(options);
            }
        }
        else {
            if (this.hasBehavior('select')) {
                var selectBehavior = this.cfg.behaviors['select'],
                    ext = {
                        params: [
                            {name: this.id + '_instantSelection', value: this.getRowKey(node)}
                        ]
                    };

                selectBehavior.call(this, ext);
            }
        }
    },
    fireNodeUnselectEvent: function (node) {
        if (this.cfg.behaviors) {
            var unselectBehavior = this.cfg.behaviors['unselect'];

            if (unselectBehavior) {
                var ext = {
                    params: [
                        {name: this.id + '_instantUnselection', value: this.getRowKey(node)}
                    ]
                };

                unselectBehavior.call(this, ext);
            }
        }
    },
    fireContextMenuEvent: function (node) {
        if (this.hasBehavior('contextMenu')) {
            var contextMenuBehavior = this.cfg.behaviors['contextMenu'],
                ext = {
                    params: [
                        {name: this.id + '_contextMenuNode', value: this.getRowKey(node)}
                    ]
                };

            contextMenuBehavior.call(this, ext);
        }
    },
    getRowKey: function (node) {
        return node.attr('data-rowkey');
    },
    isNodeSelected: function (node) {
        return $.inArray(this.getRowKey(node), this.selections) != -1;
    },
    isSingleSelection: function () {
        return this.cfg.selectionMode == 'single';
    },
    isMultipleSelection: function () {
        return this.cfg.selectionMode == 'multiple';
    },
    isCheckboxSelection: function () {
        return this.cfg.selectionMode == 'checkbox';
    },
    addToSelection: function (rowKey) {
        if (!PrimeFaces.inArray(this.selections, rowKey)) {
            this.selections.push(rowKey);
        }
    },
    removeFromSelection: function (rowKey) {
        this.selections = $.grep(this.selections, function (r) {
            return r !== rowKey;
        });
    },
    removeDescendantsFromSelection: function (rowKey) {
        var newSelections = [];
        for (var i = 0; i < this.selections.length; i++) {
            if (this.selections[i].indexOf(rowKey + '_') !== 0)
                newSelections.push(this.selections[i]);
        }
        this.selections = newSelections;
    },
    nodeClick: function (event, nodeContent) {
        if ($(event.target).is(':not(.ui-tree-toggler)')) {
            var node = nodeContent.parent(),
                selectable = nodeContent.hasClass('ui-tree-selectable');

            if (this.cfg.onNodeClick) {
                this.cfg.onNodeClick.call(this, node, event);
            }

            if (selectable && this.cfg.selectionMode) {
                var selected = this.isNodeSelected(node),
                    metaKey = event.metaKey || event.ctrlKey,
                    shiftKey = event.shiftKey;

                if (this.isCheckboxSelection()) {
                    this.toggleCheckboxNode(node);
                }
                else {
                    if (selected && (metaKey)) {
                        this.unselectNode(node);
                    }
                    else {
                        if (this.isSingleSelection() || (this.isMultipleSelection() && !metaKey)) {
                            this.unselectAllNodes();
                        }

                        if (this.isMultipleSelection() && shiftKey && this.cursorNode && (this.cursorNode.parent().is(node.parent()))) {
                            var parentList = node.parent(),
                                treenodes = parentList.children('li.ui-treenode'),
                                currentNodeIndex = treenodes.index(node),
                                cursorNodeIndex = treenodes.index(this.cursorNode),
                                startIndex = (currentNodeIndex > cursorNodeIndex) ? cursorNodeIndex : currentNodeIndex,
                                endIndex = (currentNodeIndex > cursorNodeIndex) ? (currentNodeIndex + 1) : (cursorNodeIndex + 1);

                            for (var i = startIndex; i < endIndex; i++) {
                                var treenode = treenodes.eq(i);
                                if (treenode.is(':visible')) {
                                    if (i === (endIndex - 1))
                                        this.selectNode(treenode);
                                    else
                                        this.selectNode(treenode, true);
                                }
                            }
                        }
                        else {
                            this.selectNode(node);
                            this.cursorNode = node;
                        }
                    }
                }

                if ($(event.target).is(':not(:input:enabled)')) {
                    PrimeFaces.clearSelection();
                    this.focusNode(node);
                }
            }
        }
    },
    nodeRightClick: function (event, nodeContent) {
        PrimeFaces.clearSelection();

        if ($(event.target).is(':not(.ui-tree-toggler)')) {
            var node = nodeContent.parent(),
                selectable = nodeContent.hasClass('ui-tree-selectable');

            if (selectable && this.cfg.selectionMode) {
                var selected = this.isNodeSelected(node);
                if (!selected) {
                    if (this.isCheckboxSelection()) {
                        this.toggleCheckboxNode(node);
                    }
                    else {
                        this.unselectAllNodes();
                        this.selectNode(node, true);
                        this.cursorNode = node;
                    }
                }

                this.fireContextMenuEvent(node);
            }
        }
    },
    bindEvents: function () {
        throw "Unsupported Operation";
    },
    selectNode: function (node, silent) {
        throw "Unsupported Operation";
    },
    unselectNode: function (node, silent) {
        throw "Unsupported Operation";
    },
    unselectAllNodes: function () {
        throw "Unsupported Operation";
    },
    preselectCheckbox: function () {
        throw "Unsupported Operation";
    },
    toggleCheckboxNode: function (node) {
        throw "Unsupported Operation";
    },
    isEmpty: function () {
        throw "Unsupported Operation";
    },
    toggleCheckboxState: function (checkbox, checked) {
        if (checked)
            this.uncheck(checkbox);
        else
            this.check(checkbox);
    },
    partialCheck: function (checkbox) {
        var box = checkbox.children('.ui-chkbox-box'),
            icon = box.children('.ui-chkbox-icon'),
            treeNode = checkbox.closest('.ui-treenode'),
            rowKey = this.getRowKey(treeNode);

        treeNode.find('> .ui-treenode-content > .ui-treenode-label').removeClass('ui-state-highlight');
        icon.removeClass('ui-icon-blank ui-icon-check').addClass('ui-icon-minus');
        treeNode.removeClass('ui-treenode-selected ui-treenode-unselected').addClass('ui-treenode-hasselected').attr('aria-checked', false).attr('aria-selected', false);

        this.removeFromSelection(rowKey);
    },
    check: function (checkbox) {
        var box = checkbox.children('.ui-chkbox-box'),
            icon = box.children('.ui-chkbox-icon'),
            treeNode = checkbox.closest('.ui-treenode'),
            rowKey = this.getRowKey(treeNode);

        box.removeClass('ui-state-hover');
        icon.removeClass('ui-icon-blank ui-icon-minus').addClass('ui-icon-check');
        treeNode.removeClass('ui-treenode-hasselected ui-treenode-unselected').addClass('ui-treenode-selected').attr('aria-checked', true).attr('aria-selected', true);

        this.addToSelection(rowKey);
    },
    uncheck: function (checkbox) {
        var box = checkbox.children('.ui-chkbox-box'),
            icon = box.children('.ui-chkbox-icon'),
            treeNode = checkbox.closest('.ui-treenode'),
            rowKey = this.getRowKey(treeNode);

        box.removeClass('ui-state-hover');
        icon.removeClass('ui-icon-minus ui-icon-check').addClass('ui-icon-blank');
        treeNode.removeClass('ui-treenode-hasselected ui-treenode-selected').addClass('ui-treenode-unselected').attr('aria-checked', false).attr('aria-selected', false);

        this.removeFromSelection(rowKey);
    },
    isExpanded: function (node) {
        return this.getNodeChildrenContainer(node).is(':visible');
    },
    focusNode: function () {
        throw "Unsupported Operation";
    }
});

/**
 * PrimeFaces Vertical Tree Widget
 */
PrimeFaces.widget.VerticalTree = PrimeFaces.widget.BaseTree.extend({
    init: function (cfg) {
        this._super(cfg);

        this.container = this.jq.children('.ui-tree-container');
        this.cfg.rtl = this.jq.hasClass('ui-tree-rtl');
        // this.cfg.collapsedIcon = this.cfg.rtl ? 'ui-icon-triangle-1-w' : 'ui-icon-triangle-1-e';
        this.scrollStateHolder = $(this.jqId + '_scrollState');
        if (!this.cfg.disabled) {
            if (this.cfg.draggable) {
                this.initDraggable();
            }
            if (this.cfg.droppable) {

                this.initDroppable();
            }
            this.setupButtons();
        }

        this.restoreScrollState();
    },
    bindEvents: function () {
        var $this = this,
            togglerSelector = '.ui-tree-toggler',
            nodeLabelSelector = '.ui-tree-selectable .ui-treenode-label',
            nodeContentSelector = '.ui-treenode-content';

        this.jq.off('click.tree-toggle', togglerSelector)
            .on('click.tree-toggle', togglerSelector, null, function (e) {
                var toggleIcon = $(this),
                    node = toggleIcon.closest('li');

                if (toggleIcon.hasClass($this.cfg.collapsedIcon))
                    $this.expandNode(node);
                else
                    $this.collapseNode(node);
            });

        if (this.cfg.highlight && this.cfg.selectionMode) {
            this.jq.off('mouseout.tree mouseover.tree', nodeLabelSelector)
                .on('mouseout.tree', nodeLabelSelector, null, function () {
                    var label = $(this);

                    label.removeClass('ui-state-hover');

                    if ($this.isCheckboxSelection()) {
                        label.siblings('div.ui-chkbox').children('div.ui-chkbox-box').removeClass('ui-state-hover');
                    }
                })
                .on('mouseover.tree', nodeLabelSelector, null, function () {
                    var label = $(this);

                    $(this).addClass('ui-state-hover');

                    if ($this.isCheckboxSelection()) {
                        label.siblings('div.ui-chkbox').children('div.ui-chkbox-box').addClass('ui-state-hover');
                    }
                });
        }

        if (this.isCheckboxSelection()) {
            var checkboxSelector = '.ui-chkbox-box:not(.ui-state-disabled)';

            this.jq.off('mouseout.tree-checkbox mouseover.tree-checkbox click.tree-checkbox', checkboxSelector)
                .on('mouseout.tree-checkbox', checkboxSelector, null, function () {
                    $(this).removeClass('ui-state-hover').parent().siblings('span.ui-treenode-label').removeClass('ui-state-hover');
                })
                .on('mouseover.tree-checkbox', checkboxSelector, null, function () {
                    $(this).addClass('ui-state-hover').parent().siblings('span.ui-treenode-label').addClass('ui-state-hover');
                });
        }

        this.jq.off('click.tree-content', nodeContentSelector)
            .on('click.tree-content', nodeContentSelector, null, function (e) {
                $this.nodeClick(e, $(this));
            });

        if (this.cfg.filter) {
            this.filterInput = this.jq.find('.ui-tree-filter');
            PrimeFaces.skinInput(this.filterInput);

            this.filterInput.on('keydown.tree-filter', function (e) {
                var key = e.which,
                    keyCode = $.ui.keyCode;

                if ((key === keyCode.ENTER || key === keyCode.NUMPAD_ENTER)) {
                    e.preventDefault();
                }
            })
                .on('keyup.tree-filter', function (e) {
                    var keyCode = $.ui.keyCode,
                        key = e.which;

                    switch (key) {
                        case keyCode.UP:
                        case keyCode.LEFT:
                        case keyCode.DOWN:
                        case keyCode.RIGHT:
                        case keyCode.ENTER:
                        case keyCode.NUMPAD_ENTER:
                        case keyCode.TAB:
                        case keyCode.ESCAPE:
                        case keyCode.SPACE:
                        case keyCode.HOME:
                        case keyCode.PAGE_DOWN:
                        case keyCode.PAGE_UP:
                        case keyCode.END:
                        case keyCode.DELETE:
                        case 16: //shift
                        case 17: //keyCode.CONTROL:
                        case 18: //keyCode.ALT:
                        case 91: //left window or cmd:
                        case 92: //right window:
                        case 93: //right cmd:
                        case 20: //capslock:
                            break;

                        default:
                            //function keys (F1,F2 etc.)
                            if (key >= 112 && key <= 123) {
                                break;
                            }

                            var metaKey = e.metaKey || e.ctrlKey;

                            if (!metaKey) {
                                if ($this.filterTimeout) {
                                    clearTimeout($this.filterTimeout);
                                }

                                $this.filterTimeout = setTimeout(function () {
                                    $this.filter();
                                    $this.filterTimeout = null;
                                }, 300);
                            }
                            break;
                    }
                });
        }

        this.jq.on('scroll.tree', function (e) {
            $this.saveScrollState();
        });

        this.bindKeyEvents();
    },
    bindKeyEvents: function () {
        var $this = this,
            pressTab = false;

        this.jq.on('mousedown.tree', function (e) {
            if ($(e.target).is(':not(:input:enabled)')) {
                e.preventDefault();
            }
        })
            .on('focus.tree', function () {
                if (!$this.focusedNode && !pressTab) {
                    $this.focusNode($this.getFirstNode());
                }
            });

        this.jq.off('keydown.tree blur.tree', '.ui-treenode-label').on('keydown.tree', '.ui-treenode-label', null, function (e) {
            if (!$this.focusedNode) {
                return;
            }

            var searchRowkey = "",
                keyCode = $.ui.keyCode;

            switch (e.which) {
                case keyCode.LEFT:
                    var rowkey = $this.focusedNode.data('rowkey').toString(),
                        keyLength = rowkey.length;

                    if ($this.isExpanded($this.focusedNode)) {
                        $this.collapseNode($this.focusedNode);
                    }
                    else {
                        var nodeToFocus = null;
                        for (var i = 1; i < parseInt(keyLength / 2) + 1; i++) {
                            searchRowkey = rowkey.substring(0, keyLength - 2 * i);
                            nodeToFocus = $this.container.find("li:visible[data-rowkey = '" + searchRowkey + "']");
                            if (nodeToFocus.length) {
                                $this.focusNode(nodeToFocus);
                                break;
                            }
                        }
                    }

                    e.preventDefault();
                    break;

                case keyCode.RIGHT:
                    if (!$this.focusedNode.hasClass('ui-treenode-leaf')) {
                        var rowkey = $this.focusedNode.data('rowkey').toString(),
                            keyLength = rowkey.length;

                        if (!$this.isExpanded($this.focusedNode)) {
                            $this.expandNode($this.focusedNode);
                        }

                        if (!$this.isExpanded($this.focusedNode) && !$this.cfg.dynamic) {
                            searchRowkey = rowkey + '_0';
                            var nodeToFocus = $this.container.find("li:visible[data-rowkey = '" + searchRowkey + "']");

                            if (nodeToFocus.length) {
                                $this.focusNode(nodeToFocus);
                            }
                        }
                    }

                    e.preventDefault();
                    break;

                case keyCode.UP:
                    var nodeToFocus = null,
                        prevNode = $this.focusedNode.prev();

                    if (prevNode.length) {
                        nodeToFocus = prevNode.find('li.ui-treenode:visible:last');
                        if (!nodeToFocus.length) {
                            nodeToFocus = prevNode;
                        }
                    }
                    else {
                        nodeToFocus = $this.focusedNode.closest('ul').parent('li');
                    }

                    if (nodeToFocus.length) {
                        $this.focusNode(nodeToFocus);
                    }

                    e.preventDefault();
                    break;

                case keyCode.DOWN:
                    var nodeToFocus = null,
                        firstVisibleChildNode = $this.focusedNode.find("> ul > li:visible:first");

                    if (firstVisibleChildNode.length) {
                        nodeToFocus = firstVisibleChildNode;
                    }
                    else if ($this.focusedNode.next().length) {
                        nodeToFocus = $this.focusedNode.next();
                    }
                    else {
                        var rowkey = $this.focusedNode.data('rowkey').toString();

                        if (rowkey.length !== 1) {
                            nodeToFocus = $this.searchDown($this.focusedNode);
                        }
                    }

                    if (nodeToFocus && nodeToFocus.length) {
                        $this.focusNode(nodeToFocus);
                    }

                    e.preventDefault();
                    break;

                case keyCode.ENTER:
                case keyCode.NUMPAD_ENTER:
                case keyCode.SPACE:
                    if ($this.cfg.selectionMode) {
                        var selectable = $this.focusedNode.children('.ui-treenode-content').hasClass('ui-tree-selectable');

                        if ($this.cfg.onNodeClick) {
                            $this.cfg.onNodeClick.call($this, $this.focusedNode, e);
                        }

                        if (selectable) {
                            var selected = $this.isNodeSelected($this.focusedNode);

                            if ($this.isCheckboxSelection()) {
                                $this.toggleCheckboxNode($this.focusedNode);
                            }
                            else {
                                if (selected) {
                                    $this.unselectNode($this.focusedNode);
                                }
                                else {
                                    if ($this.isSingleSelection()) {
                                        $this.unselectAllNodes();
                                    }

                                    $this.selectNode($this.focusedNode);
                                    $this.cursorNode = $this.focusedNode;
                                }
                            }
                        }
                    }

                    e.preventDefault();
                    break;

                case keyCode.TAB:
                    pressTab = true;
                    $this.jq.focus();
                    setTimeout(function () {
                        pressTab = false;
                    }, 2);

                    break;
            }
        })
            .on('blur.tree', '.ui-treenode-label', null, function (e) {
                if ($this.focusedNode) {
                    $this.getNodeLabel($this.focusedNode).removeClass('ui-treenode-outline');
                    $this.focusedNode = null;
                }
            });

        /* For copy/paste operation on drag and drop */
        // $(document.body).on('keydown.tree', function (e) {
        //     $this.shiftKey = e.shiftKey;
        // })
        //     .on('keyup.tree', function () {
        //         $this.shiftKey = false;
        //     });
    },
    searchDown: function (node) {
        var nextOfParent = node.closest('ul').parent('li').next(),
            nodeToFocus = null;

        if (nextOfParent.length) {
            nodeToFocus = nextOfParent;
        }
        else if (node.hasClass('ui-treenode-leaf') && node.closest('ul').parent('li').length == 0) {
            nodeToFocus = node;
        }
        else {
            var rowkey = node.data('rowkey').toString();

            if (rowkey.length !== 1) {
                nodeToFocus = this.searchDown(node.closest('ul').parent('li'));
            }
        }

        return nodeToFocus;
    },
    collapseNode: function (node) {
        var _self = this,
            nodeContent = node.find('> .ui-treenode-content'),
            toggleIcon = nodeContent.find('> .ui-tree-toggler'),
            nodeType = node.data('nodetype'),
            nodeIcon = toggleIcon.nextAll('span.ui-treenode-icon'),
            iconState = this.cfg.iconStates[nodeType],
            childrenContainer = node.children('.ui-treenode-children');

        //aria
        nodeContent.find('> .ui-treenode-label').attr('aria-expanded', false);

        toggleIcon.addClass(_self.cfg.collapsedIcon).removeClass('ui-icon-triangle-1-s');

        if (iconState) {
            nodeIcon.removeClass(iconState.expandedIcon).addClass(iconState.collapsedIcon);
        }

        if (this.cfg.animate) {
            childrenContainer.slideUp('fast', function () {
                _self.postCollapse(node, childrenContainer);
            });
        }
        else {
            childrenContainer.hide();
            this.postCollapse(node, childrenContainer);
        }
    },
    postCollapse: function (node, childrenContainer) {
        if (this.cfg.dynamic && !this.cfg.cache) {
            childrenContainer.empty();
        }

        this.fireCollapseEvent(node);
    },
    //@Override
    getNodeChildrenContainer: function (node) {
        return node.children('.ui-treenode-children');
    },
    //@Override
    showNodeChildren: function (node) {
        var nodeContent = node.find('> .ui-treenode-content'),
            toggleIcon = nodeContent.find('> .ui-tree-toggler'),
            nodeType = node.data('nodetype'),
            nodeIcon = toggleIcon.nextAll('span.ui-treenode-icon'),
            iconState = this.cfg.iconStates[nodeType];

        //aria
        nodeContent.find('> .ui-treenode-label').attr('aria-expanded', true);

        toggleIcon.addClass('ui-icon-triangle-1-s').removeClass(this.cfg.collapsedIcon);

        if (iconState) {
            nodeIcon.removeClass(iconState.collapsedIcon).addClass(iconState.expandedIcon);
        }

        if (this.cfg.animate) {
            node.children('.ui-treenode-children').slideDown('fast');
        }
        else {
            node.children('.ui-treenode-children').show();
        }
    },
    unselectAllNodes: function () {
        this.selections = [];
        this.jq.find('.ui-treenode-label.ui-state-highlight').each(function () {
            $(this).removeClass('ui-state-highlight').closest('.ui-treenode').attr('aria-selected', false);
        });
    },
    selectNode: function (node, silent) {
        node.attr('aria-selected', true)
            .find('> .ui-treenode-content > .ui-treenode-label').removeClass('ui-state-hover').addClass('ui-state-highlight');

        this.addToSelection(this.getRowKey(node));
        this.writeSelections();

        if (!silent)
            this.fireNodeSelectEvent(node);
    },
    unselectNode: function (node, silent) {
        var rowKey = this.getRowKey(node);

        node.attr('aria-selected', false).find('> .ui-treenode-content > .ui-treenode-label').removeClass('ui-state-highlight ui-state-hover');

        this.removeFromSelection(rowKey);
        this.writeSelections();

        if (!silent)
            this.fireNodeUnselectEvent(node);
    },
    toggleCheckboxNode: function (node) {
        var $this = this,
            checkbox = node.find('> .ui-treenode-content > .ui-chkbox'),
            checked = checkbox.find('> .ui-chkbox-box > .ui-chkbox-icon').hasClass('ui-icon-check');

        this.toggleCheckboxState(checkbox, checked);

        if (this.cfg.propagateDown) {
            node.children('.ui-treenode-children').find('.ui-chkbox').each(function () {
                $this.toggleCheckboxState($(this), checked);
            });

            if (this.cfg.dynamic) {
                this.removeDescendantsFromSelection(node.data('rowkey'));
            }
        }

        if (this.cfg.propagateUp) {
            node.parents('li.ui-treenode-parent').each(function () {
                var parentNode = $(this),
                    parentsCheckbox = parentNode.find('> .ui-treenode-content > .ui-chkbox'),
                    children = parentNode.find('> .ui-treenode-children > .ui-treenode');

                if (checked) {
                    if (children.filter('.ui-treenode-unselected').length === children.length)
                        $this.uncheck(parentsCheckbox);
                    else
                        $this.partialCheck(parentsCheckbox);
                }
                else {
                    if (children.filter('.ui-treenode-selected').length === children.length)
                        $this.check(parentsCheckbox);
                    else
                        $this.partialCheck(parentsCheckbox);
                }
            });
        }

        this.writeSelections();

        if (checked)
            this.fireNodeUnselectEvent(node);
        else
            this.fireNodeSelectEvent(node);
    },
    preselectCheckbox: function () {
        this.jq.find('.ui-chkbox-icon').not('.ui-icon-check').each(function () {
            var icon = $(this),
                node = icon.closest('li');

            if (node.children('.ui-treenode-children').find('.ui-chkbox-icon.ui-icon-check').length > 0) {
                node.addClass('ui-treenode-hasselected');
                icon.removeClass('ui-icon-blank').addClass('ui-icon-minus');
            }
        });
    },
    check: function (checkbox) {
        this._super(checkbox);
        checkbox.siblings('span.ui-treenode-label').addClass('ui-state-highlight').removeClass('ui-state-hover');
    },
    uncheck: function (checkbox) {
        this._super(checkbox);
        checkbox.siblings('span.ui-treenode-label').removeClass('ui-state-highlight');
    },
    initDraggable: function () {
        // this.makeDraggable(this.jq.find('span.ui-treenode-content'));
        this.makeDraggable(this.jq.find('li.ui-treenode.ui-treenode-leaf[data-nodetype="event"]>span'), 'event');
        this.makeDraggable(this.jq.find('li.ui-treenode[data-nodetype="section"]>span'), 'section');
    },
    initDroppable: function () {
        //for sections
        this.makeDropPoints(
            this.jq.find($('li[data-nodetype="section"]').parent().prev('li.ui-tree-droppoint')
                .add($('li[data-nodetype="section"]').next('li.ui-tree-droppoint')))
            , 'section');
        //for both types
        // this.makeDropPoints(this.jq.find($('.ui-treenode-children li:nth-last-child(1)')), 'both');
        //for events
        // this.makeDropPoints(this.jq.find($('.ui-treenode-children li.ui-tree-droppoint:not(li:nth-last-child(1))')), 'event');
        // ########################################################################################
        // this.makeDropPoints(this.jq.find($('.ui-treenode-children li.ui-tree-droppoint')), 'event');
        // this.makeDropNodes(this.jq.find($('li[data-nodetype="section"] > span.ui-treenode-droppable')), 'event');
        // this.makeDropNodes(this.jq.find($('li[data-nodetype="event"] > span.ui-treenode-droppable')), 'event');

        this.makeDropPoints(this.jq.find($('.ui-treenode-children li.ui-tree-droppoint').not($('.ui-treenode-children li.ui-tree-droppoint:only-child'))), 'event');
        this.makeDropNodes(this.jq.find($('li[data-nodetype="section"] > span.ui-treenode-droppable')), 'event');
        this.makeDropNodes(this.jq.find($('li[data-nodetype="event"] > span.ui-treenode-droppable')), 'event');


        this.initDropScrollers('section');
        this.initDropScrollers('event');
    },
    makeDraggable: function (elements, newdragdropScope) {
        var $this = this,
            dragdropScope = this.cfg.dragdropScope || this.id,
            dragdropScope = newdragdropScope;

        elements.draggable({

            start: function (event, ui) {


                var type = $(event.target).parent().attr('data-nodetype');
                if (type === 'section') {
                    // ($('li[data-nodetype="section"]').prev().add($('li[data-nodetype="section"]').next()).addClass('ui-state-valid-droppoint'));
                    ($('li[data-type="section"]').addClass('ui-state-valid-droppoint'));
                    setPosition([{
                        name: 'oldSectionPosition',
                        value: $(event.target).parent().attr('data-rowkey')
                        // value : $(event.target).parent().nextAll('li[data-nodetype="section"]:first').not($(event.target).parent()).attr('data-rowkey')
                    }, {
                        name: 'oldEventPosition',
                        value: null
                    }]);
                }
                if (type === 'event') {
                    // ($('li[data-nodetype="event"]').prev().add($('li[data-nodetype="event"]').next()).addClass('ui-state-valid-droppoint'));
                    // ($('li[data-nodetype="event"]').prev().add($('li[data-nodetype="event"]').next()).add($('li[data-nodetype="section"].ui-treenode-leaf > ul.ui-treenode-children'))).addClass('ui-state-valid-droppoint');
                    ($('li[data-type="event"]').not($('li[data-type="event"]:only-child'))).addClass('ui-state-valid-droppoint');
                    setPosition([{
                        name: 'oldSectionPosition',
                        value: $(event.target).parent().parent().parent().parent().attr('data-rowkey')
                        // value : $(event.target).parent().parent().parent().nextAll('li[data-nodetype="section"]:first').attr('data-rowkey')
                    }, {
                        name: 'oldEventPosition',
                        value: $(event.target).parent().attr('data-rowkey')
                        // value : $(event.target).parent().nextAll('li[data-nodetype="event"]:first').not($(event.target).parent()).attr('data-rowkey')
                    }]);
                }
                if (ui.helper) {
                    var element = $(event.target).parent(),
                        source = PF($(element.data('dragsourceid')).data('widget')),
                        height = 20;

                    // if (source.cfg.multipleDrag && element.find('.ui-treenode-label').hasClass('ui-state-highlight')) {
                    //     source.draggedSourceKeys = $this.findSelectedParentKeys(source.selections.slice());
                    //     height = 20 * (source.draggedSourceKeys.length || 1);
                    // }

                    $(ui.helper).height(height);
                }
            },
            helper: function () {
                // var el = $('<div class="ui-tree-draghelper ui-state-highlight"></div>');
                // el.width($this.jq.width());
                // return el;

                // var coordinates = evt.coordinate;
                // setPosition([ {
                //     name : 'latitudeMin',
                //     value : coordinates[0]
                // }, {
                //     name : 'longitudeMin',
                //     value : coordinates[1]
                // } ]);
                // console.log(coordinates);
                // PF('dlg').show();


                return $("<div></div>").append($(event.target).parent().clone());
            },
            helper: 'clone',
            appendTo: document.body,
            zIndex: ++PrimeFaces.zindex,
            revert: function (is_valid_drop) {
                if (!is_valid_drop) {
                    console.log("revert triggered");
                    $('li.ui-tree-droppoint.ui-droppable,ul.ui-treenode-children,ul.ui-droppable.ui-state-valid-droppoint').removeClass('ui-state-valid-droppoint');
                }
                return true;
            },
            scope: dragdropScope,
            containment: 'document'
        })
            .data({
                'dragsourceid': this.jqId,
                'dragmode': this.cfg.dragMode
            });
    },
    stopEvent: function (event, ui) {
        alert($(event.target));
    },
    makeDropPoints: function (elements, newdragdropScope) {
        var $this = this,
            dragdropScope = this.cfg.dragdropScope || this.id,
            dragdropScope = newdragdropScope;
        elements.droppable({
            hoverClass: 'ui-state-hover',
            accept: 'span.ui-treenode-content',
            tolerance: 'pointer',
            scope: dragdropScope,
            drop: function (event, ui) {
                var dragSource = PF($(ui.draggable.data('dragsourceid')).data('widget')),
                    dropSource = $this,
                    dropPoint = $(this),
                    dropNode = dropPoint.closest('li.ui-treenode-parent'), //always log for element before section or event
                    dropNodeKey = $this.getRowKey(dropNode),
                    transfer = (dragSource.id !== dropSource.id),
                    draggedSourceKeys = dragSource.draggedSourceKeys,
                    isDroppedNodeCopy = ($this.cfg.dropCopyNode && $this.shiftKey && transfer),
                    draggedNodes,
                    dragNodeKey;

                if (draggedSourceKeys) {
                    draggedNodes = dragSource.findNodes(draggedSourceKeys);
                }
                else {
                    draggedNodes = [ui.draggable];
                }

                if ($this.cfg.controlled) {
                    $this.droppedNodeParams = [];
                }

                for (var i = (draggedNodes.length - 1); i >= 0; i--) {
                    var draggedNode = $(draggedNodes[i]),
                        dragMode = ui.draggable.data('dragmode'),
                        dragNode = draggedNode.is('li.ui-treenode') ? draggedNode : draggedNode.closest('li.ui-treenode'),
                        dragNode = (isDroppedNodeCopy) ? dragNode.clone() : dragNode,
                        targetDragNode = $this.findTargetDragNode(dragNode, dragMode);

                    dragNodeKey = $this.getRowKey(targetDragNode);

                    if ($this.cfg.controlled) {
                        $this.droppedNodeParams.push({
                            'ui': ui,
                            'dragSource': dragSource,
                            'dragNode': dragNode,
                            'targetDragNode': targetDragNode,
                            'dropPoint': dropPoint,
                            'dropNode': dropNode,
                            'transfer': transfer
                        });
                    }
                    else {
                        $this.onDropPoint(ui, dragSource, dragNode, targetDragNode, dropPoint, dropNode, transfer);
                    }
                }

                draggedSourceKeys = (draggedSourceKeys && draggedSourceKeys.length) ? draggedSourceKeys.reverse().join(',') : dragNodeKey;
                //todo
                if (MyApp.validDrop) {
                    $this.fireDragDropEvent({
                        'dragNodeKey': draggedSourceKeys,
                        'dropNodeKey': dropNodeKey,
                        'dragSource': dragSource.id,
                        'dndIndex': dragNode.attr('data-nodetype') === 'event' ? dropPoint.parent().prevAll('div').children('li.ui-treenode').add(dropPoint.prev('li[data-nodetype=event]')).length : dropPoint.parent().prevAll('div').children('li.ui-treenode').add(dropPoint.prev('li[data-nodetype=section]')).length,

                        // dropPoint.parent().prevAll('div').length,
                        // .add(dropPoint.prev('li[data-nodetype=event]')),
                        // dropPoint.parent().prevAll('div').children('li.ui-treenode').add(dropPoint.prev('li[data-nodetype=event]')).length,
                        'transfer': transfer,
                        'isDroppedNodeCopy': isDroppedNodeCopy
                    });
                }

                dragSource.draggedSourceKeys = null;

                if (isDroppedNodeCopy) {
                    $this.initDraggable();
                }
            }
        });
    },
    onDropPoint: function (ui, dragSource, dragNode, targetDragNode, dropPoint, dropNode, transfer) {
        var dragNodeDropPoint = targetDragNode.next('li.ui-tree-droppoint'),
            oldParentNode = targetDragNode.parent().closest('li.ui-treenode-parent');
        // targetDragNode = targetDragNode.parent();
        // dropPoint = dropPoint.parent();
        //todo die richtigen dragnodes/droppoints finden
        ui.helper.remove();
        dropPoint.removeClass('ui-state-hover');
        $('li.ui-tree-droppoint.ui-droppable,ul.ui-treenode-children').removeClass('ui-state-valid-droppoint');

        var validDrop = this.validateDropPoint(dragNode, dropPoint);
        if (!validDrop) {
            MyApp.validDrop = false;
            return;
        }
        else {
            MyApp.validDrop = true;
        }


        targetDragNode.parent().insertAfter(dropPoint.parent());

        if (transfer) {
            if (dragSource.cfg.selectionMode) {
                dragSource.unselectSubtree(targetDragNode);
            }

            dragNodeDropPoint.remove();
            this.updateDragDropBindings(targetDragNode);
        }
        else {
            dragNodeDropPoint.insertAfter(targetDragNode);
        }

        if (oldParentNode.length && (oldParentNode.find('> ul.ui-treenode-children > div > li.ui-treenode').length === 0)) {
            this.makeLeaf(oldParentNode);
        }

        targetDragNode.show();

        if (this.isCheckboxSelection()) {
            this.syncDNDCheckboxes(dragSource, oldParentNode, dropNode);
        }

        this.syncDragDrop();
        if (transfer) {
            dragSource.syncDragDrop();
        }
    },
    makeDropNodes: function (elements, newdragdropScope) {
        var $this = this,
            dragdropScope = this.cfg.dragdropScope || this.id,
            dragdropScope = newdragdropScope;

        elements.droppable({
            accept: '.ui-treenode-content',
            tolerance: 'pointer',
            scope: dragdropScope,

            over: function (event, ui) {
                var type1 = $(event.target).parent().attr('data-nodetype');
                var type2 = $(ui.draggable).parent().attr('data-nodetype');
                if (type1 !== type2) {
                    $(this).children('.ui-treenode-label').addClass('ui-state-hover');
                }
                else {
                    // return;
                }
            },
            out: function (event, ui) {
                $(this).children('.ui-treenode-label').removeClass('ui-state-hover');
            },
            drop: function (event, ui) {
                var dragSource = PF($(ui.draggable.data('dragsourceid')).data('widget')),
                    dropSource = $this,
                    droppable = $(this),
                    dropNode = droppable.closest('li.ui-treenode'),
                    dropNodeKey = $this.getRowKey(dropNode),
                    transfer = (dragSource.id !== dropSource.id),
                    draggedSourceKeys = dragSource.draggedSourceKeys,
                    isDroppedNodeCopy = ($this.cfg.dropCopyNode && $this.shiftKey && transfer),
                    draggedNodes,
                    dragNodeKey,
                    dndIndex;


                if (draggedSourceKeys) {
                    draggedNodes = dragSource.findNodes(draggedSourceKeys);
                }
                else {
                    draggedNodes = [ui.draggable];
                }

                if ($this.cfg.controlled) {
                    $this.droppedNodeParams = [];
                }

                for (var i = 0; i < draggedNodes.length; i++) {
                    var draggedNode = $(draggedNodes[i]),
                        dragMode = ui.draggable.data('dragmode'),
                        dragNode = draggedNode.is('li.ui-treenode') ? draggedNode : draggedNode.closest('li.ui-treenode'),
                        dragNode = (isDroppedNodeCopy) ? dragNode.clone() : dragNode,
                        targetDragNode = $this.findTargetDragNode(dragNode, dragMode),
                        oldParentNode = targetDragNode.parent().closest('li.ui-treenode-parent');
                    //validate custom drag/drop constraints
                    var validDrop = $this.validateDropNode(dragNode, dropNode, oldParentNode, targetDragNode);
                    if (!validDrop) {
                        return;
                    }


                    if (i === 0) {
                        dndIndex = targetDragNode.prevAll('li.ui-treenode').length;
                    }

                    dragNodeKey = $this.getRowKey(targetDragNode);

                    if ($this.cfg.controlled) {
                        $this.droppedNodeParams.push({
                            'ui': ui,
                            'dragSource': dragSource,
                            'dragNode': dragNode,
                            'targetDragNode': targetDragNode,
                            'droppable': droppable,
                            'dropNode': dropNode,
                            'transfer': transfer
                        });
                    }
                    else {
                        $this.onDropNode(ui, dragSource, dragNode, targetDragNode, droppable, dropNode, transfer);
                    }
                }

                draggedSourceKeys = (draggedSourceKeys && draggedSourceKeys.length) ? draggedSourceKeys.reverse().join(',') : dragNodeKey;

                $this.fireDragDropEvent({
                    'dragNodeKey': draggedSourceKeys,
                    'dropNodeKey': dropNodeKey,
                    'dragSource': dragSource.id,
                    'dndIndex': dndIndex,
                    'transfer': transfer,
                    'isDroppedNodeCopy': isDroppedNodeCopy
                });

                dragSource.draggedSourceKeys = null;

                if (isDroppedNodeCopy) {
                    $this.initDraggable();
                }
            }
        });
    },
    onDropNode: function (ui, dragSource, dragNode, targetDragNode, droppable, dropNode, transfer) {
        var dragNodeDropPoint = targetDragNode.next('li.ui-tree-droppoint'),
            oldParentNode = targetDragNode.parent().closest('li.ui-treenode-parent'),
            childrenContainer = dropNode.children('.ui-treenode-children');

        ui.helper.remove();
        droppable.children('.ui-treenode-label').removeClass('ui-state-hover');

        var validDrop = this.validateDropNode(dragNode, dropNode, oldParentNode, targetDragNode);
        if (!validDrop) {
            return;
        }

        if (childrenContainer.children('li.ui-treenode').length === 0) {
            this.makeParent(dropNode);
        }

        targetDragNode.hide();
        childrenContainer.append(targetDragNode);

        if (oldParentNode.length && (oldParentNode.find('> ul.ui-treenode-children > li.ui-treenode').length === 0)) {
            this.makeLeaf(oldParentNode);
        }

        if (transfer) {
            if (dragSource.cfg.selectionMode) {
                dragSource.unselectSubtree(targetDragNode);
            }

            dragNodeDropPoint.remove();
            this.updateDragDropBindings(targetDragNode);
        }
        else {
            childrenContainer.append(dragNodeDropPoint);
        }

        //wtf
        targetDragNode.fadeIn();

        if (this.isCheckboxSelection()) {
            this.syncDNDCheckboxes(dragSource, oldParentNode, dropNode);
        }

        this.syncDragDrop();
        if (transfer) {
            dragSource.syncDragDrop();
        }
    },
    findSelectedParentKeys: function (arr) {
        for (var i = 0; i < arr.length; i++) {
            var key = arr[i];
            for (var j = 0; j < arr.length && key !== -1; j++) {
                var tempKey = arr[j];
                if (tempKey !== -1 && key.length > tempKey.length && key.indexOf(tempKey) === 0) {
                    arr[i] = -1;
                }
            }
        }

        return arr.filter(function (item) {
            return item !== -1;
        });
    },
    initDropScrollers: function (newdragdropScope) {
        var $this = this,
            dragdropScope = this.cfg.dragdropScope || this.id,
            dragdropScope = newdragdropScope;

        this.jq.prepend('<div class="ui-tree-scroller ui-tree-scrollertop"></div>').append('<div class="ui-tree-scroller ui-tree-scrollerbottom"></div>');

        this.jq.children('div.ui-tree-scroller').droppable({
            accept: '.ui-treenode-content',
            tolerance: 'pointer',
            scope: dragdropScope,
            over: function () {
                var step = $(this).hasClass('ui-tree-scrollertop') ? -10 : 10;

                $this.scrollInterval = setInterval(function () {
                    $this.scroll(step);
                }, 100);
            },
            out: function () {
                clearInterval($this.scrollInterval);
            }
        });
    },
    scroll: function (step) {
        this.container.scrollTop(this.container.scrollTop() + step);
    },
    updateDragDropBindings: function (node) {
        //self droppoint
        node.after('<li class="ui-tree-droppoint ui-droppable"></li>');
        this.makeDropPoints(node.next('li.ui-tree-droppoint'));

        //descendant droppoints
        var subtreeDropPoints = node.find('li.ui-tree-droppoint');
        if (subtreeDropPoints.hasClass('ui-droppable') && !this.shiftKey && !this.cfg.dropCopyNode) {
            subtreeDropPoints.droppable('destroy');
        }
        this.makeDropPoints(subtreeDropPoints);

        //descendant drop node contents
        var subtreeDropNodeContents = node.find('span.ui-treenode-content');
        if (subtreeDropNodeContents.hasClass('ui-droppable') && !this.shiftKey && !this.cfg.dropCopyNode) {
            subtreeDropNodeContents.droppable('destroy');
        }
        this.makeDropNodes(subtreeDropNodeContents);

        if (this.cfg.draggable) {
            subtreeDropNodeContents.data({
                'dragsourceid': this.jqId,
                'dragmode': this.cfg.dragMode
            });
        }
    },
    findTargetDragNode: function (dragNode, dragMode) {
        var targetDragNode = null;

        if (dragMode === 'self') {
            targetDragNode = dragNode;
        } else if (dragMode === 'parent') {
            targetDragNode = dragNode.parent().closest('li.ui-treenode');
        } else if (dragMode === 'ancestor') {
            targetDragNode = dragNode.parent().parents('li.ui-treenode:last');
        }

        if (targetDragNode.length === 0) {
            targetDragNode = dragNode;
        }

        return targetDragNode;
    },
    findNodes: function (rowkeys) {
        var nodes = [];
        for (var i = 0; i < rowkeys.length; i++) {
            nodes.push($(this.jqId + '\\:' + rowkeys[i]));
        }

        return nodes;
    },
    updateRowKeys: function () {
        var children = this.jq.find('> ul.ui-tree-container > li.ui-treenode');
        this.updateChildrenRowKeys(children, null);
    },
    updateChildrenRowKeys: function (children, rowkey) {
        var $this = this;

        children.each(function (i) {
            var childNode = $(this),
                oldRowKey = childNode.attr('data-rowkey'),
                newRowKey = (rowkey === null) ? i.toString() : rowkey + '_' + i;

            childNode.attr({
                'id': $this.id + ':' + newRowKey,
                'data-rowkey': newRowKey
            });

            if (childNode.hasClass('ui-treenode-parent')) {
                $this.updateChildrenRowKeys(childNode.find('> ul.ui-treenode-children > li.ui-treenode'), newRowKey);
            }
        });
    },
    validateDropPoint: function (dragNode, dropPoint) {
        if (dragNode.parent().html() === dropPoint.parent().html() || dragNode.parent().prev().html() === dropPoint.parent().html()) {
            return false;
        }

        //dropped before or after
        // if (dragNode.parent() === dropPoint || dragNode.prev().get(0) === dropPoint.get(0)) {
        //     return false;
        // }
        if (dropPoint.prev()[0] == dragNode[0] || dragNode[0] == dropPoint.next('div').children()[0]) {
            return false;
        }

        //descendant of dropnode
        if (dragNode.has(dropPoint.get(0)).length) {
            return false;
        }

        //drop restriction
        if (dragNode.parent().get(0) == dropPoint.get(0)) {
            return false;
        }

        return true;
    },
    validateDropNode: function (dragNode, dropNode, oldParentNode, targetDragNode) {
        //dropped on parent
        if (oldParentNode.get(0) === dropNode.get(0))
            return false;

        //descendant of dropnode
        if (dragNode.has(dropNode.get(0)).length) {
            return false;
        }

        //same node
        if (dragNode === dropNode) {
            return false;
        }


        //drop restriction
        if (this.cfg.dropRestrict) {
            if (this.cfg.dropRestrict === 'sibling') {
                return false;
            }
        }

        //must not be on one of the same kind (hikesections/hikeevents)
        if (dragNode.data("nodetype") == dropNode.data("nodetype")) {
            return false;
        }
        //must not have a different parent type
        return dragNode.data("nodetype") === 'event' && dropNode.data("nodetype") === 'section';

    },
    makeLeaf: function (node) {
        node.removeClass('ui-treenode-parent').addClass('ui-treenode-leaf');
        node.find('> .ui-treenode-content > .ui-tree-toggler').addClass('ui-treenode-leaf-icon').removeClass('ui-tree-toggler ui-icon ui-icon-triangle-1-s');
        node.children('.ui-treenode-children').hide().children().remove();
    },
    makeParent: function (node) {
        node.removeClass('ui-treenode-leaf').addClass('ui-treenode-parent');
        node.find('> span.ui-treenode-content > span.ui-treenode-leaf-icon').removeClass('ui-treenode-leaf-icon').addClass('ui-tree-toggler ui-icon ui-icon-triangle-1-e');
        node.children('.ui-treenode-children').append('<li class="ui-tree-droppoint ui-droppable"></li>');

        this.makeDropPoints(node.find('> ul.ui-treenode-children > li.ui-tree-droppoint'));
    },
    syncDragDrop: function () {
        var $this = this;

        if (this.cfg.selectionMode) {
            var selectedNodes = this.findNodes(this.selections);

            this.updateRowKeys();
            this.selections = [];
            $.each(selectedNodes, function (i, item) {
                $this.selections.push(item.attr('data-rowkey'));
            });
            this.writeSelections();
        }
        else {
            this.updateRowKeys();
        }
    },
    syncDNDCheckboxes: function (dragSource, oldParentNode, newParentNode) {
        if (oldParentNode.length) {
            dragSource.propagateDNDCheckbox(oldParentNode);
        }

        if (newParentNode.length) {
            this.propagateDNDCheckbox(newParentNode);
        }
    },
    unselectSubtree: function (node) {
        var $this = this;

        if (this.isCheckboxSelection()) {
            var checkbox = node.find('> .ui-treenode-content > .ui-chkbox');

            this.toggleCheckboxState(checkbox, true);

            node.children('.ui-treenode-children').find('.ui-chkbox').each(function () {
                $this.toggleCheckboxState($(this), true);
            });
        }
        else {
            node.find('.ui-treenode-label.ui-state-highlight').each(function () {
                $(this).removeClass('ui-state-highlight').closest('li.ui-treenode').attr('aria-selected', false);
            });
        }
    },
    propagateDNDCheckbox: function (node) {
        var checkbox = node.find('> .ui-treenode-content > .ui-chkbox'),
            children = node.find('> .ui-treenode-children > .ui-treenode');

        if (children.length) {
            if (children.filter('.ui-treenode-unselected').length === children.length)
                this.uncheck(checkbox);
            else if (children.filter('.ui-treenode-selected').length === children.length)
                this.check(checkbox);
            else
                this.partialCheck(checkbox);
        }

        var parent = node.parent().closest('.ui-treenode-parent');
        if (parent.length) {
            this.propagateDNDCheckbox(parent);
        }
    },
    fireDragDropEvent: function (event) {
        var $this = this,
            options = {
                source: this.id,
                process: event.transfer ? this.id + ' ' + event.dragSource : this.id
            };

        options.params = [
            {name: this.id + '_dragdrop', value: true},
            {name: this.id + '_dragNode', value: event.dragNodeKey},
            {name: this.id + '_dragSource', value: event.dragSource},
            {name: this.id + '_dropNode', value: event.dropNodeKey},
            {name: this.id + '_dndIndex', value: event.dndIndex},
            {name: this.id + '_isDroppedNodeCopy', value: event.isDroppedNodeCopy}
        ];

        if (this.cfg.controlled) {
            options.oncomplete = function (xhr, status, args) {
                if (args.access) {
                    for (var i = 0; i < $this.droppedNodeParams.length; i++) {
                        var params = $this.droppedNodeParams[i];
                        if (params.dropPoint)
                            $this.onDropPoint(params.ui, params.dragSource, params.dragNode, params.targetDragNode, params.dropPoint, params.dropNode, params.transfer);
                        else
                            $this.onDropNode(params.ui, params.dragSource, params.dragNode, params.targetDragNode, params.droppable, params.dropNode, params.transfer);
                    }
                }
            };
        }

        if (this.hasBehavior('dragdrop')) {
            var dragdropBehavior = this.cfg.behaviors['dragdrop'];

            dragdropBehavior.call(this, options);
        }
        else {
            PrimeFaces.ajax.AjaxRequest(options);
        }
    },
    isEmpty: function () {
        return (this.container.children().length === 0);
    },
    getFirstNode: function () {
        return this.jq.find('> ul.ui-tree-container > li:first-child');
    },
    getNodeLabel: function (node) {
        return node.find('> span.ui-treenode-content > span.ui-treenode-label');
    },
    focusNode: function (node) {
        if (this.focusedNode) {
            this.getNodeLabel(this.focusedNode).removeClass('ui-treenode-outline');
        }

        this.getNodeLabel(node).addClass('ui-treenode-outline').focus();
        this.focusedNode = node;
    },
    /**
     * Ajax filter
     */
    filter: function () {
        var $this = this,
            options = {
                source: this.id,
                update: this.id,
                process: this.id,
                global: false,
                formId: this.cfg.formId,
                params: [{name: this.id + '_filtering', value: true},
                    {name: this.id + '_encodeFeature', value: true}],
                onsuccess: function (responseXML, status, xhr) {
                    PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                        widget: $this,
                        handle: function (content) {
                            $this.container.html(content);
                        }
                    });

                    return true;
                }
            };

        PrimeFaces.ajax.Request.handle(options);
    },
    restoreScrollState: function () {
        var scrollState = this.scrollStateHolder.val(),
            scrollValues = scrollState.split(',');

        this.jq.scrollLeft(scrollValues[0]);
        this.jq.scrollTop(scrollValues[1]);
    },
    saveScrollState: function () {
        var scrollState = this.jq.scrollLeft() + ',' + this.jq.scrollTop();

        this.scrollStateHolder.val(scrollState);
    },
    clearScrollState: function () {
        this.scrollStateHolder.val('0,0');
    },
    setupButtons: function () {
        var $this = this;

        PrimeFaces.skinButton(this.jq.find('.ui-button'));

        // this.jq.find('li.ui-tree-droppoint.ui-section-droppoint.ui-droppable[data-type="event"] > button').click(function () {
        //     PF('sidebar_right').show();
        // });
        //
        //
        // adding sections
        this.jq.find('li.ui-tree-droppoint.ui-section-droppoint.ui-droppable[data-type="section"] > button').click(function () {
            updatePrimefacesWidget([{
                name: 'toBeRefreshedWidgetID',
                value: 'sectionDialogWidget'
            }]);
            resetCurrentSection()
            addSection([{
                name: 'newSectionPosition',
                value: $(event.target).parents().eq(2).prevAll('div').children('li.ui-treenode').add($(event.target).parent().parent().prev('li[data-nodetype=section]')).length
            }]);
        });

        //adding  events
        this.jq.find('li.ui-tree-droppoint[data-type="event"] > button').click(function (event) {
            addEvent([{
                name: 'newEventPosition',
                value: $(event.target).parents().eq(2).prevAll('div').children('li.ui-treenode').add($(event.target).parent().parent().prev('li[data-nodetype=event]')).length
            }, {
                //TODO RICHTIGE SELEKTOR FÜR ALLE EVENTS UND SECTIONEN
                name: 'sectionPosition',
                value: $(event.target).closest('li[data-nodetype=section]').parent().prevAll('div').children('li[data-nodetype=section]').length

                // value: $(event.target).parent().parent().parent().prevAll('div').children('li.ui-treenode').add($(event.target).parent().parent().prev('li[data-nodetype=section]')).length

                // value: $(event.target).parents().eq(5).prevAll('div').length
            }]);
            // PF('sidebar_right').show();
        });

        // //editing  events
        // this.jq.find('li.ui-droppable[data-type="event"] > button').click(function () {
        //     editEvent([{
        //         name: 'toEditEventSection',
        //         value: $(event.target).parent().parent().parent().prevAll('div').children('li.ui-treenode').add($(event.target).parent().parent().prev('li[data-nodetype=event]')).length
        //     }]);
        // });
    },
    moveUp: function () {
        var $this = this,
            // selectedItems = $this.list.children('.ui-orderlist-item.ui-state-highlight'),
            selectedItems = this.selections;


        selectedItems = $this.list.children('.ui-orderlist-item.ui-state-highlight'),
            itemsToMoveCount = selectedItems.length,
            movedItemsCount = 0,
            hasFirstChild = selectedItems.is(':first-child');

        if (hasFirstChild) {
            return;
        }

        selectedItems.each(function () {
            var item = $(this);

            if (!item.is(':first-child')) {
                item.hide($this.cfg.effect, {}, 'fast', function () {
                    item.insertBefore(item.prev()).show($this.cfg.effect, {}, 'fast', function () {
                        movedItemsCount++;

                        if (itemsToMoveCount === movedItemsCount) {
                            $this.saveState();
                            $this.fireReorderEvent();
                        }
                    });
                });
            }
            else {
                itemsToMoveCount--;
            }
        });
    },
    trigger_drop: function () {
        var draggable = $("#draggable").draggable(),
            droppable = $("#droppable").droppable(),

            droppableOffset = droppable.offset(),
            draggableOffset = draggable.offset(),
            dx = droppableOffset.left - draggableOffset.left,
            dy = droppableOffset.top - draggableOffset.top;

        draggable.simulate("drag", {
            dx: dx,
            dy: dy
        });
    }
});

$.widget("ui.droppable", {
    version: "1.12.1",
    widgetEventPrefix: "drop",
    options: {
        accept: "*",
        addClasses: true,
        greedy: false,
        scope: "default",
        tolerance: "intersect",
        activate: null,
        deactivate: null,
        drop: null,
        out: null,
        over: null
    },
    _create: function () {
        var ah, ai = this.options, ag = ai.accept;
        this.isover = false;
        this.isout = true;
        this.accept = $.isFunction(ag) ? ag : function (aj) {
            return aj.is(ag)
        };
        this.proportions = function () {
            if (arguments.length) {
                ah = arguments[0]
            } else {
                return ah ? ah : ah = {width: this.element[0].offsetWidth, height: this.element[0].offsetHeight}
            }
        };
        this._addToManager(ai.scope);
        ai.addClasses && this._addClass("ui-droppable")
    },
    _addToManager: function (ag) {
        $.ui.ddmanager.droppables[ag] = $.ui.ddmanager.droppables[ag] || [];
        $.ui.ddmanager.droppables[ag].push(this)
    },
    _splice: function (ag) {
        var ah = 0;
        for (; ah < ag.length; ah++) {
            if (ag[ah] === this) {
                ag.splice(ah, 1)
            }
        }
    },
    _destroy: function () {
        var ag = $.ui.ddmanager.droppables[this.options.scope];
        this._splice(ag)
    },
    _setOption: function (ah, ai) {
        if (ah === "accept") {
            this.accept = $.isFunction(ai) ? ai : function (aj) {
                return aj.is(ai)
            }
        } else {
            if (ah === "scope") {
                var ag = $.ui.ddmanager.droppables[this.options.scope];
                this._splice(ag);
                this._addToManager(ai)
            }
        }
        this._super(ah, ai)
    },
    _activate: function (ah) {
        var ag = $.ui.ddmanager.current;
        this._addActiveClass();
        if (ag) {
            this._trigger("activate", ah, this.ui(ag))
        }
    },
    _deactivate: function (ah) {
        var ag = $.ui.ddmanager.current;
        this._removeActiveClass();
        if (ag) {
            this._trigger("deactivate", ah, this.ui(ag))
        }
    },
    _over: function (ah) {
        var ag = $.ui.ddmanager.current;
        if (!ag || (ag.currentItem || ag.element)[0] === this.element[0]) {
            return
        }
        if (this.accept.call(this.element[0], (ag.currentItem || ag.element))) {
            //todo auf ag.element nodetyp testen
            if (typeof this.element.parent()[0].attributes['data-nodetype'] == 'undefined' || this.element.parent()[0].attributes['data-nodetype'].value == 'section') {
                this._addHoverClass();
            }
            this._trigger("over", ah, this.ui(ag))
        }
    },
    _out: function (ah) {
        var ag = $.ui.ddmanager.current;
        if (!ag || (ag.currentItem || ag.element)[0] === this.element[0]) {
            return
        }
        if (this.accept.call(this.element[0], (ag.currentItem || ag.element))) {
            this._removeHoverClass();
            this._trigger("out", ah, this.ui(ag))
        }
    },
    _drop: function (ah, ai) {
        var ag = ai || $.ui.ddmanager.current, aj = false;
        if (!ag || (ag.currentItem || ag.element)[0] === this.element[0]) {
            return false
        }
        this.element.find(":data(ui-droppable)").not(".ui-draggable-dragging").each(function () {
            var ak = K(this).droppable("instance");
            if (ak.options.greedy && !ak.options.disabled && ak.options.scope === ag.options.scope && ak.accept.call(ak.element[0], (ag.currentItem || ag.element)) && I(ag, $.extend(ak, {offset: ak.element.offset()}), ak.options.tolerance, ah)) {
                aj = true;
                return false
            }
        });
        if (aj) {
            return false
        }
        if (this.accept.call(this.element[0], (ag.currentItem || ag.element))) {
            this._removeActiveClass();
            this._removeHoverClass();
            this._trigger("drop", ah, this.ui(ag));
            return this.element
        }
        return false
    },
    ui: function (ag) {
        return {
            draggable: (ag.currentItem || ag.element),
            helper: ag.helper,
            position: ag.position,
            offset: ag.positionAbs
        }
    },
    _addHoverClass: function () {
        this._addClass("ui-droppable-hover")
    },
    _removeHoverClass: function () {
        this._removeClass("ui-droppable-hover")
    },
    _addActiveClass: function () {
        this._addClass("ui-droppable-active")
    },
    _removeActiveClass: function () {
        this._removeClass("ui-droppable-active")
    }
});


$(function () {
    $("#draggable").draggable();
    $("#droppable").droppable({
        drop: function (event, ui) {
            console.log(event, ui);
            alert('dropped!');
        }
    });
});

function trigger_drop() {
    var draggable = $("#draggable").draggable(),
        droppable = $("#droppable").droppable(),

        droppableOffset = droppable.offset(),
        draggableOffset = draggable.offset(),
        dx = droppableOffset.left - draggableOffset.left,
        dy = droppableOffset.top - draggableOffset.top;

    draggable.simulate("drag", {
        dx: dx,
        dy: dy
    });
}
