<%-- 
    Document   : departmentSubjectTaggingList
    Created on : Dec 5, 2018, 2:23:36 PM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="col-lg-12" style="margin-top:10px;">
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Department Code:
                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <select class="form-control" id="dept_code" name="dept_code" onchange="getDepartmentMaster();"  data-live-search="true" data-validation="required">               
                    <option value="">Select</option>
                    <c:forEach items="${depart_code}" var="depart_code">
                        <option value="${depart_code[0]}"><c:out value="${depart_code[1]} / ${depart_code[2]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <!--           <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
                <button type="button" class="fa fa-search btn btn-success" ></button>
                </div>-->
    </div>
    <div class="row col-lg-12 form-group" ></div>
    <div class="row col-lg-12 form-group" ></div>
    <div class="row col-lg-12 form-group" ></div>
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th></th>
                <th style="width:60px">Sl.No</th>
                <th style="width:25%">Department</th>
                <th style="width:25%">Subject Code</th>
                <th>Subject Description</th>
                <!--                <th>Department Code</th>
                                <th>Department</th>-->
            </tr>
        </thead>  
    </table>
</div> 
<script>

    $("#dept_code").chosen();
    var val = getValue();
    var link = val == 'N' ? 'DepartmentSubjectTagging/add' : '';
    var cb = {
        add: {
            link: link ,
            name: "Add",
            value: ""
        },
        edit: {
            link: "",
            name: "",
            value: ""
        },
        delete: {
            link: "DepartmentSubjectTagging/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "DepartmentSubjectTagging/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }

    }

    commonButtonMethod(cb);
    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        if ($("#chk1" + index).prop('checked') == true) {
            $("#chk1" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk1" + index).prop("checked", true);
            $("#del_img" + index).hide();
            $("#d_img" + index).show();
        }
    }

    function checkChild(v, i) {
        $("#del_img" + i).hide();
        $("#d_img" + i).show();
    }

    function getDepartmentMaster() {
        $.blockUI();
        var dept_code = ($("#dept_code").val());
        var sec_code = ($("#sec_code").val());
        $('#datatable').DataTable().clear().draw();
        if (dept_code != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "DepartmentSubjectTagging/getDepartmentwise",
                data: '&dept_code=' + $("#dept_code").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var j = 1;
                    var str = '';
                    var str2 = '';
                    var str3 = '';
                    var str4 = '';
                    var str5 = '';
                    var str6 = '';
                    if (data.taggedlist != null && data.taggedlist != '') {
                        $.each(data.taggedlist, function (i, item)
                        {

                            var str1 = '<tr id="' + item[0] + '">';
                            str1 += '<td id="' + item[0] + '~@~' + item[2] + '~@~' + item[4] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[2] + '~@~' + item[4] + '"/>';
                            str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                            str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                            str1 += '  <td>' + j + '</td>';
                            str1 += '  <td>' + item[6] + '</td>';
                            str1 += '  <td>' + item[1] + '</td>';
                            str1 += '  <td>' + item[3] + '</td></tr>';

//                              '</tr>';
                            tablelist.row.add($(str1)).draw();
                            j++;
                        });
                    } else {
                        BootstrapDialog.alert("No Data Found..");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
            setTimeout($.unblockUI, 2000);
        } else {
            $.unblockUI();
            BootstrapDialog.alert("Please Select Required Fields..");
        }
    }


</script>


