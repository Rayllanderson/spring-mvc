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
	var post_url = $(this).attr("action");
	var request_method = $(this).attr("method");
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
	$.each(response, function(key) {
		$htmlstring =
			`<tr>
					<td>${response[key].id}</td>
					<td> <a href="/infos/${response[key].id}" class="nav-link"><span >${response[key].name}</span></a></td>
					<td> ${response[key].gender}</td>
					<td> ${response[key].profession.name}</td>
					<td> <a href="/pessoas/${response[key].id}">Editar</a> </td>
					<td> <a href="/pessoas/delete/${response[key].id}">Excluir</a> </td>
				</tr>`;
		$("#peopleTable").append($htmlstring);
	})
}

function clearTable(id) {
	$("#" + id + " tbody").empty();
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