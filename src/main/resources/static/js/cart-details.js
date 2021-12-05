const apiUrl = '/api/cart';

(function(){
    let doc = document;
    let selectAllBtn = doc.querySelector('input[name=selectAll]');
    let offers = doc.querySelectorAll('.cart-offer');
    let removeItemsBtn = doc.getElementById('removeItems');
    let continueBtn = doc.getElementById('continueBtn');
    let totalPriceField = doc.getElementById('totalPrice');

    selectAllBtn.addEventListener('change', selectItems);

    removeItemsBtn.addEventListener('click', removeItems);

    continueBtn.addEventListener('click', continueCheckout);

    function selectItems(e){
        e.preventDefault();

        let itemInputs = doc.querySelectorAll('input[name=productSelector]');

        if(e.target.checked){
            itemInputs.forEach(item => {
               item.checked = true;
            });
        }else{
            itemInputs.forEach(item => {
                item.checked = false;
            })
        }

        recalculateTotalPrice();
    }

    offers.forEach(offer => {
        let offerSelector = offer.querySelector('input[name=productSelector]');
        let priceTag = offer.querySelector('.item-total-price');
        let plusBtn = offer.querySelector('.plus');
        let minusBtn = offer.querySelector('.minus');
        let quantity = offer.querySelector('input[name=offerQuantity]');

        offerSelector.addEventListener('change', (e)=>{
            e.preventDefault();

            recalculateTotalPrice();
        });

        quantity.addEventListener('change', (e)=>{
            e.preventDefault();

            let total = calculatePrice(quantity);

            priceTag.textContent = `$ ${total}`;

            recalculateTotalPrice();
        });

        plusBtn.addEventListener('click', (e)=>{
            e.preventDefault();

            let total = addQuantity(quantity);

            priceTag.textContent = `$ ${total}`;

            recalculateTotalPrice();
        });

        minusBtn.addEventListener('click', (e)=>{
           e.preventDefault();

           let total = removeQuantity(quantity);

            priceTag.textContent = `$ ${total}`;

            recalculateTotalPrice();
        });
    });

    function recalculateTotalPrice(){
        let checkedOffers = [...doc.querySelectorAll('input[name=productSelector]:checked')];

        let totalPrice = 0;

        checkedOffers.map(input => {
            let offer = findOffer(input);

            let offerQuantityField = offer.querySelector('input[name=offerQuantity]');

            let singlePrice = Number(offerQuantityField.getAttribute('data-single-price'));

            let quantity = Number(offerQuantityField.value);

            return (singlePrice * quantity);
        }).forEach((price) => totalPrice += price);

        totalPriceField.textContent = `$ ${totalPrice.toFixed(2)}`;
    }

    function calculatePrice(input){
        let singlePrice = Number(input.getAttribute('data-single-price'));
        let quantity = Number(input.value);

        if(quantity <= 0){
            quantity = 1;
        }

        let totalPrice = singlePrice * quantity;

        return totalPrice.toFixed(2);
    }

    function addQuantity(input){
        let singlePrice = Number(input.getAttribute('data-single-price'));
        let quantity = Number(++input.value);

        input.value = quantity;

        let totalPrice = singlePrice * quantity;

        return totalPrice.toFixed(2);
    }

    function removeQuantity(input){
        let singlePrice = Number(input.getAttribute('data-single-price'));
        let quantity = Number(--input.value);

        if(quantity <= 0){
            quantity = 1;
        }

        input.value = quantity;

        let totalPrice = singlePrice * quantity;

        return totalPrice.toFixed(2);
    }

    function removeItems(e){
        e.preventDefault();

        selectAllBtn.checked = false;

        let selectedItems = doc.querySelectorAll('input[name=productSelector]:checked');

        selectedItems.forEach(async (item) => {
            let target = findOffer(item);

            let offerId = target.getAttribute('data-id');

            let optionId = Number(item.getAttribute('data-option'));

            let offerOptionIndex = null;

            if(optionId >= 0){
                offerOptionIndex = optionId;
            }

            let data = {offerOptionIndex};

            let csrf = doc.querySelector('.csrf');

            let csrfHeader = csrf.getAttribute('name');
            let csrfValue = csrf.value;

            let request = await fetch(`${apiUrl}/${offerId}/remove`, {
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

                target.parentElement.remove();

                recalculateTotalPrice();
            }
        });
    }

    async function continueCheckout(e){
        e.preventDefault();

        continueBtn.disabled = true;

        let selectedItems = [...doc.querySelectorAll('input[name=productSelector]:checked')];

        if(selectedItems.length === 0){
            //TODO Show error message
            continueBtn.disabled = false;
            return;
        }

        let deliveryAddress = doc.querySelector('input[name=deliveryAddress]:checked') || null;

        if(deliveryAddress == null){
            //TODO Show error message
            continueBtn.disabled = false;
            return;
        }

        let cartItems = [];

        selectedItems.map(item => {
            let offer = findOffer(item);

            let offerId = offer.getAttribute('data-id');

            let optionId = Number(item.getAttribute('data-option'));

            let quantityInput = offer.querySelector('input[name=offerQuantity]');

            let quantity = Number(quantityInput.value);

            if(quantity <= 0){
                quantityInput.value = 1;

                quantity = 1;
            }

            let offerOptionIndex = null;

            if(optionId >= 0){
                offerOptionIndex = optionId;
            }

            return {offerId, quantity, offerOptionIndex};
        }).forEach(o => {
            cartItems.push(o);
        });

        let csrf = doc.querySelector('.csrf');

        let csrfHeader = csrf.getAttribute('name');
        let csrfValue = csrf.value;

        let deliveryAddressId = Number(deliveryAddress.value);

        let data = {deliveryAddress: deliveryAddressId, cartItems};

        //TODO Send cart data to checkout REST controller
        let request = await fetch(`${apiUrl}/checkout`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfValue
            },
            body: JSON.stringify(data)
        });

        if(request.ok){
            let response = await request.text();

            //TODO Show success message with redirection to my offers page link :)
            console.log(response);

            selectedItems.forEach(item => {
               let offer = findOffer(item);

               offer.parentElement.remove();
            });

            recalculateTotalPrice();

            continueBtn.disabled = false;
        }
    }

    //MISC

    function findOffer(el){
        let target = el;

        while(!target.classList.contains('cart-offer') && target.tagName != 'BODY'){
            target = target.parentElement;
        }

        return target;
    }
})();