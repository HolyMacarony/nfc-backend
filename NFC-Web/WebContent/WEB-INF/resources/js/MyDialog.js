if (!PrimeFaces.utils) {

    PrimeFaces.utils = {

        resolveDynamicOverlayContainer: function (widget) {
            return widget.cfg.appendTo
                ? PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(widget.cfg.appendTo)
                : $(document.body);
        },

        /**
         * Removes all the dynamic overlays for the given id.
         */
        removeAllDynamicOverlays: function (overlayId) {
            $(PrimeFaces.escapeClientId(overlayId)).remove();
        },

        /**
         * Removes the overlay from the appendTo overlay container.
         */
        removeDynamicOverlay: function (widget, overlay, overlayId, appendTo) {

            // if the id contains a ':'
            appendTo.children(PrimeFaces.escapeClientId(overlayId)).not(overlay).remove();

            // if the id does NOT contain a ':'
            appendTo.children("[id='" + overlayId + "']").not(overlay).remove();
        },

        appendDynamicOverlay: function (widget, overlay, overlayId, appendTo) {
            var elementParent = overlay.parent();

            // skip when the parent currently is already the same
            // this likely happens when the dialog is updated directly instead of a container
            // as our ajax update mechanism just updates by id
            if (!elementParent.is(appendTo)
                && !appendTo.is(overlay)) {

                PrimeFaces.utils.removeDynamicOverlay(widget, overlay, overlayId, appendTo);

                overlay.appendTo(appendTo);
            }
        },

        addModal: function (id, zIndex, tabbablesCallback) {
            PrimeFaces.utils.preventTabbing(id, zIndex, tabbablesCallback);

            var modalId = id + '_modal';

            var modalOverlay = $('<div id="' + modalId + '" class="ui-widget-overlay ui-dialog-mask"></div>');
            modalOverlay.appendTo($(document.body));
            modalOverlay.css('z-index', zIndex);

            return modalOverlay;
        },

        preventTabbing: function (id, zIndex, tabbablesCallback) {
            //Disable tabbing out of modal and stop events from targets outside of the overlay element
            var $document = $(document);
            $document.on('focus.' + id + ' mousedown.' + id + ' mouseup.' + id, function (event) {
                if ($(event.target).zIndex() < zIndex) {
                    event.preventDefault();
                }
            });
            $document.on('keydown.' + id, function (event) {
                var target = $(event.target);
                if (event.which === $.ui.keyCode.TAB) {
                    var tabbables = tabbablesCallback();
                    if (tabbables.length) {
                        var first = tabbables.filter(':first'),
                            last = tabbables.filter(':last'),
                            focusingRadioItem = null;

                        if (first.is(':radio')) {
                            focusingRadioItem = tabbables.filter('[name="' + first.attr('name') + '"]').filter(':checked');
                            if (focusingRadioItem.length > 0) {
                                first = focusingRadioItem;
                            }
                        }

                        if (last.is(':radio')) {
                            focusingRadioItem = tabbables.filter('[name="' + last.attr('name') + '"]').filter(':checked');
                            if (focusingRadioItem.length > 0) {
                                last = focusingRadioItem;
                            }
                        }

                        if (target.is(document.body)) {
                            first.focus(1);
                            event.preventDefault();
                        }
                        else if (event.target === last[0] && !event.shiftKey) {
                            first.focus(1);
                            event.preventDefault();
                        }
                        else if (event.target === first[0] && event.shiftKey) {
                            last.focus(1);
                            event.preventDefault();
                        }
                    }
                }
                else if (!target.is(document.body) && (target.zIndex() < zIndex)) {
                    event.preventDefault();
                }
            });
        },

        removeModal: function (id) {
            var modalId = id + '_modal';

            // if the id contains a ':'
            $(PrimeFaces.escapeClientId(modalId)).remove();

            // if the id does NOT contain a ':'
            $(document.body).children("[id='" + modalId + "']").remove();

            PrimeFaces.utils.enableTabbing(id);
        },

        enableTabbing: function (id) {
            $(document).off('focus.' + id + ' mousedown.' + id + ' mouseup.' + id + ' keydown.' + id);
        },

        /**
         * Checks if a modal for the given id is currently displayed.
         *
         * @param {type} id the base id
         */
        isModalActive: function (id) {
            var modalId = id + '_modal';

            return $(PrimeFaces.escapeClientId(modalId)).length === 1
                || $(document.body).children("[id='" + modalId + "']").length === 1;
        },


        /**
         * Registers the document handler, to execute the hideCallback when it's clicked outside of the overlay panel.
         *
         * @param {type} widget the widget instance
         * @param {type} hideNamespace the namespace
         * @param {type} overlay the overlay $element
         * @param {type} resolveIgnoredElementsCallback the callback which resolves the elements to ignore when clicked
         * @param {type} hideCallback will be executed when clicked outside
         */
        registerHideOverlayHandler: function (widget, hideNamespace, overlay, resolveIgnoredElementsCallback, hideCallback) {

            widget.addDestroyListener(function () {
                $(document).off(hideNamespace);
            });

            $(document).off(hideNamespace).on(hideNamespace, function (e) {
                if (overlay.is(":hidden")) {
                    return;
                }

                var $eventTarget = $(e.target);

                // do nothing when the element should be ignored
                if (resolveIgnoredElementsCallback) {
                    var elementsToIgnore = resolveIgnoredElementsCallback();
                    if (elementsToIgnore) {
                        if (elementsToIgnore.is($eventTarget) || elementsToIgnore.has($eventTarget).length > 0) {
                            return;
                        }
                    }
                }

                // do nothing when the clicked element is a child of the overlay
                if (overlay.is($eventTarget) || overlay.has($eventTarget).length > 0) {
                    return;
                }

                // old check:
                /*
                var offset = overlay.offset();
                if (e.pageX < offset.left
                        || e.pageX > offset.left + overlay.width()
                        || e.pageY < offset.top
                        || e.pageY > offset.top + overlay.height()) {
                    hideCallback();
                }
                */

                hideCallback(e);
            });
        },


        registerResizeHandler: function (widget, resizeNamespace, element, resizeCallback) {

            widget.addDestroyListener(function () {
                $(window).off(resizeNamespace);
            });

            $(window).off(resizeNamespace).on(resizeNamespace, function (e) {
                if (element && element.is(":hidden")) {
                    return;
                }

                resizeCallback(e);
            });
        },

        registerDynamicOverlay: function (widget, overlay, overlayId) {
            widget.addDestroyListener(function () {
                var appendTo = PrimeFaces.utils.resolveDynamicOverlayContainer(widget);
                PrimeFaces.utils.removeDynamicOverlay(widget, overlay, overlayId, appendTo);
            });

            var appendTo = PrimeFaces.utils.resolveDynamicOverlayContainer(widget);
            PrimeFaces.utils.appendDynamicOverlay(widget, overlay, overlayId, appendTo);
        },


        registerScrollHandler: function (widget, scrollNamespace, scrollCallback) {

            var scrollParent = widget.getJQ().scrollParent();
            if (scrollParent.is('body')) {
                scrollParent = $(window);
            }

            widget.addDestroyListener(function () {
                scrollParent.off(scrollNamespace);
            });

            scrollParent.off(scrollNamespace).on(scrollNamespace, function (e) {
                scrollCallback(e);
            });
        },

        unbdingScrollHandler: function (widget, scrollNamespace) {
            var scrollParent = widget.getJQ().scrollParent();
            if (scrollParent.is('body')) {
                scrollParent = $(window);
            }

            scrollParent.off(scrollNamespace);
        }
    };

}
PrimeFaces.widget = {};

