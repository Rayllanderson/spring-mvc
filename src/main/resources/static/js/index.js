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