function registerUser() {
    const email = document.getElementById('email').value;
    const psw = document.getElementById('psw').value;
    const pswRepeat = document.getElementById('pswRepeat').value;
    const role = "USER";

    const registrationData = {
        email: email,
        psw: psw,
        role: role
    };

    if (psw !== pswRepeat) {
        alert('Passwords do not match.');
        return;
    }

    fetch('http://localhost:8080/rest/auth/create', {
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
    .then(token => {
        localStorage.setItem('accessToken', token);
        window.location.href = 'login.html';
        // Weiterleitung zur login.html um sich dann anzumelden
    })
    .catch(error => {
        console.error('Error registering user:', error);
    });
}

function loginUser() {
    const email = document.getElementById('email').value;
    const psw = document.getElementById('psw').value;

    const loginData = {
        email: email,
        psw: psw
    };

    fetch('http://localhost:8080/rest/auth/verify', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (response.ok) {
            console.log('Login successful.');
            
        } else if (response.status === 401) {
            alert('Invalid password.');
        } else if (response.status === 404) {
            alert('User not found.');
        } else {
            alert('Login failed.');
        }
    })
    .catch(error => {
        console.error('Error logging in:', error);
    });
}

function changeEmailParagraph() {
    const email = localStorage.getItem('accessToken').toString;
    document.getElementById('emailParagraph').innerHTML = email;
}