/**
 * BaseWidget for PrimeFaces Widgets
 */
PrimeFaces.widget.BaseWidget = Class.extend({

    init: function (cfg) {
        this.cfg = cfg;
        this.id = cfg.id;
        this.jqId = PrimeFaces.escapeClientId(this.id);
        this.jq = $(this.jqId);
        this.widgetVar = cfg.widgetVar;
        this.destroyListeners = [];

        //remove script tag
        $(this.jqId + '_s').remove();

        if (this.widgetVar) {
            var $this = this;
            this.jq.on("remove", function () {
                PrimeFaces.detachedWidgets.push($this.widgetVar);
            });
        }
    },

    //used in ajax updates, reloads the widget configuration
    refresh: function (cfg) {
        this.destroyListeners = [];

        return this.init(cfg);
    },

    //will be called when the widget after a ajax request if the widget is detached
    destroy: function () {
        PrimeFaces.debug("Destroyed detached widget: " + this.widgetVar);

        for (var i = 0; i < this.destroyListeners.length; i++) {
            var destroyListener = this.destroyListeners[i];
            destroyListener.call(this, this);
        }
    },

    //checks if the given widget is detached
    isDetached: function () {
        var element = document.getElementById(this.id);
        if (typeof(element) !== 'undefined' && element !== null) {
            return false;
        }

        return true;
    },

    //returns jquery object representing the main dom element related to the widget
    getJQ: function () {
        return this.jq;
    },

    /**
     * Removes the widget's script block from the DOM.
     *
     * @param {string} clientId The id of the widget.
     */
    removeScriptElement: function (clientId) {
        $(PrimeFaces.escapeClientId(clientId) + '_s').remove();
    },

    hasBehavior: function (event) {
        if (this.cfg.behaviors) {
            return this.cfg.behaviors[event] != undefined;
        }

        return false;
    },

    addDestroyListener: function (listener) {
        if (!this.destroyListeners) {
            this.destroyListeners = [];
        }
        this.destroyListeners.push(listener);
    }

});

