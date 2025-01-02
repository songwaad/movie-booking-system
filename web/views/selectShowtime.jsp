<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Select Showtime"); 
    request.setAttribute("nav-movies", "");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/selectShowtime.css">
<link rel="stylesheet" href="../css/progreesbar.css">
<script src="../js/selectShowtime.js"></script>
<script src="../js/progressBar.js"></script>
<script src="../js/dynamicDate.js"></script>
<script>let currentStep = 1;</script>

<!-- Movie -->
<%@ include file="components/progressbar.jsp" %>
<div class="container">
    <div id="movie-details"></div>
    <div class="dateContainer" style="display:absolute; margin:5% auto;"></div>
</div>
   
<div class="movieTimeContainer" >
    
</div>


<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<%@ include file="components/bottom.jsp" %>