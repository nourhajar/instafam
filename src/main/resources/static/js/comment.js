 $(document).ready(function(){
	 $('.comment_button').click(function(){
	        $.ajax({
	            url: '/comments',
	            method: "POST",
	            data:$(this).serialize(),
	            success: function(response){
	                $('#comment_container').html(response);
	            },
	            error: console.error
	        });
	        return false;
	    });
 }