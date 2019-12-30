<#import "parts/common.ftl" as c>
<@c.page "HomePage">
<!-- IMPLICITLY TRANSLATE ROLE TO STRING -->
<h1>You ${user.name} have role:<#list user.roles as role>${role}<#sep>, </#list> </h1>
<p><a href="getAll">LOOK ALL BOOKS</a></p>
<p><a href="CreateBook">ADD NEW BOOK</a></p>
</@c.page>
