window.onload = function() { //hides error message
  let error = document.getElementById("error");
  if (window.location.search.indexOf('error') === -1) {
    error.style.display = "none";
  }
}