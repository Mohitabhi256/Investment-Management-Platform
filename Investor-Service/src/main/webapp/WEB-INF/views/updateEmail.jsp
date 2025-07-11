<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Investor Email</title>
    <style>
        body {
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f9f9f9;
        }
        .form-container {
            width: 300px;
            padding: 20px;
            background-color: #fff;
            border: 2px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        button {
            padding: 10px;
            width: 100%;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Email</h2>
        <form action="/submitInvestorEmailUpdate" method="post">
            <input type="hidden" name="_method" value="PATCH">
            <input type="hidden" name="investorEmail" id="investorEmail" value="${investorEmail}">
            
            <input type="text" name="newEmail" placeholder="New Email Address" required>
            <input type="password" name="investorPassword" placeholder="enter your Password" required>
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>



