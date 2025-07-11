<!DOCTYPE html>
<html>
<head>
    <title>Investor Registration</title>
    <style><%@include file="/WEB-INF/style/styleGlobal.css"%></style>
    <style><%@include file="/WEB-INF/style/styleLogin.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
    </header>
    
    <h2>Investor Registration</h2>
    <form action="/register/investor" method="POST">
        <label for="name">Full Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <div class="button-container">
            <button type="submit">Register</button>
            <a href="/login/investor" class="login-btn">Login</a>
        </div>
    </form>
</body>
</html>
