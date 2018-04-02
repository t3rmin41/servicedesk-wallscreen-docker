<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')" var="allowEditScreens" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Screens page</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
    <div style="float:right">
        <security:authorize access="isAuthenticated()">
            <security:authentication property="principal.username" /> | <a href="logout">Logout</a>
        </security:authorize>
    </div>
    <div>
        <table id="screens" border="1">
            <thead>
                <tr><th>ID</th><th>Title</th><th>Actions</th></tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <c:choose>
       <c:when test="${allowEditScreens}">
        <hr /> 
        <div>
         <form id="addscreenform">
            <table id="addscreentable" border="1">
                <thead>
                    <tr><td colspan="2">Add new screen</td></tr>
                </thead>
                <tbody>
                    <tr><td>Screen title: </td><td><input type="text" name="title" value=""></td></tr>
                    <tr><td colspan="2"><input type="submit" value="Create screen" /></td></tr>
                </tbody>
            </table>
          </form>
         </div>
       </c:when>
    </c:choose>
    <div>
        <a href="/welcome">Go to previous page</a>
    </div>
    </body>
</html>
<script>
$(document).ready(function(){
    var allowScreensEdit = ${allowEditScreens};
    loadEditableScreens();
});
function loadEditableScreens() {
        $.ajax({
            url: "/screens/editable",
            type: "GET",
            success: function(data, textStatus, jQxhr){
                console.log(data);
                tbody = "";
                $.each(data, function(index, screen){
                    tbody += 
                        "<tr>"+
                            "<td>"+screen.id+"</td>"+
                            "<td><a href=\"/screenview/"+screen.id+"\">"+screen.title+"</a></td>"+
                            "<td><span style=\"text-decoration: underline; cursor: pointer\" onclick=\"deleteScreen("+screen.id+")\">Delete screen</span></td>"+
                       "</tr>";
                });
                $("#screens tbody").html(tbody);
            },
            error: function(jqXhr, textStatus, errorThrown){
                console.log(errorThrown);
            }
       });
}
function resetScreenAddForm() {
  $("#addscreenform").get(0).reset();
}
$("#addscreenform").submit(function(e) {
  //prevent Default functionality
  e.preventDefault();
  var screenBean = {};
  screenBean.title = $("#addscreenform input[name=title]").val();
  $.ajax({
          url: "/screens/create",
          type: "POST",
          contentType: "application/json; charset=utf-8",
          data: JSON.stringify(screenBean),
          success: function(data, textStatus, jQxhr){
              console.log(data);
              resetScreenAddForm();
              loadEditableScreens();
          },
          error: function(jqXhr, textStatus, errorThrown){
              console.log(errorThrown);
          }
  });
});
function deleteScreen(id) {
  //e.preventDefault();
  $.ajax({
      url: "/screens/delete/"+id,
      type: "DELETE",
      success: function(data, textStatus, jQxhr){
          console.log(data);
          loadEditableScreens();
      },
      error: function(jqXhr, textStatus, errorThrown){
          console.log(errorThrown);
          loadEditableScreens();
      }
  });
}
</script>