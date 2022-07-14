/**
 * 
 */


function Chk() {

	var result = Array();

	var cnt = 0;

	var chkbox = $(".form-check-input");

	for(i=0;i<chkbox.length;i++) {

		if(chkbox[i].checked == true) {

			result[cnt] = chkbox[i].value;

			cnt++;

		}

	}

	$('#result').val(result);

}



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
	
	
function selectAll1(selectAll)  {
	  const checkboxes1 
	     = document.querySelectorAll('input[for="novice"]');
	 
	  
	  checkboxes1.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	 
	}
	
function selectAll2(selectAll)  {
	
	  const checkboxes2 
	     = document.querySelectorAll('input[for="novice2"]');
	  
	  
	  checkboxes2.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
	
function selectAll3(selectAll)  {
	
	  const checkboxes3 
	     = document.querySelectorAll('input[for="novice3"]');
	  
	  
	  checkboxes3.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll4(selectAll)  {
	
	  const checkboxes4 
	     = document.querySelectorAll('input[for="novice4"]');
	  
	  
	  checkboxes4.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll5(selectAll)  {
	
	  const checkboxes5 
	     = document.querySelectorAll('input[for="novice5"]');
	  
	  
	  checkboxes5.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll6(selectAll)  {
	
	  const checkboxes6
	     = document.querySelectorAll('input[for="novice6"]');
	  
	  
	  checkboxes6.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll7(selectAll)  {
	
	  const checkboxes7
	     = document.querySelectorAll('input[for="novice7"]');
	  
	  
	  checkboxes7.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll8(selectAll)  {
	
	  const checkboxes8
	     = document.querySelectorAll('input[for="novice8"]');
	  
	  
	  checkboxes8.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
function selectAll9(selectAll)  {
	
	  const checkboxes9 
	     = document.querySelectorAll('input[for="novice9"]');
	  
	  
	  checkboxes9.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
