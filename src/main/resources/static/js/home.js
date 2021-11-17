import {render, html} from '//unpkg.com/lighterhtml?module';

const doc = document;

const apiUrl = '/api/offers';

(function (){
    let offersWrapper = doc.getElementById('offersWrapper');
    let firstFetch = false;
    let lastFetchedPage = 0;
    let pageNumber = 0;
    let totalPages = 0;

    window.addEventListener('scroll', checkScroll);

    window.onload = ()=>{
      getOffers();
    };

    let offerTemplate = (content) => html.node`
        <div class="col-12 col-md-4 product">
            <a class="product-link" href="/">
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
        let request = await fetch(`${apiUrl}?pageNo=${lastFetchedPage++}`, {
            method: 'GET'
        });

        if(request.ok){
            let response = await request.json();

            lastFetchedPage = response.number;

            if(!firstFetch){
                firstFetch = true;
                totalPages = response.totalPages - 1;
                renderContent(response.content);
                return;
            }

            console.log(pageNumber);
            console.log(response);

            if(lastFetchedPage >= totalPages){
                lastFetchedPage = totalPages;
                return;
            }

            renderContent(response.content);
        }

        return false;
    }

    function renderContent(offers){
        offers.map(offer => {
            return offerTemplate(offer);
        }).forEach(o => {
            offersWrapper.appendChild(o);
        });
    }

    function checkScroll(e){
        if ((window.innerHeight + window.scrollY) >= doc.body.offsetHeight) {
            getOffers();
        }
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
})();