<#include "../base.ftl">

<@layout>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${i18n("admin.registration.requests.title")}</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>${i18n("admin.registration.requests.username")}</th>
                                    <th>${i18n("admin.registration.requests.email")}</th>
                                    <th>${i18n("admin.registration.requests.reason")}</th>
                                    <th>${i18n("admin.registration.requests.date")}</th>
                                    <th>${i18n("admin.registration.requests.actions")}</th>
                                </tr>
                            </thead>
                            <tbody id="registration-requests-list">
                                <!-- Requests will be loaded here dynamically -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Template for registration request row -->
<script type="text/template" id="registration-request-template">
    <tr data-id="<%= id %>">
        <td><%= username %></td>
        <td><%= email %></td>
        <td><%= reason %></td>
        <td><%= createdDate %></td>
        <td>
            <button class="btn btn-success btn-sm approve-request">
                <i class="fa fa-check"></i> ${i18n("admin.registration.requests.approve")}
            </button>
            <button class="btn btn-danger btn-sm reject-request">
                <i class="fa fa-times"></i> ${i18n("admin.registration.requests.reject")}
            </button>
        </td>
    </tr>
</script>
</@layout> 

