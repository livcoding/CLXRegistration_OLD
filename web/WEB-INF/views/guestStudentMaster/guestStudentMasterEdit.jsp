

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
        <ul class="pager wizard">
            <li class="btn button-previous"><a href="#">Previous</a></li>
            <li class="btn button-next"><a href="#">Next</a></li>
        </ul>
        <div class="tab-pane active" id="tab1">
            <%@include file="guestStudentMasterPersonalDetailEdit.jsp"%>
        </div>
        <div class="tab-pane " id="tab2">
            <%@include file="guestStudentMasterAddressEdit.jsp"%>
        </div>
        <div class="tab-pane" id="tab3">
            <%@include file="guestStudentMasterPhotoEdit.jsp"%>
        </div>
        <ul class="pager wizard">
            <li class="btn button-previous"><a href="#">Previous</a></li>
            <li class="btn button-next"><a href="#">Next</a></li>
        </ul>
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
            }
        }
        );
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
                                    var lg =  parseInt(document.getElementById("sphoto").files.length);
                                    if (lg > 0) {
                                        formData.append('file', $('input[type=file]')[0].files[0]);
                                    }
                                    var lt = parseInt(document.getElementById("ssignature").files.length);
                                    if (lt > 0) {
                                        formData.append('sign', $('input[type=file]')[1].files[0]);
                                    }
                                }
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
                                                if (tabIndex != 2) {
                                                    wizard.bootstrapWizard('next');
                                                } else {
                                                    loadForm("guestStudentMaster/list");
                                                }
                                                 toastr.success(Success['update_success'], "Success!!");
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
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
</script>
