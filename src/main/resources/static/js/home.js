import {render, html} from '//unpkg.com/lighterhtml?module';

const doc = document;

const apiUrl = '/api/offers';

(function (){
    let offersWrapper = doc.getElementById('offersWrapper');
    let firstFetch = false;
    let lastFetchedPage = 0;
    //Search
    let searchForm = doc.getElementById('searchForm');
    let lastSearchPageNo = 0;
    let firstSearch = false;
    let searchInput = searchForm.querySelector('input[type=text]');
    let searchCategorySelect = searchForm.querySelector('select');

    searchForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        await search();
    });

    searchInput.addEventListener('change', resetSearch);
    searchCategorySelect.addEventListener('change', resetSearch);

    let observer = new IntersectionObserver(async function(entries) {
        // isIntersecting is true when element and viewport are overlapping
        // isIntersecting is false when element and viewport don't overlap
        if(entries[0].isIntersecting === true) {
            if(firstSearch !== false){
                await search();
                return;
            }

            await getOffers();
        }
    }, { threshold: [0] });

    observer.observe(doc.getElementById('bottom'));

    let offerTemplate = (content) => html.node`
        <div class="col-12 col-md-4 product">
            <a class="product-link" href="/offers/${content.id}/details">
                <!-- Advert carousel -->
                <div class="product-images-wrapper d-flex flex-nowrap" onclick=${(e) => preventClick(e)}>
                    <div class="product-images-previewer d-flex justify-content-between">
                        <button type="button" class="d-flex justify-content-center align-items-center" disabled><i class="fas fa-chevron-left"></i></button>
                        <button type="button" class="d-flex justify-content-center align-items-center"><i class="fas fa-chevron-right"></i></button>
                    </div>
                    ${content.images.map(renderImage)}
                </div>
                <!-- Advert Title -->
                <div class="product-title">
                    ${content.title}
                </div>
                <!-- Advert price tag -->
                <div class="price-tag d-flex flex-column flex-lg-row justify-content-between">
                    <span class="price">${`$${content.price}`}</span>
                    <!--
                    <div>
                        <span class="old-price">$150,000.<small>99</small></span>
                    </div>
                    -->
                </div>
                <!--
                <div class="sold">5 sold</div>
                -->
            </a>
        </div>
    `;

    async function getOffers(){
        if(await request()){
            lastFetchedPage++;
        }

        async function request(){
            let request = await fetch(`${apiUrl}?pageNo=${lastFetchedPage}`, {
                method: 'GET'
            });

            if(request.ok){
                let response = await request.json();

                console.log(response);

                if(!firstFetch){
                    firstFetch = true;
                    renderContent(response.content);
                    return true;
                }

                renderContent(response.content);
                return true;
            }

            return false;
        }
    }

    function renderContent(offers){
        offers.map(offer => {
            return offerTemplate(offer);
        }).forEach(o => {
            assignActions(o);

            offersWrapper.appendChild(o);
        });
    }

    function preventClick(e){
        e.preventDefault();

        return false;
    }

    function renderImage(image){
        return html.node`
            <div class="ratio ratio-1x1 product-image">
                <img src=${`/headingImage/${image}`} alt="#">
            </div>
            `;
    }

    //Carousel event listeners attach function
    function assignActions(carousel) {
        carousel.setAttribute('data-index', 0);

        let images = [...carousel.querySelectorAll('.product-image')];

        let nextBtn = carousel.querySelector('.product-images-previewer button:last-child');
        let prevBtn = carousel.querySelector('.product-images-previewer button:first-child');

        if(images.length <= 1){
            nextBtn.classList.add('d-none');
            prevBtn.classList.add('d-none');

            return;
        }
        
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
    }

    async function search(){
        let searchText = searchInput.value.trim();

        let searchCategory = searchCategorySelect.value;

        let csrf = searchForm.querySelector('#searchCsrf');

        if(!searchText){
            return;
        }

        let data = {search: searchText, category: searchCategory};

        let csrfHeader = csrf.getAttribute('name');
        let csrfValue = csrf.value;

        if(await request()){
            lastSearchPageNo++;
        }

        async function request(){
            let request = await fetch(`${apiUrl}/search?pageNo=${lastSearchPageNo}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfValue
                },
                body: JSON.stringify(data)
            });

            if(request.ok){
                let result = await request.json();

                if(!firstSearch){
                    firstSearch = true;
                    offersWrapper.innerHTML = '';
                    renderContent(result.content);
                    return true;
                }

                extendSearch(result);
                return true;
            }

            return false;
        }
    }

    function extendSearch(result){
        renderContent(result.content);
    }

    function resetSearch(e){
        firstSearch = false;
        lastSearchPageNo = 0;
    }
})();