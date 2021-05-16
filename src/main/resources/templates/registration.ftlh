<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <title>Spring Security Example </title>
</head>
<body>

<h3>Add new user</h3>

<form action="/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>

<#if userExistsMessage??>${userExistsMessage}</#if>
</body>
</html>