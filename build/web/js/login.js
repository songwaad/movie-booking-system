$(document).ready(function () {
    localStorage.setItem("previousPage", window.location.href);

    function checkSession() {
        $.ajax({
            url: "/Losther_JSP/api/checkSession",
            type: "GET",
            success: function (response) {
                if (response.loggedIn) {
                    $('#login-button').hide();
                    $('#logout-button').show();
                    $('#ticket-button').show();
                } else {
                    $('#ticket-button').hide();
                    $('#logout-button').hide();
                    $('#login-button').show();
                }
            },
            error: function () {
                $('#ticket-button').hide();
                $('#logout-button').hide();
                $('#login-button').show();
            }
        });
    }

    function redirectToPreviousPage() {
        const previousPage = localStorage.getItem("previousPage");
        window.location.href = previousPage && previousPage !== "null" ? previousPage : "/Losther_JSP/index.jsp";
        localStorage.removeItem("previousPage");
    }

    checkSession();

    $("#login-form").on("submit", function (event) {
        event.preventDefault();

        const loginData = {
            email: $("#email").val(),
            password: $("#password").val(),
            action: "login"
        };

        $.ajax({
            type: "POST",
            url: "/Losther_JSP/api/login",
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function (response) {
                if (response.role === "admin") {
                    window.location.href = "/Losther_JSP/views/admin.jsp";
                } else {
                    redirectToPreviousPage();
                }
                checkSession();
            },
            error: function (xhr, status, error) {
                $("#errorMessage").text("An error occurred: " + error);
            },
        });
    });

    $("#logout-button").on("click", function (event) {
        event.preventDefault();

        $.ajax({
            url: "/Losther_JSP/api/login",
            type: "GET",
            success: function () {
                $('#ticket-button').hide();
                $('#logout-button').hide();
                $('#login-button').show();
                $('#username').text('');
                redirectToPreviousPage();
            },
            error: function (xhr, status, error) {
                console.log("Logout error:", error);
            },
        });
    });
});
