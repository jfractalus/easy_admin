var initializeModal = function(result, params){
    document.body.insertAdjacentHTML('afterEnd', result);
    $('#table').bootstrapTable();
    $('#table').bootstrapTable('hideColumn', 'shortD');
    $('#table').bootstrapTable('hideColumn', 'jsonViewRow');
    showModal(params);
}

var showModal = function (modalId) {
    $(modalId).modal({backdrop: false});
}

var showAlert = function(alertId){
    var alert = $(alertId);
    if(alert.is('.hidden')){
        alert.removeClass('hidden');
    }else{
        alert.show();
    }
}

