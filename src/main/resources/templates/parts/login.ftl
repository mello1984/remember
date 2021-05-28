<#macro login path isRegisterForm>
    <form action="${path}" method="post" <#if isRegisterForm> id="register-form" <#else>id="login-form"</#if> >

        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Username</label>
            <div class="col-sm-6">
                <input type="text" name="username" placeholder="Username"
                       class="form-control ${(usernameError??)?string('is-invalid','')}"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>

        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="password" name="password" placeholder="Password"
                       class="form-control ${(passwordError??)?string('is-invalid','')}"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label">Password confirm</label>
                <div class="col-sm-6">
                    <input type="password" name="passwordConfirm" placeholder="Password confirmation"
                           class="form-control ${(passwordConfirmError??)?string('is-invalid','')}"/>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-1 col-form-label"> Email</label>
                <div class="col-sm-6">
                    <input type="email" name="email" placeholder="some@some.com"
                           class="form-control ${(emailError??)?string('is-invalid','')}"/>

                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>

                </div>
            </div>

        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><#if !isRegisterForm> Sign in <#else> Create</#if></button>
        <#if !isRegisterForm><a href="/registration">Register</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-outline-secondary">Sign Out</button>
    </form>
</#macro>