<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select Stocks</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
    <script>
        function addToBasket(stockId) {
            const units = document.getElementById('units-' + stockId).value;
            fetch('/addToBasket/${advisorId}', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'stockId=' + stockId + '&units=' + units
            })
            .then(response => response.text())
            .then(data => {
                alert(data);
                updateButton(stockId);
            })
            .catch(error => console.error('Error:', error));
        }

        function updateButton(stockId) {
            const button = document.getElementById('button-' + stockId);
            button.textContent = 'Remove';
            button.onclick = function() { removeFromBasket(stockId); };
        }

        function removeFromBasket(stockId) {
            fetch('/removeFromBasket/${advisorId}', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'stockId=' + stockId
            })
            .then(response => response.text())
            .then(data => {
                alert(data);
                updateButtonToAdd(stockId);
            })
            .catch(error => console.error('Error:', error));
        }

        function updateButtonToAdd(stockId) {
            const button = document.getElementById('button-' + stockId);
            button.textContent = 'Add';
            button.onclick = function() { addToBasket(stockId); };
        }
    </script>
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
    
    <h2>Select Stocks</h2>
    
    <table>
        <thead>
            <tr>
                <th>Symbol</th>
                <th>Company Name</th>
                <th>Closing Price</th>
                <th>Units</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stock" items="${stocks}">
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${stock.closingPrice}</td>
                    <td><input type="number" id="units-${stock.id}" min="1" value="1"></td>
                    <td>
                        <button type="button" id="button-${stock.id}" onclick="addToBasket(${stock.id})">Add</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <button type="button" onclick="window.location.href='/createBasket/${advisorId}'">Proceed</button>
</body>
</html>
