<!-- src/main/webapp/WEB-INF/views/home.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
    .blueLeftBorder{
    border: 1px solid;
    border-left: 5px solid blue;
    margin: 10px;
    padding: 10px;
    box-shadow: 5px 7px #888888;
    }
    </style>
    <script type="text/javascript">
        function submitForm1() {
            const num1 = document.getElementById("num11").value;
            const num2 = document.getElementById("num12").value;
            const url = "/v1/add/" + num1 + "/" + num2;
            console.log(url);
            window.location.href = url;
            return false; // Prevent actual form submission
        }

        function submitForm2() {
            const num1 = document.getElementById("num21").value;
            const num2 = document.getElementById("num22").value;
            const url = "/v2/add?num1=" + num1 + "&num2=" + num2;
            console.log(url);
            window.location.href = url;
            return false; // Prevent actual form submission
        }
    </script>
</head>
<body>
    <h1>Welcome to the Home Page</h1>

    <form class="blueLeftBorder" action="/processForm" method="get">
        <h2> Add, v1 using forward </h2>
        <label for="num1">Number 1:</label>
        <input type="number" id="num1" name="num1" required>
        <br>
        <label for="num2">Number 2:</label>
        <input type="number" id="num2" name="num2" required>
        <br>
        <input type="submit" value="Add">
    </form>

    <form class="blueLeftBorder" onsubmit="return submitForm1();">
        <h2> Add, v1 </h2>
        <label for="num1">Number 1:</label>
        <input type="number" id="num11" name="num1" required>
        <br>
        <label for="num2">Number 2:</label>
        <input type="number" id="num12" name="num2" required>
        <br>
        <input type="submit" value="Add">
    </form>

    <form class="blueLeftBorder" onsubmit="return submitForm2();">
        <h2> Add, v2 </h2>
        <label for="num1">Number 1:</label>
        <input type="number" id="num21" name="num1" required>
        <br>
        <label for="num2">Number 2:</label>
        <input type="number" id="num22" name="num2" required>
        <br>
        <input type="submit" value="Add">
    </form>
</body>
</html>
