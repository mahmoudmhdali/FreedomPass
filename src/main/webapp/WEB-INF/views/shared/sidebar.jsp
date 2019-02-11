<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
    <div class="menu_section">
        <ul class="nav side-menu">
            <sec:authorize access="hasRole('ROLE_SYS_VIEW_REPORTS')">
                <li><a href="<c:url value='/reports'/>"><i class="fa fa-tachometer"></i><spring:message code="sSideBar.dashboard"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SYS_VIEW_SYSTEM_USERS','SYS_ADD_SYSTEM_USERS','SYS_EDIT_SYSTEM_USERS','SYS_DELETE_SYSTEM_USERS')">
                <li><a href="<c:url value='/systemUsers/view'/>"><i class="fa fa-user"></i><spring:message code="sSideBar.systemUsers"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SYS_VIEW_CORPORATE_USERS','SYS_ADD_CORPORATE_USERS','SYS_EDIT_CORPORATE_USERS','SYS_DELETE_CORPORATE_USERS','SYS_INITIATE_PAYMENT')">
                <li><a href="<c:url value='/corporateUsers/view'/>"><i class="fa fa-building-o"></i><spring:message code="sSideBar.corporateUsers"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SYS_VIEW_REPORTS','SYS_VIEW_CAMPAIGN','SYS_MANAGE_CAMPAIGNS')">
                <li><a href="<c:url value='/campaign/view/all'/>"><i class="fa fa-calendar"></i><spring:message code="sSideBar.corporateCampaigns"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SYS_VIEW_LIST','SYS_ADD_LIST','SYS_EDIT_LIST','SYS_DELETE_LIST')">
                <li><a href="<c:url value='/myLists/view'/>"><i class="fa fa-navicon"></i><spring:message code="sSideBar.targetLists"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SYS_VIEW_OFFER','SYS_ADD_OFFER','SYS_EDIT_OFFER','SYS_DELETE_OFFER')">
                <li><a href="<c:url value='/offer/view'/>"><i class="fa fa-newspaper-o"></i><spring:message code="sSideBar.corporatePlans"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_SYS_SYSTEM_CONFIGURATION')">
                <li><a href="<c:url value='/systemConfiguration'/>"><i class="fa fa-cog"></i><spring:message code="sSideBar.configuration"/></a></li>  
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_COR_IS_CORPORATE_USER')">
                <li><a><i class="fa fa-user"></i> <spring:message code="sSideBar.profile"/> <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                        <li><a href="<c:url value='/profile'/>"><spring:message code="sSideBar.accountInfo"/></a></li>
                        <li><a href="<c:url value='/help'/>"><spring:message code="sSideBar.help"/></a></li>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('COR_VIEW_REPORTS')">
                <li><a href="<c:url value='/dashboard'/>"><i class="fa fa-tachometer"></i><spring:message code="sSideBar.dashboard"/></a></li>
                <sec:authorize access="!hasRole('COR_CREATE_AND_MANAGE_CAMPAIGN')">
                    <li><a><i class="fa fa-podcast"></i> <spring:message code="sSideBar.campaigns"/> <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="<c:url value='/campaign/view/all'/>"><spring:message code="sSideBar.view"/></a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </sec:authorize>
            <sec:authorize access="hasRole('COR_CREATE_AND_MANAGE_CAMPAIGN')">
                <li><a><i class="fa fa-podcast"></i> <spring:message code="sSideBar.campaigns"/> <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                        <li><a href="<c:url value='/campaign/view/all'/>"><spring:message code="sSideBar.view"/></a></li>
                        <li><a href="<c:url value='/campaign/add/view'/>"><spring:message code="sSideBar.new"/></a></li>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('COR_VIEW_LIST','COR_ADD_LIST','COR_ADD_LIST','COR_DELETE_LIST')">
                <li><a href="<c:url value='/myLists/view'/>"><i class="fa fa-navicon"></i><spring:message code="sSideBar.myLists"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('COR_VIEW_APPS','COR_ADD_APPS','COR_EDIT_APPS','COR_DELETE_APPS')">
                <li><a href="<c:url value='/myApps/view'/>"><i class="fa fa-caret-square-o-right"></i><spring:message code="sSideBar.myApps"/></a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_SUPPORT')">
                <li><a href="<c:url value='/support'/>"><i class="fa fa-file-text"></i><spring:message code="sSideBar.logs"/></a>
                <li><a href="<c:url value='/support/configuration'/>"><i class="fa fa-cog"></i><spring:message code="sSideBar.configuration"/></a></li>
            </sec:authorize>
        </ul>
    </div>
</div>