function nameValidation() {
	const name = document.getElementById("name").value;
	if (name) {
		return true;
	}
	alert('nome deve ser informado');
	return false;
}

function numberValidation() {
	const number = document.getElementById("number").value;
	if (number) {
		return true;
	}
	alert('número deve ser informado');
	return false;
}