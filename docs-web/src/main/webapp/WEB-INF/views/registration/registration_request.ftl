<#include "../base.ftl">

<@layout>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${i18n("registration.request.title")}</h3>
                </div>
                <div class="panel-body">
                    <form id="registration-request-form" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">${i18n("registration.request.username")}</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">${i18n("registration.request.email")}</label>
                            <div class="col-sm-9">
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">${i18n("registration.request.password")}</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">${i18n("registration.request.reason")}</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="reason" name="reason" rows="4" required></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-primary">${i18n("registration.request.submit")}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout> 
