(function(){
    let doc = document;
    let productImagesWrapper = doc.querySelector('.product-images-wrapper');
    let carouselBtnWrapper = doc.querySelector('.product-images-previewer');

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
})();