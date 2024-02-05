function registerUser() {
    const email = document.getElementById('email').value;
    const psw = document.getElementById('psw').value;
    const pswRepeat = document.getElementById('pswRepeat').value;
    const role = USER;

    const registrationData = {
        email: email,
        psw: psw,
        role: role
    };

    if (psw !== pswRepeat) {
        alert('Passwords do not match.');
        return;
    }

    fetch('http://localhost:8080/rest/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registrationData)
    })
    .then(response => {
        if (response.ok) {
            console.log('Registration successful.');
        } else if (response.status === 409) {
            alert('Email is already registered.');
        } else {
            alert('Registration failed.');
        }
    })
    .catch(error => {
        console.error('Error registering user:', error);
    });
}

