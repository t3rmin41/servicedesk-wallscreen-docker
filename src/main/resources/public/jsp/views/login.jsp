<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
      <div style="width:10%; margin:0 auto;">
        <a href="/">Go to index page</a>
      </div>
      <div>
        <form id="loginform">
          <center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Login Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="username" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="" /></td>
                    </tr>
                    <tr>
                        <td colspan=2 align="center"><input type="submit" value="Login" /></td>
                    </tr>
                </tbody>
              </table>
            </center>
          </form>
        </div>
    </body>
</html>
<script>
$(document).ready(function(){
    console.log("Login page is ready!");
    
    $("#loginform").submit(function(e) {
        //prevent Default functionality
        e.preventDefault();

        //do your own request an handle the results
        $.ajax({
                url: "/users/login",
                type: "POST",
                data: $("#loginform").serialize(),
                success: function(data, textStatus, jQxhr){
                    console.log(data);
                    window.location.href = data.requestedURL;
                },
                error: function(jqXhr, textStatus, errorThrown){
                    console.log(errorThrown);
                }
        });
    });
    /**/
});
</script>