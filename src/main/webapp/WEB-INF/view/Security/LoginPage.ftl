<#import "../parts/common.ftl" as c>
<@c.page "LoginPage">
Login page

<form action="signIn/process" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="form-group row">
	    <label class="col-sm-2 col-form-label"> User Name : </label>
	    <div class="col-sm-7">
			<input type="text" class="form-control" name="username"/> 
		</div>
    </div>
    <div class="form-group row">
	    <label class="col-sm-2 col-form-label"> Password : </label>
	    <div class="col-sm-7">
			<input type="password" class="form-control" name="password"/> 
		</div>
    </div>
    <#if loginError??>
	    <div style="color: #ff0000;">
	    	${loginError}
	    </div>
    </#if>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="signUp">signUp</a>
</@c.page>