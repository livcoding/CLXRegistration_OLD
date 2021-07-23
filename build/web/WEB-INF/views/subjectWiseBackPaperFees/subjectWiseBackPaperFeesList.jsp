<%-- 
    Document   : SubjectWiseBackPaperFeesList
    Created on : 13 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="semesterCode" name="semesterCode" onchange="getGridDate();" data-validation="required">
                <option value="">Select</option>
                <c:forEach items="${semCode}" var="list">
                    <option value="${list[0]}"><c:out value="${list[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
                    <th style="display:none"></th>
                    <th>Sl.No</th>
                    <th>Semester Code</th>
                    <th>Subject Code</th>
                    <th>Subject Description</th>
                    <th>Back Paper Fee Rs.</th>
                </tr>
            </thead>  
            <tbody>
            </tbody>
        </table>
    </div>
</form> 
<script>

    $("#semesterCode").chosen({width: "100%"});
    $("#department").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});
    $("#departments").chosen({width: "100%"});

    var cb = {
        add: {
            link: "subjectWiseBackPaperFees/add",
            name: "Add / Edit",
            value: ""
        },
        edit: {
            link: "subjectWiseBackPaperFees/edit",
            name: "",
            value: ""
        },
        delete: {
            link: "subjectWiseBackPaperFees/delete",
            name: "",
            value: ""
        },
        list: {
            link: "subjectWiseBackPaperFees/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);
    
    function getGridDate() {
        $('#datatable').DataTable().clear();
        var tablelist = $('#datatable').DataTable();
        var semesterCode = $("#semesterCode").val();
        if (semesterCode != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "subjectWiseBackPaperFees/getGridDate",
                data: '&regid=' + $("#semesterCode").val(),
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
                        var semesterPattern = ''
                        var semester = ''
                        var deactive = ''

                        $.each(data.gridData, function (i, item)
                        {
//                            var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '">';
                            var str1 = '<tr >';
                            str1 += '  <td style="display:none"></td>'; //Sno.
                            str1 += '  <td>' + count + '</td>'; //Sno.
                            str1 += '  <td>' + item[3] + '</td>'; //SemesterCode
                            str1 += '  <td>' + item[4] + '</td>'; //SubjectCode
                            str1 += '  <td>' + item[5] + '</td>'; //SubjectDesc
                            str1 += '  <td>' + item[6] + '</td></tr>'; //BackPaperFees
                            tablelist.row.add($(str1)).draw();
                            count++;
                        });
                    } else {
                        $('#datatable').DataTable().clear().draw();
                        var semesterCode = $("#semesterCode").val();
                        BootstrapDialog.alert("No Data Found For Semester Code");
                        $("#semesterCode").val('').trigger("chosen:updated");
                    }
                    setTimeout($.unblockUI, 1000);

                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }

</script>