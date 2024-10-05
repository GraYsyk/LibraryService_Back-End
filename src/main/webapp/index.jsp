<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Service</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f8ff;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        h2 {
            font-size: 2.5em;
            color: #0066cc;
            text-align: center;
            margin-bottom: 20px;
        }

        button {
            display: inline-block;
            font-size: 1.2em;
            color: #fff;
            background-color: #0066cc;
            padding: 10px 20px;
            border-radius: 8px;
            margin: 10px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        button:hover {
            background-color: #004999;
            transform: scale(1.05);
        }

        button:active {
            transform: scale(1);
        }

        .container {
            text-align: center;
            padding: 40px;
            background-color: #fff;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            border-radius: 12px;
        }

    </style>
    <script>
        function goToPeopleList() {
            window.location.href = "people";
        }

        function goToBookList() {
            window.location.href = "book";
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Library Service</h2>
    <button onclick="goToPeopleList()">People list</button>
    <br/>
    <button onclick="goToBookList()">Book list</button>
</div>
</body>
</html>
