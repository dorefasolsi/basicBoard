/**
 * 
 */

 


/* enroll */
  $(function(){
      //아이디 정규식
      $('#enrollUserId').blur(function(){
           var $enrollUserId = $('#enrollUserId').val();
           var regExp =  /^[a-z\d]{5,20}$/;
           //영문 소문자, 숫자, 5자~20자, 문자내모든패턴검사
           if(!regExp.test($enrollUserId)){
               $('#id-feedback').val("5~20자의 영문 소문자와 숫자만 사용 가능합니다.");
               return false;
           }
           else if(regExp.test($enrollUserId)){
               $('#id-feedback').val("");
            $.ajax({
               url : "/enroll/validate",
               data : {userId : $enrollUserId},
               type : "post",
               contentType : "application/x-www-form-urlencoded",
               success : function(data){
                   if(data.data == 0){
                       $('#id-feedback').val("사용 가능한 아이디입니다.");
                   } else {
                       $('#id-feedback').val("이미 사용 중인 아이디입니다. 다시 입력하세요.");
                       $('#enrollUserId').val("");
                   }
               }
            })
               return true;
           }

      })	
      
      
      //비밀번호 정규식
      $('#enrollUserPwd').blur(function(){
          var $enrollUserPwd = $('#enrollUserPwd').val();
          var regExp = /^[a-zA-Z\d!@#$%]{8,16}$/; //8~16자 영소문자, 대문자, 숫자, 특수문자
          
          if(!regExp.test($enrollUserPwd)){
              $('#pwd-feedback').val("올바르지 않은 비밀번호 형식입니다. 8~16자의 영어 대소문자, 숫자, 특수문자(!@#$%)만 사용하실 수 있습니다.");
              return false;
          }
          else if(regExp.test($enrollUserPwd)){
              $('#pwd-feedback').val("");
              return true;
          }
      })
      
      $('#enorllUserPwdCheck').click(function(){
          alert("dd");
      })
      
      $('#enorllUserPwdCheck').blur(function(){
          var $enrollUserPwd = $('#enrollUserPwd').val();
          var $enorllUserPwdCheck = $('#enorllUserPwdCheck').val();

          if($enrollUserPwd != $enorllUserPwdCheck){
              $('#pwd-check-feedback').val("틀림~");
          }
          
          else if($enrollUserPwd == $enorllUserPwdCheck){
              $('#pwd-check-feedback').val("");
          }
          
      })
      
      $('#enrollUserName').blur(function(){
          var $enrollUserName = $('#enrollUserName').val();
          var regExp = /^[가-힣]{2,13}$/; //한글만 가능, 2~13자
          
          if(!regExp.test($enrollUserName)){
              $('#name-feedback').val("2~14자 사이로 한글만 입력하세요.");
              return false;
          }
          else if(regExp.test($enrollUserName)){
              $('#name-feedback').val("");
              return true;
          }
      })
  })
  
/* list*/
	$(function(){
		$("#boardTable>tbody>tr").click(function(){
			location.href = '/board/' + $(this).children(".boardNo").text();
			
		})
	})

/* detail */
	function deleteBtn_click(){
      var result = confirm("정말 삭제하시겠습니까?");
      if(result == true){
              document.getElementById('deleteForm').submit();    				
      }else{
          return;
      }
	}
	
    
	function limitText(inputElement, maxLength) {
	    if (inputElement.value.length > maxLength) {
	        alert(maxLength + "자 이상은 입력하실 수 없습니다.");
	        inputElement.value = inputElement.value.substring(0, maxLength);
	    }
	}
	
	/*update*/
	window.onload = $(function(){
	   	$('.update-hidden-boardNo').hide();	    	
	})
		
