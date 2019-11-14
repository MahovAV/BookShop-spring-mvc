<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create new Book</title>
</head>
<body>
    <form name="NewBook" action="/BookIsCreated" method="post">
        <p>Name of book</p>
        <input title="Name" type="text" name="name">  <!-- implicitly goes to constructor -->
        <p>Chose genre of book</p>
        <#list AllGenres as genre>
            <br>
                <input type="checkbox" name="EnumOfGenre" value="${genre}" >${genre}
            <br>
        </#list>
        <p>Write names of authors of book(comma-separated list)</p>
        <input title="Name" type="text" name="Authors">
        <input type="submit" value="Create New Book">
    </form>
</body>
</html>


