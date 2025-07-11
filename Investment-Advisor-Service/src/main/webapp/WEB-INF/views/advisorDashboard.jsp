<!DOCTYPE html>
<html>
<head>
    <title>Investment Advisor Dashboard</title>
    <%-- <style><%@include file="/WEB-INF/style/styleGlobal.css"%></style> --%>
    <style><%@include file="/WEB-INF/style/styleAdvisor.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile">View Profile</a>
                <a href="/updateAdvisorProfile/${email}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Advisor Dashboard</h2>
    
    <div class="main-options">
        <button onclick="location.href='/viewStocks/${id}'">View Stocks</button>
        <button onclick="location.href='/createBasket/${id}'">Create Baskets</button>
        <button onclick="location.href='/viewMyBaskets/${id}'">View My Baskets</button>
    </div>
</body>
</html>
