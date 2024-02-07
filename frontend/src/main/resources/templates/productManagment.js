const id = 0;

function cardDirection(element) {

    id=element.id;

    const name = "Produktname";
    const foiltype = "Folientyp";
    const description = "Produktbeschreibung";
    const price = 0;

    // Definieren der URL des Endpunkts
    const url = '/rest/product/' + id;

    const options = {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('accessToken')
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
            name = data.name;
            foiltype = data.foiltype;
            description = data.description;
            price = data.price;
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });

        document.getElementById("cardName").innerText = name.toString;
        document.getElementById("cardDescription").innerText = description.toString;
        document.getElementById("cardPrice").innerText = price.toString;
        document.getElementById("cardFoiltype").innerText = foiltype.toString;
}