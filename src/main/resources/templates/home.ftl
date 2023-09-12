<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ChowChariot - Your Favorite Meals Delivered</title>
    <style>
        body {
            text-align: center;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        .logo {
            max-width: 200px;
        }

        form {
            display: inline-block;
        }

        .button {
            padding: 10px 20px;
            margin: 5px;
            text-decoration: none;
            font-size: 1.2em;
            color: #fff;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 20%;  
            height: 3%;  
        }

        .button-container {
            margin-top: 5%;
        }
    </style>
</head>

<body>
    <img src="images/companylogo.png" alt="ChowChariot logo" class="logo">
    <h1>Welcome to ChowChariot!</h1>
    <p>Your favorite meals delivered fast at your doorstep. Experience the taste of the best local and international cuisines from the comfort of your home.</p>
    <div class="button-container">
        <a href="/chowchariot/home?page=home&cmd=login&type=customer" class="button">login</a>
        <a href="/chowchariot/home?page=home&cmd=signup" class="button">create an account</a>
    </div>
</body>

</html>