function addToBasket() {
    let id = document.getElementById("cardID");

    var cartCountElement = document.getElementById('navbarContainer').innerHTML.document.getElementById('cart-count');
    var cartCountInteger = parseInt(cartCountElement);
    cartCountInteger++;
    document.getElementById('cart-count').innerText(cartCountInteger.toString);

    //var productInBasket = document.createElement("div");
    //productInBasket.id = "productInBasket";

    //productInBasket.textContent = "test";

    //divElement.classList.add("style");
    //document.body.appendChild(productInBasket);
}