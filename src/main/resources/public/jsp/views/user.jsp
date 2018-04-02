<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User edit</title>
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
        <form id="edituser">
            <table border="1">
                <thead>
                    <tr><td colspan="2">Edit user</td></tr>
                </thead>
                <tbody>
                    <input type="hidden" name="userId" value="" />
                    <tr><td>Username: </td><td><input type="text" name="username" value=""></td></tr>
                    <tr><td>Password: </td><td><input type="password" name="password" value=""></td></tr>
                    <tr><td>Roles: </td>
                        <td id="rolesInput">
                        </td>
                    <tr><td colspan="2"><input type="submit" value="Edit user" /></td></tr>
                </tbody>
            </table>
        </form>
    </div>
    <div>
        <a href="/usersPage">Go to previous page</a>
    </div>
    </body>
</html>
<script>
function resetUserEditForm() {
    $("#edituser").get(0).reset();
    $("#edituser input[name='roles[]']").removeAttr('checked');
}
function loadUser() {
    $.ajax({
        url: "/users/"+window.location.href.substring(window.location.href.lastIndexOf('/')+1),
        type: "GET",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            $("#edituser input[name=userId]").val(data.id);
            $("#edituser input[name=username]").val(data.username);
            $.each(data.roles, function(index, rolename) {
                $("#edituser input[name='roles[]']").filter(function(i, obj) {
                    return obj.value && obj.value == rolename;
                }).attr('checked', true);
            });
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
        }
   });
}
function loadAllRoles() {
    return $.ajax({
        url: "/users/roles",
        type: "GET",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            var tdRoles = "";
            $.each(data, function(index, role){
                tdRoles += "<input type=\"checkbox\" name=\"roles[]\" value=\""+index+"\" />"+role+"<br />";
            });
            $("#edituser tbody tr td#rolesInput").html(tdRoles);
            return data;
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
        }
   });
}
$("#edituser").submit(function(e) {
    //prevent Default functionality
    e.preventDefault();
    var userBean = {};
    userBean.id = $("#edituser input[name=userId]").val();
    userBean.username = $("#edituser input[name=username]").val();
    userBean.password = $("#edituser input[name=password]").val();
    var roles = [];
    $("#edituser input[name='roles[]']:checked").each(function(){
        roles.push($(this).val());
    });
    userBean.roles = roles;
    $.ajax({
            url: "/users/update",
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(userBean),
            success: function(data, textStatus, jQxhr){
                console.log(data);
                resetUserEditForm();
                loadUser();
                $("#updateSuccess").html("User updated successfully");
                $("#updateSuccess").css("background-color", "#58FF33");
                $("#updateSuccess").css("display", "block");
            },
            error: function(jqXhr, textStatus, errorThrown){
                console.log(errorThrown);
                $("#updateSuccess").html("Failed to update user : "+jqXhr.statusText);
                $("#updateSuccess").css("background-color", "#F35A53");
                $("#updateSuccess").css("display", "block");
                resetUserEditForm();
                loadUser();
            }
    });
});
$(document).ready(function(){
    loadAllRoles().done(loadUser());
    $("#updateSuccess").css("display", "none");
    console.log("User ready!");
});
</script>