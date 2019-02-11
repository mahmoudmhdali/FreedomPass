<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master>
    <jsp:attribute name="css">
        <title>Spring API Template | <spring:message code="title.errorHandler"/></title>
        <link rel="icon" href="<c:url value='/assets/images/title-logo.png'/>">
        <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="right_col" role="main">
            <div class="col-middle">
                <div class="text-center text-center">
                    <br/>
                    <br/>
                    <h2 style="color:red">Oops!</h2>
                    <br/>
                    <p>${errorStatus}</p>
                    <br/> <br/> <br/>
<!--                    <a style="font-size: 40px" data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value='/logout'/>">
                        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                    </a>-->
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="js">

    </jsp:attribute>
</t:master>
