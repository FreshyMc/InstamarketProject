import {html, render} from 'https://unpkg.com/lit-html?module';

const doc = document;
let specifications = [];
let properties = [];

/* Add specifications functionality */

(function(){
    let count = 0;

    let addSpecificationBtn = doc.getElementById('addSpecificationBtn');
    let specificationsGroup = doc.getElementById('specificationsGroup');

    addSpecificationBtn.addEventListener('click', addSpecificationInputGroup);

    let specificationInputGroupTemplate = (id) => html`
        <div class="input-group mb-2" data-id="${id}">
            <button type="button" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                <span class="visually-hidden">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="javascript:void(0);" @click=${(e) => deleteSpecification(e, id)}>Remove specification</a></li>
            </ul>
            <input type="text" class="form-control" name="properties[${id}].name" placeholder="Specification title">
            <input type="text" class="form-control" name="properties[${id}].value" placeholder="Specification value">
        </div>
    `;

    function addSpecificationInputGroup(e){
        e.preventDefault();

        specifications.push({id: count, el: specificationInputGroupTemplate(count)});

        count++;

        let contentToRender = specifications.reduce((acc, curr)=>{
            acc.push(curr.el);

            return acc;
        }, []);

        renderSpecifications(contentToRender);
    }

    function deleteSpecification(e, id){
        e.preventDefault();

        let target = e.target;

        while(target.tagName != 'DIV'){
            target = target.parentElement;
        }

        specifications = specifications.filter((el) => {
           return el.id !== id;
        });

        target.remove();
    }

    function renderSpecifications(contentToRender){
        render(contentToRender, specificationsGroup);
    }
})();

/* Add options functionality */

(function (){
    let count = 0;

    let addOptionBtn = doc.getElementById('addOptionBtn');
    let optionsGroup = doc.getElementById('optionsGroup');

    addOptionBtn.addEventListener('click', addOption);

    let optionGroupTemplate = (id) => html`
        <div class="input-group mb-2" data-id="${id}">
            <button type="button" class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                <span class="visually-hidden">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="javascript:void(0);" @click=${(e) => deleteOption(e, id)}>Remove option</a></li>
            </ul>
            <input type="file" class="form-control" name="options[${id}].image">
            <input type="text" class="form-control" name="options[${id}].value" placeholder="Option text">
        </div>
    `;

    function addOption(e){
        e.preventDefault();

        properties.push({id: count, el: optionGroupTemplate(count)});

        count++;

        let contentToRender = properties.reduce((acc, curr)=>{
            acc.push(curr.el);

            return acc;
        }, []);

        renderOptions(contentToRender);
    }

    function deleteOption(e, id){
        e.preventDefault();

        let target = e.target;

        while(target.tagName != 'DIV'){
            target = target.parentElement;
        }

        properties = properties.filter((el) => {
            return el.id !== id;
        });

        target.remove();
    }

    function renderOptions(contentToRender){
        render(contentToRender, optionsGroup);
    }
})();