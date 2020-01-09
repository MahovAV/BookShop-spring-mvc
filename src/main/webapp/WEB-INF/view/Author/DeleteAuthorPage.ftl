<#import "../parts/AskUser.ftl" as askUser>
<@askUser.page 
"DeleteAuthor" 
"Are you sure you want to delete author ?" 
"../DeleteBook/${Book_id}"
/>