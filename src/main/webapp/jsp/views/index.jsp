<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Service desk wall screen application</title>
    <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
</head>
<body>
    <div>
        <div>
            <h1>Service desk wall screen application</h1>
            
            Click <strong><a href="login">here</a></strong> to login
        </div>
    </div>
</body>
</html>
<script>
$(document).ready(function(){
    console.log("Ready!");
});
</script>