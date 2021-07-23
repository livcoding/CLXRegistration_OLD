<%-- 
    Document   : CreditWiseBackPaperFeesAdd
    Created on : 11 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                 
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="semesterCode" name="semesterCode" onchange="getSubjectCode();clearCodeComponent();" data-validation="required">
                    <option value="">Select</option>
                    <c:forEach items="${semCode}" var="semCode">
                        <option value="${semCode[0]}"><c:out value="${semCode[1]}"/> / <c:out value="${semCode[2]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="subjectCode" name="subjectCode" onchange="clearSubjectComponent();" data-validation="required">
                    <option value="">Select</option>
                </select>
            </div>
        </div>
    </div>                
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Component<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="subjectComp"  onchange="getAddGridDate();clearTable();" name="subjectComp" data-validation="required">
                    <option value="">Select</option>
                    <c:forEach items="${subjectComp}" var="subjectComp">
                        <option value="${subjectComp[0]}"><c:out value="${subjectComp[1]}"/> / <c:out value="${subjectComp[2]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>             
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Staff Selection:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="radio" name="staffselection" id="staffselection1" value="TTBased" onclick="hideStaff(); getStaffCode(this.value);" >Teaching Load/Time Table Based <br>
                <input type="radio" name="staffselection" id="staffselection2" value="" onclick="showStaff(); clearStaff();">Others <br>
                <div id="other" style="display:none;">
                    &nbsp; &nbsp; &nbsp; <input type="radio" name="staffselection" id="staffselection3" onclick="getStaffCode(this.value);" value="TEACHING">Teaching <br>
                    &nbsp; &nbsp; &nbsp; <input type="radio" name="staffselection" id="staffselection4" onclick="getStaffCode(this.value);" value="NTEACHING">Non Teaching<br>
                    &nbsp; &nbsp; &nbsp; <input type="radio" name="staffselection" id="staffselection5" onclick="getStaffCode(this.value);" value="ALL">ALL
                </div>
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Staff Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="staffCode" name="staffCode" data-validation="required">
                    <option value="">Select</option>
                </select>
            </div>
        </div>
    </div>           
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="coordinatorType" multiple="true" onchange="allowSave();" name="coordinatorType" data-validation="required">
                    <c:forEach items="${coordinatorType}" var="coordinatorType">
                        <option value="${coordinatorType[0]}"><c:out value="${coordinatorType[2]}"/> / <c:out value="${coordinatorType[3]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group">
        <div class="col-lg-6 form-group">
            <label style="text-transform: capitalize;"class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">With Effect From Date:
                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <div class='input-group date' id='fromDate'>
                    <input type='text' name="fromDate"id="fromDate" onblur="checkToDate();" class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>          
            </div>
        </div> 
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">To Date:
                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <div class='input-group date' id='toDate'>
                    <input type='text' name="toDate"  id="toDate" onblur="checkFromDate();" class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>          
            </div>
        </div> 
    </div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <p style="color:red" id="hint"><b><u>Hint:</u></b> &nbsp; <span style="color:green">Green coloured records are already saved</span></p>
        <table id="datatable1" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header1">
                <tr class="" >
                    <th></th>
                    <th style="width:40px;">Slno.</th>
                    <th style="width:80px;">Component</th>
                    <th style="width:100px;">Academic Year</th>
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
    <div class="row col-lg-12">
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save/Update</a>
            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
        </div>
    </div>
    <input type="hidden" id="totalCount" name="totalCount"value="">
