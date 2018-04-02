<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR', 'ROLE_MANAGER')" var="allowEditOrderStatus" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logged in</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
    <div style="float:right">
        <security:authorize access="isAuthenticated()">
            <security:authentication property="principal.username" /> | <a href="logout">Logout</a>
        </security:authorize>
    </div>
    <div>
      <h3>Successfully logged in</h3>
    </div>
    <div>
        <a href="/usersPage">View users</a>
    </div>
    <div>
        <a href="/screensPage">View screens</a>
    </div>
    <div>
        <a href="/clocksPage">View clocks</a>
    </div>
    </body>
</html>
<script>
$(document).ready(function(){
    console.log("Ready!");
});
</script>