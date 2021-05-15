<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<#assign hasName = name??>
<#if hasName>
Hello, ${name}!
<#else>
Hello, unknown!
</#if>

</body>
</html>