PrimeFaces.widget.DynamicOverlayWidget = PrimeFaces.widget.BaseWidget.extend({
    //@Override
    init: function (cfg) {
        this._super(cfg);

        if (this.cfg.appendTo) {
            this.appendTo = PrimeFaces.utils.resolveDynamicOverlayContainer(this);
            PrimeFaces.utils.appendDynamicOverlay(this, this.jq, this.id, this.appendTo);
        }
    },

    //@Override
    refresh: function (cfg) {
        this._super(cfg);

        if (this.appendTo) {
            PrimeFaces.utils.removeDynamicOverlay(this, this.jq, this.id, this.appendTo);
        }
        PrimeFaces.utils.removeModal(this.id);

        this.appendTo = null;
        this.modalOverlay = null;
    },

    //@Override
    destroy: function () {
        this._super();

        if (this.appendTo) {
            PrimeFaces.utils.removeDynamicOverlay(this, this.jq, this.id, this.appendTo);
        }
        PrimeFaces.utils.removeModal(this.id);

        this.appendTo = null;
        this.modalOverlay = null;
    },

    enableModality: function () {
        this.modalOverlay = PrimeFaces.utils.addModal(this.id,
            this.jq.css('z-index') - 1,
            $.proxy(function () {
                return this.getModalTabbables();
            }, this));
    },

    disableModality: function () {
        PrimeFaces.utils.removeModal(this.id);
        this.modalOverlay = null;
    },

    getModalTabbables: function () {
        return null;
    }
});

/**
 * PrimeFaces Dialog Widget
 */
