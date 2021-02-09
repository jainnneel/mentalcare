	let deleteComment;
	let postcomment;
	let replyForm;	    
	$(document).ready(function() {
		
	 $(function () {
		 let pid =  document.querySelector('.pid').innerHTML;
		const showcomment = () =>{
		    $.ajax({
		      type : "GET",
		      url : "getallcomment/"+pid,
		      success: function(result){
		    	  $('.commnentshow').html(result);
		      },
		      error : function(e) {
		        alert("ERROR: ", e);
		        console.log("ERROR: ", e);
		      }
		    });  
	  };
	  showcomment();
	  
	  deletePost = (id) => {
			 $.ajax({
			      type : "GET",
			      url : "delete/"+id,
			      success: function(result){
			    	 console.log("success");
			    	 window.location="/";
			      },
			      error : function(e) {
			        console.log("ERROR: ", e);
			      }
			    });
		 }
	  
	  $('#commentForm').on('submit',function(e){
			e.preventDefault();
			const formdata = {
					review:$('#cmtreview').val()
			}
			$.ajax({
				 type : "POST",
		          contentType:"application/json",
		     	  url :  pid+'/comment',
		     	  data : formdata,
		     	  dataType : 'json',
		     	  success : function(result) {
		     		 console.log(result)
		     		 resetData(); 
		     		 showcomment();
		     		},
		      error : function(e) {
		        /* window.location="/login"; */
		        console.log("ERROR: ", e);
		      }
			})
		      
	  	     function resetData(){
		      $("#cmtreview").val("");
		    }
		}) 
	   deleteComment = (cid) => {
		  console.log("fsff")
		  let ida = cid.split('_');
		  console.log(ida)
		  $.ajax({
		      type : "GET",
		      url : "deleteComment/"+ida[0]+"/"+ida[1],
		      success: function(result){
		    	 console.log("success");
		    	 showcomment();
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
			 
				 var formData = {
			        review : $("#replyreview-"+commentId).val()
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
			     		 showcomment();
			     	},
			      error : function(e) {
			        /* window.location="/login"; */
			        console.log("ERROR: ", e);
			      }
			    });
//			      resetData();
			      
			    
			    function resetData(){
			      $("#title").val("");
			      $("#content").val("");
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
		})
	
		 

})
