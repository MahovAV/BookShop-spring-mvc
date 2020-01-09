<#macro page nameOfTitle question action>
<#import "common.ftl" as c>
<@c.page "DeleteBook">
${question}
<form name="DeleteBook" action="${action}" method="post">
<input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="submit" value="YES" name="descition">
    <input type="submit" value="NO" name="descition"> 
</form>
</@c.page>
</#macro>