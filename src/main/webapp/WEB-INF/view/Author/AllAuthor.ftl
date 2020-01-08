<#import "../parts/common.ftl" as c>
<@c.page "All Authors">
	<p><a href="Home">HOME</a></p>
    <h1>List of all authors</h1>
    <p><strong>You cannot create author without book</strong></p>    
    <table border="3px" align="center" style="width:95%;">
    <tbody>
        <tr>
            <td colspan="5" align="left" >List of all books</td>
        </tr>
        <tr>
            <td><strong><em>Avatar</em><br /></strong></td>
            <td><strong><em>Name</em></strong></td>
            <td><strong><em>Change</em></strong></td>
            <td><strong><em>Delete</em></strong></td>
        </tr>
        <#list authors as author>
        <tr>
            <td>
	        	<#if author.avatarFileName??>
	   				<img src="images/${author.avatarFileName}" width="100" height="150"/>
	   			<#else>
	   				<img src="images/no-image.jpg" width="100" height="150"/>
	   			</#if>
   			</td>
            <td>${author.name}</td>
			<td><a href="ChangeAuthor/${author.id}">Change</a></td>
			<td><a href="DeleteAuthor/${author.id}">Delete</a></td>
        </tr>
        </#list>
    </tbody>
</table>
</@c.page>
