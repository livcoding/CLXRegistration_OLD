<%-- 
    Document   : registrationInstructionUploadList
    Created on : Jan 3, 2020, 10:53:10 AM
    Author     : priyanka.tyagi
--%>
<style>
    #tablebody .fa-file{
        font-size:35px;
        color:lightgray;
    }
    #tablebody .fa-file-pdf-o{
        font-size:35px;
    }
</style>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" name="ajaxform2"  class="form-horizontal" enctype="multipart/form-data" >
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="regCode" name="regCode" onchange="getSubjectCode();" data-validation="required">
                    <option value="">Select</option>
                    <c:forEach items="${dto}" var="dto">
                        <option value="${dto.id.instituteid}~@~${dto.id.registrationid}"><c:out value="${dto.registrationcode}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Instruction Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="insType" name="insType" data-validation="required" >
                    <option value="">Select</option>
                    <option value="C"> Course Registration </option>
                    <option value="S"> Supplementary Registration </option>
                    <option value="E"> Elective Registration </option>
                    <option value="G"> GIP Registration </option>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12" >
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Upload Instruction:
                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-9"> 
                <input type="file" id="uploadInstruction" name="uploadInstruction"  data-validation-optional="true" class="" data-icon="false">
                <!--&nbsp;<input type="checkbox" id="confPhoto" name="confPhoto" value="1">Confirm Photo Upload-->
            </div>
        </div>
    </div>
    <div class="col-lg-12 form-group">              
        <div style="margin-top: 3px;margin-right: 10px;" class="form-group pull-right">
            <a class="btn btn-success btn-sm btn-flat" onclick="saveData();"> Save</a>
            <!--            <button class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
                        <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>-->
        </div>
    </div>
</form>
<div class="col-lg-12" >
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th>Slno.</th>
                <th>Semester Code</th>
                <th>Semester Description</th>
                <th>Course Registration</th>
                <th>Elective Registration</th>
                <th>GIP Registration</th>
                <th>Supplementary Registration</th>
            </tr>
        </thead>
        <tbody id="tablebody">
        </tbody>
    </table>
</div>
<script>
    $(document).ready(function () {
        check();
        $('[data-toggle="tooltip"]').tooltip();
    });

    $("#regCode").chosen({width: "100%"});
    $("#insType").chosen({width: "100%"});
    var cb = {
        add: {
            link: "",
            name: "",
            value: ""
        },
        edit: {
            link: "",
            name: "",
            value: ""
        },
        delete: {
            link: "",
            name: "",
            value: ""
        },
        list: {
            link: "registrationInstructionUpload/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);
    function saveRegInstructionData() {
        if ($('#uploadInstruction').val() != '') {
            var ext = $('#uploadInstruction').val().split('.').pop().toLowerCase();
            if ($.inArray(ext, ['jpg', 'jpeg']) == -1) {
                BootstrapDialog.alert('Invalid extension! Only jpg and jpeg is allowed here.');
                $('#uploadInstruction').val('');
            } else {
                $("#ajaxform_tab12").submit();
            }
        } else {
            BootstrapDialog.alert("Please select any photo!");
        }
    }

    function saveData() {
        var registrationid = $("#regCode").val().split('~@~')[1];
        var instructionType = $("#insType").val();
        var instituteid = $("#regCode").val().split('~@~')[0];
        if ($("#regCode").val() != '') {
            if (instructionType != '') {
                var data = registrationid + "~@~" + instructionType + "~@~" + instituteid;
                //Use  processRequest(); method of BulkPhotoUploadServlet then getRequestReponse(); method of RegistrationInstructionUploadService Service
                $('#ajaxform2').attr('action', 'BulkPhotoUploadServlet?refor=RegistrationInstructionUpload&data=' + data + '');
                var imagefile = $('#uploadInstruction').val();
                if (imagefile != '') {
                    var ext = imagefile.split('.').pop().toLowerCase();
                    if ($.inArray(ext, ['pdf']) == -1) {
                        BootstrapDialog.alert('Invalid extension! Only pdf is allowed here.');
                    } else {
                        $("#ajaxform2").submit();
                        toastr.success(Success['save_success'], "Success!!");
                        setTimeout(function () {
                            loadForm("registrationInstructionUpload/list");
                        }, 1200);
                    }
                } else {
                    BootstrapDialog.alert("Please Attached Instruction File");
                }
            } else {
                BootstrapDialog.alert("Please Select Instruction Type");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code");
        }
    }
    function check() {
        $("#tablebody").empty();
        $.ajax({
            type: "POST",
            url: "registrationInstructionUpload/check",
            data: '&acad_year=0',
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str = '';
                var count = 1;
                $.each(data.griddata, function (i, item)
                {
                    var course = '';
                    var elective = '';
                    var gip = '';
                    var supplementry = '';
                    str += '<tr>';
                    str += '<td align="center">' + count + '</td>';
                    str += ' <td>' + item[0] + '</td>';
                    str += ' <td>' + item[1] + '</td>';
                    if (item[2] != null) {
                        course += '<a href="data:application/pdf;base64,' + item[2] + '" download="CounrseInstruction.pdf" class="fa fa-file-pdf-o" ></a>';
                    } else {
                        course += '<a class="fa fa-file"  data-toggle = "tooltip" title = "No File Found" / ></a>';
                    }
                    if (item[3] != null) {
                        elective += '<a href="data:application/pdf;base64,' + item[3] + '" download="CounrseInstruction.pdf" class="fa fa-file-pdf-o" ></a>';
                    } else {
                        elective += '<a class="fa fa-file" data-toggle = "tooltip" title = "No File Found" / ></a>';
                    }
                    if (item[4] != null) {
                        gip += '<a href="data:application/pdf;base64,' + item[4] + '" download="CounrseInstruction.pdf" class="fa fa-file-pdf-o" ></a>';
                    } else {
                        gip += '<a class="fa fa-file" data-toggle = "tooltip" title = "No File Found" / ></a>';
                    }
                    if (item[5] != null) {
                        supplementry += '<a href="data:application/pdf;base64,' + item[5] + '" download="CounrseInstruction.pdf" class="fa fa-file-pdf-o" ></a>';
                    } else {
                        supplementry += '<a class="fa fa-file" data-toggle = "tooltip" title = "No File Found" / ></a>';
                    }
                    str += ' <td>' + course + '</td>';
                    str += ' <td>' + elective + '</td>';
                    str += ' <td>' + gip + '</td>';
                    str += ' <td>' + supplementry + '</td>';
                    str += '</tr>';
                    count++;
                });
                $("#tablebody").append(str);
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
</script>
