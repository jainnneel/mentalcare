$( document ).ready(function() {
		
		let i=0;
		$('#postcontent').click(function(){
		if(i==0){
			$('#postform').removeClass('hide');
			i=1;
		}else{
			$('#postform').addClass('hide');
			i=0;
			}
		})
		
	    $("#postform").submit(function(event) {
	    	event.preventDefault();
	    	ajaxPost();
	  });
	    
	    
	    function ajaxPost(){
	 
	      var formData = {
	        title : $("#title").val(),
	        content :  $("#content").val()
	      }
	      
	      $.ajax({
	      type : "POST",
	      contentType:"application/json",
	      url :  "post",
	      data : JSON.stringify(formData),
	      dataType : 'json',
	      success : function(result) {
	        if(result.status == "done"){
	        	console.log(result);
	        	location.reload();
	        }else if(result.status == "notdone"){
	        	 document.querySelector('.title').textContent="";
	    		 document.querySelector('.content').textContent="";
	        	 $.each(result.errorMessages, function (key, value) {
	        	if(key=="title")
	        	{ document.querySelector('.title').textContent=value;}
	        	else if(key=="content")
	        	 {document.querySelector('.content').textContent=value;}
	        	  });
	        	 console.log(result.errorMessages)
	        }else if(result.status == "not"){
	        	window.location="/login";
	        }
	      },
	      error : function(e) {
	        window.location="/login";
	        console.log("ERROR: ", e);
	      }
	    });
	}
})