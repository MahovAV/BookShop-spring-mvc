<#import "../parts/common.ftl" as c>
<@c.page "CreateBook">
<h1>Create Book page</h1>
<form name="NewBook" action="CreateBook" method="post" enctype="multipart/form-data">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<p>Name of book</p>
	<input title="Name" type="text" name="name" value="<#if book??>${book.name}</#if>">
	<#if nameError??>
          <div style="color: #ff0000;">
               ${nameError}
          </div>
    </#if>
    <p>Book cover</p>
    <input type="file" name="file">
	<#if book??>
		<p>Chose genre of book</p>                                        
		<#list AllGenres as genre>
			<br>
		        <input type="checkbox" name="EnumOfGenre" value="${genre}"
		        ${book.stringsFromCheckedGenres?seq_contains(genre)?string("checked", "")}>
		    <br>
		    ${genre}
		</#list> 
	<#else>
	<!-- there is no book should return just all books -->
	<p>Chose genre of book</p> 
		<#list AllGenres as genre>
    		<br>
    			<input type="checkbox" name="EnumOfGenre" value="${genre}" >${genre}
    		<br>
    	</#list>
	</#if>   
		<p>Write names of authors of book(comma-separated list)</p>
		<!-- Authors:string which will All related authors -->
		<input title="Name" type="text" name="InputedAuthor" value="<#if book??>${book.stringFromAuthors}</#if>">
		<#if authorsError??>
          <div style="color: #ff0000;">
               ${authorsError}
          </div>
    </#if>
    <p>Description<Br>
   	<textarea name="information" cols="70" rows="7"><#if book??>${book.information}</#if></textarea></p>
	<input type="submit" value="Create new book">
</form>
</@c.page>