</form>
<input type="hidden"  id="checkcheckbox" name="checkcheckbox" value="">
<script>


    $("#semesterCode").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});
    $("#subjectComp").chosen({width: "100%"});
    $("#staffCode").chosen({width: "100%"});
    $("#coordinatorType").chosen({width: "100%"});
    $(function () {
        $('#regAllowDate').datetimepicker({format: 'DD/MM/YYYY'});
        $('#fromDate').datetimepicker({format: 'DD/MM/YYYY'});
        $('#toDate').datetimepicker({format: 'DD/MM/YYYY', useCurrent: false});
        $("#fromDate").on("dp.change", function (e) {
            $('#toDate').data("DateTimePicker").minDate(e.date);
        });
        $("#toDate").on("dp.change", function (e) {
            $('#fromDate').data("DateTimePicker").maxDate(e.date);
        });
        $('#datatable1').DataTable().clear().draw();
    });
    function clearCodeComponent() {
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subjectComp").val('').trigger("chosen:updated");
        $('#datatable1').DataTable().clear().draw();
    }
    function clearSubjectComponent() {
        $("#subjectComp").val('').trigger("chosen:updated");
        $('#datatable1').DataTable().clear().draw();
    }
    function clearStaff() {
        $("#staffCode").empty();
        $("#staffCode").val('').trigger("chosen:updated");
    }
    function myReset() {
        $("#semesterCode").val('').trigger("chosen:updated");
        $("#subjectCode").empty();
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subjectComp").val('').trigger("chosen:updated");
        $("#staffCode").empty();
        $("#staffCode").val('').trigger("chosen:updated");
        $("#coordinatorType").val('').trigger("chosen:updated");
        $('#other').attr("style", "display:none");
        $("#checkcheckbox").val('');
        $('#datatable1').DataTable().clear().draw();
    }
    function hideStaff() {
        $('#other').attr("style", "display:none");
    }
    function showStaff() {
        $('#other').removeAttr("style");
    }

    function allowSave() {
        var t = $("#totalCount").val();
        if ($("#coordinatorType").val() != '') {
            var save = false;
            for (var i = 1; i < t; i++) {
                if ($("#chk" + i + "").is(":checked")) {
                    save = true;
                }
            }
            if (save) {
                $("#checkcheckbox").val('');
            } else {
                $("#checkcheckbox").val("0");
            }
        } else {
            $("#checkcheckbox").val('');
        }
    }
    function getChekedValue(index) {
        if ($("#chk" + index).is(":checked")) {
            $("#checkcheckbox").val('');
        } else {
            $("#checkcheckbox").val("0");
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
    function getStaffCode(staffType) {
        $("#staffCode").empty();
        var regid = $("#semesterCode").val();
        var subid = $("#subjectCode").val();
        if (regid != '') {
            if (subid != '') {
                $.ajax({
                    url: "batchWiseCoordinator/getStaffCode",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: "regId=" + $("#semesterCode").val() + "&subjectId=" + $("#subjectCode").val() + "&staffType=" + staffType,
                    error: (function (response) {
                        alert('Server Error' + response);
                    }),
                    success: function (data) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.staffCode, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[2] + ' (' + item[1] + ')</option>'
                        });
                        $("#staffCode").append(str1);
                        $("#staffCode").trigger("chosen:updated");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Subject Code First!");
                $("#staffselection1").prop("checked", false);
                $("#staffselection2").prop("checked", false);
                $("#staffselection3").prop("checked", false);
                $("#staffselection4").prop("checked", false);
                $("#staffselection5").prop("checked", false);
                $('#other').attr("style", "display:none")
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code First!");
            $("#staffselection1").prop("checked", false);
            $("#staffselection2").prop("checked", false);
            $("#staffselection3").prop("checked", false);
            $("#staffselection4").prop("checked", false);
            $("#staffselection5").prop("checked", false);
            $('#other').attr("style", "display:none")
        }
    }

    function getAddGridDate() {
        $('#datatable1').DataTable().clear();
        var tablelist = $('#datatable1').DataTable();
        var semesterCode = $("#semesterCode").val();
        var subjectCode = $("#subjectCode").val();
        var subjectComp = $("#subjectComp").val();
        if (semesterCode != '') {
            if (subjectCode != '') {
                if (subjectComp != '') {
                    $.blockUI();
                    $.ajax({
                        type: "POST",
                        url: "batchWiseCoordinator/getAddGridData",
                        data: '&registrationid=' + $("#semesterCode").val() + '&subjectid=' + $("#subjectCode").val() + '&subjectcomponentid=' + $("#subjectComp").val(),
                        dataType: "json",
                        async: false,
                        timeout: 5000,
                        cache: false,
                        beforeSend: function (xhr, options) {
                            return true;
                        },
                        success: function (data) {
                            if (data.addGridData != '' && data.addGridData != null) {
                                $('#datatable1').DataTable().clear();
                                var count = 1;
                                $.each(data.addGridData, function (i, item)
                                {
                                    if (item[8] > 0) {
                                        var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '" style="color:green;font-weight:bold">';
                                    } else {
                                        var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[3] + '" style="font-weight:bold">';
                                    }
                                    str1 += '  <td ><input type="checkbox" id="chk' + i + '" name="chk' + i + '" value="' + item[1] + '" onclick="getChekedValue(' + i + ')"/></td>';
                                    str1 += '  <td>' + count + '</td>'; //Sno.
                                    str1 += '  <td>' + item[2] + '</td>'; //component
                                    str1 += '  <td>' + item[3] + '</td>'; //academic Year
                                    str1 += '  <td>' + item[4] + '</td>'; //program
                                    str1 += '  <td>' + item[5] + '</td>'; //section
                                    str1 += '  <td>' + item[6] + '</td>'; //subsection
                                    str1 += '  <td>' + item[7] + '</td>'; //styno
                                    tablelist.row.add($(str1)).draw();
                                    count++;
                                });
                            } else {
                                $('#datatable1').DataTable().clear().draw();
                                BootstrapDialog.alert("No Data found for this Selected Data");
                                $("#subjectComp").val('').trigger("chosen:updated");
                            }
                            setTimeout($.unblockUI, 1000);
                            $('#totalCount').val(count - 1);
                        },
                        error: function (response) {
                            toastr.warning('Something Wrong...............', "Warning!!");
                            setTimeout($.unblockUI, 1000);
                        }
                    });
                } else {
                    BootstrapDialog.alert("Please Select Subject Component First!");
                }
            } else {
                BootstrapDialog.alert("Please Select Subject Code First!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code First!");
        }
    }

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "batchWiseCoordinator/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            getAddGridDate();
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                        setTimeout($.unblockUI, 1000);
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
    function saveData() {
        var checkcheckbox = $("#checkcheckbox").val();
        if (checkcheckbox == '') {
            $("#ajaxform").submit();
        } else {
            BootstrapDialog.alert("Please Select atleast one checkbox");
        }
    }

</script>