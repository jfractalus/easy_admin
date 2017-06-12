var saveRecord = function (url, id, callback) {
    if(id === undefined || id==null){
        $.post(url , serializeObject($('form')), callback);
    }else{
        $.put(url , serializeObject($('form')), callback);
    }
}

var deleteRecord = function (url, id, callback) {
    $.delete(url , id, callback);
    window.location.href = url;
}

jQuery.each( [ "post", "put", "delete" ], function( i, method ) {
    jQuery[ method ] = function( url, data, callback) {
        return jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: url,
            type: method,
            dataType: 'json',
            data: JSON.stringify(data),
            success: callback,
            error: callback
        }).done(function (url) {
            window.location.href = url;
        })
        .error(function() {
            console.log("error");
        });
    };
});

var serializeObject = function(form){
    var o = {};
    var disabled = form.find(':input:disabled').removeAttr('disabled');
    form.find(':input:checkbox').attr('disabled','disabled');
    var a = form.serializeArray(':input:not(:checkbox)');
    disabled.attr('disabled','disabled');
    form.find(':input:checkbox').removeAttr('disabled');
    $.each(a, function() {
        if(this.name.includes('.')){
            var item = this.name.split('.',1);
            var subItem = this.name.split('.',2)[1];
            if (o[item] !== undefined) {
                var jsonSubItem = o[item];
                jsonSubItem[subItem] = this.value;
            } else {
                var jsonSubItem = {};
                jsonSubItem[subItem] = this.value;
                o[item] = jsonSubItem || '';
            }
        }
        else if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            if(this.value.includes('{')) {
                o[this.name].push(JSON.parse(this.value));
            }else{
                o[this.name].push(this.value || '');
            }
        }else if(this.value.includes('{')) {
            o[this.name] = JSON.parse(this.value);
        }else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var getHTMLasync = function(theUrl, callback, params) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText, params);
    }
    xmlHttp.open("GET", theUrl, true);
    xmlHttp.send();
}

// Deprecated by authors
var getHTMLsync = function(theUrl, callback, params) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send();
    if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
        callback(xmlHttp.responseText, params);
}

