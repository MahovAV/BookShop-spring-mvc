<#import "../parts/common.ftl" as c>
<@c.page "DeleteBook">
Are you sure you want to delete book ?
<form name="DeleteBook" action="../DeleteBook/${Book_id}" method="post">
<input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="submit" value="YES" name="descition">
    <input type="submit" value="NO" name="descition"> 
</form>
</@c.page>