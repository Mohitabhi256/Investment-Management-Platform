<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Profile</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
</head>
<body>
<input type="hidden" name="advisorEmail" id="advisorEmail" value="${advisorEmail}">

    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile">View Profile</a>
                <a href="#" id="updateProfileLink">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>

    <h2>Update Profile Options</h2>
    
    <div class="update-options" style="width: 300px; height: 200px; background-color: #f0f0f0; border: 2px solid #ccc; text-align: center; padding: 20px;">
         <!--  <button href="#" id="updateEmailButton">Update Email</button>-->
         <button <a href="#" id="updateEmailLink">Update Email</a>></button>
        <button <a href="#" id="updatePasswordLink">Update Password</a>></button>
        
       
    </div>
     
    <script>
        document.getElementById('updateProfileLink').onclick = function(event) {
        	
            event.preventDefault(); // Prevent the default anchor click behavior
            var email = document.getElementById('advisorEmail').value;
            var encodedEmail = encodeURIComponent(email); // Encode the email to handle special characters
            // Redirect to the complete URL with the email
            window.location.href = '/updateAdvisorProfile/' + encodedEmail; // Ensure this matches your controller's path
        };
        document.getElementById('updateEmailLink').onclick = function() {
            var email = document.getElementById('advisorEmail').value;
            var encodedEmail = encodeURIComponent(email);
            window.location.href = '/updateAdvisorEmail/' + encodedEmail; // Redirect with email
        };

        // Update Password Button
        document.getElementById('updatePasswordLink').onclick = function() {
            var email = document.getElementById('advisorEmail').value;
            var encodedEmail = encodeURIComponent(email);
            window.location.href = '/updateAdvisorPassword/' + encodedEmail; // Redirect with email
        };
    </script>
</body>
</html>
