<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users page</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
    <div style="float:right">
        <security:authorize access="isAuthenticated()">
            <security:authentication property="principal.username" /> | <a href="logout">Logout</a>
        </security:authorize>
    </div>
    <div>
        <table id="users" border="1">
            <thead>
                <tr><th>Username</th><th>Enabled</th><th>Roles</th></tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <br />
    </div>
    <div>
        <form id="adduser">
            <table border="1">
                <thead>
                    <tr><td colspan="2">Add new user</td></tr>
                </thead>
                <tbody>
                    <tr><td>Username: </td><td><input type="text" name="username" value=""></td></tr>
                    <tr><td>Password: </td><td><input type="password" name="password" value=""></td></tr>
                    <tr><td>Roles: </td>
                        <td id="rolesInput">
                        </td>
                    <tr><td colspan="2"><input type="submit" value="Create user" /></td></tr>
                </tbody>
            </table>
        </form>
    </div>
    <div>
        <a href="/welcome">Go to previous page</a>
    </div>
    </body>
</html>
<script>
function resetUserAddForm() {
    $("#adduser").get(0).reset();
    $("#adduser input[name='roles[]']").removeAttr('checked');
}
function loadAllUsers() {
    $.ajax({
        url: "/users/all",
        type: "GET",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            tbody = "";
            $.each(data, function(index, bean){
                tbody += "<tr>"+
                           "<td><a href=\"/userview/"+bean.id+"\">"+bean.username+"</a></td>"+
                           "<td>"+bean.enabled+"</td>"+
                           "<td>"+bean.roles.join()+"</td>"+
                           "<td colspan=\"2\"><a href=\"\" onclick='deleteUserById("+bean.id+")'>Delete</a></td>"+
                         "</tr>";
            });
            $("#users tbody").html(tbody);
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
        }
   });
}
function loadAllRoles() {
    $.ajax({
        url: "/users/roles",
        type: "GET",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            var tdRoles = "";
            $.each(data, function(index, role){
                tdRoles += "<input type=\"checkbox\" name=\"roles[]\" value=\""+index+"\" />"+role+"<br />";
            });
            $("#adduser tbody tr td#rolesInput").html(tdRoles);
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
        }
   });
}
$("#adduser").submit(function(e) {
    //prevent Default functionality
    e.preventDefault();
    var userBean = {};
    userBean.username = $("#adduser input[name=username]").val();
    userBean.password = $("#adduser input[name=password]").val();
    var roles = [];
    $("#adduser input[name='roles[]']:checked").each(function(){
        roles.push($(this).val());
    });
    userBean.roles = roles;
    $.ajax({
            url: "/users/create",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(userBean),
            success: function(data, textStatus, jQxhr){
                console.log(data);
                resetUserAddForm();
                loadAllUsers();
            },
            error: function(jqXhr, textStatus, errorThrown){
                console.log(errorThrown);
            }
    });
});
function deleteUserById(id) {
    //e.preventDefault();
    $.ajax({
        url: "/users/delete/"+id,
        type: "DELETE",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            resetUserAddForm();
            loadAllUsers();
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
            resetUserAddForm();
            loadAllUsers();
        }
    });
}
$(document).ready(function(){
    loadAllUsers();
    loadAllRoles();
    console.log("Users ready!");
});
</script>