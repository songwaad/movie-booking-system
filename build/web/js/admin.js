$(document).ready(function () {
    
    fetchPayment();
    
    function fetchPayment() {
        $.ajax({
            url: "/Losther_JSP/api/payment",
            type: "GET",
            dataType: "json",
            success: function (payments) {
                displayPayment(payments);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching data:", error);
            }
        });
    }

    function updateStatus(paymentId, bookingId, status) {
        $.ajax({
            url: "/Losther_JSP/api/updatePaymentStatus",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ paymentId: paymentId, bookingId: bookingId, status: status }),
            success: function () {
                console.log("Status updated successfully");
                fetchPayment(); // อัปเดตข้อมูลใหม่หลังจากเปลี่ยนสถานะ
            },
            error: function (xhr, status, error) {
                console.error("Error updating status:", error);
            }
        });
    }

    function displayPayment(payments) {
        const $paymentList = $("#payment-list tbody");
        $paymentList.empty(); // เคลียร์ข้อมูลเก่า
    
        payments.forEach((payment) => {
            const $paymentRow = $(`
                <tr>
                    <td>${payment.paymentId}</td>
                    <td>${payment.bookingId}</td>
                    <td>${payment.amount}</td>
                    <td>${payment.paymentMethod}</td>
                    <td>${payment.paymentDate}</td>
                    <td>${payment.status}</td>
                    <td>
                        <button class="activate-btn">Activate</button>
                        <button class="cancel-btn">Cancel</button>
                    </td>
                </tr>
            `);
    
            // กำหนด event handler สำหรับปุ่ม Activate และ Cancel
            $paymentRow.find(".activate-btn").on("click", function () {
                updateStatus(payment.paymentId, payment.bookingId, "active");
            });
    
            $paymentRow.find(".cancel-btn").on("click", function () {
                updateStatus(payment.paymentId, payment.bookingId, "cancelled");
            });
    
            // เพิ่มแถวในตาราง
            $paymentList.append($paymentRow);
        });
    }
});
