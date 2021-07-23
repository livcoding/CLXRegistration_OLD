 <%@ include file="include.jsp" %>
 
<script>

    $(document).ready(function () {
        var url = readQueryString();
        if (url != null) {
            var array = url.split("=");
            var tokenName = array[0].toString();
            var tokenValue = array[1].toString();

            $.ajax({
                type: 'POST',
                timeout: 120000,
                url: 'authTokenValidate',
                datatype: "String",
                data: '&tokenName=' + tokenName + '&tokenValue=' + tokenValue,
                success: function (res) {
                    var response=res.split("~@~");
                    if(response[0]=="success")
                    {
                      BootstrapDialog.alert(response[1]);
                        window.location = "loadPopup";  
//                      window.location = "loadDashboard";  
                    }else{
                        BootstrapDialog.alert(response[1]);
                    }
                    

                },
                error: function () {
                    BootstrapDialog.alert("error");
                }
            });
        }

    });
    function  readQueryString() {
        var browserUrl = window.location.href.toString();
        var contentToken = browserUrl.split("#");
        if (contentToken[1] != null) {
            var arrayTokens = contentToken[1].toString().split("&");
            return arrayTokens[0].toString();
        } else {
            return "";
        }
    }

</script>
