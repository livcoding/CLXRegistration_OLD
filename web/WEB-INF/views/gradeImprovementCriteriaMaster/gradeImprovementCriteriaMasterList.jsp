<%-- 
    Document   : gradeImprovementCriteriaMasterList
    Created on : Mar 12, 2019, 12:17:02 PM
    Author     : campus.trainee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th style="display:none"></th>
                <th>Sl.No</th>
                <th>Program Code</th>
                <th>Program Desc</th>
                <th>CGPA From</th>
                <th>CGPA To</th>
                <th>Applicable Grades</th>
            </tr>
        </thead>  
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${setUPGIP}" var="list">
                <tr id="${list[6]}~@~${list[0]}">
<!--                    <td id="${list[6]}~@~${list[0]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[6]}~@~${list[0]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                <td style="display:none"> </td> 
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list[1]}</td>
                    <td>${list[2]}</td>
                    <td>${list[3]}</td>
                    <td>${list[4]}</td>
                    <td>${list[5]}</td>
                    <c:if test="${list[5]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[5]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                </tr>
                <% i++;%>
            </c:forEach>  
        </tbody>
    </table>
</div> 

<script>
    var cb = {
        add: {
            link: "gradeImprovementCriteriaMaster/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "gradeImprovementCriteriaMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "gradeImprovementCriteriaMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "gradeImprovementCriteriaMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
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
            $("#del_img" + index).hide();
            $("#d_img" + index).show();
        }
    }

</script>

