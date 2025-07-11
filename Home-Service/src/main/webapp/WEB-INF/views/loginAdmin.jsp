<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <style><%@include file="/WEB-INF/style/styleGlobal.css"%></style>
    <style><%@include file="/WEB-INF/style/styleLogin.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
    </header>
    
    <h2>Admin Login</h2>
    <form action="/login/admin" method="POST">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
</body>
</html>
