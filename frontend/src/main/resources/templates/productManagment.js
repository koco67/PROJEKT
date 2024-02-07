//let id = 0;

function giveTestToken() {

    alert(token);

    const registrationData = {
        email: "test@gmail.com",
        psw: "123",
        role: USER
    };

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
        alert(token);
    })
    .catch(error => {
        console.error('Error registering user:', error);
    });

}

function cardDirection(element) {

    let id=element.id;
    //giveTestToken();

    let name = "Produktname";
    let foiltype = "Folientyp";
    let description = "Produktbeschreibung";
    let price = 0;

    // Definieren der URL des Endpunkts
    const url = 'http://localhost:8080/rest/product/' + id;

    console.log("test");

    const options = {
        method: 'GET',
        headers: {
            'Authorization': "localStorage.getItem('accessToken')"
        }
    };

    // Senden des Fetch-Requests
    fetch(url, options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            name = String(data.name);
            foiltype = String(data.foiltype);
            description = String(data.description);
            price = parseFloat(data.price);
            console.log(data);

            document.getElementById("cardID").innerText(id);
            document.getElementById("cardName").innerText(name);
            document.getElementById("cardDescription").innerText(description);
            document.getElementById("cardPrice").innerText(price);
            document.getElementById("cardFoiltype").innerText(foiltype);
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });

        //document.getElementById("cardName").innerText = name.toString;
        //document.getElementById("cardDescription").innerText = description.toString;
        //document.getElementById("cardPrice").innerText = price.toString;
        //document.getElementById("cardFoiltype").innerText = foiltype.toString;
}