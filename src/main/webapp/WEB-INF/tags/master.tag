<%@tag description="Freedom Pass master page" pageEncoding="UTF-8"%>
<%@attribute name="css" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="js" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <jsp:invoke fragment="css"/>
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
                /*                width: inherit;*/
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
                            <div class="site_title"><span class="fa fa-mobile"></span>&nbsp;&nbsp;<span>Bulk </span><span style="background-color: #FFF; color: #2a3f54; border-radius: 10px; padding: 5px">Broadcast</span></div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- menu profile quick info -->
                        <div class="profile clearfix">
                            <div class="profile_pic">
                                <sec:authorize access="!hasAnyRole('SYS_IS_SYSTEM_USER','SUPPORT','INSTALLER')">
                                    <img src="<c:url value='/profile/photo'/>" alt="..." class="img-circle profile_img">
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('SYS_IS_SYSTEM_USER','SUPPORT','INSTALLER')">
                                    <img src="<c:url value='/assets/images/user.png'/>" alt="..." class="img-circle profile_img">
                                </sec:authorize>
                            </div>
                            <div class="profile_info">
                                <span><spring:message code="tag.greeting"/>,</span>
                                <h2 class="authenticationName">${auth.getName()}</h2>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- /menu profile quick info -->
                        <br />
                        <!-- sidebar menu -->												 
                        <jsp:include page="/WEB-INF/views/shared/sidebar.jsp"/>						
                        <!-- /sidebar menu -->
                        <!-- /menu footer buttons -->
                        <div class="sidebar-footer hidden-small">
                            <a data-toggle="tooltip" data-placement="top" title="Configuration">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                            </a>
                            <a id="FullScreen" data-toggle="tooltip" data-placement="top" title="<spring:message code="shared.fullScreen"/>">
                                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                            </a>
                            <c:if test="${pageContext.response.locale == 'en'}">
                                <a data-toggle="tooltip" data-placement="top" href="?lang=fr" title="Français">
                                    <span class="fa fa-language" aria-hidden="true"></span>
                                </a>
                            </c:if>
                            <c:if test="${pageContext.response.locale == 'fr'}">
                                <a data-toggle="tooltip" data-placement="top" href="?lang=en" title="English">
                                    <span class="fa fa-language" aria-hidden="true"></span>
                                </a>
                            </c:if>
                            <a data-toggle="tooltip" data-placement="top" title="<spring:message code="shared.logout"/>" href="<c:url value='/logout'/>">
                                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                            </a>
                        </div>
                        <!-- /menu footer buttons -->
                    </div>
                </div>
                <!-- top navigation -->
                <jsp:include page="/WEB-INF/views/shared/navbar.jsp"/>
                <!-- /top navigation -->
                <!-- page content -->
                <jsp:invoke fragment="body"/>
                <!-- /page content -->
                <!-- footer content -->
                <footer>
                    <div class="text-center">
                        <spring:message code="tag.rights"/>
                    </div>
                    <div class="clearfix"></div>
                </footer>
                <!-- /footer content -->
            </div>
        </div>
        <!-- /compose -->

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
        <jsp:invoke fragment="js"/>
        <script>
            $(document).ajaxStart(function () {
                Pace.restart();
            });
            $('#menu1 li').on('mousedown', function () {
                var notId = $(this).attr('data-not-id');
                var ajaxData = {notId: notId}
                $.ajax({
                    data: ajaxData,
                    url: '<c:url value="/notifications/read"/>',
                    async: false
                });
            });

            $('.notification-bill').on('click', function () {
                $.ajax({
                    url: '<c:url value="/notifications/seen"/>',
                    error: function (error) {
                        renderAjaxError(error);
                    }
                });
            });
        </script>
    </body>
</html>
