$(document).ready(function () {
  fetchCurrentMovies();
  $movieList.addClass("hidden");

  setTimeout(() => {
    $movieList.empty();
    movies.forEach((movie) => {
      const $movieItem = $(`
                <div class="movie">
                    <a href="movieDetails.jsp?id=${movie.movieId}">
                        <img src="${movie.imageUrl}" alt="${movie.title}">
                        <h5>${new Date(
                          movie.releaseDate
                        ).toLocaleDateString()}</h5>
                        <h4>${movie.title}</h4>
                    </a>
                </div>
            `);
      $movieList.append($movieItem);
    });

    // แสดงรายการภาพยนตร์อีกครั้ง
    $movieList.removeClass("hidden");
  }, 500); // รอเวลาให้เพียงพอสำหรับการ transition
});

function fetchComingMovies() {
  $.ajax({
    url: "/Losther_JSP/api/movieShowtime/getComingMovieShowing",
    type: "GET",
    dataType: "json",
    success: function (curMovies) {
      displayMovies(curMovies);
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
    },
  });
}

function fetchCurrentMovies() {
  $.ajax({
    url: "/Losther_JSP/api/movieShowtime/getCurrentMovieShowing",
    type: "GET",
    dataType: "json",
    success: function (curMovies) {
      displayMovies(curMovies);
    },
    error: function (xhr, status, error) {
      console.error("Error fetching current movies:", error);
    },
  });
}

function displayMovies(movies) {
  const $movieList = $("#movie-list");

  $movieList.empty();
  movies.forEach((movie) => {
    const $movieItem = $(`
            <div class="movie">
                <a href="selectShowtime.jsp?id=${movie.movieId}">
                    <img src="${movie.imageUrl}" alt="${movie.title}">
                    <h5>${new Date(movie.releaseDate).toLocaleDateString()}</h5>
                    <h4>${movie.title}</h4>
                </a>
            </div>
        `);
    $movieList.append($movieItem);
  });
}

function switchPath(path) {
  if (path === "/getCurrentMovieShowing") {
    $("#nowShowing").addClass("active");
    $("#comingShowing").removeClass("active");
    fetchCurrentMovies();
  } else if (path === "/getComingMovieShowing") {
    $("#nowShowing").removeClass("active");
    $("#comingShowing").addClass("active");
    fetchComingMovies();
  }
}


