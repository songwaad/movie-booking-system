$(document).ready(function () {
    // รับ movieId จาก URL
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get("id");

    if (movieId) {
        fetchMovieDetails(movieId);
    }

    function fetchMovieDetails(movieId) {

        $.ajax({
            url: `/Losther_JSP/api/movie/${movieId}`,
            type: "GET",
            dataType: "json",
            success: function (movie) {
                displayMovieDetails(movie);
                localStorage.setItem("movieId", movie.movieId);
                localStorage.setItem("movieTitle", movie.title);
                localStorage.setItem("movieImageUrl", movie.imageUrl);
                
            },
            error: function (xhr, status, error) {
              console.error("Error fetching data:", error);
            },
          });
    }

    function displayMovieDetails(movie) {
        $("#movie-details").html(`
            <div class="movieContainer">
                <div class="movieImage">
                    <img src="${movie.imageUrl}" alt="${movie.title}">
                </div>
                <div class="movieDetail">
                    <div class="movieHead">
                        <h1>${movie.title}</h1>
                        <p>Release Date : ${new Date(movie.releaseDate).toLocaleDateString()}</p>
                        <p>Genre : Action</p>
                        <p>Duration : ${movie.duration} Mins</p>
                        <a href="movieDetails.jsp?id=${movie.movieId}" class="booking">DETAIL</a>
                    </div>
                </div>
            </div>
        `);
    }

});
