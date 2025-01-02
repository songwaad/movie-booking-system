document.addEventListener('DOMContentLoaded', function () {
    const movieTableBody = document.querySelector('#movieTable tbody');
    const addMovieBtn = document.querySelector('#addMovieBtn');
    const movieModal = document.querySelector('#movieModal');
    const closeModal = document.querySelector('#closeModal');
    const movieForm = document.querySelector('#movieForm');
    const modalTitle = document.querySelector('#modalTitle');
    
    // API URL
    const API_URL = '/Losther_JSP/api/movie';

    // Show the movie list
    function getMovies() {
        fetch(API_URL)
            .then(response => response.json())
            .then(movies => {
                movieTableBody.innerHTML = '';
                movies.forEach(movie => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${movie.movieId}</td>
                        <td>${movie.title}</td>
                        <td>${new Date(movie.releaseDate).toLocaleDateString()}</td>
                        <td>${movie.duration}</td>
                        <td>${movie.synopsis}</td>
                        <td><a href="${movie.trailer}" target="_blank">Watch</a></td>
                        <td><img src="${movie.imageUrl}" alt="Movie Image" width="100"></td>
                        <td>
                            <button class="editBtn" data-id="${movie.movieId}">Edit</button>
                            <button class="deleteBtn" data-id="${movie.movieId}">Delete</button>
                        </td>
                    `;
                    movieTableBody.appendChild(row);
                });

                // Edit movie
                document.querySelectorAll('.editBtn').forEach(button => {
                    button.addEventListener('click', editMovie);
                });

                // Delete movie
                document.querySelectorAll('.deleteBtn').forEach(button => {
                    button.addEventListener('click', deleteMovie);
                });
            });
    }

    // Open the modal for adding a new movie
    addMovieBtn.addEventListener('click', () => {
        modalTitle.textContent = 'Add Movie';
        movieForm.reset();
        movieModal.style.display = 'block';
    });

    // Close the modal
    closeModal.addEventListener('click', () => {
        movieModal.style.display = 'none';
    });

    // Add or update movie
    movieForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const movieData = {
            title: document.querySelector('#title').value,
            releaseDate: document.querySelector('#releaseDate').value,
            duration: document.querySelector('#duration').value,
            synopsis: document.querySelector('#synopsis').value,
            trailer: document.querySelector('#trailer').value,
            imageUrl: document.querySelector('#imageUrl').value
        };

        const method = movieData.movieId ? 'PUT' : 'POST';
        const url = movieData.movieId ? `${API_URL}/${movieData.movieId}` : API_URL;

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(movieData)
        })
        .then(response => response.json())
        .then(() => {
            getMovies();
            movieModal.style.display = 'none';
        });
    });

    // Edit movie
    function editMovie(e) {
        const movieId = e.target.getAttribute('data-id');
        fetch(`${API_URL}/${movieId}`)
            .then(response => response.json())
            .then(movie => {
                modalTitle.textContent = 'Edit Movie';
                document.querySelector('#movieId').value = movie.movieId;
                document.querySelector('#title').value = movie.title;
                document.querySelector('#releaseDate').value = movie.releaseDate;
                document.querySelector('#duration').value = movie.duration;
                document.querySelector('#synopsis').value = movie.synopsis;
                document.querySelector('#trailer').value = movie.trailer;
                document.querySelector('#imageUrl').value = movie.imageUrl;
                movieModal.style.display = 'block';
            });
    }

    // Delete movie
    function deleteMovie(e) {
        const movieId = e.target.getAttribute('data-id');
        if (confirm('Are you sure you want to delete this movie?')) {
            fetch(`${API_URL}/${movieId}`, {
                method: 'DELETE'
            })
            .then(() => {
                getMovies();
            });
        }
    }

    // Initial load of movies
    getMovies();
});
