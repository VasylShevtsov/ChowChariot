<!DOCTYPE html>
<html>
<head>
    <title>Menu - ChowChariot</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 0;
            margin: 0;
        }

        h1 {
            color: #333;
            text-align: center;
            padding: 20px 0;
        }

        #menu-table {
            margin: auto;
            width: 80%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #333;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #checkout-button {
            display: block;
            width: 200px;
            height: 40px;
            margin: 20px auto;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        #checkout-button:hover {
            background-color: #45a049;
        }

        .quantity-input {
            width: 40px;
        }
    </style>
</head>
<body>
    <h1>Menu</h1>
    <form action="/chowchariot/home?page=dashboard&cmd=checkout&restID=${restID}" method="post">
        <table id="menu-table">
            <tr>
                <th>Item Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <#list items as item>
            <tr>
                <td>${item.itemName}</td>
                <td>${item.category}</td>
                <td>${item.unitCost}</td>
                <td>
                    <input type="number" id="${item.itemID}" name="${item.itemID}" min="0" class="quantity-input">
                </td>
            </tr>
            </#list>
        </table>
        <input type="submit" id="checkout-button" value="Checkout">
    </form>
    <a href="/chowchariot/home?page=dashboard?type=customer">Back to Restaurants</a>
</body>
</html>
