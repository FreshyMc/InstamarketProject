let doc = document;

let messagingForm = doc.getElementById('messaging-form');
let msgArea = messagingForm.querySelector('textarea[name="msg-area"]');
/*

let ws = new WebSocket("ws://localhost:8080/socket/demo");

ws.addEventListener('open', function(e){
   console.log('Open ws' ,e);
});

ws.addEventListener('message', function (e){
    console.log(e.data);
});

function sendMessage(msg){
    ws.send(msg);
}

messagingForm.addEventListener('submit', function (e){
    e.preventDefault();

    sendMessage(msgArea.value);

    messagingForm.reset();
})

 */