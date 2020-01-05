<#import "../parts/common.ftl" as c>
<@c.page "ChangeBook">
<h1>ChangeBookPage</h1>
<form name="ChangeBook" action="../ChangeBook/${book.id}" method="post" enctype="multipart/form-data">
   <input type="hidden" name="_csrf" value="${_csrf.token}" />
   <p>Name of book</p>
   <input title="Name" type="text" name="name" value="${book.name}" >
   <#if nameError??>
      <div style="color: #ff0000;">
         ${nameError}
      </div>
   </#if>
   <p>Book cover</p>
   <input type="file" name="file">
   <p>Chose genre of book</p>
   <#list AllGenres as genre>
   		<br>
   			<input type="checkbox" name="EnumOfGenre" value="${genre}"
   			${book.stringsFromCheckedGenres?seq_contains(genre)?string("checked", "")}>
   		<br>
   		${genre}
   </#list>
   <p>Write names of authors of book(comma-separated list)</p>
   <input title="Name" type="text" name="InputedAuthor" value="${book.stringFromAuthors}">
   <#if authorsError??>
   <div style="color: #ff0000;">
      ${authorsError}
   </div>
   </#if>
   <p>Description <Br>
      <textarea name="information" cols="70" rows="7">${book.information}</textarea>
   </p>
   <input type="submit" value="Change book">
</form>
</@c.page>