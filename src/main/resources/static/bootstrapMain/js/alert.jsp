
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <script>
        var msg = "<c:out value='${msg}'/>";
        var url = "<c:out value='${url}'/>";
        alert(msg);
        location.href = url;
    </script>
</body>
</html>