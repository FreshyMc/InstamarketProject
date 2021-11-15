import {render, html} from '//unpkg.com/lighterhtml?module';

const doc = document;

let newAddresses = [];

//TODO Script for dynamic address management
(function(){
    let count = 0;

    let addressesForm = doc.getElementById('addressesForm');
    let addAddressBtn = doc.getElementById('addAddressBtn');
    let newAddressesWrapper = doc.getElementById('newAddresses');
    let saveNewAddressesBtn = doc.getElementById('saveNewAddressesBtn');

    addressesForm.addEventListener('submit', validateForm);

    addAddressBtn.addEventListener('click', addAddress);

    let addAddressTemplate = (id, content) => html.node`
        <div class="mb-2 p-2 address">
            <div class="mb-2">
                <input type="text" class="form-control" name="newAddress[${id}].country" value=${content.country} placeholder="Country">
                <div class="mt-2 form-alert invalid-feedback">
                    <p class="m-0 p-0">Country field is required.</p>
                </div>
            </div>
            <div class="mb-2">
                <input type="text" class="form-control" name="newAddress[${id}].postalCode" value=${content.postalCode} placeholder="Postal Code">
                <div class="mt-2 form-alert invalid-feedback">
                    <p class="m-0 p-0">Postal Code field is required.</p>
                </div>
            </div>
            <div class="mb-2">
                <input type="text" class="form-control" name="newAddress[${id}].city" value=${content.city} placeholder="City">
                <div class="mt-2 form-alert invalid-feedback">
                    <p class="m-0 p-0">City field is required.</p>
                </div>
            </div>
            <div class="mb-2">
                <input type="text" class="form-control" name="newAddress[${id}].street" value=${content.street} placeholder="Street">
                <div class="mt-2 form-alert invalid-feedback">
                    <p class="m-0 p-0">Street field is required.</p>
                </div>
            </div>
            <textarea class="form-control" name="newAddress[${id}].details" value=${content.moreInformation} placeholder="More information (Optional)"></textarea>
            <div class="mt-2 d-flex justify-content-end align-items-center">
                <button class="remove-address-btn btn d-flex" type="button" onclick=${(e) => removeAddress(e, id)}><i class="fas fa-times align-self-center"></i><span class="ps-2">Remove address</span></button>
            </div>
        </div>
    `;

    function addAddress(e){
        saveNewAddressesBtn.style.display = 'block';

        let newAddress = addAddressTemplate(count, {});

        newAddresses.push({id: count, el: newAddress});

        count++;

        newAddressesWrapper.appendChild(newAddress);
    }

    function removeAddress(e, id){
        e.preventDefault();

        let target = e.target;

        while(!target.classList.contains('address')){
            target = target.parentElement;
        }

        target.remove();

        count = 0;

        newAddresses = newAddresses.filter(el => {
            return el.id !== id;
        }).map(el => {
            el.id = count;

            let contents = extractContent(el.el);

            el.el = addAddressTemplate(count, contents);

            count++;

            return el;
        });

        newAddressesWrapper.innerHTML = '';

        let contentToRender = newAddresses.reduce((acc, curr)=>{
            acc.push(curr.el);

            return acc;
        }, []).forEach(el => {
            newAddressesWrapper.appendChild(el);
        });

        if(newAddresses.length == 0){
            saveNewAddressesBtn.style.display = 'none';
        }
    }

    function extractContent(el){
        let inputs = el.querySelectorAll('input');
        let textareas = el.querySelectorAll('textarea');

        let contents = {};

        inputs.forEach(input => {
            let attr = input.getAttribute('name');
            let name = attr.substring(attr.lastIndexOf('.') + 1);

            contents[name] = input.value;
        });

        textareas.forEach(textarea => {
            let attr = textarea.getAttribute('name');
            let name = attr.substring(attr.lastIndexOf('.') + 1);

            contents[name] = textarea.value;
        });

        return contents;
    }

    function validateForm(e){
        e.preventDefault();

        let valid = true;

        newAddressesWrapper.querySelectorAll('input').forEach(i => {
           let val = i.value.trim();

           if(!val){
               i.classList.add('is-invalid');

               valid = false;
           }
        });

        if(valid){
            this.submit();
        }
    }
})();