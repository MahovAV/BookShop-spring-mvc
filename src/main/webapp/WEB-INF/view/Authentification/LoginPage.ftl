<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Spring Security Example </title>
</head>
<body>
Login page
<form action="signIn/process" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <#if loginError??>
	    <div style="color: #ff0000;">
	    	${loginError}
	    </div>
    </#if>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="signUp">signUp</a>
</body>
</html>