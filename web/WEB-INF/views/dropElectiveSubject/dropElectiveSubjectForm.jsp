<%-- 
    Document   : dropElectiveSubjectForm
    Created on : Jan 19, 2019, 2:17:59 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <div class="box-header with-border bg-navy">
                <h3 class="box-title header_name" id="box-title"></h3>
                <div class="box-tools pull-right">                  
                    <button onclick="javascript:blank();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                </div>
            </div>
            <div class="box-body" >
                <form id="ajaxform" class="form-horizontal">
                    <div class="col-lg-12" style="margin-top:10px;"> 
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Drop Elective: 
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6 radio-inline" style="margin-top: -10px;">
                                    <input type="radio"  id="cgpaBased" name="dropElectiveBased" onclick="clearGrid();" checked="true" value="cgpa"/>CGPA  Based &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="radio" id="subjectBased" name="dropElectiveBased" onclick="clearGrid();" value="subject"/>Selected Subject
                                </div>
                            </div>  
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="regCode" name="regCode" onchange="getSubjectType();" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                        <c:forEach items="${data}" var="reList">
                                            <option value="${reList[0]}"><c:out value="${reList[1]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>  
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Type:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="subjectType" name="subjectType" onchange="getSubjectCode();" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="subjectCode" name="subjectCode" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                                <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6" style="margin-top: -10px;">                                    
                                    <a href="javascript:loadData();" class="btn btn-success fa fa-search"></a>
                                </div>
                            </div>  
                        </div> 
                        <div class="col-lg-12 container">
                            <table id="itemListChoice" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th width="1px;"></th>
                                        <th width="9px;">Sl No</th>                            
                                        <th width="18px;" >Academic Year</th>                             
                                        <th width="18px;" >Program</th>                             
                                        <th width="18px;" >Sty Type</th>                             
                                        <th width="18px;" >Sty No.</th>                             
                                        <th width="18px;" >No of Students</th>  
                                    </tr>
                                </thead>  
                                <tbody>
                                </tbody>
                            </table>
                        </div> 
                        <div class="col-lg-12 form-group">              
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                            </div>
                        </div>
                        <input type="hidden" name="countval" id="countval" value=""/>
                    </div> 
                </form>
            </div>
        </div>
    </div>
</div>
<script>

    $("#regCode").chosen({width: "100%"});
    $("#subjectType").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});
    $('#itemListChoice').DataTable({
        searching: true, paging: true, info: true
    });

    function clearGrid() {
        $('#itemListChoice').DataTable().clear().draw();
    }

    function getSubjectType() {
        $("#subjectType").empty();
        $.ajax({
            url: "dropElectiveSubject/getSubjectType",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#regCode").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.subjecttype, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                });
                $("#subjectType").append(str1);
                $("#subjectType").trigger("chosen:updated");
            }
        });
    }

    function getSubjectCode() {
        $("#subjectCode").empty();
        $.ajax({
            url: "dropElectiveSubject/getSubjectCode",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#regCode").val() + "&subjectType=" + $("#subjectType").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.subjectcode, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#subjectCode").append(str1);
                $("#subjectCode").trigger("chosen:updated");
            }
        });
    }

    function loadData() {
        $('#itemListChoice').DataTable().clear().draw();
        var itemChildList = $('#itemListChoice').DataTable();
        var radioValue = $("input[name='dropElectiveBased']:checked").val();
        if ($("#regCode").val() != '' && $("#subjectType").val() != '' && $("#subjectCode").val() != '') {
            $.ajax({
                url: "dropElectiveSubject/getGridData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCode").val() + "&subjectType=" + $("#subjectType").val() + "&subjectCode=" + $("#subjectCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    var count = new Array();
                    if (data.listdata != null) {
                        $.blockUI();
                        var countt = 1;
                        if (radioValue == "cgpa") {
                            $.each(data.listdata, function (i, item)
                            {
                                var str1 = '';
                                var cou = 0;
                                str1 += ' <tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '">';
                                str1 += ' <td id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '"><input type="hidden" class="case" checked="true" id="chk' + i + '" name="chk' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '"/>';
                                str1 += ' </td>';
                                str1 += ' <td>' + countt + '</td>';
                                str1 += ' <td>' + item[0] + '</td>';
                                str1 += ' <td>' + item[2] + '</td>';
                                str1 += ' <td>' + item[5] + '</td>';
                                str1 += ' <td>' + item[6] + '</td>';
                                str1 += ' <td>' + item[7] + '</td>';
                                str1 += ' </tr>';
                                count.push(cou++);
                                countt++;
                                itemChildList.row.add($(str1)).draw();
                            });
                        } else {
                            $.each(data.listdata, function (i, item)
                            {
                                var str1 = '';
                                var cou = 0;
                                str1 += ' <tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '">';
                                str1 += ' <td id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[6] + '~@~' + item[8] + '"/>';
                                str1 += ' </td>';
                                str1 += ' <td>' + countt + '</td>';
                                str1 += ' <td>' + item[0] + '</td>';
                                str1 += ' <td>' + item[2] + '</td>';
                                str1 += ' <td>' + item[5] + '</td>';
                                str1 += ' <td>' + item[6] + '</td>';
                                str1 += ' <td>' + item[7] + '</td>';
                                str1 += ' </tr>';
                                count.push(cou++);
                                countt++;
                                itemChildList.row.add($(str1)).draw();
                            });
                        }
                        $("#countval").val(count);
                        setTimeout($.unblockUI, 1000);
                    } else {
                        BootstrapDialog.alert("Data Not Found ");
                    }
                }
            });

        } else {
            BootstrapDialog.alert("Please Select Semester Code, Subject Type And Subject Code !");
        }
    }

    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.ajax({
                                    type: "POST",
                                    url: "dropElectiveSubject/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved.', "Success!!");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function saveData() {
        var radioValue = $("input[name='dropElectiveBased']:checked").val();
        if ($("#regCode").val() != '' && $("#subjectType").val() != '' && $("#subjectCode").val() != '') {
            if (radioValue == 'cgpa') {
                $("#ajaxform").submit();
            } else {
                var countval1 = $("#countval").val().split(",");
                var checkedStatus = 0;
                for (var j = 0; j < countval1.length; j++) {
                    if ($('#chk' + j).is(':checked')) {
                        checkedStatus++;
                    }
                }
                if (checkedStatus == 0) {
                    BootstrapDialog.alert("Please select atleast one check box");
                } else {
                    $("#ajaxform").submit();
                }
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code, Subject Type And Subject Code !");
        }
    }

</script>
