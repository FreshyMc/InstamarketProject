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

let carousels = doc.querySelectorAll('.product-images-wrapper');

[...carousels].forEach(function(carousel) {
    carousel = carousel;

    let images = carousel.querySelectorAll('.product-image');

    images = [...images];

    let controlBtns = carousel.querySelectorAll('.product-images-previewer button');

    prevBtn = controlBtns[0];
    nextBtn = controlBtns[1];

    (function assignActions() {
        carousel.setAttribute('data-index', 0);

        nextBtn.addEventListener('click', function(e) {
            e.preventDefault();

            let index = Number(carousel.getAttribute('data-index'));

            index++;

            if (index >= images.length - 1) {
                prevBtn.disabled = false;
                nextBtn.disabled = true;

                index = images.length - 1;

                carousel.setAttribute('data-index', index);

                images[0].style.marginLeft = `-${index * 100}%`;
                return;
            } else {
                prevBtn.disabled = false;
                nextBtn.disabled = false;

                carousel.setAttribute('data-index', index);

                images[0].style.marginLeft = `-${index * 100}%`;
            }
        });

        prevBtn.addEventListener('click', function(e) {
            e.preventDefault();

            let index = Number(carousel.getAttribute('data-index'));

            index--;

            if (index <= 0) {
                nextBtn.disabled = false;
                prevBtn.disabled = true;

                carousel.setAttribute('data-index', 0);

                images[0].style.marginLeft = 0;
                return;
            } else {
                prevBtn.disabled = false;
                nextBtn.disabled = false;

                carousel.setAttribute('data-index', index);

                images[0].style.marginLeft = `-${index * 100}%`;
            }
        });
    })();
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