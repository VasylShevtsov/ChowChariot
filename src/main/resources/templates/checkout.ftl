<!DOCTYPE html>
<html>
<head>
    <title>Checkout - ChowChariot</title>
    <style>
        /* Your CSS styles here */
    </style>
</head>
<body>
    <h1>Checkout</h1>
    <form action="/chowchariot/home?page=dashboard&cmd=process-checkout&restID=${restID}" method="post">
        <table>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            <#list orderItems as orderItem>
            <tr>
                <td>${orderItem.item.itemName}</td>
                <td>${orderItem.quantity}</td>
                <td>${orderItem.item.unitCost}</td>
            </tr>
            </#list>
        </table>
        <p>Total: ${total}</p>
        <label for="firstName">First Name:</label><br>
        <input type="text" id="firstName" name="firstName"><br>
        <label for="lastName">Last Name:</label><br>
        <input type="text" id="lastName" name="lastName"><br>
        <label for="address">Address:</label><br>
        <input type="text" id="address" name="address"><br>
        <label for="phoneNumber">Phone Number:</label><br>
        <input type="tel" id="phoneNumber" name="phoneNumber"><br>
        <label for="paymentInfo">Payment Info:</label><br>
        <input type="text" id="paymentInfo" name="paymentInfo"><br>
        <input type="submit" value="Confirm Order">
    </form>
    <a href="/chowchariot/home?page=dashboard&cmd=menu&restID=${restID}">Back to Menu</a>
</body>
</html>
