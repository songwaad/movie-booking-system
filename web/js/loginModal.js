$(document).ready(function () {
    // open modal
    $("#login-button").on("click", function () {
      $("#register-form").hide();
      $("#login-form").show();
  
      $("#login-modal").css("display", "block");
  
      setTimeout(function () {
        $("#login-modal").addClass("show");
      }, 1);
  
      $("#login-modal").animate({ scrollTop: 0 }, 500);
    });
  
    // close modal
    $("#close-login-modal-btn").on("click", function () {
      $("#login-modal").removeClass("show");
      setTimeout(function () {
        $("#login-modal").css("display", "none");
      }, 300);
    });
  
    // close modal when clicking outside
    $(window).on("click", function (event) {
      if ($(event.target).is("#login-modal")) {
        $("#login-modal").removeClass("show");
        setTimeout(function () {
          $("#login-modal").css("display", "none");
        }, 300);
      }
    });
  
    // Switch to Register form
    $("#switchToRegister").on("click", function () {
      $("#login-form").hide();
      $("#register-form").show();
  
      $("#login-modal").animate({ scrollTop: 0 }, 500);
    });
  
    // Switch to Login form
    $("#switchToLogin").on("click", function () {
      $("#register-form").hide();
      $("#login-form").show();
  
      $("#login-modal").animate({ scrollTop: 0 }, 500);
    });
  });