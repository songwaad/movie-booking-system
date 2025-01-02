$(document).ready(function () {
  let userId;
  let formBooking;

  const previousPage = localStorage.getItem("previousPage");

  const movieId = localStorage.getItem("movieId");
  const showtimeId = localStorage.getItem("showtimeId");
  const movieTitle = localStorage.getItem("movieTitle");
  const movieImageUrl = localStorage.getItem("movieImageUrl");

  const urlParams = new URLSearchParams(window.location.search);

  fetchData(showtimeId);
  function fetchData(showtimeId) {
    const seatApiUrl = `/Losther_JSP/api/seat/${showtimeId}`;
    const showtimeApiUrl = `/Losther_JSP/api/selectTime/${showtimeId}`;

    $.when($.getJSON(seatApiUrl), $.getJSON(showtimeApiUrl))
      .done(function (seatData, showtimeData) {
        const showtime = showtimeData[0];
        localStorage.setItem("showTime", formatTimeTo24Hour(showtime.showTime));
        localStorage.setItem("showDate", showtime.showDate);
        localStorage.setItem("cinemaName", showtime.name);
        createSeatButtons(seatData[0]);
        displayMovie(movieTitle, movieImageUrl, showtime);
      })
      .fail(function (error) {
        console.error("Error fetching data:", error);
      });
  }

  let selectedSeats = []; // เก็บที่นั่งที่เลือก (ชื่อที่นั่ง เช่น A1, B2)
  let selectedSeatIds = []; // เก็บ seatId ของที่นั่งที่เลือก
  let totalPrice = 0; // ราคาเต็ม
  let normalPrice = 100; // ราคา normal
  let vipPrice = 200; // ราคา vip

  // ฟังก์ชันสร้างปุ่มที่นั่ง
  function createSeatButtons(seatData) {
    const $container = $(".seat-container");
    const $seatBox = $("<div>").addClass("seat-box");
    const $monitor = $("<div>").addClass("monitor");
    $seatBox.append($monitor);

    let currentRow = ""; // ตัวแปรเพื่อเช็คแถวปัจจุบัน
    let $rowDiv;

    seatData.forEach((seat) => {
      // เช็คว่าแถวเปลี่ยนหรือไม่
      if (seat.row !== currentRow) {
        currentRow = seat.row;
        $rowDiv = $("<div>").addClass("row"); // สร้าง div ของแถวใหม่
        $seatBox.append($rowDiv);
      }

      // สร้างปุ่มสำหรับที่นั่งในแถวนั้น
      const seatNumber = `${seat.row}${seat.col}`;
      const seatType = seat.seatType; // ประเภทที่นั่ง (normal, vip)
      const seatPrice = seatType === "vip" ? vipPrice : normalPrice; // เลือกราคาตามประเภทที่นั่ง
      const seatStatus = seat.status; // สถานะของที่นั่ง (activate, notActivate)

      const $button = $("<button>")
        .text(seatNumber)
        .data("seatNumber", seatNumber)
        .data("seatPrice", seatPrice) // เก็บราคาที่นั่งใน data ของปุ่ม
        .data("seatId", seat.seatId) // เก็บ seatId
        .addClass(seatType); // เพิ่มคลาสตามประเภทที่นั่ง (normal, vip)

      // เช็คสถานะที่นั่ง
      if (seatStatus === "notActivate") {
        $button.prop("disabled", true); // ทำให้ปุ่มกดไม่ได้
        $button.addClass("unavailable"); // เพิ่มคลาส unavailable เพื่อให้แสดงผลแตกต่าง
      } else {
        // เพิ่ม event click เพื่อเก็บหรือเอาออกจาก select
        $button.on("click", function () {
          const seatValue = $(this).data("seatNumber");
          const seatPrice = $(this).data("seatPrice");
          const seatId = $(this).data("seatId");

          if (selectedSeats.includes(seatValue)) {
            // ถ้าที่นั่งถูกเลือกอยู่แล้ว ให้เอาออกจาก select
            selectedSeats = selectedSeats.filter((item) => item !== seatValue);
            selectedSeatIds = selectedSeatIds.filter((id) => id !== seatId); // เอา seatId ออก
            totalPrice -= seatPrice; // หักราคาออก
            $(this).removeClass("selected"); // เอาคลาสออกเพื่อเปลี่ยนสี
          } else {
            // ถ้าที่นั่งยังไม่ถูกเลือก ให้เพิ่มเข้าไปใน select
            selectedSeats.push(seatValue);
            selectedSeatIds.push(seatId); // เพิ่ม seatId
            totalPrice += seatPrice; // เพิ่มราคาเข้าไป
            $(this).addClass("selected"); // เพิ่มคลาสเพื่อเปลี่ยนสี
          }

          updateSeatDisplay(); // อัพเดตแสดงที่นั่งที่เลือก
          updateTotalPrice(); // อัพเดตราคาทั้งหมด
        });
      }

      $rowDiv.append($button);
    });

    $container.append($seatBox);
  }

  // ฟังก์ชันอัพเดตที่นั่งที่เลือก
  function updateSeatDisplay() {
    // แสดงที่นั่งที่ถูกเลือกในข้อความ
    $("#selected-seats").text(`${selectedSeats.join(", ")}`);
    $("#selected-seat-ids").text(`${selectedSeatIds.join(", ")}`); // แสดง seatId ที่เลือก
  }

  // ฟังก์ชันอัพเดตราคารวม
  function updateTotalPrice() {
    // แสดงราคารวม
    $("#total-price").text(`${totalPrice}.- Baht`);
  }

  
  // ฟังก์ชันแสดงข้อมูลภาพยนตร์และที่นั่ง
  function displayMovie(movieTitle, movieImageUrl, showtime) {
    $("#movie-details").html(`
          <div class="movie-image">
              <img src="${movieImageUrl}" alt="${movieTitle}">
          </div>
          <h1>${movieTitle}</h1>
          <p>Date: ${showtime.showDate}</p>
          <p>Time: ${formatTimeTo24Hour(showtime.showTime)}</p>
          <p>Cinema: ${showtime.name}</p>
          <p>Seat: <span id="selected-seats"></span></p> <!-- แสดงที่นั่งที่เลือกที่นี่ -->
          <p>Price: <span id="total-price"></span></p>
      `);
  }

  // ฟังก์ชันแปลงเวลาเป็นรูปแบบ 24 ชั่วโมง
  function formatTimeTo24Hour(time) {
    const [timePart, modifier] = time.split(" ");
    let [hours, minutes, seconds] = timePart.split(":");
    if (modifier === "PM" && hours !== "12") {
      hours = parseInt(hours) + 12;
    } else if (modifier === "AM" && hours === "12") {
      hours = "00";
    }
    return `${hours}:${minutes}`;
  }

  // เรียกใช้งานฟังก์ชัน
  // เรียกเช็ค session และกำหนด userId
  function checkSession() {
    return $.ajax({
      url: "/Losther_JSP/api/checkSession",
      type: "GET",
      success: function (response) {
        userId = response.userId;
        console.log("response.userId:", response.userId);
      },
      error: function () {
        console.log("Error: Failed to check session.");
      },
    });
  }

  // ฟังก์ชันสร้าง booking ใหม่
  function createBooking() {
    const formBooking = {
      userId: userId,
      showtimeId: showtimeId,
      status: "notActivate",
      totalAmount: totalPrice,
    };

    return $.ajax({
      url: "/Losther_JSP/api/booking",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(formBooking),
      success: function (response) {
        bookingId = response.bookingId;
        localStorage.setItem("bookingId", response.bookingId);
        console.log("Booking Success:", response);
      },
      error: function (xhr, status, error) {
        console.error("Booking failed:", error);
      }
    });
  }

  // ฟังก์ชันจัดเก็บข้อมูลที่นั่งสำหรับ booking
  function saveBookingSeats() {
    const formBookingSeat = selectedSeatIds.map((seatId) => ({
      bookingId: bookingId,
      seatId: seatId,
    }));

    
    $.ajax({
      url: "/Losther_JSP/api/bookingSeat",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(formBookingSeat),
      success: function (response) {
        bookingId = response.bookingId;
        localStorage.setItem("bookingId", response.bookingId);
        console.log("Booking Seat Success:", response);
      },
      error: function (xhr, status, error) {
        console.error("Booking Seat failed:", error);
      }
    });
    
  }


  function updateSeatStatus() {
    $.ajax({
      url:"/Losther_JSP/api/updateSeatStatus",
      type:"POST",
      contentType: "application/json",
      data: JSON.stringify({seatIds: selectedSeatIds}),
      success: function(response) {
        console.log("Response", response);
      },
      error: function(error) {
        console.log("JSON.stringify:",JSON.stringify({seatIds: selectedSeatIds}));
        console.error("Error updateSeatStatus:",error);
      }
    })
  }
  


  // ฟังก์ชันบันทึกข้อมูลที่นั่งที่เลือกใน localStorage เมื่อกดปุ่ม Pay
  $("#pay-button").on("click", async function () {
    try {
      await checkSession();
      await createBooking();
      await saveBookingSeats();
      await updateSeatStatus();

      // บันทึกข้อมูลใน localStorage
      localStorage.setItem("selectedSeats", JSON.stringify(selectedSeats));
      localStorage.setItem("totalPrice", totalPrice); // เก็บราคารวม

      window.location.href = "/Losther_JSP/views/payment.jsp";
    } catch(error) {
      console.error("Error during booking process:", error);
    }
  });

});
