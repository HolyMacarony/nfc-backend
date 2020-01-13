function removeMarkAndToolTip(event, terminator = false) {

    function checkTimeout(timer1, timer2, timer3, timer4,timer5) {
        clearTimeout(timer1);
        clearTimeout(timer2);
        clearTimeout(timer3);
        clearTimeout(timer4);
        clearTimeout(timer5);
    }

    if (typeof timer1 !== 'undefined' && typeof timer2 !== 'undefined' && typeof timer3 !== 'undefined'&& typeof timer4 !== 'undefined'&& typeof timer5 !== 'undefined') {
        checkTimeout(timer1, timer2, timer3,timer4,timer5);
    }
    if (!terminator) {
        timer1 = setTimeout(hideTooltips, 5500);
        timer2 = setTimeout(clearToBeDeletedEventDelayed.bind(null, event), 5000);
        timer3 = setTimeout(clearToBeDeletedSectionDelayed.bind(null, event), 5000);
        timer4 = setTimeout(clearToBeDeletedUserDelayed.bind(null, event), 5000);
        timer5 = setTimeout(clearToBeDeletedHikeDelayed.bind(null, event), 5000);
    }
}

function hideTooltips() {
    for (var w in PrimeFaces.widgets) {
        if (w.startsWith('eventToolTipVar') || w.startsWith('sectionToolTipVar') || w.startsWith('userToolTipVar')|| w.startsWith('hikeToolTipVar')) {
            PrimeFaces.widgets[w]._hide();
        }
    }
}


function clearToBeDeletedEventDelayed(event) {
    if (typeof clearToBeDeletedEvent === "function") {
        clearToBeDeletedEvent();
        refreshPrimefacesWidget(event);
    }
}

function clearToBeDeletedSectionDelayed(event) {
    if (typeof clearToBeDeletedSection === "function") {
        clearToBeDeletedSection();
        refreshPrimefacesWidget(event);
    }
}

function clearToBeDeletedUserDelayed(event) {
    if (typeof clearToBeDeletedUser === "function") {
        clearToBeDeletedUser();
        refreshPrimefacesWidget(event);
    }
}
function clearToBeDeletedHikeDelayed(event) {
    if (typeof clearToBeDeletedHike === "function") {
        clearToBeDeletedHike();
        refreshPrimefacesWidget(event);
    }
}


function refreshPrimefacesWidget(event) {
    updatePrimefacesWidget([{
        name: 'toBeRefreshedWidgetID',
        value: "widget_" + event.target.parentElement.id.replace(/:/g, "_")
    }]);
}

function refreshPrimefacesWidgetByWidgetVar(widgetvar) {
    updatePrimefacesWidgetByWidgetVar([{
        name: 'toBeRefreshedWidgetID',
        value: widgetvar
    }]);
}

//prevent dialogs from closing when form is invalid
function hideDialogOnSuccess(args, dialogWidgetVar) {
    if (args && !args.validationFailed) {
        PF(dialogWidgetVar).hide();
    }
    else {
        PF(dialogWidgetVar).jq.effect("shake", {
            times: 6
        }, 300);
    }
}


function showFirstInvalidTab() {
    var errorTab = $('div.ui-tabs-panel:has(.ui-message-error)').attr('id');
    if (errorTab) {
        var expression = 'a[href="#' + errorTab + '"]';
        var link = $(expression);
        if (link) {
            link.click();
        }
    }
}