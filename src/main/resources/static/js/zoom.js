window.addEventListener('DOMContentLoaded', function(){
    //TODO
    let doc = document;
    let productImages = doc.getElementById('product-overview');
    let images = productImages.querySelectorAll('product-image');

    images.forEach(img => {
        img.addEventListener('mouseover', zoomImage);
        img.addEventListener('mouseout', resetImage);
    });

    productImages.onmouseover = function(e){
        //TODO Show zoomed popup
    }

    productImages.onmouseout = function(e){
        //TODO Exit zoom popup
    }

    function init(){
        let canvas = doc.createElement('canvas');

        let dimensions = productImages.getBoundingClientRect();

        let cw = dimensions.width / 2;
        let ch = dimensions.height / 2;

        canvas.width = cw;
        canvas.height = ch;

        canvas.setAttribute('class', 'product-preview-canvas');

        productImages.appendChild(canvas);
    }

    init();
});