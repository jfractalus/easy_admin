var socket = new SockJS('/event');
var stompClient = Stomp.over(socket);

var socketConnectAndSubscribe = function(urlObject, callback, callbackParams) {
    stompClient.connect({}, function(frame) {
        var url = '/topic/' + urlObject;
        stompClient.subscribe(url, function(result){
            console.log("result: " + result.body);
            callback(callbackParams);
        });
    });
};

var subscribeToChangeEntity = function(urlObject, itemId, oldJsonView, prefixNameOfSubEntities) {
    var json = JSON.parse(prefixNameOfSubEntities);
    stompClient.connect({}, function(frame) {
        subscribe('/topic' + urlObject + '/' + itemId, oldJsonView, false);
        for (var i = 0; i < json.length; i++) {
            subscribe('/topic' + json[i] + '/onDelete', oldJsonView, true);
        }
    });
};

var subscribe = function (url, oldJsonView, subEntityChange) {
    stompClient.subscribe(url, function(result){
        var socketMessage = JSON.parse(result.body);
        var usersAreEqual = compareUser(socketMessage["fromUser"]);
        if(!usersAreEqual && socketMessage["delete"]){
            if(!subEntityChange){
                $('#contentAlert').html("Запись удалена");
                $('#refreshChangeBtn').css("display", "none");
                $('#leaveMyChangeBtn').css("display", "none");
                $('#showDialog').click();
                return;
            } else{
                $('#leaveMyChangeBtn').css("display", "none");
                $('#contentAlert').html("Удалена вложенная запись");
            }
        }
        var newJsonView = socketMessage["body"];
        console.log("oldJsonView: " + oldJsonView);
        console.log("newJsonView: " + newJsonView);
        var jsonViewsAreEqual = compareJson(oldJsonView, newJsonView);
        if(!usersAreEqual && !jsonViewsAreEqual){
            $('#showDialog').click();
        }
    });
};

var compareUser = function (fromUser) {
    var currentUser =  $("#currentUser").text();
    return currentUser == fromUser;
};

var compareJson = function(oldJsonView, newJsonView){
    return _.isEqual(oldJsonView, newJsonView) ? true : false;
};

var socketDisconnect = function() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
};


