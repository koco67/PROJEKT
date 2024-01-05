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

function toggleMenu() {
    var dropdownMenu = document.getElementById("dropdownMenu");
    dropdownMenu.style.display = (dropdownMenu.style.display === "block") ? "none" : "block";
}

document.addEventListener("click", function(event) {
    var dropdownMenu = document.getElementById("dropdownMenu");
    if (event.target.closest(".menu-icon") === null && event.target.closest(".dropdown-content") === null) {
        dropdownMenu.style.display = "none";
    }
});

// Close the dropdown when the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.menu-icon')) {
        var dropdownMenu = document.getElementById("dropdownMenu");
        if (dropdownMenu.style.display === "block") {
            dropdownMenu.style.display = "none";
        }
    }
}
