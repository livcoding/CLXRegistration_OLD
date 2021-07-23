<%-- 
    Document   : registrationMasterAdd
    Created on : Jan 10, 2019, 4:00:01 PM
    Author     : ashutosh1.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<div id="rootwizard" class="col-lg-12">
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container col-lg-12" >
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab1"  data-toggle="tab">Personal Information</a></li>
                    <li><a href="#tab2" data-toggle="tab">Address Detail</a></li>
                    <!--                   <li><a href="#tab3" data-toggle="tab">Photo/Signature Upload</a></li>-->
                </ul>
            </div>
        </div>
    </div>
    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <%@include file="guestStudentMasterPersonalDetails.jsp"%>
        </div>
        <div class="tab-pane " id="tab2">
            <%@include file="guestStudentMasterAddress.jsp"%>
        </div>
        <div class="tab-pane" id="tab3">
            <%@include file="guestStudentMasterPhoto.jsp"%>
        </div>
        <!--        <ul class="pager wizard">
                    <li class="btn button-previous"><a href="#">Previous</a></li>
                    <li class="btn button-next"><a href="#">Next</a></li>
                </ul>-->
    </div>
    <input type="hidden" id='form_id' class="">   <input type="hidden" class="" id='url'>
    <input type="hidden" id='tabNo' class="">   <input type="hidden" class="" id='tVal'>
</div> 


<script>
    var wizard;
    $(document).ready(function () {
        wizard = $('#rootwizard').bootstrapWizard({'nextSelector': '.button-next', 'previousSelector': '.button-previous', onTabClick: function (tab, navigation, index) {
                return false;
            }, onNext: function (tab, navigation, index) {
                var current = parseInt(index) + 1;
                if ($("#tVal").val() != '') {
                    var pk = $("#tVal").val();
                    $("#student_pk" + current).val('');
                    $("#student_pk" + current).val(pk);
                } else {
                    BootstrapDialog.alert("Please fill the student personal form first !!");
                    return false;
                }
            }
        });
        $('#rootwizard').bootstrapWizard();
    });
    function setFormIdAndUrl(id, url, tabNo) {
        $("#form_id").val(id);
        $("#url").val(url);
        $("#tabNo").val(tabNo);
        $("#ajaxform_tab" + tabNo).submit();
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
                                var tabIndex = $("#tabNo").val();
                                var formData = new FormData();
                                var url = $("#url").val();
                                if (tabIndex != 3) {
                                    var form = document.getElementById($("#form_id").val());
                                    formData = new FormData(form);
                                } else {
                                    formData.append('student_pk3', $('#student_pk3').val());
                                    var lg = parseInt(document.getElementById("sphoto").files.length);
                                    if (lg > 0) {
                                        formData.append('file', $('input[type=file]')[0].files[0]);
                                    }
                                    var lt = parseInt(document.getElementById("ssignature").files.length);
                                    if (lt > 0) {
                                        formData.append('sign', $('input[type=file]')[1].files[0]);
                                    }
                                }

//                                if (tabIndex == 1 || tabIndex != 1 && $("#tVal").val() != '') {
                                $.ajax({
                                    type: "POST",
                                    url: url,
                                    data: formData,
                                    dataType: "json",
                                    async: false,
                                    processData: false,
                                    contentType: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data.status === 'Success') {
                                            var tabCount = 1;
                                            tabCount = parseInt(tabIndex) + 1;
                                            if (tabIndex == 1) {
                                                $("#tVal").val(data.guestStudentId);
                                                $("#student_pk" + tabCount).val(data.guestStudentId);
                                            } else if (tabIndex > 1 && tabIndex < 3) {
                                                var stdPk = $("#tVal").val();
                                                $("#student_pk" + tabCount).val(stdPk);
                                            }
                                            if (tabIndex != 2) {
                                                wizard.bootstrapWizard('next');
                                            } else {
                                                loadForm("guestStudentMaster/list");
                                            }
                                            toastr.success(Success['save_success'], "Success!!");
                                        } else if (data.status === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
//                                } else {
//                                    BootstrapDialog.alert("Please submit the personal form first !!");
//                                }
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

</script>
