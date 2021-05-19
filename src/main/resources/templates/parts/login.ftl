<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Username</label>
            <div class="col-sm-6"><input class="form-control" type="text" name="username" placeholder="Username"/></div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Password</label>
            <div class="col-sm-6"><input class="form-control" type="password" name="password" placeholder="Password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><#if !isRegisterForm> Sign in <#else> Create</#if></button>
        <#if !isRegisterForm><a href="/registration">Register</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Sign Out</button>
    </form>
</#macro>