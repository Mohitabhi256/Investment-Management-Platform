<!-- otp.html -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OTP Verification</title>
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
    <h1>Enter the OTP SENT TO YOUR EMAIL</h1>
    <form action="/verifyOtp" id="otpForm" method="post">
    <input type="hidden" name="investorEmail" value="${investorEmail}"/>
        <input type="text" id="otp" name="otp" required placeholder="Enter OTP"/>
        
        <button type="submit">Submit</button>
    </form>
    
    
</body>
</html>
