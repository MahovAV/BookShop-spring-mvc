<#import "../parts/common.ftl" as c>
<@c.page "All Books">
    <h1>List of all books</h1>
    <p><a href="CreateBook">ADD NEW BOOK</a></p>    
    <table border="3px" align="center" style="width:95%;">
    <tbody>
        <tr>
            <td colspan="5" align="left" >List of all books</td>
        </tr>
        <tr>
            <td><strong><em>Book Cover</em><br /></strong></td>
            <td><strong><em>Name</em></strong></td>
            <td><strong><em>Change</em></strong></td>
            <td><strong><em>Delete</em></strong></td>
        </tr>
        <#list books as book>
        <tr>
            <td>
	        	<#if book.bookCoverFileName??>
	   				<img src="images/${book.bookCoverFileName}" width="100" height="150"/>
	   			<#else>
	   				<img src="images/no-image.jpg" width="100" height="150"/>
	   			</#if>
   			</td>
            <td>${book.name}</td>
			<td><a href="ChangeBook/${book.id}">Change</a></td>
			<td><a href="DeleteBook/${book.id}">Delete</a></td>
        </tr>
        </#list>
    </tbody>
</table>
</@c.page>


