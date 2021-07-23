<%-- 
    Document   : oldvsNewSubjectList
    Created on : Mar 6, 2019, 12:15:21 PM
    Author     : ankur.goyal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <!--<th width=""></th>-->
                <th width="" >Sl.No</th>
                <th width="" >Old Subject Code</th>
                <th width="" >Old Subject Name</th>
                <th width="" >New Subject Code</th>
                <th width="" >WEF</th>
                <th width="" >Remarks</th>
                <th width="" >Entry Date</th>
                <th width="" >Deactive</th>
            </tr>
        </thead>  
        <tfoot>
            <tr id="filterrow" class="no-print">
                <!--<td></td>-->
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </tfoot>
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${oldVsNewSubject}" var="list">
                <tr id="${list[0]}~@~${list[1]}">
<!--                    <td id="${list[0]}~@~${list[1]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[0]}~@~${list[1]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list[8]}</td>  <!--Old Subject Code-->
                    <td>${list[9]}</td>  <!--Old Subject Name-->
                    <td>${list[10]}</td>  <!--New Subject Code-->
                    <td>${list[3]}</td>  <!--WEF-->
                    <td>${list[4]}</td>  <!--Remarks-->
                    <td>${list[7]}</td>  <!--Entry Date-->
                    <td>${list[5]}</td>  <!--Deactive-->
                </tr>
                <% i++;%>
            </c:forEach>   
        </tbody>
    </table>
</div> 

<script>

    var cb = {
        add: {
            link: "oldVsNewSubject/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "oldVsNewSubject/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "oldVsNewSubject/delete",
            name: "",
            value: ""
        },
        list: {
            link: "oldVsNewSubject/list"
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



