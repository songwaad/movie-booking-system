<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Cinema | Admin"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/cinemaAdmin.css">
<script src="../js/cinemaAdmin.js"></script>

<%@ include file="components/login-modal.jsp" %>

<nav id="navbar">
    <div class="menu">
        <p class="logo">ADMIN</p>
        <a href="admin.jsp" class="">Payment</a>
         <a href="#" class="active">Cinema</a>
    </div>
    <div class="icon">
        <div><a href="#" id="login-button"><i class="fa-solid fa-right-to-bracket"></i></a></div>
        <div><a href="#" id="logout-button"><i class="fa-solid fa-power-off"></i></a></div>
    </div>
</nav>

<div class="container">
    <h1 style="font-weight: 600;">Showtime</h1>
    <div class="table-container">
        <table id="cinema-list" class="cinema-table">
            <thead>
                <tr style="font-weight: 500;">
                    <th>Cinema Id</th>
                    <th>Name</th>
                    <th>Open Time</th>
                    <th>Close Time</th>
                    <th>Contact Number</th>
                    <th>Image URL</th>
                    <th>Address</th>
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
