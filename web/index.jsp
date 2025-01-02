<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("pageTitle", "Home");
    request.setAttribute("nav-home", "active");
    
    request.setAttribute("href-home", "#"); 
    request.setAttribute("href-movies", "views/movies.jsp"); 
    request.setAttribute("href-cinemas", "views/cinemas.jsp"); 
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOSTHER | <%= request.getAttribute("pageTitle")%></title>
        <%@ include file="views/home/importCSS_JS.jsp" %>
    </head>
<body>
<header>
    <!-- NAVBAR -->
    <%@ include file="views/components/navbar.jsp" %>
    <!-- NAVBAR -->
    <div class="video-container">
        <video id="movie-video" src="videos/Gwen Stacy's Death Scene - The Amazing Spider-Man 2 (2014) Movie CLIP HD_2.mp4" autoplay muted loop></video>
    </div>
    <!-- Content -->
    <div class="content">
        <h1 id="title">I lost Gwen, she was my MJ.</h1>
        <p id="description">
            The Amazing Spider-Man 2 : The emergence of a powerful new villain and the return of an old friend bring Peter Parker to the realisation that all his enemies have one thing in common: Oscorp. Based on Marvel Comics characters.</p>
        <div class="detail">
            <h6 id="origin">Marvel Entertainment</h6>
            <h5 id="genre">Action, Adventure, Sci-Fi</h5>
            <h4 id="year">2014</h4>
        </div>
        <div class="btns">
            <a href="views/selectShowtime.jsp?id=mov00024" class="booking">Booking</a>
            <a href="views/movieDetails.jsp?id=mov00024"><i class="fa-solid fa-circle-info"></i></a>
        </div>
    </div>
    <!-- Content -->    
</header>
<%@ include file="views/components/login-modal.jsp" %>
<%@ include file="views/components/bottom.jsp" %>


