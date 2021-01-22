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

