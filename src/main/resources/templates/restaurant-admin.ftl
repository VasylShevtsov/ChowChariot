<!DOCTYPE html>
<html>

<head>
    <title>Menu Items</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px;
    }

    h1 {
        text-align: center;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    th,
    td {
        padding: 10px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f2f2f2;
    }

    form {
        margin-bottom: 20px;
    }

    form label {
        display: block;
        margin-bottom: 5px;
    }

    form input[type="text"] {
        width: 80%;
        padding: 8px;
        margin-bottom: 10px;
    }

    form button {
        background-color: #4CAF50;
        color: #fff;
        border: none;
        padding: 10px 20px;
        cursor: pointer;
    }

    form button[type="submit"] {
        background-color: #4CAF50;
    }

    form button[type="submit"]:hover {
        background-color: #45a049;
    }

    form button[type="button"] {
        background-color: #f44336;
    }

    form button[type="button"]:hover {
        background-color: #d32f2f;
    }

    .add-form {
        border: 1px solid #ddd;
        padding: 20px;
    }

    .update-form {
        border: 1px solid #ddd;
        padding: 20px;
    }

    .update-form label {
        display: block;
        margin-bottom: 5px;
    }

    .update-form button {
        background-color: #4CAF50;
        color: #fff;
        border: none;
        padding: 10px 20px;
        cursor: pointer;
        margin-top: 10px;
    }

    .update-form button:hover {
        background-color: #45a049;
    }

    #logout-button {
        position: absolute;
        top: 10px;
        right: 10px;
    }
    </style>
</head>

<body>
    <form id="logout-form" action="/chowchariot/home?page=home" method="post">
            <input type="hidden" name="cmd" value="logout">
            <button id="logout-button" type="submit">Logout</button>
        </form>
    </form>
    <h1>Menu Items</h1>
    <form method="post" action="/chowchariot/home?cmd=remove-from-menu">
        <table>
            <tr>
                <th>ID</th>
                <th>Category</th>
                <th>Price</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            <#list menuItems as item>
                <tr>
                    <td>
                        ${item.itemID}
                    </td>
                    <td>
                        ${item.category}
                    </td>
                    <td>
                        ${item.unitCost}
                    </td>
                    <td>
                        ${item.itemName}
                    </td>
                    <td><button type="submit" name="removeItem" value="${item.itemID}">Remove</button></td>
                </tr>
            </#list>
        </table>
    </form>
    <div class="add-form">
        <form action="/chowchariot/home?cmd=add-to-menu" method="post">
            <h2>Add New Item</h2>
            <label for="category">Category:</label>
            <input type="text" id="category" name="category"><br>
            <label for="price">Price:</label>
            <input type="text" id="price" name="price"><br>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name"><br>
            <button type="submit" name="addItem">Add Item</button>
        </form>
    </div>
    <div class="update-form">
        <form action="/chowchariot/home?cmd=update-account" method="post">
            <h2>Update Account Information</h2>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" <#if restaurant.name?has_content>placeholder="${restaurant.name}"</#if>>
            <button type="submit" name="fieldType" value="name">Update</button>
            <label for="cuisineType">Cuisine Type:</label>
            <input type="text" id="cuisineType" name="cuisineType" <#if restaurant.cuisineType?has_content>placeholder="${restaurant.cuisineType}"</#if>>
            <button type="submit" name="fieldType" value="cuisineType">Update</button>
            <label for="location">Location:</label>
            <input type="text" id="location" name="location" <#if restaurant.location?has_content>placeholder="${restaurant.location}"</#if>>
            <button type="submit" name="fieldType" value="location">Update</button>
            <label for="hours">Hours:</label>
            <input type="text" id="hours" name="hours" <#if restaurant.hours?has_content>placeholder="${restaurant.hours}"</#if>>
            <button type="submit" name="fieldType" value="hours">Update</button>
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" <#if restaurant.phone?has_content>placeholder="${restaurant.phone}"</#if>>
            <button type="submit" name="fieldType" value="phone">Update</button>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" <#if restaurant.email?has_content>placeholder="${restaurant.email}"</#if>>
            <button type="submit" name="fieldType" value="email">Update</button>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" <#if restaurant.password?has_content>placeholder="${restaurant.password}"</#if>>
            <button type="submit" name="fieldType" value="password">Update</button>
        </form>
    </div>
</body>

</html>