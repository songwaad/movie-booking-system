$(document).ready(function () {
    const $circles = $(".circle");
    const $indicator = $(".indicator");
  
    $circles.each(function (index) {
      const $circle = $(this);
      const $circleDetail = $circle.next(".circleDetail");
      if (index < currentStep) {
        $circle.addClass("active");
        $circleDetail.addClass("active");
      } else {
        $circle.removeClass("active");
        $circleDetail.removeClass("active");
      }
    });
  
    // ปรับความกว้างของ indicator ตาม currentStep
    $indicator.css(
      "width",
      `${((currentStep - 1) / ($circles.length - 1)) * 100}%`
    );
  });