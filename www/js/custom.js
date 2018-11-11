$(document).ready(function () {
    $.ajax(
        {
            url: "http://localhost:8080/mock"
        })
        .then(function (data) {
            console.log(data);
            
        });
});