<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Select Seat"); 
    request.setAttribute("nav-movies", "");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/progreesbar.css">
<link rel="stylesheet" href="../css/selectSeat.css">
<script src="../js/selectSeat.js"></script>
<script src="../js/progressBar.js"></script>
<script>currentStep = 2;</script>

<%@ include file="components/progressbar.jsp" %>
<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<div class="container">
    <div class="seat-container">
    </div>
    <div class="movie-container">
        <div id="movie-details">    
        </div>
        <button id="pay-button">PAY</button>
    </div>
</div>

<%@ include file="components/bottom.jsp" %>