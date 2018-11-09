let stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);

  $("#username").prop("disabled", connected);
  $("#message").prop("disabled", !connected);
  $("#sendMessage").prop("disabled", !connected);


  if (connected) {
    $("#conversation").show();
  }
  else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}

function connect() {
  let socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  return new Promise(
    (resolve) => {
      stompClient.connect({}, (frame => resolve(frame)))
    });
}

function subscribeToGreetings(frame) {
  setConnected(true);
  console.log('Connected: ' + frame);
  return new Promise(
    (resolve) => {
      stompClient.subscribe('/topic/lobby', function (greeting) {
        showGreeting(JSON.parse(greeting.body));
      });
      resolve();
    });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  stompClient.send("/app/entry", {}, JSON.stringify({'name': $("#username").val()}));
}

function sendMessage() {
  stompClient.send("/app/lobby", {}, JSON.stringify({'name': $("#username").val(), 'message': $("#message").val()}));
  const message = document.getElementById('message');
  message.value = '';

}

function showGreeting(body) {
  $("#greetings").append(
    `<tr class="msg">
        <td class="msg-timestamp block">${body.timestamp}</td>
        <td class="msg-name">${body.name}</td>
        <td class="msg-message" style="width: 100%;"> ${body.message} </td></tr>`);
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });

  $("#connect").click(function (e) {
    const username = document.getElementById('username');
    if (username.checkValidity()) {
      connect()
        .then(subscribeToGreetings)
        .then(sendName);
    }
    document.getElementById('message').focus();
    document.getElementById('message').select();
  });

  $("#disconnect").click(function () {
    disconnect();
  });

  $("#sendMessage").click(function () {
    const message = document.getElementById('message');
    if (!(message.value === '')) {
      console.log(message);
      sendMessage();
    }
  });
});