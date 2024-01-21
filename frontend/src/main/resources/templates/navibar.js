document.getElementById('navbarContainer').innerHTML = '<!DOCTYPE html>\n' +
    '<html lang="en">\n' +
    '<head>\n' +
    '  <meta charset="UTF-8">\n' +
    '  <meta name="viewport" content="width=device-width, initial-scale=1.0">\n' +
    '  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-ez+SRdIo/tZ7s5wq9tg1FkFq6jIEG2P8txIjZ9iEoKA6qEGg2PWsnABhTG8KAEna" crossorigin="anonymous">\n' +
    '  <link rel="stylesheet" href="style.css">\n' +
    '  <title>Pokemon Card Shop Basket Page</title>\n' +
    '</head>\n' +
    '<body>\n' +
    '\n' +
    '<!-- Navigation Bar -->\n' +
    '<nav class="navbar">\n' +
    '    <a href="index.html">'+
    '  <div class="logo">Pokemon Shop</div>\n' +
    '    <a/>\n' +
    '  <div class="search-bar">\n' +
    '    <input type="text" placeholder="Search...">\n' +
    '  </div>\n' +
    '  <div class="cart-icon">\n' +
    '    <a href="basketpage.html">'+
    '     🛒 <!-- Unicode Shopping Cart Symbol -->\n' +
    '    <a/>\n' +
    '    <span id="cart-count">0</span>\n' +
    '  </div>\n' +
    '  <div class="dropdown" style="float:right;">\n' +
    '    <div class="menu-icon" onclick="toggleMenu()">☰</div>\n' +
    '    <!-- Popup-Menü -->\n' +
    '    <div class="dropdown-content" id="dropdownMenu" style="right: 0">\n' +
    '      <a href="orderhistorypage.html">Order History</a>\n' +
    '      <a href="accountpage.html">Account</a>\n' +
    '      <a href="#">Help</a>\n' +
    '      <a href="#">Switch Account</a>\n' +
    '      <a href="#">Log out</a>\n' +
    '    </div>\n' +
    '  </div>\n' +
    '</nav>\n' +
    '\n' +
    '<!-- Shopping Cart Modal -->\n' +
    '<div class="cart-modal" id="cartModal">\n' +
    '  <!-- Cart content goes here -->\n' +
    '  <div class="cart-items">Your cart is empty.</div>\n' +
    '  <button onclick="closeCart()">Close</button>\n' +
    '</div>\n' +
    '\n' +
    '<script src="script.js"></script>\n' +
    '\n' +
    '</body>';