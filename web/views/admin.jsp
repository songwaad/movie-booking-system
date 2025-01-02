<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Payment | Admin"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/admin.css">
<script src="../js/admin.js"></script>

<%@ include file="components/login-modal.jsp" %>

<nav id="navbar">
    <div class="menu">
        <p class="logo">ADMIN</p>
        <a href="#" class="active">Payment</a>
           <a href="movieAdmin.jsp" class="">Movie</a>
    </div>
    <div class="icon">
        <div><a href="#" id="login-button"><i class="fa-solid fa-right-to-bracket"></i></a></div>
        <div><a href="#" id="logout-button"><i class="fa-solid fa-power-off"></i></a></div>
    </div>
</nav>

<div class="container">
    <h1 style="font-weight: 600;">Payment</h1>
    <div class="table-container">
        <table id="payment-list" class="payment-table">
            <thead>
                <tr style="font-weight: 500;">
                    <th>Payment Id</th>
                    <th>Booking Id</th>
                    <th>Amount</th>
                    <th>Payment Method</th>
                    <th>Payment Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <!-- ตารางข้อมูลจะถูกเพิ่มที่นี่โดย jQuery -->
            </tbody>
        </table>
    </div>
</div>

<%@ include file="components/bottom.jsp" %>
