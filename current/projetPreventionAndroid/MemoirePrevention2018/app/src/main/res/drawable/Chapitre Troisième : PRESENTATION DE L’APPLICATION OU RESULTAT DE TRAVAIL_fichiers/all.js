/*
Rodrigue Lunar VRAI
+25771729146
+243840422337
*/

$(function(){
	$('#arrow').click(function(){
		var $this = $(this);
		var classe = $this.attr('class');
		
		if(classe !='opened'){
			$this.addClass('opened');
			$('#tb_content').animate({width: '320px'},{duration: 400,queue:false,});
			$('#tb_header').css('textIndent', '0');
			$('#tb_contents').show();
		}else{
			$this.removeClass('opened');
			$('#tb_content').animate({width: '20px'},{duration: 400,queue:false,});
			$('#tb_header').css('textIndent', '-3000px');
			$('#tb_contents').hide();
		}
	});
	
	/* ************************** */
	$('#form').attr('action', 'uploads_assync.php'); //Change de default target
	$('#target').addClass('target'); //Change de default attribute of the target block
	/* ************************** */
	function formSender(){
		$('#form').submit(function(e) {	
			e.preventDefault();
			$(this).ajaxSubmit({ 
				target:   '#target', 
				beforeSubmit: function() {
				  $('#form').css({backgroundColor:'rgba(83,50,83,0.2)', opacity:'0.3'}).prepend('<img src="img/loading.gif" alt="..." class="load"/>');
					
				},
				success:function (){
					$(".load").hide();
					$('#target').fadeIn(); //Show the target block
					status();
					counter();
					resetForm();
				},
				resetForm: false
			}); 
			return false;
		});
	}
	formSender();
	/* *********************** */
	//Status handler
	function status(){
		$('.failed').click(function(){
			$(this).fadeOut();
		
		});
		
		$('.succes').click(function(){
			$(this).fadeOut();
		
		});
	}
	status();
	
	//Counter
	var z = 0;
	function counter(){
		var x = setTimeout(function(){
			if(z < 3){
				z++;
			}
			counter();
		},2500);
		////////////////
		if(z >= 3){
			clearTimeout(x);
			$('.failed, .succes').fadeOut();
			z = 0;
		}
	}
	
	///////////////////// Form Resetter
	function resetForm(){
		var $x = $('#target .notif').attr('class').split(' ');
		var $class = $x[1];
		if($class=='succes'){
			//Temporiser
			$('.form').css({opacity:'1', backgroundColor:'transparent'});
			temporiser();
		}else{
			$('.form').css({opacity:'1', backgroundColor:'rgba(255,230,230,0.1)'});
		}
	}
	
	//////////////////////////
	var m = 0;
	function temporiser(){
		v = setTimeout(function(){
			if(m < 3){m++;}
			temporiser();
		},1000);
		////////////////
		if(m >= 3){
			clearTimeout(v);
			$('#form, .form')[0].reset();
			$('.form :input:not(:submit)').val('');
			$('#form, .form').css('opacity', '1');
			m = 0;
		}
	}
	
	//Printer
	function imprimer(){
		 $("#printer").click(function(e){
            e.preventDefault();
			$('#mybestpub').hide();
			
			var mode = $("input[name='mode']:checked").val();
			var close = mode == "popup" && $("input#closePop").is(":checked");
			var extraCss = $("input[name='extraCss']").val();
			
			var keepAttr = [];
			$(".chkAttr").each(function(){
				if ($(this).is(":checked") == false )
					return;
				keepAttr.push( $(this).val() );
			});
			
			var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="IE=edge"/>';

			var options = { mode : mode, popClose : close, extraCss : extraCss, retainAttr : keepAttr, extraHead : headElements };  
			 var print = "#impression";
			 
			$( print ).printArea( options );
			
        });
	} 
	imprimer();
	
	
	/* ******** */
	function fbLiker(){
		$('#downloader').click(function(e){
			e.preventDefault();
			var file = $('#headers').text();
			var $titles ='Téléchargement en cours';
			var $links = $(this).attr('href')+'files='+file;
			window.open($links, $titles, 'height=400, width=500, top=150, left=300, toolbar=no, menubar=no, location=no, resizable=no, scrollbars=no, status=no');
			
		});
	}
	fbLiker();
	
	/* ******** */
	function popUp(){
		$('#downloader').click(function(e){
			e.preventDefault();
			
			var $title ='Speed';
			var $link = $(this).attr('href');
			window.open($link, $title, 'height=400, width=500, top=150, left=300, toolbar=no, menubar=no, location=no, resizable=no, scrollbars=no, status=no');
			
		});
	}
	popUp();
	
	
});