<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Docker-LDE</title>
<!-- CSS for Bootstrap -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
  .btn-file {
    position: relative;
    overflow: hidden;
  }
  .btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 100px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    cursor: inherit;
    display: block;
  }
</style>
<script type="text/javascript">
	$(function() {
		$('button[type=submit]').click(function(e) {
			e.preventDefault();
			//Disable submit button
			$(this).prop('disabled',true);
			
			
			var form = document.forms[0];
			var formData = new FormData(form);
				
			// Ajax call for file uploaling
			var ajaxReq = $.ajax({
				url : 'fileUpload',
				type : 'POST',
				data : formData,
				cache : false,
				contentType : false,
				processData : false,
				xhr: function(){
					//Get XmlHttpRequest object
					 var xhr = $.ajaxSettings.xhr() ;
					
					//Set onprogress event handler 
					 xhr.upload.onprogress = function(event){
						var perc = Math.round((event.loaded / event.total) * 100);
						$('#progressBar').text(perc + '%');
						$('#progressBar').css('width',perc + '%');
					 };
					 return xhr ;
				},
				beforeSend: function( xhr ) {
					//Reset alert message and progress bar
					$('#alertMsg').text('');
					$('#progressBar').text('');
					$('#progressBar').css('width','0%');
                }
			});

			// Called on success of file upload
			ajaxReq.done(function(msg) {
				$('#alertMsg').text(msg);
				$('input[type=file]').val('');
				$('button[type=submit]').prop('disabled',false);
			});
			
			// Called on failure of file upload
			ajaxReq.fail(function(jqXHR) {
				$('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
						' - '+jqXHR.statusText+')');
				$('button[type=submit]').prop('disabled',false);
			});
		});
	});
</script>
</head>
<br>
<h2 style="text-align: center"> Docker UI</h2>
</br>
  <div class="container">
    <form action="fileUpload" method="post" enctype="multipart/form-data">
      <div class="form-group">
        <input class="btn btn-primary" type="file" name="file">
      </div>
      <div class="form-group">
        <button class="btn btn-primary" type="submit">Upload</button>
      </div>
    </form>
    <br />
    <div class="progress">
      <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
    </div>
    <div id="alertMsg" style="color: red;font-size: 18px;"></div>
  </div>
</body>
</html>