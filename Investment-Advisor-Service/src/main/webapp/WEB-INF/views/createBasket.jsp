<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Basket</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
    <script>
    function updateSelectedStocks(advisorId) {
        fetch('/getSelectedStocks/' + advisorId)
            .then(response => response.json())
            .then(data => {
                const selectedStocksDiv = document.getElementById('selectedStocks');
                selectedStocksDiv.innerHTML = '';
                let totalPrice = 0;

                data.forEach(stock => {
                    const stockDiv = document.createElement('div');
                    const units = stock.units; // Assuming the units are included in the response
                    const stockTotalPrice = stock.closingPrice * units;

                    stockDiv.innerHTML = stock.symbol +`x`+ units +`=`+ stockTotalPrice;
                    selectedStocksDiv.appendChild(stockDiv);
                    totalPrice += stockTotalPrice;
                });

                document.getElementById('totalPrice').textContent = totalPrice.toFixed(2);
            })
            .catch(error => console.error('Error:', error));
    }

    // Function to remove stock from basket
    function removeFromBasket(stockId, advisorId) {
        fetch('/removeFromBasket/' + advisorId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'stockId=' + stockId
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            updateSelectedStocks(advisorId);
        })
        .catch(error => console.error('Error:', error));
    }

    // Call updateSelectedStocks on page load
    window.onload = function() {
        const advisorId = document.getElementById('advisorId').value;
        updateSelectedStocks(advisorId);
    };

    </script>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile/${advisorId}">View Profile</a>
                <a href="/updateProfile/${advisorId}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Create Basket</h2>
    
    <form action="/createBasket/${advisorId}" method="post">
        <input type="hidden" id="advisorId" name="advisorId" value="${advisorId}">
        
        
        <label for="stocks">Selected Stocks:</label>
        <div id="selectedStocks"></div>
        <button type="button" onclick="window.location.href='/selectStocks/${advisorId}'">Select Stocks</button><br><br>
        
        <label for="totalPrice">Total Price:</label>
        <span id="totalPrice">0.00</span><br><br>
        <label for="basketName">Basket Name:</label>
        <input type="text" id="basketName" name="basketName" required><br><br>
        
        <label for="strategy">Strategy:</label>
        <textarea id="strategy" name="strategy" maxlength="100" required></textarea><br><br>
        
        <input type="submit" value="Create Basket">
    </form>
</body>
</html>
