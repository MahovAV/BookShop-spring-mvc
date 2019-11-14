<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>List of all books</h1>
    <table>
        <tr>
            <td>id</td>
            <td>name</td>]
            <td>Change books(ADMIN ONLY!)</td>
        </tr>

        <#list books as book>
        <tr>
            <td>${book.id}</td>
            <td>${book.name}</td>
            <td><a href="/ChangeBook/${book.id}">change</a></td>
            <!--Sending book id as path variable-->
        <tr>
        </#list>
    </table>
</body>
</html>