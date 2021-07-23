<%-- 
    Document   : RegistrationSubjectGroupList
    Created on : 19 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="semesterCode" name="semesterCode" onchange="getSubjectCode();" data-validation="required">
                    <option value="">Select</option>
                    <c:forEach items="${semCode}" var="semCode">
                        <option value="${semCode[0]}"><c:out value="${semCode[1]}"/> / <c:out value="${semCode[2]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="subjectCode" onchange="getGridDate();" name="subjectCode" data-validation="required">
                    <option value="">Select</option>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
                    <th></th>
                    <th>Slno.</th>
                    <th>Component</th>
                    <th>Coordinator Name(Code)</th>
                    <th>Coordinator Type</th>
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Academic Year</th>
                    <th>Program</th>
                    <th>Section</th>
                    <th>Sub Section</th>
                    <th>STY No.</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script>

    $("#semesterCode").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});

    var cb = {
        add: {
            link: "batchWiseCoordinator/add",
            name: "Add / Edit",
            value: ""
        },
        edit: {
            link: "batchWiseCoordinator/edit",
            name: "",
            value: ""
        },
        delete: {
            link: "batchWiseCoordinator/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "batchWiseCoordinator/list"
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
    
    function getSubjectCode() {
        $("#subjectCode").empty();
        $.ajax({
            url: "batchWiseCoordinator/getSubjectCode",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#semesterCode").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.subjectCode, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[2] + ' (' + item[1] + ')</option>'
                });
                $("#subjectCode").append(str1);
                $("#subjectCode").trigger("chosen:updated");
            }
        });
    }


    function getGridDate() {
        $('#datatable').DataTable().clear();
        var tablelist = $('#datatable').DataTable();
        var subjectCode = $("#subjectCode").val();
        if (subjectCode != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "batchWiseCoordinator/getGridDate",
                data: '&subjectid=' + $("#subjectCode").val() + '&regId=' + $("#semesterCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.gridData != '' && data.gridData != null) {
                        $('#datatable').DataTable().clear();
                        var count = 1;
                        $.each(data.gridData, function (i, item)
                        {
                            var fd='--';
                            var td='--';
                            if(item[9]!=null){
                                fd=item[9];
                            }
                            if(item[10]!=null){
                                td=item[10];
                            }
                            var str1 = '<tr id="">';
                            str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[3] + '~@~' + item[4] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[3] + '~@~' + item[4] + '"/>';
                            str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                            str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                            str1 += '  <td>' + count + '</td>'; //Sno.
                            str1 += '  <td>' + item[5] + '</td>'; //component
                            str1 += '  <td>' + item[6] + ' (' + item[7] + ')</td>'; //Emp Name Code
                            str1 += '  <td>' + item[8] + '</td>'; //coordinator Type
                            str1 += '  <td>' + fd + '</td>'; //FromDate
                            str1 += '  <td>' + td  + '</td>'; //ToDate
                            str1 += '  <td>' + item[11]+ '</td>'; //academic Year
                            str1 += '  <td>' + item[12] + '</td>'; //program
                            str1 += '  <td>' + item[13] + '</td>'; //section
                            str1 += '  <td>' + item[14] + '</td>'; //subsection
                            str1 += '  <td>' + item[15] + '</td>'; //styno
                            tablelist.row.add($(str1)).draw();
                            count++;
                        });
                    } else {
                        $('#datatable').DataTable().clear().draw();
                        BootstrapDialog.alert("No Data found for this Subject Code");
                        $("#subjectCode").val('').trigger("chosen:updated");
                    }
                    setTimeout($.unblockUI, 1000);

                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Subject Code..");
        }
    }
</script>