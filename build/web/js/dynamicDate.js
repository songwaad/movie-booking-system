$(document).ready(function() {
    const dateContainer = $(".dateContainer");
    const movieTimeContainer = $(".movieTimeContainer");
    const daysOfWeek = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
    const months = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
    const today = new Date();

    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get('id');

    if (!movieId) {
        console.error("Movie ID is required.");
        return;
    }

    for (let i = 0; i < 14; i++) {
        let date = new Date(today);
        date.setDate(today.getDate() + i);

        let day = daysOfWeek[date.getDay()];
        let month = months[date.getMonth()];
        let dateNum = date.getDate();
        let dateValue = date.toISOString().split('T')[0];

        dateContainer.append(`
            <button class="dateDetail" value="${dateValue}">
                <h4>${day}</h4>
                <p>${dateNum}</p>
                <h5>${month}</h5>
            </button>
        `);
    }

    let showtimes = [];

    $.ajax({
        url: `/Losther_JSP/api/showtime/${movieId}`,
        method: "GET",
        success: function(response) {
            showtimes = response;
            $(".dateContainer button").first().trigger("focus");
            $(".dateContainer button").first().trigger("click");
        },
        error: function(error) {
            console.error("Error fetching showtimes:", error);
        }
    });

    // เมื่อคลิกปุ่มวันที่
    $(document).on("click", ".dateDetail", function() {
        const selectedDate = $(this).val();
        movieTimeContainer.empty();

        // กรอง showtimes ที่ตรงกับวันที่ที่เลือก
        const filteredShowtimes = showtimes.filter(showtime => {
            const showDate = new Date(showtime.showDate).toISOString().split('T')[0]; // แปลง showDate ให้เป็น 'YYYY-MM-DD'
            return showDate === selectedDate;
        });

        const groupedByCinema = {};
        filteredShowtimes.forEach(showtime => {
            if (!groupedByCinema[showtime.cinemaId]) {
                groupedByCinema[showtime.cinemaId] = [];
            }
            groupedByCinema[showtime.cinemaId].push(showtime);
        });

        for (const cinemaId in groupedByCinema) {
            const showtimesForCinema = groupedByCinema[cinemaId];
            let cinemaContent = `
                <div class="movieTime">
                    <div class="flexbox">
                        <div class="image">
                            <img src="../${showtimesForCinema[0].imageUrl}" alt="">
                        </div>
                        <div class="content">
                            <h3>${showtimesForCinema[0].name}</h3>
                            <p>Sound : TH/EN</p>
            `;
            
            showtimesForCinema.forEach(showtime => {
                const startTime = formatTimeTo24Hour(showtime.showTime);
                cinemaContent += `
                    <a href="selectSeat.jsp?showtimeId=${showtime.showtimeId}" class="showtimeLink">${startTime}</a>
                `;
            });

            cinemaContent += `
                    </div>
                </div>
            </div>
            `;
            movieTimeContainer.append(cinemaContent);
        }

        if (filteredShowtimes.length === 0) {
            movieTimeContainer.append(`<div class="movieTime">
                    <div class="noShowtime"><p>No showtimes available for this date.</p></div></div>`);
        }
    });

    // เมื่อคลิกลิงก์ showtime เพื่อเก็บข้อมูลใน Local Storage
    $(document).on("click", ".showtimeLink", function() {
        const url = new URL($(this).attr('href'), window.location.href);
        const showtimeId = url.searchParams.get("showtimeId");
        localStorage.setItem("showtimeId", showtimeId);
    });

    function formatTimeTo24Hour(time) {
        const [timePart, modifier] = time.split(' ');
        let [hours, minutes, seconds] = timePart.split(':');
        if (modifier === 'PM' && hours !== '12') {
            hours = parseInt(hours) + 12;
        } else if (modifier === 'AM' && hours === '12') {
            hours = '00';
        }
        return `${hours}:${minutes}`;
    }
});

document.querySelector('.dateDetail').click();
