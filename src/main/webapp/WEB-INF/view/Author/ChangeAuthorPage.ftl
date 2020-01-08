<#import "../parts/common.ftl" as c>
<@c.page "Change Author">
<form action="../ChangeAuthor/${author.id}" enctype="multipart/form-data" method="post" name="ChangeBook">
    <input name="_csrf" type="hidden" value="${_csrf.token}" />
    <p>Name</p>
    <input title="Name" name="name" type="text" value="${author.name}" />
       <#if nameError??>
      <div style="color: #ff0000;">
         ${nameError}
      </div>
   </#if>
    <p>Avatar</p>
    <input name="file" type="file" />
    <p>Books which author has written(comma-separated list)</p>
    <input title="Name" name="writtenBooks" type="text" value="${author.writtenBooks}" />
    <#if booksError??>
      <div style="color: #ff0000;">
         ${booksError}
      </div>
   </#if>
    <p>
        About author
        <br />
        <textarea cols="70" name="information" rows="7">${author.information}</textarea>
    </p>
    <p>Country
        <input title="Name" name="addres" type="text" value="${author.addres}" />
    </p>
    <input type="submit" value="submit changes" />
</form>
</@c.page>