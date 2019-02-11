<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle authenticationName" data-toggle="dropdown" aria-expanded="false" style="text-align: left; width: 180px">
                        <sec:authorize access="isAuthenticated()">
                            ${auth.getName()}
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            ANONYMOUS
                        </sec:authorize>
                        &nbsp;<span class=" fa fa-angle-down"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                        <sec:authorize access="!hasRole('SYS_IS_SYSTEM_USER')">
                            <c:set var="getRole" value="user" />
                            <li><a href="<c:url value='/profile'/>"><i class="fa fa-user pull-right"></i> <spring:message code="sNavbar.profile"/></a></li>
                            <li><a href="<c:url value='/help'/>"><i class="fa fa-question-circle pull-right"></i> <spring:message code="sNavbar.help"/></a></li>	
                        </sec:authorize>
                        <sec:authorize access="hasRole('SYS_IS_SYSTEM_USER')">
                            <li><a href="<c:url value='/system/manageProfile'/>"><i class="fa fa-user pull-right"></i> <spring:message code="sNavbar.profile"/></a></li>
                        </sec:authorize>
                        <li><a href="<c:url value='/logout'/>"><i class="fa fa-sign-out pull-right"></i> <spring:message code="shared.logout"/></a></li>
                    </ul>
                </li>
                <li role="presentation" class="dropdown notification-bill">
                    <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                        <i class="fa fa-2x fa-bell" style="color: rgba(42, 63, 84, 0.54) !important"></i>
                        <span class="badge" style="background-color: rgba(255, 0, 0, 0.64)"><c:if test="${unSeenNotificationsCount > 0}">${unSeenNotificationsCount}</c:if></span>
                        </a>
                        <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu" style="max-height: 400px; overflow-y: auto">
                        <c:choose>
                            <c:when test="${notifications.size()>0}">
                                <c:forEach var="notification" items="${notifications}">
                                    <li data-not-id="${notification.getId()}" <c:if test="${notification.isRead()}">style="background-color: white;"</c:if>>
                                        <a href="<c:url value="${notification.getHref()}"/>">
                                            <sec:authorize access="hasRole('SYS_IS_SYSTEM_USER')">
                                                <span class="image"><img src="<c:url value='/campaign/view/user/photo/${notification.getFromCorporateProfileId()}'/>" alt="Profile Image"></span>
                                            </sec:authorize>
                                            <sec:authorize access="!hasRole('SYS_IS_SYSTEM_USER')">
                                                <span class="image"><img src="<c:url value='/assets/images/user.png'/>" alt="Profile Image"></span>
                                            </sec:authorize>
                                            <span>
                                                <span>${notification.getCorporateCompanyName()}</span>
                                                <span class="time pull-right" title="<fmt:formatDate type="date" value="${notification.getCreatedAt()}"/>"><fmt:formatDate pattern="hh:mm a" value="${notification.getCreatedAt()}"/></span>
                                            </span>
                                            <span class="message">
                                                ${notification.getContent()}
                                            </span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <div class="text-center">
                                        <strong><spring:message code="sNavbar.noNewNotif"/></strong>
                                    </div>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
