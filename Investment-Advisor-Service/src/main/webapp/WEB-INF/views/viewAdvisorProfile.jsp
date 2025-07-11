<!DOCTYPE html>
<html>
<head>
    <title>View Profile</title>
    <style><%@include file="/WEB-INF/style/styleAdvisor.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile">View Profile</a>
                <a href="/updateProfile">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>View Profile</h2>
    
    <div class="profile-details">
        <!-- Profile details will be dynamically populated here -->
    </div>
</body>
</html>
