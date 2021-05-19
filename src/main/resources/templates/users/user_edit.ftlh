<#import "../parts/common.ftl" as c>
<@c.page "Edit user">
    <div class="input-group mb-3">
        <form method="post" action="/users">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="userId" value="${user.id}"/>

            <div class="mb-3"> Id: ${user.id}<br> </div>

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Name</span>
                </div>
                <input type="text" name="username" class="form-control" placeholder="Username" aria-label="Username"
                       aria-describedby="basic-addon1" value="${user.username}">
            </div>

<#--                        Username: <label> <input type="text" value="${user.username}" name="username"/> </label><br>-->


            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                        <input type="checkbox" name="active" aria-label="Checkbox for following text input">
                    </div>
                </div>
                <div class="ml-2"> Active</div>
            </div>


            <#list roles as r>
                <div>
                    <label><input type="checkbox" name="${r}" ${user.roles?seq_contains(r)?string("checked", "")}>${r}
                    </label>
                </div>
            </#list>

            <button type="submit">Save</button>
        </form>
    </div>

</@c.page>