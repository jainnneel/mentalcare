const showcommment = (pid) => {
			
			let button = document.querySelector('#show-'+pid);
			let comments = document.querySelector('#div-'+pid);
			
			if(button.innerHTML == "Show Comment"){
				comments.classList.remove('hide');
				button.innerHTML = "hide Comment";
			}else{
				comments.classList.add('hide');
				button.innerHTML = "Show Comment";
			}
			commentsreq(pid);
		}
		
		const commentsreq = (pid) => {
			$.ajax({
			      type : "GET",
			      url : "/post/getallcomment/"+pid,
			      success: function(result){
			    	  $('#comment-'+pid).html(result);
			      },
			      error : function(e) {
			        alert("ERROR: ", e);
			        console.log("ERROR: ", e);
			      }
			    });  
		    }
		
		$('form[id*="commentForm-"]').on("submit",(e)=>{
			 event.preventDefault();
			 
			 
			 
			 let pid =  $(e.target).attr("id").split("-")[1]
			 if($("#cmtreview-"+pid).val().trim()==""){
				 alert("empty not allow")
			 }else{
				 var formData = {
			        review : $("#cmtreview-"+pid).val().trim()
			      }
			 	console.log(formData)
			 
			      $.ajax({
			    	  type : "POST",
			          contentType:"application/x-www-form-urlencoded",
			     	  url :  '/post/'+pid+'/comment',
			     	  data : formData,
			     	  dataType : 'json',
			     	  success : function(result) {
			     		 commentsreq(pid);
			     		 resetData();
			     	},
			      error : function(e) {
			        /* window.location="/login"; */
			        console.log("ERROR: ", e);
			      }
			    });
			     
				  function resetData(){
			      $("#cmtreview-"+pid).val("");
			       }
			 }
		 })
		 
		 deletePost = (id) => {
			 $.ajax({
			      type : "GET",
			      url : "/post/delete/"+id,
			      success: function(result){
			    	 console.log("success");
			    	 location.reload();
			      },
			      error : function(e) {
			        console.log("ERROR: ", e);
			      }
			    });
		 }
		
		 deleteComment = (cid) => {
				  let ida = cid.split('_');
				  console.log(ida)
				  $.ajax({
				      type : "GET",
				      url : "/post/deleteComment/"+ida[0]+"/"+ida[1],
				      success: function(result){
				    	 console.log("success");
				    	 commentsreq(ida[1]);
				      },
				      error : function(e) {
				        alert("you can't delete parent comment");
				        console.log("ERROR: ", e);
				      }
				    });  
				  }
		
		   $('.commnentshow').on("submit","form[id*='replyForm-']",(e)=>{
					 event.preventDefault();
					 console.log($(e.target).attr("id"))
					 let commentId =  $(e.target).attr("id").split("-")[1]
					   if($("#replyreview-"+commentId).val().trim()==""){
						   alert("empty not allowed")
					   }else{
						 var formData = {
					        review : $("#replyreview-"+commentId).val().trim()
					      }
					 	console.log(formData)
					 	console.log('/post/'+$("#pid-"+commentId).val()+'/'+$("#cid-"+commentId).val()+'/comment')
					      $.ajax({
					    	  type : "POST",
					          contentType:"application/json",
					     	  url :  '/post/'+$("#pid-"+commentId).val()+'/'+$("#cid-"+commentId).val()+'/comment',
					     	  data : formData,
					     	  dataType : 'json',
					     	  success : function(result) {
					     		 commentsreq($("#pid-"+commentId).val());
					     	},
					      error : function(e) {
					        /* window.location="/login"; */
					        console.log("ERROR: ", e);
					      }
					    });
//					      resetData();
					      
					    
					    function resetData(){
					      $("#title").val("");
					      $("#content").val("");
					    }
					   }
				 })
			  
				 $(".commnentshow").on("click","button[id*='replyBtn-']",function () {
					
					 $("form[id*='replyForm-']").each(function () {
			    		let commentId = $(this).prop("id").split("-")[1]
			    		let formId = "replyForm-" + commentId
			    	  	let replybtn = "replyBtn-"+ commentId
			    	  	$('#'+replybtn).removeClass("hide")
			    	    $("#"+formId).addClass("hide")
			    	  })
			    	  let commentId = $(this).prop("id").split("-")[1]
					 console.log(commentId)
			    	  let formId = "replyForm-" + commentId
			    	  let replybtn = $(this).prop("id")
			    	  $('#'+replybtn).addClass("hide")
			    	  $("#"+formId).removeClass("hide")
			    	})
			
			