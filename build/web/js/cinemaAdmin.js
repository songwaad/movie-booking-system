$(document).ready(function () {
    fetchCinema();
    
    function fetchCinema() {
        $.ajax({
            url: "/Losther_JSP/api/cinema",
            type: "GET",
            dataType: "json",
            success: function (cinemas) {
                displayCinema(cinemas);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching data:", error);
            }
        });
    }

    function deleteCinema(cinemaId) {
        $.ajax({
            url: `/Losther_JSP/api/cinema/${cinemaId}`,
            type: "DELETE",
            success: function () {
                console.log("Cinema deleted successfully");
                fetchCinema(); // อัปเดตรายการหลังลบ
            },
            error: function (xhr, status, error) {
                console.error("Error deleting cinema:", error);
            }
        });
    }

    function editCinema(cinema) {
        $.ajax({
            url: "/Losther_JSP/api/cinema",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(cinema),
            success: function () {
                console.log("Cinema updated successfully");
                fetchCinema(); // อัปเดตรายการหลังแก้ไข
            },
            error: function (xhr, status, error) {
                console.error("Error updating cinema:", error);
            }
        });
    }

    function displayCinema(cinemas) {
        const $cinemaList = $("#cinema-list tbody");
        $cinemaList.empty(); // เคลียร์ข้อมูลเก่า
    
        cinemas.forEach((cinema) => {
            const $cinemaRow = $(`
                <tr>
                    <td>${cinema.cinemaId}</td>
                    <td>${cinema.name}</td>
                    <td>${cinema.openTime}</td>
                    <td>${cinema.closeTime}</td>
                    <td>${cinema.contactNumber}</td>
                    <td>${cinema.imageUrl}</td>
                    <td>${cinema.address}</td>
                    <td>
                        <button class="edit-btn">Edit</button>
                        <button class="delete-btn">Delete</button>
                    </td>
                </tr>
            `);
            
            // กำหนด event handler สำหรับปุ่ม Edit และ Delete
            $cinemaRow.find(".delete-btn").on("click", function () {
                deleteCinema(cinema.cinemaId);
            });

            $cinemaRow.find(".edit-btn").on("click", function () {
                // รับข้อมูลที่ต้องการแก้ไขจากผู้ใช้
                const updatedCinema = {
                    cinemaId: cinema.cinemaId,
                    name: prompt("Enter new name:", cinema.name),
                    openTime: prompt("Enter new open time:", cinema.openTime),
                    closeTime: prompt("Enter new close time:", cinema.closeTime),
                    contactNumber: prompt("Enter new contact number:", cinema.contactNumber),
                    imageUrl: prompt("Enter new image URL:", cinema.imageUrl),
                    address: prompt("Enter new address:", cinema.address)
                };
                editCinema(updatedCinema);
            });

            $cinemaList.append($cinemaRow);
        });
    }
});
