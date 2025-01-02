<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Payment"); 
    request.setAttribute("nav-movies", "");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/progreesbar.css">
<link rel="stylesheet" href="../css/payment.css">
<script src="../js/payment.js"></script>
<script src="../js/progressBar.js"></script>
<script>currentStep = 3;</script>

<%@ include file="components/progressbar.jsp" %>
<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>


<div class="container">
    <div class="payment-box" id="payment-box">
        
    </div>
</div>


<%@ include file="components/bottom.jsp" %>