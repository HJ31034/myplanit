// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
// 아이디 중복 체크 
function checkId(){
    var userId = $('#userId').val();
$.ajax({
    url:'/idCheck',
    type:'post',
    data:{userId:userId},
    dataType:'json',
    success:
    	function(cnt){ //컨트롤러에서 넘어온 cnt값을 받는다 
    	
        if(cnt != 1){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디 
            
        	$('.id_ok').css("display","inline-block"); 
            $('.id_already').css("display", "none");
        } else if (cnt ==1){ // cnt가 1일 경우 -> 이미 존재하는 아이디
            $('.id_already').css("display","inline-block");
            $('.id_ok').css("display", "none");
        } 
   
    	
    
    },
    error:function(){
        alert("에러입니다");
    }
});
};



//비밀번호 확인 스크릡트 
$(function(){
$("#alert-success").hide();
$("#alert-danger").hide();
$("input").keyup(function(){
    var pwd1 = $("#pwd1").val();
    var pwd2 = $("#pwd2").val();
    
    if(pwd1 != ""){
        if(pwd1 == pwd2 && pwd2 !=""){
            $("#alert-success").show();
            $("#alert-danger").hide();
            $("#alert-info").hide();
            $("#submit").removeAttr("disabled");
        }else if(pwd1 != pwd2 && pwd2!="" ) {
            $("#alert-success").hide();
            $("#alert-danger").show();
            $("#alert-info").hide();
            $("#submit").attr("disabled", "disabled");
        } 
    }
});
});
//휴대폰 번호 - 
$(function(){

	$("input").keyup(function(){
	    var phone = $("#phone").val();
	    var patternPhone= new RegExp("01[016789]-[^0][0-9]{2,4}-[0-9]{3,4}");
	    
	    if(phone != ""){
	        if(phone.length >= 3){
	            $("#phone").val(phone.replace(/[^0-9]/g, '')
	          		  .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, ""));
	           
	           
	            $("#submit").removeAttr("disabled");
	        }else if(phonePattern.test(phone) ) {
	            
	           
	            $("#submit").attr("disabled", "disabled");
	        } 
	    }
	});
	}); 
