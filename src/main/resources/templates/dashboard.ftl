<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - ChowChariot</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        h1, h2 {
            color: #333;
            text-align: center;
        }

        #header {
            background-color: #4CAF50;
            color: white;
            padding: 20px 0;
        }

        #logout-button {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: white;
            color: #4CAF50;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }

        #logout-button:hover {
            background-color: #f4f4f4;
        }

        #restaurant-table {
            margin: 50px auto;
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

        a {
            color: #4CAF50;
            text-decoration: none;
        }

        a:hover {
            color: #333;
        }
    </style>
</head>
<body>
    <div id="header">
        <h1>Welcome to ChowChariot!</h1>
        <form id="logout-form" action="/chowchariot/home?page=home" method="post">
            <input type="hidden" name="cmd" value="logout">
            <button id="logout-button" type="submit">Logout</button>
        </form>
    </div>

    <h2>Available Restaurants:</h2>

    <#assign myDefault = "TBD">
    <table id="restaurant-table">
        <tr>
            <th>Restaurant Name</th>
            <th>Cuisine Type</th>
            <th>Location</th>
        </tr>
        <#list restaurants as restaurant>
            <tr>
                <td><a href="/chowchariot/home?page=dashboard&cmd=customer&restID=${restaurant.restID}&menu=${restaurant.menuID}">${restaurant.name}</a></td>
                <td>${(restaurant.cuisineType)!myDefault}</td>
                <td>${(restaurant.location)!myDefault}</td>
            </tr>
        </#list>
    </table>
</body>
</html>

