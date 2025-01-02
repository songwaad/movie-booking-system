$(document).ready(function() {
    const bookingId = localStorage.getItem("bookingId");
    const selectedSeats = JSON.parse(localStorage.getItem("selectedSeats")) || [];
    const movieTitle = localStorage.getItem("movieTitle");
    const showtimeId = localStorage.getItem("showtimeId");
    const showDate = localStorage.getItem("showDate");
    const showTime = localStorage.getItem("showTime");
    const cinemaName = localStorage.getItem("cinemaName");
    const amount = localStorage.getItem("totalPrice");
    const seatString = selectedSeats.join(", ");

    displayPaymentInfo();

    // ฟังก์ชันสำหรับแสดงข้อมูลการชำระเงิน
    function displayPaymentInfo() {
        $("#payment-box").html(`
            <div class="imageBox">
                <img src="../images/payment.png" alt="payment-qrcode">
            </div>
            <h3>${movieTitle}</h3>
            <p>Date: ${showDate}</p>
            <p>Time: ${showTime}</p>
            <p>Cinema: ${cinemaName}</p>
            <p>Seat: ${seatString}</p>
            <p>Price: ${amount}.- Baht</p>
            <button id="confirmPay-button">CONFIRM PAY</button>
        `);
    }

    function createPayment() {
        const formPayment = {
            bookingId : bookingId,
            amount : amount,
            paymentMethod : "PromtPAY",
            status : "notActivate"
        }

        $.ajax({
            url: "/Losther_JSP/api/payment",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formPayment),
            success: function(response) {
                console.log("Create Payment Succesee", response);
            },
            error: function(error) {
                console.error("Create Payment Error", error);
            }

        })
    }

    $("#confirmPay-button").on("click", async function () {
        await createPayment();
        window.location.href = "/Losther_JSP/views/finish.jsp";
    });
});
