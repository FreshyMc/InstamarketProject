import {html} from '//unpkg.com/lighterhtml?module';

const apiUrl = '/api/offers';

(function (){
    let doc = document;
    let toastMessageWrapper = doc.getElementById('toastMessages');
    let removeBtns = doc.querySelectorAll('.remove-btn');
    let emptyMessage = doc.querySelector('.specifications-empty-message');

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

    removeBtns.forEach(btn => {
        btn.addEventListener('click', removeFromWishList);
    });

    async function removeFromWishList(e){
        e.preventDefault();

        let target = e.target;

        target.disabled = true;

        let form = target.parentElement;

        let wishListItem = form;

        while(!wishListItem.classList.contains('wishlist-item')){
            wishListItem = wishListItem.parentElement;
        }

        let offerId = target.getAttribute('data-id');

        let csrf = form.querySelector('.csrf');

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

            if(response.favourite == false){
                appendToastMessage('Offer removed from the wishlist!');

                wishListItem.remove();

                if(doc.querySelectorAll('.wishlist-item').length === 0){
                    emptyMessage.style.display = 'block';
                }

                target.disabled = false;

                return;
            }
        }

        appendToastMessage('Offer failed to be removed from the wishlist!');

        target.disabled = false;
    }

    function appendToastMessage(message){
        let toast = toastMessageTemplate(message);

        toastMessageWrapper.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);
    }
})();