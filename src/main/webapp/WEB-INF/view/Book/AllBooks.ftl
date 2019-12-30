<#import "../parts/common.ftl" as c>
<@c.page "All Books">
    <h1>List of all books</h1>
    <p><a href="CreateBook">ADD NEW BOOK</a></p>
    <table style="font: 20px arial;">
        <tr>
            <td>Name</td>
            <td>Change</td>
            <td>Delete</td>
        </tr>

        <#list books as book>
        <tr>
            <td>${book.name}</td>
            <td><a href="ChangeBook/${book.id}">Change</a></td>
            <td><a href="DeleteBook/${book.id}">Delete</a></td>
            <!--Sending book id as path variable-->
        <tr>
        </#list>
    </table>
</@c.page>
