<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Movies"); 
    request.setAttribute("nav-movies", "active");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "#"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/movies.css">
<script src="../js/showingMovie.js"></script>

<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<div class="container">
    <div class="showingAndComingLink">
        <button class="active" id="nowShowing" onclick="switchPath('/getCurrentMovieShowing'); return false;">Now Showing</button>
        <div id="line"></div>
        <button class="" id="comingShowing" onclick="switchPath('/getComingMovieShowing'); return false;">Coming Soon</button>
    </div>
    <div class="flexbox" id="movie-list">
        <div class="movie">
        </div>
    </div>
</div>


<%@ include file="components/bottom.jsp" %>


