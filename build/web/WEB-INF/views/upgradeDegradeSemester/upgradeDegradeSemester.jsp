<%-- 
    Document   : upgradeDegradeSemester
    Created on : Jan 16, 2019, 2:28:37 PM
    Author     : ankur.goyal
--%>

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
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Action Type
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="actionType" name="actionType" checked="true" value="up" onclick="Allreset();changeLable('Upgrade');"/>Upgrade
                            </div>
                            <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="actionType" name="actionType" value="DG" onclick="Allreset();changeLable('Degrade');"/> Degrade
                            </div>
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="academicYear" name="academicYear" onchange="resetProgramCode();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${acadYear}" var="list">
                                    <option value="${list}"><c:out value="${list}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" data-live-search="true" data-validation="required" onchange="getBranchCode();">               
                                <option value="">Select</option>
                                <c:forEach items="${programList}" var="list">
                                    <option value="${list[2]}"><c:out value="${list[0]} -- ${list[1]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Branch Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="branch" name="branch" multiple="true" data-live-search="true" onchange="resetSTY();" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
                            <button type="button" class="fa fa-search btn btn-success" onclick="getCurrentSTYNo();"></button>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Current STY Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="currentSTY" name="currentSTY" onchange="clearUPDEsty();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"><span id="lable">Upgrade</span> STY Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> &nbsp;&nbsp;</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <input type="text" id="upDegradeSty" name="upDegradeSty" maxlength="1" readonly="true" data-validation-help="Max Length is 1" data-validation="required" placeholder="Upgrade STY Number" onblur="checkSemester();" class="form-control" data-validation-error-msg="">
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"></label>
                        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6 checkbox-inline">
                            <input type="checkbox" onclick="upgradeBatch();" id="upgradeOrDegrade" name="upgradeOrDegrade" value="Y"/> <span id="lable1">Upgrade</span> Batch / Section and Sub Batch / Sub Section
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getGridData();" class="btn btn-success btn-sm btn-flat"> Load Data</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="col-lg-12" style="margin-top:10px;">
                        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                        <table id="itemList" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                            <thead id="header">
                                <tr class="" >
                                    <th width="1px;"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkAll();"/></th>
                                    <th style="width:30px">Sl.No</th>
                                    <th style="width:100px" >Enrollment No</th>
                                    <th style="width:250px">Name</th>
                                    <th>Program</th>
                                    <th>Branch</th>
                                    <th style="width:100px">Current STY No</th>
                                    <th style="width:100px"><span id="tb">Upgrade</span> STY No</th>
                                    <th>Remarks</th>
                                </tr>
                            </thead> 
                        </table>
                    </div>
                    <div class="col-lg-12 form-group">              
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat" id="save" disabled="true"> Upgrade STY No.</a>
                        </div>
                    </div>
                    <input type="hidden"   name="countval1" id="countval1" value=""/>
                    <input type="hidden" name="checkedCount" id="checkedCount" value=""/>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $("#academicYear").chosen();
    $("#program").chosen();
    $("#branch").chosen();
    $("#currentSTY").chosen();

    var btns = [];
    table1 = $('#itemList').DataTable({
        "lengthMenu": [[-1], ["All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table1, {
                    buttons: btns
                });
                $('#button_wrapper').append(table1.buttons(0, null).container());
            }, 100);

    function changeLable(value) {
        var savebtn = document.getElementById("save");
        var lable = document.getElementById("lable");
        var lable1 = document.getElementById("lable1");
        var tb = document.getElementById("tb");
        savebtn.innerHTML = value + " STY No.";
        lable.innerHTML = value;
        lable1.innerHTML = value;
        tb.innerHTML = value;
        $("#upDegradeSty").attr("placeholder", value + " STY Number");
    }
    function myReset() {
        $("#academicYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#currentSTY").empty();
        $("#currentSTY").val('').trigger("chosen:updated");
        $("#upDegradeSty").val('');
        $('#itemList').DataTable().clear().draw();
        var savebtn = document.getElementById("save");
        var lable = document.getElementById("lable");
        var lable1 = document.getElementById("lable1");
        savebtn.innerHTML = "Upgrade STY No.";
        lable.innerHTML = "Upgrade";
        lable1.innerHTML = "Upgrade";
        $("#upDegradeSty").attr("placeholder", "Upgrade STY Number");
    }
    function Allreset() {
        $("#academicYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#currentSTY").empty();
        $("#currentSTY").val('').trigger("chosen:updated");
        $("#upDegradeSty").val('');
        $('#itemList').DataTable().clear().draw();
    }
    function resetProgramCode() {
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#currentSTY").empty();
        $("#currentSTY").val('').trigger("chosen:updated");
        $("#upDegradeSty").val('');
        $('#itemList').DataTable().clear().draw();
    }
    function resetSTY() {
        $("#currentSTY").empty();
        $("#currentSTY").val('').trigger("chosen:updated");
        $("#upDegradeSty").val('');
        $('#itemList').DataTable().clear().draw();
    }
    function clearUPDEsty() {
        $("#upDegradeSty").val('');
        $('#itemList').DataTable().clear().draw();
    }
    function upgradeBatch() {
        if ($("#upgradeOrDegrade").prop('checked') == true) {
            BootstrapDialog.alert("Do you not want to Update respective STY No. batch while Upgrdation/De Grdation");
        }
    }

    commonButtonMethod(cb);

    function getBranchCode() {
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#currentSTY").empty();
        $("#currentSTY").val('').trigger("chosen:updated");
        var acadYear = $("#academicYear").val();
        var program = $("#program").val();
        if (acadYear != '') {
            if (program != '') {
                $("#branch").empty();
                $.ajax({
                    type: "POST",
                    url: "upgardeDegradeSemester/getBranch",
                    data: '&program=' + $("#program").val(),
                    dataType: "json",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {

                        if (data.branchCode != null && data.branchCode != '')
                        {
                            var str1 = '';
                            $.each(data.branchCode, function (i, item)
                            {
                                str1 += '<option value="' + item[4] + '">' + item[0] + ' -- ' + item[1] + '</option>'
                            });
                            $("#branch").append(str1);
                            $("#branch").trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Branch Not Found..Please Select Another Program..");
                        }
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong.', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select the Program Code..");
            }
        } else {
            $("#program").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select the Academic Year..");
        }
    }

    function getCurrentSTYNo() {
        var acadYear = $("#academicYear").val();
        var program = $("#program").val();
        var branch = $("#branch").val();
        $("#currentSTY").empty();
        if (acadYear != '' && program != '' && branch != '') {
            $.ajax({
                type: "POST",
                url: "upgardeDegradeSemester/getCurrentStyNo",
                data: '&academicYear=' + $("#academicYear").val() + '&program=' + $("#program").val() + '&branch=' + $("#branch").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.styList != null && data.styList != '')
                    {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.styList, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#currentSTY").append(str1);
                        $("#currentSTY").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("No STY No. Found..Please Select Another Branch..");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }

    function checkAll() {
        var count = $("#countval1").val();
        if ($("#chkAll").is(":checked")) {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", true);
            }
        } else {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", false);
            }
        }
    }

    function getGridData() {
        var acadYear = $("#academicYear").val();
        var program = $("#program").val();
        var branch = $("#branch").val();
        var currentSty = $("#currentSTY").val();
        var action = $("input[name='actionType']:checked").val();
        $('#itemList').DataTable().clear().draw();
        $("#countval1").val('');
        var itemChildList = $('#itemList').DataTable();
        if (acadYear != '' && program != '' && branch != '' && currentSty != '' && currentSty != null) {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "upgardeDegradeSemester/getGridData",
                data: '&academicYear=' + $("#academicYear").val() + '&program=' + $("#program").val() + '&branch=' + $("#branch").val() + '&currentSTY=' + $("#currentSTY").val() + '&action=' + action,
                dataType: "json",
                async: true,
                timeout: 15000,
                cache: false,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    var count = 0;
                    if (data.gridData != '') {
                        var j = 1;
                        var str2 = '';
                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '';
                            if (item[8] == null) {
                                str2 = '--';
                            } else {
                                str2 = item[8];
                            }
                            str1 += ' <tr id="' + item[7] + '~@~' + item[5] + '">';
                            str1 += ' <td id="' + item[7] + '~@~' + item[5] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[7] + '~@~' + item[5] + '"/>';
                            str1 += ' </td>';
                            str1 += ' <td>' + (i + 1) + '</td>';
                            str1 += ' <td>' + item[0] + '</td>'; //Enrollment number
                            str1 += ' <td>' + item[1] + '</td>'; //Student name
                            str1 += ' <td>' + item[3] + '</td>'; //Program
                            str1 += ' <td>' + item[4] + '</td>'; //Branch
                            str1 += ' <td>' + item[5] + '</td>'; //Current Sty number
                            str1 += ' <td>' + item[6] + '</td>'; //New Sty Number
                            str1 += ' <td>' + str2 + '</td>'; //Remarks
                            str1 += ' </tr>';
                            count = count + 1;
                            itemChildList.row.add($(str1)).draw();
                            $("#save").removeAttr("disabled");
                        });
                        $("#upDegradeSty").val(data.styNum);
                        $("#countval1").val(count);
                    } else {
                        BootstrapDialog.alert("Data not Available in this STY No., Please Select Another STY No.");
                    }
                    $.unblockUI();
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }

    function saveData() {
        var newSty = $("#upDegradeSty").val();
        var countval1 = $("#countval1").val()
        if (countval1 != '') {
            var checkedStatus = 0;
            if (newSty != '') {
            } else {
                BootstrapDialog.alert("Upgrade / Degrade STY Number can not blank or Zero not Allowed.");
            }
            for (var j = 0; j < countval1; j++) {
                if ($('#chk' + j).is(':checked')) {
                    checkedStatus++;
                }
            }
            $("#checkedCount").val(countval1);
            if (checkedStatus == 0) {
                BootstrapDialog.alert("Please select atleast one check box");
            } else {
                //            $("#ajaxform").submit();
                $.ajax({
                    type: "POST",
                    url: "upgardeDegradeSemester/save",
                    data: $("#ajaxform").serialize(),
                    datatype: "String",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.savaGridData == 'Success') {
                            toastr.success('STY Number Upgraded/ Degraded Successfully', "Success!!");
                            loadForm("upgardeDegradeSemester/list");
                        } else if (data.savaGridData == 'Error') {
                            toastr.error('Form Submission Failed.', "Error!!");
                        } else {
                            BootstrapDialog.alert(data.savaGridData);
                        }
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong.', "Warning!!");
                    }
                });
            }
        } else {
            BootstrapDialog.alert("Data not available in grid..! ");
        }
    }

</script>



