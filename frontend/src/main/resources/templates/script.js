let cartCount = 0;

function openCart() {
    document.getElementById('cartModal').style.display = 'flex';
}

function closeCart() {
    document.getElementById('cartModal').style.display = 'none';
}

// Placeholder function to add a product to the cart
function addToCart() {
    cartCount++;
    document.getElementById('cart-count').innerText = cartCount;
}
