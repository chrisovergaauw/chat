let stompClient = null;
let sessionId = "";
let username = "";

window.onload = function() {
  connect()
    .then(() => {
      subscribeToChatRoom();
      subscribeToPrivateChannel();
    });
};

function connect() {
  let socket = new SockJS('/secured/room');
  stompClient = Stomp.over(socket);
  return new Promise(
    (resolve) => {
      stompClient.connect({}, function (frame) {
        let url = stompClient.ws._transport.url;
        url = url.replace(
          "ws://localhost:8080/secured/room/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        sessionId = url;
        username = frame.headers['user-name'];
        resolve();
      });
    });
}

function subscribeToChatRoom(frame) {
  return new Promise(
    (resolve) => {
      stompClient.subscribe('/secured/chatRoomHistory', function(msgOut) {
        showMessages(JSON.parse(msgOut.body));
        resolve();
      });
    });
};

function subscribeToPrivateChannel(frame) {
  return new Promise(
    (resolve) => {
      stompClient.subscribe('/secured/user/queue/specific-user'
        + '-user' + sessionId, function (msgOut) {
         showMessages(JSON.parse(msgOut.body));
         resolve();
        });
    });
}

function sendMessage() {
  stompClient.send("/spring-security-mvc-socket/secured/chatRoom", {}, JSON.stringify({'from': username, 'text': $("#message").val()}));
  const message = document.getElementById('message');
  message.value = '';

}

function showMessages(body) {
  if (Array.isArray(body)) {
    body.forEach(msg => showMessage(msg));
  } else {
    showMessage(body);
  }
}

function showMessage(msg) {
  $("#greetings").append(
    `<tr class="msg">
        <td class="msg-timestamp block">${msg.timestamp}</td>
        <td class="msg-name">${msg.from}</td>
        <td class="msg-message" style="width: 100%;"> ${msg.text} </td></tr>`);
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });

  $("#sendMessage").click(function () {
    const message = document.getElementById('message');
    if (!(message.value === '')) {
      console.log(message);
      sendMessage();
    }
  });
});