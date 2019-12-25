<head>
    <meta charset="UTF-8">
    <title>Create new Book</title>
</head>
<body>
    <form name="NewBook" action="BookIsCreated" method="post">
	<p>Name of book</p>
	<input title="Name" type="text" name="name" value="<#if book??>${book.name}</#if>">
	<#if nameError??>
          <div style="color: #ff0000;">
               ${nameError}
          </div>
     </#if>
     <#if book??>
		<p>Chose genre of book</p>       
			<!-- CHECK ALL CHECKBOXES -->                                  
			<#list AllGenres as genre>
		        <br>
		            <#assign itWas = false>
		            <#list book.stringsFromCheckedGenres as CheckedGenre>
		                <!-- CHECK WEATHER IT IS IN COLLECTION -->
		                <#if (CheckedGenre==genre)>
		                    <#assign itWas = true>
		                </#if>
		            </#list>
		            <input type="checkbox" name="EnumOfGenre" value="${genre}"
		                <#if (itWas==true)>checked<#else></#if>
		            >
		        <br>
		    ${genre}
		    </#list>
		    <#else>
		    <!-- there is no book should return just all books -->
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
</body>
</html>


