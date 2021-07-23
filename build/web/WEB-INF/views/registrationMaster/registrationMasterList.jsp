<%-- 
    Document   : registrationMasterList
    Created on : Jan 10, 2019, 12:35:33 PM
    Author     : ashutosh1.kumar
--%>
<%@include file="../mainjstl.jsp"%>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th style="display:none"></th>  
                <th>Sl.No</th>
                <th>Semester Code</th>
                <th>Semester Desc</th>
                <th>Semester From Date</th>
                <th>Semester To Date</th>
                <!--<th>Reg Event From Date</th>-->
                <!--<th>Reg Event To Date</th>-->  
                <th>Attendance From Date</th>
                <th>Attendance To Date</th>
                <th>Deactive</th>
                <th>Lock Reg</th>
            </tr>
        </thead>  
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${data}" var="list">
                <tr id="${list[0]}:${list[1]}">
<!--                    <td id="${list[1]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[1]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                    <td style="display:none"> </td> 
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list[2]}</td>
                    <td>${list[3]}</td>
                    <td>${list[4]}</td>
                    <td>${list[5]}</td>
                    <!--<td>${list[6]}</td>-->
                    <!--<td>${list[7]}</td>-->
                    <td>${list[8]}</td>
                    <td>${list[9]}</td>
                    <c:if test="${list[10]!='Y'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[10]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[11]!='Y'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[11]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                </tr>
                <% i++;%>
            </c:forEach>  
        </tbody>
    </table>
    <button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
</div>
<style>
    #myBtn {
        display: none;
        position: fixed;
        bottom: 20px;
        right: 30px;
        z-index: 99;
        font-size: 18px;
        border: none;
        outline: none;
        background-color: #005384;
        color: white;
        cursor: pointer;
        padding: 15px;
        border-radius: 4px;
    }

    #myBtn:hover {
        background-color: #555;
    }
</style>

<script>
    var val = getValue();
    var link = val == 'N' ? 'registrationMaster/add' : '';
    var cb = {
        add: {
            link: link ,
            name: "Add",
            value: ""
        },
        edit: {
            link: "registrationMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "registrationMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "registrationMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    window.onscroll = function () {
        scrollFunction()
    };
    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            document.getElementById("myBtn").style.display = "block";
        } else {
            document.getElementById("myBtn").style.display = "none";
        }
    }
    function topFunction() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    }
    commonButtonMethod(cb);
    function callChkBox(index) {
        var value = $("#chk" + index).val();
        if ($("#chk" + index).prop('checked') == true) {
            $("#chk" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk" + index).prop("checked", true);
            checkChild(value, index);
        }
    }

    function checkChild(v, i) {
        $.ajax({
            url: "registrationMaster/checkIfChildExist",
            datatype: 'String',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "pk=" + v,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data === "true") {
                    BootstrapDialog.alert("Child exists for this record!");
                    $("#chk" + i).prop("checked", false);
                    $("#chk" + i).attr("disabled", true);
                } else {
                    $("#del_img" + i).hide();
                    $("#d_img" + i).show();
                }
            }
        });
    }
</script>

