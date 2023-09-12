<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .signup-container {
            width: 300px;
            padding: 16px;
            background-color: #f1f1f1;
            margin: 0 auto;
            margin-top: 100px;
            border-radius: 4px;
        }

        .signup-container h2 {
            text-align: center;
        }

        .signup-container input[type=text], .signup-container input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 10px 0;
            border: none;
            background: #e8e8e8;
            box-sizing: border-box;

        }

        .signup-container button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .signup-container button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>

<div class="signup-container">
    <h2>Sign Up Page</h2>
    <form action="/chowchariot/home?cmd=create" method="post">
        <select id="type" name="type">
            <option value="customer">Customer</option>
            <option value="driver">Driver</option>
            <option value="restaurant">Restaurant</option>
        </select>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <label for="confirm-password"><b>Confirm Password</b></label>
        <input type="password" placeholder="Confirm Password" name="confirm-password" required>

        <button type="submit">Sign Up</button>
    </form>
</div>
<#if errorMessage??>
    <p style="color: red">${errorMessage}</p>
</#if>

</body>
</html>
