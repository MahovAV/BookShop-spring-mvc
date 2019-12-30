<#import "../parts/common.ftl" as c>
<@c.page "RegistrationPage">
<form name="signUp" action="signUp" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
			<p>NickName</p>
	    		<input title="Name" type="text" name="name" value="<#if user??>${user.name}</#if>">
		 		<#if nameError??>
	                <div style="color: #ff0000;">
	                	${nameError}
	        		</div>
	     		</#if>
	      	<p>email</p>
	      		<input title="email" type="text" name="email" value="<#if user??>${user.email}</#if>">
	      		<#if emailError??>
	                <div style="color: #ff0000;">
	                	${emailError}
	        		</div>
	     		</#if>
	        <p>password</p>
	      		<input title="Name" type="password" name="password">
	      		<#if passwordError??>  <!-- password too small -->
	                <div style="color: #ff0000;">
	                	${passwordError}
	        		</div>
	     		</#if>
	        <p>confirm password</p>
	      		<input title="Name" type="password" name="confPassword">
	      		<#if confPasswordError??>  <!-- password too small -->
	                <div style="color: #ff0000;">
	                	${confPasswordError}
	        		</div>
	     		</#if>
	<p>
		register as :
		<Br><input type="radio" name="role" value="admin" checked> Admin(clould create and update book)</Br>
		<Br><input type="radio" name="role" value="user"> User(could watch book)</Br>
	</p>
	<input type="submit" value="Sign Up">
</form>
</@c.page>