<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>API | Installer</title>
        <!-- Meta, title, CSS, favicons, etc. -->
        <link rel="icon" href="<c:url value='/assets/images/title-logo.png'/>">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap -->
        <link href="<c:url value='/assets/plugins/bootstrap/dist/css/bootstrap.min.css'/>" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="<c:url value='/assets/plugins/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet">
        <!-- NProgress -->
        <link href="<c:url value='/assets/plugins/nprogress/nprogress.css'/>" rel="stylesheet">

        <!-- bootstrap-daterangepicker -->
        <link href="<c:url value='/assets/plugins/bootstrap-daterangepicker/daterangepicker.css'/>" rel="stylesheet">

        <!-- Custom styling plus plugins -->
        <link href="<c:url value='/assets/css/custom.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/css/main.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/css/Loader.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/plugins/pace/pace.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/plugins/pnotify/dist/pnotify.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/plugins/pnotify/dist/pnotify.buttons.css'/>" rel="stylesheet">

        <link rel="stylesheet" href="<c:url value='/assets/plugins/bootstrap-formhelpers/dist/css/bootstrap-formhelpers.min.css'/>"/>
        <!-- Ion.RangeSlider -->
        <link href="<c:url value='/assets/plugins/ion.rangeSlider/css/ion.rangeSlider.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/plugins/ion.rangeSlider/css/ion.rangeSlider.skinFlat.css'/>" rel="stylesheet">
        <link href="<c:url value='/assets/css/views/adminConfiguration.css'/>" rel="stylesheet">
        <style>

            ::-webkit-scrollbar
            {
                width: 6px;  /* for vertical scrollbars */
                height: 6px; /* for horizontal scrollbars */
            }

            ::-webkit-scrollbar-track
            {
                background: rgba(240, 240, 240, 0.4);
            }

            ::-webkit-scrollbar-thumb
            {
                background: rgba(50, 50, 50, 0.5);
            }

            .navbar-nav .open .dropdown-menu {
                position: absolute;
                background: #fff;
                margin-top: 0;
                border: 1px solid #D9DEE4;
                -webkit-box-shadow: none;
                right: 0;
                left: auto;
                width: 180px;
            }


            .img-circle.profile_img {
                width: 70px;
                height: 70px;
            }
        </style>
    </head>
    <body class="nav-md">
        <!--        <div class="loading loadingDiv">Loading&#8230;</div>-->
        <div id="loader-wrapper">
            <div id="loader"></div>
            <div class="loader-section section-left"></div>
            <div class="loader-section section-right"></div>
        </div>
        <div class="container body">
            <div class="main_container">
                <div class="col-md-3 left_col">
                    <div class="left_col scroll-view">
                        <div class="navbar nav_title" style="border: 0;">
                            <div class="site_title"><span class="fa fa-mobile"></span>&nbsp;&nbsp;<span>Bulk </span><span style="background-color: #FFF; color: #2a3f54; border-radius: 10px; padding: 5px">Installer</span></div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- menu profile quick info -->
                        <div class="profile clearfix">
                            <div class="profile_pic">
                                <img src="<c:url value='/assets/images/user.png'/>" alt="..." class="img-circle profile_img">
                            </div>
                            <div class="profile_info">
                                <span>Welcome,</span>
                                <h2 class="authenticationName">Support</h2>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- /menu profile quick info -->
                        <br />
                        <!-- sidebar menu -->				
                        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                            <div class="menu_section">
                                <ul class="nav side-menu">
                                    <li><a href="<c:url value='/installation'/>"><i class="fa fa-gear"></i>Configure</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- /sidebar menu -->
                        <!-- /menu footer buttons -->
                        <div class="sidebar-footer hidden-small">
                            <a data-toggle="tooltip" data-placement="top" title="Configuration">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                            </a>
                            <a id="FullScreen" data-toggle="tooltip" data-placement="top" title="FullScreen">
                                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                            </a>
                            <a data-toggle="tooltip" data-placement="top" title="Lock">
                                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                            </a>
                            <a data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value='/logout'/>">
                                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                            </a>
                        </div>
                        <!-- /menu footer buttons -->
                    </div>
                </div>
                <!-- top navigation -->
                <div class="top_nav">
                    <div class="nav_menu">
                        <nav>
                            <div class="nav toggle">
                                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                            </div>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="">
                                    <a href="javascript:;" class="user-profile dropdown-toggle authenticationName" data-toggle="dropdown" aria-expanded="false" style="text-align: left; width: 180px">
                                        Support Installation
                                        &nbsp;<span class=" fa fa-angle-down"></span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                                        <li><a href="<c:url value='/logout'/>"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <!-- /top navigation -->
                <!-- page content -->
                <div class="right_col" role="main">
                    <div class="">
                        <div class="page-title">
                            <div class="title_left" style="margin-bottom: 20px">
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2 class="collapse-link"><a href="#">Database Settings</a></h2>
                                        <div class="clearfix"></div>
                                    </div>
                                    <br/>
                                    <div class="x_content">
                                        <form id="configuration-form" class="form-horizontal form-label-left">
                                            <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Database URL</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" value="${properties.getJdbc_url()}" type="text" name="jdbc_url" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Username</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" autocomplete="new-username" type="text" value="${properties.getJdbc_username()}" name="jdbc_username" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Password</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="password" autocomplete="new-password" value="${properties.getJdbc_password()}" name="jdbc_password" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="text-center col-md-6 col-sm-6 col-xs-12 col-md-offset-3 pull-left">
                                                    <button type="button" class="btn btn-default test-db-accessibility"><i class="fa fa-arrow-circle-up"> Accessibility Test</i></button>
                                                    <button type="button" class="btn btn-brown test-db-version"><i class="fa fa-question-circle"> Version Test</i></button>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-7 col-sm-7 col-xs-7 col-md-offset-5 text-center">
                                                    <div class="alert alert-success successConnection" style="display: none; width:250px">
                                                        <span class="alertSpan" style="font-weight: 700">Connection Success.</span>
                                                    </div>
                                                    <div class="alert alert-danger errorConnection" style="display: none; width:250px">
                                                        <span class="alertSpan" style="font-weight: 700">Connection Error.</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <br/><hr/>           
                                            <div class="x_title">
                                                <h2 class="collapse-link"><a href="#">Shared Directory</a></h2>
                                                <div class="clearfix"></div>
                                            </div>
                                            <br/>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Path</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" value="${properties.getShared_directory_path()}" name="shared_directory_path" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Username</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" value="${properties.getShared_username()}" name="shared_username" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Password</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="password" autocomplete="new-password" value="${properties.getShared_password()}" name="shared_password" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="text-center col-md-6 col-sm-6 col-xs-12 col-md-offset-3 pull-left">
                                                    <button type="button" class="btn btn-default test-shared-accessibility"><i class="fa fa-arrow-circle-up"> Accessibility Test</i></button>
                                                </div>
                                            </div>
                                            <br/><hr/>             
                                            <div class="x_title">
                                                <h2 class="collapse-link"><a href="#">Email Settings</a></h2>
                                                <div class="clearfix"></div>
                                            </div>
                                            <br/>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Host</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" value="${properties.getMail_host()}" name="mail_host" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Port</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" value="${properties.getMail_port()}" name="mail_port" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Email</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" value="${properties.getMail_username()}" name="mail_username" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Password</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="password" autocomplete="new-password" value="${properties.getMail_password()}" name="mail_password" required="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Test Email</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12">
                                                    <input class="form-control col-md-7 col-xs-12" type="text" placeholder="Enter an email address to test email settings" value="" name="testEmail">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="text-center col-md-6 col-sm-6 col-xs-12 col-md-offset-3 pull-left">
                                                    <button type="button" class="btn btn-default test-email-accessibility"><i class="fa fa-arrow-circle-up"> Accessibility Test</i></button>
                                                </div>
                                            </div>
                                            <br/><hr/>         
                                            <div class="x_title">
                                                <h2 class="collapse-link"><a href="#">Tomcat Settings</a></h2>
                                                <div class="clearfix"></div>
                                            </div>
                                            <br/>
                                            <div class="form-group">
                                                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Memory Heap</label>
                                                <div class="col-md-6 col-sm-6 col-xs-12 ">
                                                    <img class="img-responsive avatar-view" src="<c:url value='/assets/images/Tomcat_Heap_Settings.PNG'/>" alt="Avatar" title="Creating new campaign">
                                                </div>
                                            </div>

                                            <br/><hr/>
                                            <div class="form-group">
                                                <div class="text-center col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                                    <button type="submit" class="btn btn-primary">Save Settings</button>
                                                </div>
                                            </div>
                                            <br/>
                                            <div class="form-group">
                                                <div class="col-md-7 col-sm-7 col-xs-7 col-md-offset-5 text-center">
                                                    <div class="alert alert-success successAlertDiv" style="display: none; width:250px">
                                                        <span class="alertSpan" style="font-weight: 700">Settings saved.</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>      
                    </div>
                </div>
                <!-- /page content -->
                <!-- footer content -->
                <footer>
                    <div class="text-center">
                        Copyright © 2016-2017 <a href="#">Freedom Pass</a> All rights reserved.
                    </div>
                    <div class="clearfix"></div>
                </footer>
                <!-- /footer content -->
            </div>
        </div>
        <!-- /compose -->

        <div class="modal fade warningModal" role="dialog" data-keyboard="false" data-backdrop="static">
            <div class="modal-dialog-custom">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h2 class="modal-title text-center">Apply Settings</h2>
                    </div>
                    <div class="modal-body" style="max-height: 300px;overflow-y: scroll">
                        <div class="row">
                            <div class="text-center">
                                <h5>Process might take few seconds to complete.</h5>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
                        <input type="hidden" class="cancel-list-delete-btn" data-dismiss="modal"/>
                        <button type="button" class="btn btn-brown submit-setting-btn" data-list-id="">Proceed</button>
                    </div>
                </div>

            </div>
        </div>

        <div class="modal fade success-modal" role="dialog" data-keyboard="false" data-backdrop="static">
            <div class="modal-dialog-custom">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-body" style="max-height: 300px;overflow-y: scroll">
                        <div class="row">
                            <div class="text-center">
                                <i class="fa fa-check-circle fa-4x" style="color: #398439"></i>
                                <br>
                                <h5 id="successString"></h5>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade warning-modal" role="dialog" data-keyboard="false" data-backdrop="static">
            <div class="modal-dialog-custom">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-body" style="max-height: 300px;overflow-y: scroll">
                        <div class="row">
                            <div class="text-center">
                                <i class="fa fa-warning fa-4x" style="color: #EA8511"></i>
                                <br>
                                <h5 id="warningString"></h5>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade error-modal" role="dialog" data-keyboard="false" data-backdrop="static">
            <div class="modal-dialog-custom">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-body" style="max-height: 300px;overflow-y: scroll">
                        <div class="row">
                            <div class="text-center">
                                <i class="fa fa-warning fa-4x" style="color: #980d1a"></i>
                                <br>
                                <h5 id="errorString"> Connection Error. </h5>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="text-align: center">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="<c:url value='/assets/plugins/jquery/dist/jquery.min.js'/>"></script>
        <script src="<c:url value='/assets/plugins/jquery/dist/jquery-ui.min.js'/>"></script>
        <!-- Bootstrap -->
        <script src="<c:url value='/assets/plugins/bootstrap/dist/js/bootstrap.min.js'/>"></script>
        <!-- FastClick -->
        <script src="<c:url value='/assets/plugins/fastclick/lib/fastclick.js'/>"></script>
        <!-- NProgress -->
        <script src="<c:url value='/assets/plugins/nprogress/nprogress.js'/>"></script>

        <!-- DateJS -->
        <script src="<c:url value='/assets/plugins/DateJS/build/date.js'/>"></script>
        <script src="<c:url value='/assets/plugins/moment/min/moment.min.js'/>"></script>
        <script src="<c:url value='/assets/plugins/bootstrap-daterangepicker/daterangepicker.js'/>"></script>

        <!-- Custom Theme Scripts -->
        <script src="<c:url value='/assets/js/custom.js'/>"></script>
        <script src="<c:url value='/assets/js/main.js'/>"></script>
        <script src="<c:url value='/assets/plugins/pace/pace.min.js'/>"></script>
        <script src="<c:url value='/assets/plugins/pnotify/dist/pnotify.js'/>"></script>
        <script src="<c:url value='/assets/plugins/pnotify/dist/pnotify.buttons.js'/>"></script>
        <script>
            $(document).ajaxStart(function () {
                Pace.restart();
            });
            $('document').ready(function () {
                $('input[type=text], textarea').addClass('doNotCapitalize');

                $('#configuration-form').on('submit', function (e) {
                    e.preventDefault();
                    $('.warningModal').modal();
                });

                $('.submit-setting-btn').on('click', function (e) {
                    e.preventDefault();
                    $('.submit-setting-btn').lockBtn('');
                    $.ajax({
                        type: 'post',
                        url: "<c:url value='/installation/saveSettings'/>",
                        data: $('#configuration-form').serialize(),
                        success: function (data) {
                            if (data === '0' || data === '1') {
                                $('#successString').html('Installation completed. <br/><br/>  You can login as: <br/><br/> sysuser@freedomPass.com/Admin123');
                                $('.success-modal').modal();
                            } else if (data === 'Error') {
                                $('#errorString').html(' Installation partially completed. Check your DB settings. ');
                                $('.error-modal').modal();
                            }
                        },
                        error: function (error) {
                            renderAjaxError(error);
                        },
                        complete: function () {
                            $('.submit-setting-btn').unLockBtn();
                            $('.warningModal').modal('hide');
                        }
                    });
                });

                $('.test-db-accessibility').on('click', function (e) {
                    e.preventDefault();
                    $('.test-db-accessibility').lockBtn('');
                    var csrfObj = $('#configuration-form').find('[name=_csrf]').val();
                    var ajaxData = {
                        'db_url': $('#configuration-form').find('[name=jdbc_url]').val(),
                        'username': $('#configuration-form').find('[name=jdbc_username]').val(),
                        'password': $('#configuration-form').find('[name=jdbc_password]').val(),
                        '_csrf': csrfObj.value
                    };
                    $.ajax({
                        type: 'get',
                        url: "<c:url value='/installation/testDBAccessibility'/>",
                        data: ajaxData,
                        success: function (data) {
                            if (data === '0') {
                                $('#warningString').html(' Database is accessible but needs initialization. <br/><br/> Tap on Save Settings to initialize the database. <br/><br/> <h6 style = "color: red">Warning: Insufficient database privileges may cause installation issues</h6>');
                                $('.warning-modal').modal();
                            } else if (data === '1') {
                                $('#successString').html(' Database is accessible and ready for operation. ');
                                $('.success-modal').modal();
                            } else if (data === 'Error') {
                                $('#errorString').html(' Database is not accessible. ');
                                $('.error-modal').modal();
                            }
                        },
                        error: function (error) {
                            renderAjaxError(error);
                        },
                        complete: function () {
                            $('.test-db-accessibility').unLockBtn();
                        }
                    });
                });

                $('.test-db-version').on('click', function (e) {
                    e.preventDefault();
                    $('.test-db-version').lockBtn('');
                    var csrfObj = $('#configuration-form').find('[name=_csrf]').val();
                    var ajaxData = {
                        'db_url': $('#configuration-form').find('[name=jdbc_url]').val(),
                        'username': $('#configuration-form').find('[name=jdbc_username]').val(),
                        'password': $('#configuration-form').find('[name=jdbc_password]').val(),
                        '_csrf': csrfObj.value
                    };
                    $.ajax({
                        type: 'get',
                        url: "<c:url value='/installation/testDBVersion'/>",
                        data: ajaxData,
                        success: function (data) {
                            if (data === '0') {
                                $('#warningString').html(' Database version is old. <br/><br/> ==> Drop all database Objects using PROC_DROP_API_DB_OBJECTS(ivrsystem30). <br/><br/> <h6 style = "color: red">Warning: In case the database is live you need to save a backup before proceeding!</h6>');
                                $('.warning-modal').modal();
                            } else if (data === '1') {
                                $('#successString').html(' Database version is latest. Check the udpate patch scripts for any minor udpate.');
                                $('.success-modal').modal();
                            } else if (data === 'Error') {
                                $('#errorString').html(' Database is not accessible. An Error occured ');
                                $('.error-modal').modal();
                            }
                        },
                        error: function (error) {
                            renderAjaxError(error);
                        },
                        complete: function () {
                            $('.test-db-version').unLockBtn();
                        }
                    });
                });

                $('.test-shared-accessibility').on('click', function (e) {
                    e.preventDefault();
                    $('.test-shared-accessibility').lockBtn('');
                    var csrfObj = $('#configuration-form').find('[name=_csrf]').val();
                    var ajaxData = {
                        'fullPath': $('#configuration-form').find('[name=shared_directory_path]').val(),
                        'username': $('#configuration-form').find('[name=shared_username]').val(),
                        'password': $('#configuration-form').find('[name=shared_password]').val(),
                        '_csrf': csrfObj.value
                    };

                    $.ajax({
                        type: 'get',
                        url: "<c:url value='/installation/testSharedDirAccessibility'/>",
                        data: ajaxData,
                        success: function (data) {
                            if (data === '0') {
                                $('#successString').html(' Network location is accessible. <br/><br/> Tap on Save Settings to create the directory.');
                                $('.success-modal').modal();
                            } else if (data === '1') {
                                $('#errorString').html(' Network location is not accessible. <br/><br/> Make sure your network credentials are set correctly.');
                                $('.error-modal').modal();
                            }
                        },
                        error: function (error) {
                            renderAjaxError(error);
                        },
                        complete: function () {
                            $('.test-shared-accessibility').unLockBtn();
                        }
                    });
                });

                $('.test-email-accessibility').on('click', function (e) {
                    e.preventDefault();

                    if ($('#configuration-form').find('[name=testEmail]').val() === '')
                    {
                        $('#errorString').html('You should provide a test email to proceed.');
                        $('.error-modal').modal();
                    } else
                    {
                        $('.test-email-accessibility').lockBtn('');
                        var csrfObj = $('#configuration-form').find('[name=_csrf]').val();
                        var ajaxData = {
                            'mail_host': $('#configuration-form').find('[name=mail_host]').val(),
                            'mail_port': $('#configuration-form').find('[name=mail_port]').val(),
                            'mail_username': $('#configuration-form').find('[name=mail_username]').val(),
                            'mail_password': $('#configuration-form').find('[name=mail_password]').val(),
                            'testEmail': $('#configuration-form').find('[name=testEmail]').val(),
                            '_csrf': csrfObj.value
                        };
                        $.ajax({
                            type: 'get',
                            url: "<c:url value='/installation/testEmailAccessibility'/>",
                            data: ajaxData,
                            success: function (data) {
                                if (data === '0') {
                                    $('#successString').html(' Email Server is accessible. <br/><br/> Tap on Save Settings to set the email settings.');
                                    $('.success-modal').modal();
                                } else if (data === '1') {
                                    $('#errorString').html(' Email Server is not accessible. <br/><br/> Contact your IT administrator for help.');
                                    $('.error-modal').modal();
                                }
                            },
                            error: function (error) {
                                renderAjaxError(error);
                            },
                            complete: function () {
                                $('.test-email-accessibility').unLockBtn();
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>
