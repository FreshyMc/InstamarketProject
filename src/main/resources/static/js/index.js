let doc = document;
let scrollTopBtn = doc.querySelector('.scroll-top');
let scrollThreshold = 500;

window.addEventListener('scroll', function(e) {
    let scrollTop = doc.documentElement.scrollTop;

    if (scrollTop >= scrollThreshold) {
        scrollTopBtn.style.display = 'block';
    } else {
        scrollTopBtn.style.display = 'none';
    }
});

let shoppingCart = doc.getElementById('cart-preview');
let openCartBtn = doc.getElementById('openCart');
let closeCartBtn = shoppingCart.querySelector('.close-modal-btn');
let cartShown = false;

openCartBtn.addEventListener('click', toggleCart);
closeCartBtn.addEventListener('click', toggleCart);

function toggleCart(e) {
    e.preventDefault();

    if (cartShown) {
        doc.body.classList.remove('no-scroll');
        shoppingCart.classList.add('closed');
    } else {
        doc.body.classList.add('no-scroll');
        shoppingCart.classList.remove('closed');
    }

    cartShown = !cartShown;
}

let trashProductsBtn = doc.getElementById('trash-products');
let confirmationAlert = doc.getElementById('confirmation-alert');
let confirmBtn = doc.querySelector('.alert .confirm-btn');
let denyBtn = doc.querySelector('.alert .deny-btn');
let confirmationAlertShown = false;

trashProductsBtn.addEventListener('click', trashElements);

function showAlert() {
    confirmationAlert.classList.remove('closed');

    confirmationAlertShown = true;
}

function hideAlert() {
    confirmationAlert.classList.add('closed');

    confirmationAlertShown = false;
}

function trashElements(e) {
    e.preventDefault();

    let selection = doc.querySelectorAll('.cart-product input:checked');

    console.log(selection);

    if (selection.length > 0) {
        showAlert();
        return;
    }
}

function trashProducts(e) {
    e.preventDefault();

    hideAlert();
}

confirmBtn.addEventListener('click', trashProducts);

denyBtn.addEventListener('click', hideAlert);