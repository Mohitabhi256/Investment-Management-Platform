<!DOCTYPE html>
<html>
<head>
    <title>Investor Login</title>
    <style><%@include file="/WEB-INF/style/styleGlobal.css"%></style>
    <style><%@include file="/WEB-INF/style/styleLogin.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
    </header>
    
    <h2>Investor Login</h2>
    <form action="/login/investor" method="POST">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <!-- Add a link of forget Password -->
        <a href="/forgotPassword">Forgot password?</a>
        <div class="button-container">
            <button type="submit">Login</button>
            <a href="/register/investor" class="login-btn">Register</a>
        </div>
    </form>
</body>
</html>
