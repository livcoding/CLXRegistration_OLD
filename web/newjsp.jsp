<%--
   Document   : newjsp
   Created on : Jul 3, 2019, 4:21:02 PM
   Author     : ashutosh1.kumar
--%>
<%@ include file="include.jsp" %>
<% ResourceBundle resource = ResourceBundle.getBundle("campuslynx");
    String oauth_server_url = resource.getString("oauth_server_url");%>
<input type="text" id="_urls" hidden="true" value="<%=oauth_server_url%>"/>
<script>
    $(document).ready(function () {
        var oauth_server_url = document.getElementById("_urls").value.trim();
        window.location.href = oauth_server_url + "/OAuthServer/logout.do?";
    });
</script>