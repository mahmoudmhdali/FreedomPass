<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>API | Login</title>
        <link rel="icon" href="<c:url value='/assets/images/title-logo.png'/>">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Rest API">
        <meta name="author" content="FreedomPass">

        <!-- Custom Style -->
        <link href="<c:url value='/assets/css/Login.css'/>" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet">
        <!-- Animate CSS -->
        <link rel="stylesheet" href="<c:url value='/assets/css/animate.css'/>"/>
        <link href="<c:url value='/assets/plugins/pnotify/dist/pnotify.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/plugins/pnotify/dist/pnotify.buttons.css'/>" rel="stylesheet">

    </head>

    <body class="focusedform">

        <div class="verticalcenter">
            <a href="http://www.freedomPass.com/"><img src="<c:url value='/assets/images/logo.png'/>" alt="Logo" class="brand" /></a>
            <div class="panel panel-primary">
                <br>
                <c:url var="loginUrl" value="/login" />
                <form action="${loginUrl}" method="post" class="form-horizontal loginForm" style="position: absolute;margin-bottom: 0px !important; width: 100%">
                    <div class="panel-body">
                        <h4 class="text-center" style="margin-bottom: 25px;">Log in to get started</h4>
                        <sec:csrfInput/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                    <input type="text" class="form-control doNotTrack doNotCapitalize" name="email" placeholder="Email or Mobile Number"required="" />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                    <input type="password" class="form-control doNotTrack" name="password" placeholder="Password" required="" />
                                </div>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="pull-right"><label>
                                    <input type="checkbox" style="margin-bottom: 20px" id="rememberme" name="remember-me"> Remember Me 
                                </label></div>
                        </div>

                        <div class="alert alert-success successAlertDiv" style="display: none">
                            <span class="alertSpan">Email sent successfully</span>
                        </div>

                        <c:if test="${param.error != null}">
                            <div class="alert alert-danger error-div">
                                <c:set var="loginReply" value='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}'/>
                                <c:choose>
                                    <c:when test="${fn:contains(loginReply, 'Could not open Hibernate Session for transaction')}">
                                    <span>An error occurred. Please contact your support for help.</span>
                                </c:when>
                                 <c:otherwise><span>${loginReply}</span></c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>


                        <c:if test="${param.logout != null}">
                            <div class="alert alert-success">
                                <span>You have been logged out successfully.</span>
                            </div>
                        </c:if>
                    </div>
                    <div class="panel-footer">
                        <a class="pull-left btn btn-link forgetPass" style="padding-left:0">Forgot password?</a>
                        <div class="text-right">
                            <button type="submit" class="btn btn-primary submit loginBtn">Log in</button>
                        </div>
                    </div>
                </form>

                <form id="forgotPasswordForm" class="form-horizontal" style="position: absolute;margin-bottom: 0px !important; display: none; width: 100%">
                    <div class="panel-body">
                        <h4 class="text-center" style="margin-bottom: 25px;">Forgot Password</h4>
                        <sec:csrfInput/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                    <input type="email" class="form-control doNotTrack doNotCapitalize" name="email" placeholder="Email Address"required="" />
                                </div>
                            </div>
                        </div>
                        <div class="alert alert-danger alertDiv" style="display: none">
                            <span class="alertSpan">Invalid username</span>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <button type="button" class="pull-left btn btn-default backBtn">Back</button>
                        <div class="text-right">
                            <button type="submit" class="btn btn-primary submit submitEmail">Reset Password</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- jQuery -->
        <script src="<c:url value='/assets/plugins/jquery/dist/jquery.min.js'/>"></script>
        <script src="<c:url value='/assets/plugins/jquery/dist/jquery-ui.min.js'/>"></script>
        <!-- Bootstrap -->
        <script src="<c:url value='/assets/plugins/bootstrap/dist/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/assets/plugins/pnotify/dist/pnotify.js'/>"></script>
        <script src="<c:url value='/assets/plugins/pnotify/dist/pnotify.buttons.js'/>"></script>
        <script src="<c:url value='/assets/js/main.js'/>"></script>
        <script>



            $('.loginForm').on('submit', function (e) {
                $('.loginBtn').lockBtn('');
                $('.error-div').hide();
                $('.successAlertDiv').hide();
            });

            $('document').ready(function ()
            {
                $('.loginForm').find('[name=email]').focus();
                $('body').on('click', '.forgetPass', function () {
                    $('.alertDiv').hide();
                    $('.loginForm').find('[name=email]').val('');
                    $('.loginForm').find('[name=password]').val('');
                    $(".loginForm").hide("slide", {direction: "left"}, 1000);
                    $("#forgotPasswordForm").show("slide", {direction: "right"}, 1000);
                    $('#forgotPasswordForm').find('[name=email]').focus();
                });
                $('body').on('click', '.backBtn', function () {
                    $('.error-div').hide();
                    $('.successAlertDiv').hide();
                    $('#forgotPasswordForm').find('[name=email]').val('');
                    $(".loginForm").show("slide", {direction: "left"}, 1000);
                    $("#forgotPasswordForm").hide("slide", {direction: "right"}, 1000);
                    $('.loginForm').find('[name=email]').focus();
                });

                $('#forgotPasswordForm').on('submit', function (e) {
                    $('.alertDiv').hide();
                    e.preventDefault();
                    var btn = $('.submitEmail');
                    btn.lockBtn('');
                    $('.backBtn').hide();
                    $.ajax({
                        type: 'post',
                        url: "<c:url value='/resetPassword'/>",
                        data: $(this).serialize(),
                        success: function (data) {
                            if (data.statusCode == '0')
                            {
                                $('.backBtn').click();
                                $('.successAlertDiv').show();
                            } else {
                                $.handleAjaxRequest(data, null);
                            }
                        },
                        error: function (error) {
                            
                        },
                        complete: function () {
                            btn.unLockBtn('');
                            $('.backBtn').show();
                        }
                    });
                });
            });
        </script>
    </body>
</html>