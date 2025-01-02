$(document).ready(function() {
    $.ajax({
        url: "/Losther_JSP/api/ticket",
        type: "GET",
        dataType: "json",
        success: function (curTickets) {
            displayTicket(curTickets);
        },
          error: function (xhr, status, error) {
            console.error("Error fetching data:", error);
        }
    });

    function displayTicket(tickets) {
        const $ticketList = $("#ticket-list");

        $ticketList.empty();
        tickets.forEach((ticket) => {
            // กำหนด bg color สำหรับ status
            let statusClass = 'notActivate'; // Default is red (not activated)
            let bgColor = 'red'; // Default bg color

            if (ticket.status === 'active') {
                statusClass = 'active';
                bgColor = 'green';
            } else if (ticket.status === 'cancelled') {
                statusClass = 'cancelled';
                bgColor = 'black';
            }

            const $ticketItem = $(`
                <div class="ticket">
                    <div class="losther">
                        <h1>LOSTHER</h1>
                        <p>Love her in every universe.</p>
                    </div>
                    <div class="ticket-detail">
                        <h3>${ticket.movieTitle}</h3>
                        <p>${ticket.showDate} | ${ticket.showTime}</p>
                        <p>${ticket.cinemaName}</p>
                        <h5>${ticket.row}${ticket.col} | 
                            <span id="status" class="${statusClass}" style="background-color:${bgColor};">${ticket.status}</span>
                        </h5>
                    </div>
                </div>
            `);
            $ticketList.append($ticketItem);
        });
    }
});