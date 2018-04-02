<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')" var="allowEditScreens" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Screen edit</title>
        <script type="text/javascript" src="../ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
    <div style="float:right">
        <security:authorize access="isAuthenticated()">
            <security:authentication property="principal.username" /> | <a href="logout">Logout</a>
        </security:authorize>
    </div>
    <div id="updateSuccess"></div>
    <div>
        <form id="editscreen">
            <table border="1">
                <thead>
                    <tr>
                        <td>Edit screen</td>
                        <td id="screenTitle"></td>
                    </tr>
                </thead>
                <tbody>
                    <input type="hidden" name="screenId" value="" />
                    <tr><td>Title: </td><td><input type="text" name="title" value=""></td></tr>
                    <tr><td colspan="2"><input type="submit" value="Edit screen" /></td></tr>
                </tbody>
            </table>
        </form>
    </div>
    <div>
        <a href="/screensPage">Go to previous page</a>
    </div>
    </body>
</html>
<script>
function resetScreenEditForm() {
    $("#editscreen").get(0).reset();
}
function loadScreen() {
    $.ajax({
        url: "/screens/"+window.location.href.substring(window.location.href.lastIndexOf('/')+1),
        type: "GET",
        success: function(data, textStatus, jQxhr){
            //console.log(data);
            $("#editscreen input[name=screenId]").val(data.id);
            $("#editscreen input[name=title]").html(data.title);
        },
        error: function(jqXhr, textStatus, errorThrown){
            //console.log(errorThrown);
        }
   });
}
$("#editscreen").submit(function(e) {
    //prevent Default functionality
    e.preventDefault();
    var screenBean = {};
    screenBean.id = $("#editscreen input[name=screenId]").val();
    screenBean.title = $("#editscreen input[name=title]").val();
    $.ajax({
            url: "/screens/update",
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(productBean),
            success: function(data, textStatus, jQxhr){
                resetScreenEditForm();
                loadScreen();
                $("#updateSuccess").html("Screen updated successfully");
                $("#updateSuccess").css("background-color", "#58FF33");
                $("#updateSuccess").css("display", "block");
            },
            error: function(jqXhr, textStatus, errorThrown){
                console.log(errorThrown);
                $("#updateSuccess").html("Failed to update screen : "+jqXhr.statusText);
                $("#updateSuccess").css("background-color", "#F35A53");
                $("#updateSuccess").css("display", "block");
                loadScreen();
            }
    });
});
$(document).ready(function(){
    loadScreen();
    $("#updateSuccess").css("display", "none");
});
</script>