<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Advisor Login</title>
	<style><%@include file="/WEB-INF/style/styleGlobal.css"%></style>
    <style><%@include file="/WEB-INF/style/styleLogin.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
    </header>
    
    <h2>Investment Advisor Login</h2>
    <form action="/login/advisor" method="POST">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <a href="/forgotPasswordAdvisor">Forgot password?</a>
        <button type="submit">Login</button>
    </form>
</body>
</html>
