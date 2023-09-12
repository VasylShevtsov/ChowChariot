<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .login-container {
            width: 300px;
            padding: 16px;
            background-color: #f1f1f1;
            margin: 0 auto;
            margin-top: 100px;
            border-radius: 4px;
        }

        .login-container h2 {
            text-align: center;
        }

        .login-container input[type=text], .login-container input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 10px 0;
            border: none;
            background: #e8e8e8;
            box-sizing: border-box;
        }

        .login-container button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .login-container button:hover {
            opacity: 0.8;
        }

        a {
            padding: 10px 20px;
            margin: 5px;
            margin-top: 1%;
            text-decoration: none;
            font-size: 1.2em;
            color: #fff;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            display: block;
            cursor: pointer;
            width: 6%;  
            height: 3%;  
        }
    </style>
</head>
<body>
    <div>
        <a href="/chowchariot/home?page=home">back</a>
        <a href="/chowchariot/home?page=home&cmd=login&type=driver">driver</a>
        <a href="/chowchariot/home?page=home&cmd=login&type=restaurant">restaurant</a>
    </div>
    <div class="login-container">
        <h2>Login Page</h2>
        <#if userType??>
            <p> Welcome, valued ${userType} </p>
            <form action="/chowchariot/home?page=home&cmd=auth&type=${userType}" method="post">
                <label for="email"><b>Email</b></label>
                <input type="text" placeholder="Enter Email" name="email" required>

                <label for="password"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" required>

                <button type="submit">Login</button>
            </form>
            <#else>
            <p> Welcome, valued customer </p>
            <form action="/chowchariot/home?page=home&cmd=auth&type=customer" method="post">
                <label for="email"><b>Email</b></label>
                <input type="text" placeholder="Enter Email" name="email" required>

                <label for="password"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" required>

                <button type="submit">Login</button>
            </form>

        </#if>
    </div>
    <#if errorMessage??>
        <p style="color: red">${errorMessage}</p>
    </#if>

</body>
</html>
