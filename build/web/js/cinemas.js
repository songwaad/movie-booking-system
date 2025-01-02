$(document).ready(function () {
  $.ajax({
    url: "/Losther_JSP/api/cinema",
    type: "GET",
    dataType: "json",
    success: function (cinemas) {
      displayCinemas(cinemas);
    },
    error: function (error) {
      console.error("Error fetching data:", error);
    },
  });
});

function displayCinemas(cinemas) {
  const $cinemaList = $("#cinema-list");

  $cinemaList.empty();
  cinemas.forEach((cinema) => {
    const openTime = formatTimeTo24Hour(cinema.openTime);
    const closeTime = formatTimeTo24Hour(cinema.closeTime);
    const $cinemaItem = $(`
      <div class="cinemaContainer">
        <div class="imageContainer">
            <img src="../${cinema.imageUrl}" alt="${cinema.name}">
        </div>
        <div class="cinemaDetail">
            <h1>${cinema.name}</h1>
        
            <p>Opening : ${openTime} - ${closeTime}</p>
            <p>Phone : ${cinema.contactNumber}</p>
            <p>Address : ${cinema.address}</p>
        </div>
      </div>
          `);
    $cinemaList.append($cinemaItem);
  });
}

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
