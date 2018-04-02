<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')" var="allowEditClocks" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clocks page</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
    <div style="float:right">
        <security:authorize access="isAuthenticated()">
            <security:authentication property="principal.username" /> | <a href="logout">Logout</a>
        </security:authorize>
    </div>
    <div>
        <table id="clocks" border="1">
            <thead>
                <tr><th>Title</th><th>GMT</th><th>Order</th><th>Screen title</th><th>Actions</th></tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <c:choose>
       <c:when test="${allowEditClocks}">
        <hr /> 
        <div>
         <form id="addclockform">
            <table id="addclocktable" border="1">
                <thead>
                    <tr><td colspan="2">Add new clock</td></tr>
                </thead>
                <tbody>
                    <tr><td>Clock title: </td><td><input type="text" name="title" value=""></td></tr>
                    <tr><td>GMT: </td><td><input type="text" name="gmt" value=""></td></tr>
                    <tr><td>Order: </td><td><input type="text" name="order" value=""></td></tr>
                    <tr><td>Screen: </td><td><select id="screenSelect" name="screenId"></select></tr>
                    <tr><td colspan="2"><input type="submit" value="Create clock" /></td></tr>
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
    var allowClocksEdit = ${allowEditClocks};
    loadAllClocks();
    loadClockAddForm();
});
function loadAllClocks() {
        $.ajax({
            url: "/clocks/all",
            type: "GET",
            success: function(data, textStatus, jQxhr){
                console.log(data);
                tbody = "";
                $.each(data, function(index, clock){
                    tbody += 
                        "<tr>"+
                            "<td>"+clock.clockName+"</td>"+
                            "<td>"+clock.clockGMT+"</td>"+
                            "<td>"+clock.clockOrder+"</td>"+
                            "<td>"+clock.screen.title+"</td>"+
                            "<td><span style=\"text-decoration: underline; cursor: pointer\" onclick=\"deleteClock("+clock.id+")\">Delete clock</span></td>"+
                       "</tr>";
                });
                $("#clocks tbody").html(tbody);
            },
            error: function(jqXhr, textStatus, errorThrown){
                console.log(errorThrown);
            }
       });
}
function loadClockAddForm() {
  getScreens().done(function(screens){
    var screenSelectOptions = "";
    $.each(screens, function(index, screen){
      screenSelectOptions += "<option value=\""+screen.id+"\">"+screen.title+"</option>";
    });
    $("#addclocktable tbody #screenSelect").html(screenSelectOptions);
  });
}
function resetClockAddForm() {
  $("#addclockform").get(0).reset();
}
$("#addclockform").submit(function(e) {
  //prevent Default functionality
  e.preventDefault();
  var clockBean = {};
  clockBean.clockName = $("#addclockform input[name=title]").val();
  clockBean.clockGMT = $("#addclockform input[name=gmt]").val();
  clockBean.clockOrder = $("#addclockform input[name=order]").val();
  clockBean.screen = {};
  clockBean.screen.id = $("#screenSelect > option:selected").val();
  $.ajax({
          url: "/clocks/create",
          type: "POST",
          contentType: "application/json; charset=utf-8",
          data: JSON.stringify(clockBean),
          success: function(data, textStatus, jQxhr){
              console.log(data);
              resetClockAddForm();
              loadAllClocks();
          },
          error: function(jqXhr, textStatus, errorThrown){
              console.log(errorThrown);
          }
  });
});
function deleteClock(id) {
  //e.preventDefault();
  $.ajax({
      url: "/clocks/delete/"+id,
      type: "DELETE",
      success: function(data, textStatus, jQxhr){
          console.log(data);
          loadAllClocks();
      },
      error: function(jqXhr, textStatus, errorThrown){
          console.log(errorThrown);
          loadAllClocks();
      }
  });
}
function getScreens() {
  return $.ajax({
          url: "/screens/all",
          type: "GET",
          success: function(data, textStatus, jQxhr){
              return data;
          },
          error: function(jqXhr, textStatus, errorThrown){
              console.log(errorThrown);
          }
  });
}
</script>