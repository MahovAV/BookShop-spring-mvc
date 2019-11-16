<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AreYouSure</title>
</head>
<body>
    Are you sure you want to delete book ?
    <form name="DeleteBook" action="/DescitionIsMadeTranslateToDelete/${Book_id}" method="post">
        <input type="submit" value="YES" name="descition">
        <input type="submit" value="NO" name="descition">
    </form>
</body>
</html>