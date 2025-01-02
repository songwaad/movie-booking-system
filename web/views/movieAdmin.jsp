<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Movie | Admin"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/movieAdmin.css">
<script src="../js/movieAdmin.js"></script>

<%@ include file="components/login-modal.jsp" %>

<nav id="navbar">
    <div class="menu">
        <p class="logo">ADMIN</p>
        <a href="admin.jsp" class="">Payment</a>
           <a href="#" class="active">Movie</a>
    </div>
    <div class="icon">
        <div><a href="#" id="login-button"><i class="fa-solid fa-right-to-bracket"></i></a></div>
        <div><a href="#" id="logout-button"><i class="fa-solid fa-power-off"></i></a></div>
    </div>
</nav>

<div class="container">
    <h2>Movie Management</h2>
    
    <button id="addMovieBtn">Add Movie</button>
    
    <!-- Movie List Table -->
    <table id="movieTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Release Date</th>
                <th>Duration</th>
                <th>Synopsis</th>
                <th>Trailer</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
    
    <!-- Add/Edit Movie Modal -->
    <div id="movieModal" class="modal">
        <div class="modal-content">
            <span id="closeModal" class="close">&times;</span>
            <h3 id="modalTitle">Add Movie</h3>
            
            <form id="movieForm">
                <input type="hidden" id="movieId">
                <label for="title">Title:</label>
                <input type="text" id="title" required>
                
                <label for="releaseDate">Release Date:</label>
                <input type="date" id="releaseDate" required>
                
                <label for="duration">Duration (minutes):</label>
                <input type="number" id="duration" required>
                
                <label for="synopsis">Synopsis:</label>
                <textarea id="synopsis" required></textarea>
                
                <label for="trailer">Trailer URL:</label>
                <input type="text" id="trailer">
                
                <label for="imageUrl">Image URL:</label>
                <input type="text" id="imageUrl">
                
                <button type="submit">Save</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="components/bottom.jsp" %>
