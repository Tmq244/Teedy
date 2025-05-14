<#if !principal.isGuest()>
    <#if principal.isAdmin()>
        <li>
            <a href="${contextPath}/admin/registration-requests">
                <i class="fa fa-user-plus"></i> ${i18n("admin.registration.requests.title")}
            </a>
        </li>
    </#if>
<#else>
    <li>
        <a href="${contextPath}/registration/request">
            <i class="fa fa-user-plus"></i> ${i18n("registration.request.title")}
        </a>
    </li>
</#if> 