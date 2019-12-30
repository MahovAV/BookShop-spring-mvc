<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DeleteBook</title>
</head>
<body>
    Are you sure you want to delete book ?
    <form name="DeleteBook" action="../DeletingBook/${Book_id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="YES" name="descition">
        <input type="submit" value="NO" name="descition">
    </form>
</body>
</html>