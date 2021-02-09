
function hideAlerts() {
	$("#alert").hide();
	$("#alertModal").hide();
}

const isOnInfoPage = $('body.info-page').length > 0;
const isOnHomePage = $('body.home-page').length > 0;
const isOnLoginPage = $('body.login-page').length > 0;


$(function() {
	if (window.history.replaceState) {
		window.history.replaceState(null, null, window.location.href);
		hideAlerts()
		if (isOnInfoPage) {
			changeInfosElementsColors();
		}
		if (isOnLoginPage) {
			changeSizeBtnIfMobile();
		}
		if (isOnHomePage) {
			changeCardHomeSize();
		}
	}
});


function validation(nameFromDOM) {
	const name = document.getElementById(nameFromDOM).value;
	if (name) {
		return true;
	}
	alert(`${nameFromDOM} deve ser informado`);
	return false;
}

function userValidation(usernameFromDOM, passwordFromDOM) {
	const username = document.getElementById(usernameFromDOM).value;
	const password = document.getElementById(passwordFromDOM).value;
	if (username && password) {
		return true;
	}
	alert(`um ou mais campos estão vazios`);
	return false;
}


function addressValidation() {
	const cep = document.getElementById('cep').value;
	const state = document.getElementById('state').value;
	const city = document.getElementById('city').value;
	if (cep && state && city) {
		return true;
	}
	alert(`um ou mais campos estão vazios`);
	return false;
}

$("#genders").on('change', function() {
	$("#searchGender").submit();
})

$("#searchGender").submit(function(event) {
	event.preventDefault();
	const post_url = $(this).attr("action");
	const request_method = $(this).attr("method");
	const data = $('#genders').val();
	$.ajax({
		url: post_url,
		contentType: 'text/plain',
		method: request_method,
		data: data
	}).done(function(response) {
		clearTable('peopleTable');
		buildPeopleTable(response)
	});

})

function buildPeopleTable(response) {
	console.log(response)
	$.each(response, function(key) {
		$htmlstring =
			`<tr>
					<td>${response[key].id}</td>
					<td> <a href="/infos/${response[key].id}" class="nav-link"><span >${response[key].name}</span></a></td>
					<td> ${convertGenderName(response[key].gender)}</td>
					<td> ${response[key].profession.name}</td>
					<td> <a class="btn btn-outline-primary" href="/pessoas/${response[key].id}"><i class="fas fa-edit"></i></a> </td>
					<td> <a class="btn btn-outline-danger" href="/pessoas/delete/${response[key].id}"><i class="fas fa-trash-alt"></i></a> </td>
				</tr>`;
		$("#peopleTable").append($htmlstring);
	})
}

function clearTable(id) {
	$("#" + id + " tbody").empty();
}

function convertGenderName(gender) {
	return gender == 'F' ? "Feminino" : "Masculino"
}

function hideCurriculumInfos(text) {
	const divInfos = $("#curriculumInfos");
	const title = $("#titleCurriculumForm");
	if (text) {
		const hasCurriculum = text.includes("Sim");
		if (hasCurriculum) {
			divInfos.show();
			title.text("Atualizar Curriculo");
		} else {
			divInfos.hide();
		}
	}
}

function normalAlert(msg, classe) {
	$("#alert").show();
	document.getElementById('alertMsg').innerHTML = msg;
	document.getElementById("alert").className = classe;
	$("#alert").fadeTo(2700, 500).slideUp(500, function() {
		$("#alert").slideUp(500);
	});
}

function modalAlert(msg, classe) {
	$("#alertModal").show();
	document.getElementById('alertMsgModal').innerHTML = msg;
	document.getElementById("alertModal").className = classe;
	$("#alertModal").fadeTo(2700, 500).slideUp(500, function() {
		$("#alertModal").slideUp(500);
	});
}

var isMobile = {
	Android: function() {
		return navigator.userAgent.match(/Android/i);
	},
	BlackBerry: function() {
		return navigator.userAgent.match(/BlackBerry/i);
	},
	iOS: function() {
		return navigator.userAgent.match(/iPhone|iPad|iPod/i);
	},
	Opera: function() {
		return navigator.userAgent.match(/Opera Mini/i);
	},
	Windows: function() {
		return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
	},
	any: function() {
		return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
	}
};


function changeSizeBtnIfMobile() {
	if (isMobile.any()) {
		$("#divBtnsLogin").css('width', '65%');
	} else {
		$("#divBtnsLogin").css('width', '25%');
	}
}

