/**
 * 
 */

/* 공통 */
/*$(function(){
	if(msg !== null && msg !== ""){
		alert(msg);		
	}
})	*/

function alertMsg(msg){
	if(msg !== null && msg !== "" && typeof msg !== "undefined"){
		alert(msg);		
	}
}


window.onload = $(function(){
	$('.hide').hide();	    	
})



/* enroll */
$(function(){
  	//아이디 정규식
	$('#enrollUserId').blur(function(){
     	let $enrollUserId = $('#enrollUserId').val();
     	let regExp =  /^[a-z\d]{5,20}$/;
       //영문 소문자, 숫자, 5자~20자, 문자내모든패턴검사
		if(!regExp.test($enrollUserId)){
			$('#id-feedback').val("5~20자의 영문 소문자와 숫자만 사용 가능합니다.");
			$('#enrollUserId').val("");
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
		let $enrollUserPwd = $('#enrollUserPwd').val();
		let regExp = /^[a-zA-Z\d!@#$%]{8,16}$/; //8~16자 영소문자, 대문자, 숫자, 특수문자
	      
		if(!regExp.test($enrollUserPwd)){
			$('#pwd-feedback').val("올바르지 않은 비밀번호 형식입니다. 8~16자의 영어 대소문자, 숫자, 특수문자(!@#$%)만 사용하실 수 있습니다.");
			$('#enrollUserPwd').val("");
			return false;
		}
		else if(regExp.test($enrollUserPwd)){
			$('#pwd-feedback').val("");
			return true;
		}
	})
      

      
	$('#enorllUserPwdCheck').blur(function(){
		let $enrollUserPwd = $('#enrollUserPwd').val();
		let $enorllUserPwdCheck = $('#enorllUserPwdCheck').val();
		
		if($enrollUserPwd != $enorllUserPwdCheck){
			$('#pwd-check-feedback').val("비밀번호가 일치하지 않습니다.");
			$('#enorllUserPwdCheck').val("");
		}
		      
		else if($enrollUserPwd == $enorllUserPwdCheck){
			$('#pwd-check-feedback').val("");
		}
	       
	})
      
	$('#enrollUserName').blur(function(){
		let $enrollUserName = $('#enrollUserName').val();
		let regExp = /^[가-힣]{2,13}$/; //한글만 가능, 2~13자
	          
		if(!regExp.test($enrollUserName)){
			$('#name-feedback').val("2~14자 사이로 한글만 입력하세요.");
			$('#enrollUserName').val("");
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
	let result = confirm("정말 삭제하시겠습니까?");
	if(result == true){
		document.getElementById('deleteForm').submit();    				
	}else{
		return false;
	}
}
	
    
    



$(function(){
	$("#attach-a").click(function(){
		location.href = '/attachment/download/' + $("#attach-fileNo").val();
	})
})


	
/*update*/
$(function(){
	$('#update-file-delete-button').click(function(){
		let deleteCheck = confirm("첨부파일을 삭제하시겠습니다?");
		let $fileNo = $('#attach-fileNo').val();
		let $boardNo = $('#attach-boardNo').val();

		if(deleteCheck == true){
			$.ajax({
			    url : "/attachment/delete",
			    data : {
			        fileNo : $fileNo,
			        boardNo : $boardNo
			    },
			    type : "post",
			    success : function(data){
			        $('#attach-a').text('');
			        $('#attach-td').load(window.location.href + ' #attach-td');
			    }
			})
		}else{
			return false;
		}
	})
})


/*write*/

$(function(){
    $("#write_file_cancle_button").click(function(){
    	$("#write_file_input").val("");
    });
    
})

/*http://trsketch.dothome.co.kr/page_rVsN24 코드 참고*/
function charByteSize(lettersChar){
	if(lettersChar <= 0x00007F){ 
		return 1;
	} else if(lettersChar <= 0x0077FF){ // 유니코드 2바이트 -> utf-8 3바이트
		return 3;
	} else if(lettersChar <= 0x00FFFF){ // 유니코드 2바이트 -> utf-8 3바이트
		return 3;
	} else { //표정이모티콘들 웃는얼굴, 유니코드 U+1F601 ~ U+10FFFF -> utf-8에서도 4바이트로 표시
		return 4;
	}
	
}


function limitText(inputElement, maxLength) {
    let lettersValue = inputElement.value; //입력한 문자
    let lettersLength = inputElement.value.length; //문자수
    let size = 0;

    for(let i = 0; i < lettersLength; i++){
        const lettersChar = lettersValue.charCodeAt(i);
        /*const lettersEscape = escape(lettersChar);   // ㅇ : %u3147 더이상 사용되지 않음!
        const lettersUri = encodeURI(lettersChar);   //  ㅇ : %E3%85%87 -> url에서 사용
        const lettersComponent = encodeURIComponent(lettersChar); //ㅇ : %E3%85%87 -> url에서 사용 */
        size += charByteSize(lettersChar);
        if (size > maxLength) {
            inputElement.value = inputElement.value.substring(0, i);
            size = maxLength;
            alert(maxLength + "바이트까지만 입력이 가능합니다.");
        }
    }
    $("#lettersCheck").val(size + "/" + maxLength + "byte");
}

function byteCheck(inputElement, maxLength) {
    let lettersValue = inputElement.value;
    let lettersLength = inputElement.value.length;
    let size = 0;

    for(let i = 0; i < lettersLength; i++){
        const lettersChar = lettersValue.charCodeAt(i);
        size += charByteSize(lettersChar);
    }
    $("#lettersCheck").val(size + "/" + maxLength + "byte");
}


$(function(){
	$("#write_file_input").change(function(){
		let file = document.getElementById('write_file_input');
		let fileSize = file.files[0].size; //바이트로 출력됨
		if(fileSize>5242880){
			alert("파일 용량이 너무큽니다! 5MB이하의 파일만 첨부할 수 있습니다.");
			$("#write_file_input").val("");
		}
	})	
})