PrimeFaces.widget.Dialog = PrimeFaces.widget.DynamicOverlayWidget.extend({

    init: function (cfg) {
        this._super(cfg);

        this.content = this.jq.children('.ui-dialog-content');
        this.titlebar = this.jq.children('.ui-dialog-titlebar');
        this.footer = this.jq.find('.ui-dialog-footer');
        this.icons = this.titlebar.children('.ui-dialog-titlebar-icon');
        this.closeIcon = this.titlebar.children('.ui-dialog-titlebar-close');
        this.minimizeIcon = this.titlebar.children('.ui-dialog-titlebar-minimize');
        this.maximizeIcon = this.titlebar.children('.ui-dialog-titlebar-maximize');
        this.cfg.absolutePositioned = this.jq.hasClass('ui-dialog-absolute');
        this.jqEl = this.jq[0];

        this.positionInitialized = false;

        //configuration
        this.cfg.width = this.cfg.width || 'auto';
        this.cfg.height = this.cfg.height || 'auto';
        this.cfg.draggable = this.cfg.draggable === false ? false : true;
        this.cfg.resizable = this.cfg.resizable === false ? false : true;
        this.cfg.minWidth = this.cfg.minWidth || 150;
        this.cfg.minHeight = this.cfg.minHeight || this.titlebar.outerHeight();
        this.cfg.position = this.cfg.position || 'center';
        this.parent = this.jq.parent();

        this.initSize();

        //events
        this.bindEvents();

        if (this.cfg.draggable) {
            this.setupDraggable();
        }

        if (this.cfg.resizable) {
            this.setupResizable();
        }

        //docking zone
        if ($(document.body).children('.ui-dialog-docking-zone').length === 0) {
            $(document.body).append('<div class="ui-dialog-docking-zone"></div>');
        }

        //aria
        this.applyARIA();

        if (this.cfg.visible) {
            this.show();
        }

        if (this.cfg.responsive) {
            this.bindResizeListener();
        }
    },

    //@Override
    refresh: function (cfg) {
        this.positionInitialized = false;
        this.loaded = false;

        $(document).off('keydown.dialog_' + cfg.id);

        if (this.minimized) {
            var dockingZone = $(document.body).children('.ui-dialog-docking-zone');
            if (dockingZone.length && dockingZone.children(this.jqId).length) {
                this.removeMinimize();
                dockingZone.children(this.jqId).remove();
            }
        }

        this.minimized = false;
        this.maximized = false;

        this.init(cfg);
    },

    initSize: function () {
        this.jq.css({
            'width': this.cfg.width,
            'height': 'auto'
        });

        this.content.height(this.cfg.height);

        if (this.cfg.fitViewport) {
            this.fitViewport();
        }
    },

    fitViewport: function () {
        var windowHeight = $(window).height();

        var margin = this.jq.outerHeight(true) - this.jq.outerHeight();
        var headerHeight = this.titlebar.outerHeight(true);
        var contentPadding = this.content.innerHeight() - this.content.height();
        ;
        var footerHeight = this.footer.outerHeight(true) || 0;

        var maxHeight = windowHeight - (margin + headerHeight + contentPadding + footerHeight);

        this.content.css('max-height', maxHeight + 'px');
    },

    //@override
    getModalTabbables: function () {
        return this.jq.find(':tabbable').add(this.footer.find(':tabbable'));
    },

    show: function () {
        if (this.isVisible()) {
            return;
        }

        if (!this.loaded && this.cfg.dynamic) {
            this.loadContents();
        }
        else {
            if (this.cfg.fitViewport) {
                this.fitViewport();
            }

            if (this.positionInitialized === false) {
                this.jqEl.style.visibility = "hidden";
                this.jqEl.style.display = "block";
                this.initPosition();
                this.jqEl.style.display = "none";
                this.jqEl.style.visibility = "visible";
            }

            this._show();
        }
    },

    _show: function () {
        this.moveToTop();

        //offset
        if (this.cfg.absolutePositioned) {
            var winScrollTop = $(window).scrollTop();
            this.jq.css('top', parseFloat(this.jq.css('top')) + (winScrollTop - this.lastScrollTop) + 'px');
            this.lastScrollTop = winScrollTop;
        }

        if (this.cfg.showEffect) {
            var $this = this;

            this.jq.show(this.cfg.showEffect, null, 'normal', function () {
                $this.postShow();
            });
        }
        else {
            //display dialog
            this.jq.show();

            this.postShow();
        }

        if (this.cfg.modal) {
            this.enableModality();
        }
    },

    postShow: function () {
        this.fireBehaviorEvent('open');

        PrimeFaces.invokeDeferredRenders(this.id);

        //execute user defined callback
        if (this.cfg.onShow) {
            this.cfg.onShow.call(this);
        }

        this.jq.attr({
            'aria-hidden': false
            , 'aria-live': 'polite'
        });

        this.applyFocus();
    },

    hide: function () {
        if (!this.isVisible()) {
            return;
        }

        if (this.cfg.hideEffect) {
            var $this = this;

            this.jq.hide(this.cfg.hideEffect, null, 'normal', function () {
                if ($this.cfg.modal) {
                    $this.disableModality();
                }
                $this.onHide();
            });
        }
        else {
            this.jq.hide();
            if (this.cfg.modal) {
                this.disableModality();
            }
            this.onHide();
        }
    },

    applyFocus: function () {
        if (this.cfg.focus)
            PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.focus).focus();
        else
            this.jq.find(':not(:submit):not(:button):not(:radio):not(:checkbox):input:visible:enabled:first').focus();
    },

    bindEvents: function () {
        var $this = this;

        //Move dialog to top if target is not a trigger for a PrimeFaces overlay
        this.jq.mousedown(function (e) {
            if (!$(e.target).data('primefaces-overlay-target')) {
                $this.moveToTop();
            }
        });

        this.icons.on('mouseover', function () {
            $(this).addClass('ui-state-hover');
        }).on('mouseout', function () {
            $(this).removeClass('ui-state-hover');
        }).on('focus', function () {
            $(this).addClass('ui-state-focus');
        }).on('blur', function () {
            $(this).removeClass('ui-state-focus');
        });

        this.closeIcon.on('click', function (e) {
            $this.hide();
            e.preventDefault();
        });

        this.maximizeIcon.click(function (e) {
            $this.toggleMaximize();
            e.preventDefault();
        });

        this.minimizeIcon.click(function (e) {
            $this.toggleMinimize();
            e.preventDefault();
        });

        if (this.cfg.closeOnEscape) {
            $(document).on('keydown.dialog_' + this.id, function (e) {
                var keyCode = $.ui.keyCode,
                    active = parseInt($this.jq.css('z-index')) === PrimeFaces.zindex;

                if (e.which === keyCode.ESCAPE && $this.isVisible() && active) {
                    $this.hide();
                }
                ;
            });
        }
    },

    setupDraggable: function () {
        var $this = this;

        this.jq.draggable({
            cancel: '.ui-dialog-content, .ui-dialog-titlebar-close',
            handle: '.ui-dialog-titlebar',
            containment: $this.cfg.absolutePositioned ? 'document' : 'window',
            stop: function (event, ui) {
                if ($this.hasBehavior('move')) {
                    var move = $this.cfg.behaviors['move'];
                    var ext = {
                        params: [
                            {name: $this.id + '_top', value: ui.offset.top},
                            {name: $this.id + '_left', value: ui.offset.left}
                        ]
                    };
                    move.call($this, ext);
                }
            }
        });
    },

    setupResizable: function () {
        var $this = this;

        this.jq.resizable({
            handles: 'n,s,e,w,ne,nw,se,sw',
            minWidth: this.cfg.minWidth,
            minHeight: this.cfg.minHeight,
            alsoResize: this.content,
            containment: 'document',
            start: function (event, ui) {
                $this.jq.data('offset', $this.jq.offset());

                if ($this.cfg.hasIframe) {
                    $this.iframeFix = $('<div style="position:absolute;background-color:transparent;width:100%;height:100%;top:0;left:0;"></div>').appendTo($this.content);
                }
            },
            stop: function (event, ui) {
                $this.jq.css('position', 'fixed');

                if ($this.cfg.hasIframe) {
                    $this.iframeFix.remove();
                }
            }
        });

        this.resizers = this.jq.children('.ui-resizable-handle');
    },

    initPosition: function () {
        var $this = this;

        //reset
        this.jq.css({left: 0, top: 0});

        if (/(center|left|top|right|bottom)/.test(this.cfg.position)) {
            this.cfg.position = this.cfg.position.replace(',', ' ');

            this.jq.position({
                my: 'center'
                , at: this.cfg.position
                , collision: 'fit'
                , of: window
                //make sure dialog stays in viewport
                , using: function (pos) {
                    var l = pos.left < 0 ? 0 : pos.left,
                        t = pos.top < 0 ? 0 : pos.top,
                        scrollTop = $(window).scrollTop();

                    //offset
                    if ($this.cfg.absolutePositioned) {
                        t += scrollTop;
                        $this.lastScrollTop = scrollTop;
                    }

                    $(this).css({
                        left: l
                        , top: t
                    });
                }
            });
        }
        else {
            var coords = this.cfg.position.split(','),
                x = $.trim(coords[0]),
                y = $.trim(coords[1]);

            this.jq.offset({
                left: x
                , top: y
            });
        }

        this.positionInitialized = true;
    },

    onHide: function (event, ui) {
        this.fireBehaviorEvent('close');

        this.jq.attr({
            'aria-hidden': true
            , 'aria-live': 'off'
        });

        if (this.cfg.onHide) {
            this.cfg.onHide.call(this, event, ui);
        }
    },

    moveToTop: function () {
        this.jq.css('z-index', ++PrimeFaces.zindex);
    },

    toggleMaximize: function () {
        if (this.minimized) {
            this.toggleMinimize();
        }

        if (this.maximized) {
            this.jq.removeClass('ui-dialog-maximized');
            this.restoreState();

            this.maximizeIcon.children('.ui-icon').removeClass('ui-icon-newwin').addClass('ui-icon-extlink');
            this.maximized = false;

            this.fireBehaviorEvent('restoreMaximize');
        }
        else {
            this.saveState();

            var win = $(window);

            this.jq.addClass('ui-dialog-maximized').css({
                'width': win.width() - 6
                , 'height': win.height()
            }).offset({
                top: win.scrollTop()
                , left: win.scrollLeft()
            });

            //maximize content
            var contentPadding = this.content.innerHeight() - this.content.height();
            this.content.css({
                width: 'auto',
                height: this.jq.height() - this.titlebar.outerHeight() - contentPadding
            });

            this.maximizeIcon.removeClass('ui-state-hover').children('.ui-icon').removeClass('ui-icon-extlink').addClass('ui-icon-newwin');
            this.maximized = true;

            this.fireBehaviorEvent('maximize');
        }
    },

    toggleMinimize: function () {
        var animate = true,
            dockingZone = $(document.body).children('.ui-dialog-docking-zone');

        if (this.maximized) {
            this.toggleMaximize();
            animate = false;
        }

        var $this = this;

        if (this.minimized) {
            this.removeMinimize();

            this.fireBehaviorEvent('restoreMinimize');
        }
        else {
            this.saveState();

            if (animate) {
                this.jq.effect('transfer', {
                        to: dockingZone
                        , className: 'ui-dialog-minimizing'
                    }, 500,
                    function () {
                        $this.dock(dockingZone);
                        $this.jq.addClass('ui-dialog-minimized');
                    });
            }
            else {
                this.dock(dockingZone);
                this.jq.addClass('ui-dialog-minimized');
            }
        }
    },

    dock: function (zone) {
        zone.css('z-index', this.jq.css('z-index'));
        this.jq.appendTo(zone).css('position', 'static');
        this.jq.css({'height': 'auto', 'width': 'auto', 'float': 'left'});
        this.content.hide();
        this.minimizeIcon.removeClass('ui-state-hover').children('.ui-icon').removeClass('ui-icon-minus').addClass('ui-icon-plus');
        this.minimized = true;

        if (this.cfg.resizable) {
            this.resizers.hide();
        }

        this.fireBehaviorEvent('minimize');
    },

    saveState: function () {
        this.state = {
            width: this.jq.width(),
            height: this.jq.height(),
            contentWidth: this.content.width(),
            contentHeight: this.content.height()
        };

        var win = $(window);
        this.state.offset = this.jq.offset();
        this.state.windowScrollLeft = win.scrollLeft();
        this.state.windowScrollTop = win.scrollTop();
    },

    restoreState: function () {
        this.jq.width(this.state.width).height(this.state.height);
        this.content.width(this.state.contentWidth).height(this.state.contentHeight);

        var win = $(window);
        this.jq.offset({
            top: this.state.offset.top + (win.scrollTop() - this.state.windowScrollTop)
            , left: this.state.offset.left + (win.scrollLeft() - this.state.windowScrollLeft)
        });
    },

    loadContents: function () {
        var $this = this,
            options = {
                source: this.id,
                process: this.id,
                update: this.id,
                params: [
                    {name: this.id + '_contentLoad', value: true}
                ],
                onsuccess: function (responseXML, status, xhr) {
                    PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                        widget: $this,
                        handle: function (content) {
                            this.content.html(content);
                        }
                    });

                    return true;
                },
                oncomplete: function () {
                    $this.loaded = true;
                    $this.show();
                }
            };

        if (this.hasBehavior('loadContent')) {
            var loadContentBehavior = this.cfg.behaviors['loadContent'];
            loadContentBehavior.call(this, options);
        }
        else {
            PrimeFaces.ajax.Request.handle(options);
        }
    },

    applyARIA: function () {
        this.jq.attr({
            'role': 'dialog'
            , 'aria-labelledby': this.id + '_title'
            , 'aria-hidden': !this.cfg.visible
        });

        this.titlebar.children('a.ui-dialog-titlebar-icon').attr('role', 'button');
    },

    isVisible: function () {
        return this.jq.is(':visible');
    },

    bindResizeListener: function () {
        var $this = this;

        PrimeFaces.utils.registerResizeHandler(this, 'resize.' + this.id, null, function () {
            if ($this.cfg.fitViewport) {
                $this.fitViewport();
            }

            if ($this.isVisible()) {
                // instant reinit position
                $this.initPosition();
            }
            else {
                // reset, so the dialog will be positioned again when showing the dialog next time
                $this.positionInitialized = false;
            }
        });
    },

    fireBehaviorEvent: function (event) {
        if (this.cfg.behaviors) {
            var behavior = this.cfg.behaviors[event];

            if (behavior) {
                behavior.call(this);
            }
        }
    },

    removeMinimize: function () {
        this.jq.appendTo(this.parent).removeClass('ui-dialog-minimized').css({'position': 'fixed', 'float': 'none'});
        this.restoreState();
        this.content.show();
        this.minimizeIcon.removeClass('ui-state-hover').children('.ui-icon').removeClass('ui-icon-plus').addClass('ui-icon-minus');
        this.minimized = false;

        if (this.cfg.resizable) {
            this.resizers.show();
        }
    }

});

