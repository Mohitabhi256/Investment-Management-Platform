<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Investor Dashboard</title>
<style><%@include file="/WEB-INF/style/styleInvestor.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile/${investorId}">View Profile</a>
                <a href="/updateProfile/${email}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Investor Dashboard</h2>
    
    <div class="investor-options">
        <button onclick="location.href='/viewBaskets/${investorId}'">View Baskets</button>
        <button onclick="location.href='/addFunds/${investorId}'">Add Funds</button>
        <button onclick="location.href='/withdrawFunds/${investorId}'">Withdraw Funds</button>
        <button onclick="location.href='/viewInvestments/${investorId}'">View Investments</button>
         <button onclick="location.href='/viewPortfolio/${investorId}'">View Portfolio</button>
    </div>
</body>
</html>
