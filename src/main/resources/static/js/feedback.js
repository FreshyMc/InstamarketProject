import {html} from '//unpkg.com/lighterhtml?module';

//Feedback script
(function(){
    const apiUrl = '/orders/feedback';

    let orderId = -1;

    let doc = document;
    let feedbackModal = doc.getElementById('feedbackModal');
    let feedbackForm = doc.getElementById('feedbackForm');
    let submitBtn = feedbackForm.querySelector('button[type=submit]');
    let feedbackStar = feedbackForm.querySelector('.feedback-selector');
    let stars = feedbackStar.querySelectorAll('i');
    let ratingInput = feedbackForm.querySelector('input[name=rating]');
    let feedbackArea = feedbackForm.querySelector('textarea[name=feedback]');
    let addFeedbackBtns = doc.querySelectorAll('button[data-bs-toggle=modal]');

    let feedbackBsModal = new bootstrap.Modal(feedbackModal, {});

    let toastMessageWrapper = doc.getElementById('toastMessages');

    addFeedbackBtns.forEach(btn => {
        btn.addEventListener('click', ev => {
            orderId = Number(btn.getAttribute('data-order-id'));
        });
    });

    feedbackForm.addEventListener('submit', sendFeedback);

    feedbackStar.addEventListener('mouseout', resetFeedback);

    stars.forEach(star => {
        star.addEventListener('mouseover', updateStarCount);

        star.addEventListener('click', selectStarFeedback);
    });

    const starClasses = ['one-star', 'two-star', 'three-star', 'four-star'];

    let selected = -1;

    function updateStarCount(e){
        e.preventDefault();

        let target = e.target;

        let starValue = Number(target.getAttribute('data-star'));

        resetStars();

        feedbackStar.classList.add(starClasses[starValue]);

        if(selected >= 0){
            resetStars();

            feedbackStar.classList.add(starClasses[selected]);
        }
    }

    function selectStarFeedback(e){
        e.preventDefault();

        let target = e.target;

        let starValue = Number(target.getAttribute('data-star'));

        selected = starValue;

        ratingInput.value = starValue;

        resetStars();

        feedbackStar.classList.add(starClasses[starValue]);
    }

    function resetFeedback(){
        resetStars();

        if(selected >= 0){
            feedbackStar.classList.add(starClasses[selected]);
        }
    }

    function resetStars(){
        feedbackStar.classList.remove(...starClasses);
    }

    feedbackModal.addEventListener('hidden.bs.modal', resetModal);

    function resetModal(){
        orderId = -1;

        selected = -1;

        ratingInput.value = selected;

        resetStars();

        feedbackArea.value = '';
    }

    async function sendFeedback(e){
        e.preventDefault();

        toggleBtnAccess(submitBtn, true);

        if(selected < 0 || orderId < 0){
            toggleBtnAccess(submitBtn);

            return;
        }

        let csrf = doc.querySelector('.csrf');

        let csrfHeader = csrf.getAttribute('name');
        let csrfValue = csrf.value;

        let feedback = feedbackArea.value;

        let data = {rating: selected, feedback, orderId};

        let request = await fetch(`${apiUrl}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfValue
            },
            body: JSON.stringify(data)
        });

        if(request.ok){
            doc.querySelector(`[data-order-id="${orderId}"]`).remove();

            feedbackBsModal.hide();

            appendToastMessage('Successfully sent your feedback!');
            return;
        }

        feedbackBsModal.hide();

        appendToastMessage('We have failed to send your feedback. Try again later.');

        toggleBtnAccess(submitBtn);
    }

    //MISC

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

    function toggleBtnAccess(btn, access = false){
        btn.disabled = access;
    }
})();