/**
 * PrimeFaces ConfirmDialog Widget
 */
PrimeFaces.widget.ConfirmDialog = PrimeFaces.widget.Dialog.extend({

    init: function (cfg) {
        cfg.draggable = false;
        cfg.resizable = false;
        cfg.modal = true;

        if (!cfg.appendTo && cfg.global) {
            cfg.appendTo = '@(body)';
        }

        this._super(cfg);

        this.title = this.titlebar.children('.ui-dialog-title');
        this.message = this.content.children('.ui-confirm-dialog-message');
        this.icon = this.content.children('.ui-confirm-dialog-severity');

        if (this.cfg.global) {
            PrimeFaces.confirmDialog = this;

            this.jq.on('click.ui-confirmdialog', '.ui-confirmdialog-yes, .ui-confirmdialog-no', null, function (e) {
                var el = $(this);

                if (el.hasClass('ui-confirmdialog-yes') && PrimeFaces.confirmSource) {
                    var fn = new Function('event', PrimeFaces.confirmSource.data('pfconfirmcommand'));

                    fn.call(PrimeFaces.confirmSource.get(0), e);
                    PrimeFaces.confirmDialog.hide();
                    PrimeFaces.confirmSource = null;
                }
                else if (el.hasClass('ui-confirmdialog-no')) {
                    PrimeFaces.confirmDialog.hide();
                    PrimeFaces.confirmSource = null;
                }

                e.preventDefault();
            });
        }
    },

    applyFocus: function () {
        this.jq.find(':button,:submit').filter(':visible:enabled').eq(0).focus();
    },

    showMessage: function (msg) {
        if (msg.beforeShow) {
            eval(msg.beforeShow);
        }

        var icon = (msg.icon === 'null') ? 'ui-icon-alert' : msg.icon;
        this.icon.removeClass().addClass('ui-icon ui-confirm-dialog-severity ' + icon);

        if (msg.header)
            this.title.text(msg.header);

        if (msg.message) {
            if (msg.escape) {
                this.message.text(msg.message);
            }
            else {
                this.message.html(msg.message);
            }
        }

        this.show();
    }

});

/**
 * PrimeFaces Dynamic Dialog Widget for Dialog Framework
 */
PrimeFaces.widget.DynamicDialog = PrimeFaces.widget.Dialog.extend({

    //@Override
    show: function () {
        if (this.jq.hasClass('ui-overlay-visible')) {
            return;
        }

        if (this.positionInitialized === false) {
            this.initPosition();
        }

        this._show();
    },

    //@Override
    _show: function () {
        //replace visibility hidden with display none for effect support, toggle marker class
        this.jq.removeClass('ui-overlay-hidden').addClass('ui-overlay-visible').css({
            'display': 'none'
            , 'visibility': 'visible'
        });

        this.moveToTop();

        this.jq.show();

        if (this.cfg.height != "auto") {
            this.content.height(this.jq.outerHeight() - this.titlebar.outerHeight(true));
        }

        this.postShow();

        if (this.cfg.modal) {
            this.enableModality();
        }
    },

    //@Override
    initSize: function () {
        this.jq.css({
            'width': this.cfg.width,
            'height': this.cfg.height
        });

        if (this.cfg.fitViewport) {
            this.fitViewport();
        }
    }

});