function changeCardHomeSize() {
	if (!isMobile.any())
		$('#cardHome').css('width', '50%')
}

function clearRegisterForm() {
	$('#registerUsername').val('');
	$('#registerPassword').val('');
	$('#registerPassword2').val('');
}
function clearPasswordForm() {
	$('#currentPassword').val('');
	$('#newPassword').val('');
}

$('#formRegister').submit(function(e) {

	e.preventDefault();
	const post_url = $(this).attr("action");
	const request_method = $(this).attr("method");

	const username = $('#registerUsername').val();
	const password = $('#registerPassword').val();
	const password2 = $('#registerPassword2').val();

	$.ajax({
		method: request_method,
		url: post_url,
		data: {
			username: username,
			password: password,
			password2: password2
		}
	}).done(function(response) {
		normalAlert(response, "alert alert-success")
		$('#registerModal').modal('hide')
		clearRegisterForm()
	}).fail(function(response) {
		modalAlert(response.responseText, "alert alert-danger")
	});
})

$('#formPassword').submit(function(e) {
	e.preventDefault();
	const currentPassword = $('#currentPassword').val();
	const newPassword = $('#newPassword').val();
	const url = $(this).attr('action');
	const method = $(this).attr('method');
	$.ajax({
		method: method,
		url: url,
		data: {
			currentPassword: currentPassword,
			newPassword: newPassword
		}
	}).done(function(response) {
		normalAlert(response, 'alert alert-success')
		$('#passwordModal').modal('hide');
		clearPasswordForm();
	}).fail(function(response) {
		modalAlert(response.responseText, 'alert alert-danger');
	});
})

function changeFontColor(condition, div, successHexColor, failHexColor) {
	if (condition) {
		div.css('color', successHexColor);
	} else {
		div.css('color', failHexColor);
	}
}

var color = {
	cian: '#0dcaf0',
	pink: '#d63384',
	green: '#198754',
	red: '#dc3545'
}

function changeInfosElementsColors() {
	const textGender = $("#textInfoGender");
	const textCurriculum = $("#textInfoCurriculum");
	const isMale = textGender.text() == 'Masculino';
	const hasResume = textCurriculum.text() == 'Sim';
	changeFontColor(isMale, textGender, color.cian, color.pink);
	changeFontColor(hasResume, textCurriculum, color.green, color.red);
}


 function editPeople (div){
	const peopleId = $(div).attr("data-id")
	const url = `/contatos/${peopleId}`
	$.ajax({
		method : 'get',
		url : url
	}).done(function(response){
		console.log(response)
		$('#savePeopleModal').modal('show')
		buildPeopleForm(response)
	});
	
}

function buildPeopleForm(response){
	$('#peopleId').val(response.id)
	$('#name').val(response.name);
	$('#gender').val(response.gender);
	$('#profession').val(response.profession.id);
	$('#birthday').val(response.birthday);
}


//código copiado de https://viacep.com.br/exemplo/jquery/
$(document).ready(function() {

	function cleanFormCep() {
		// Limpa valores do formulário de cep.
		$("#city").val("");
		$("#state").val("");
		$("#cep").val("");
	}

	//Quando o campo cep perde o foco.
	$("#cep").blur(function() {

		//Nova variável "cep" somente com dígitos.
		var cep = $(this).val().replace(/\D/g, '');

		//Verifica se campo cep possui valor informado.
		if (cep != "") {

			//Expressão regular para validar o CEP.
			var validacep = /^[0-9]{8}$/;

			//Valida o formato do CEP.
			if (validacep.test(cep)) {

				//Preenche os campos com "..." enquanto consulta webservice.
				$("#city").val("...");
				$("#state").val("...");

				//Consulta o webservice viacep.com.br/
				$.getJSON(`https://viacep.com.br/ws/${cep}/json/?callback=?`, function(dados) {

					if (!("erro" in dados)) {
						//Atualiza os campos com os valores da consulta.
						$("#city").val(dados.localidade);
						$("#state").val(dados.uf);
					} //end if.
					else {
						//CEP pesquisado não foi encontrado.
						cleanFormCep();
						alert("CEP não encontrado.");
					}
				});
			} //end if.
			else {
				//cep é inválido.
				cleanFormCep();
				alert("Formato de CEP inválido.");
			}
		} //end if.
		else {
			//cep sem valor, limpa formulário.
			cleanFormCep();
		}
	});
});