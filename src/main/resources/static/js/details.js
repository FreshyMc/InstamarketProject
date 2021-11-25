import {html} from '//unpkg.com/lighterhtml?module';

const apiUrl = '/api/offers';

(function(){
    let doc = document;
    let offerActions = doc.getElementById('offerActions');
    let favouriteOfferBtn = doc.getElementById('favouriteOffer');
    let toastMessageWrapper = doc.getElementById('toastMessages');
    let productImagesWrapper = doc.querySelector('.product-images-wrapper');
    let carouselBtnWrapper = doc.querySelector('.product-images-previewer');

    let toastMessageTemplate = (message) => html.node`
        <div class="toast align-items-center show mb-2" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;
    offerActions.addEventListener('submit', function(e){
        e.preventDefault();
    });

    favouriteOfferBtn.addEventListener('click', favouriteOffer);

    async function favouriteOffer(e){
        favouriteOfferBtn.disabled = true;

        let target = e.target;
        let offerId = target.getAttribute('data-id');

        let csrf = offerActions.querySelector('#searchCsrf');

        let csrfHeader = csrf.getAttribute('name');
        let csrfValue = csrf.value;

        let request = await fetch(`${apiUrl}/${offerId}/favourite`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfValue
            }
        });

        if(request.ok){
            let response = await request.json();

            if(response.favourite){
                appendToastMessage('Offer added to the wishlist!');
            }else{
                appendToastMessage('Offer removed from the wishlist!');
            }

            favouriteOfferBtn.disabled = false;
        }

        favouriteOfferBtn.disabled = false;
    }

    function appendToastMessage(message){
        let toast = toastMessageTemplate(message);

        toastMessageWrapper.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);
    }

    productImagesWrapper.setAttribute('data-index', 0);

    let images = [...productImagesWrapper.querySelectorAll('.product-image')];

    let nextBtn = carouselBtnWrapper.querySelector('button:last-child');
    let prevBtn = carouselBtnWrapper.querySelector('button:first-child');

    nextBtn.addEventListener('click', function(e) {
        e.preventDefault();

        let index = Number(productImagesWrapper.getAttribute('data-index'));

        index++;

        if (index >= images.length - 1) {
            prevBtn.disabled = false;
            nextBtn.disabled = true;

            index = images.length - 1;

            productImagesWrapper.setAttribute('data-index', index);

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

        let index = Number(productImagesWrapper.getAttribute('data-index'));

        index--;

        if (index <= 0) {
            nextBtn.disabled = false;
            prevBtn.disabled = true;

            productImagesWrapper.setAttribute('data-index', 0);

            images[0].style.marginLeft = 0;
            return;
        } else {
            prevBtn.disabled = false;
            nextBtn.disabled = false;

            productImagesWrapper.setAttribute('data-index', index);

            images[0].style.marginLeft = `-${index * 100}%`;
        }
    });

    let optionImageModal = new bootstrap.Modal(document.getElementById('optionExpandGallery'), {backdrop: true, keyboard: false});

    let optionImage = doc.getElementById('optionExpandGalleryImage');

    let optionImages = [...doc.querySelectorAll('.option .product-image img')];

    optionImages.forEach(el => {
        el.addEventListener('click', expandImage);
    });

    function expandImage(e){
        e.preventDefault();

        let target = e.currentTarget;

        optionImageModal.show();

        optionImage.src = target.getAttribute('src');
    }
})();