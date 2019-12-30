<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
    <body>
    	<!-- IMPLICITLY TRANSLATE ROLE TO STRING -->
        <h1>You ${user.name} have role:<#list user.roles as role>${role}<#sep>, </#list> </h1>
        <p><a href="getAll">LOOK ALL BOOKS</a></p>

        <p><a href="CreateBook">ADD NEW BOOK</a></p>
    </body>
</html>
