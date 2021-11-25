import {html} from '//unpkg.com/lighterhtml?module';

const apiUrl = '/api/cart';

(function(){
    let doc = document;
    let toastMessageWrapper = doc.getElementById('toastMessages');
    let offerActions = doc.getElementById('offerActions');
    let addToCartBtn = doc.getElementById('addToCart');

    addToCartBtn.addEventListener('click', addToCart);

    async function addToCart(e){
        addToCartBtn.disabled = true;

        let target = e.target;
        let offerId = target.getAttribute('data-id');

        let offerOption = doc.querySelector('input[name=option]:checked');

        let csrf = offerActions.querySelector('#searchCsrf');

        let csrfHeader = csrf.getAttribute('name');
        let csrfValue = csrf.value;

        let data = {offerOptionIndex: null};

        if(offerOption != null){
            data.offerOptionIndex = offerOption.value;
        }

        console.log(JSON.stringify(data));

        let request = await fetch(`${apiUrl}/${offerId}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfValue
            },
            body: JSON.stringify(data)
        });

        if(request.ok){
            let response = await request.json();

            console.log(response);

            if(response.added){
                appendToastMessage('Offer added to cart!');
            }

            addToCartBtn.disabled = false;
        }

        addToCartBtn.disabled = false;
    }

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

    function appendToastMessage(message){
        let toast = toastMessageTemplate(message);

        toastMessageWrapper.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);
    }
})();