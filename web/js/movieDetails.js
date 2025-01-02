$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get("id");

    if (movieId) {
        fetchMovieDetails(movieId);
    }

    function fetchMovieDetails(movieId) {
        const movieApiUrl = `/Losther_JSP/api/movie/${movieId}`;
        const actorApiUrl = `/Losther_JSP/api/movieActor/${movieId}`;

        $.when(
            $.getJSON(movieApiUrl),
            $.getJSON(actorApiUrl)
        ).done(function (movieData, actorsData) {
            const movie = movieData[0];
            const actors = actorsData[0];

            displayMovieDetails(movie, actors);
        }).fail(function (error) {
            console.error("Error fetching data:", error);
        });
    }

    function displayMovieDetails(movie, actors) {
        const embeded = convertToEmbedUrl(movie.trailer);
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
                        <a href="selectShowtime.jsp?id=${movie.movieId}" class="booking">Booking</a>
                    </div>
                    <div class="actorContainer">
                        <h3>Actor</h3>
                        <div class="actorDetail">
                            ${actors.map(actor => `
                                <div class="actor">
                                    <div class="actorImage">
                                        <img src="${actor.imageUrl}" alt="${actor.fname}">
                                    </div>
                                    <p>${actor.fname} ${actor.lname}</p>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                </div>
            </div>
    
            <!-- Sysnopsis -->
            <div class="synopsisContainer">
                <h3>Synopsis</h3>
                    <p>
                        ${movie.synopsis}
                    </p>
            </div>

            <!-- Trailer -->
            <div class="trailerContainer">
                <h3>Trailer</h3>
                <div class="youtubeContainer">
                    <iframe width="560" height="315" src="${embeded}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
                </div>  
            </div>
        `);
    }

    function convertToEmbedUrl(youtubeUrl) {
        const videoId = youtubeUrl.split('v=')[1].split('&')[0];
        return `https://www.youtube.com/embed/${videoId}`;
    }
});
