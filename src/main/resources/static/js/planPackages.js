import { apiRequest, showToast } from './messageToast.js';

(()=>{

    const productsButtons = document.querySelectorAll(".product-buttons")
    const cartSizeEl = document.getElementById("cart-size")

    document.addEventListener("DOMContentLoaded",()=>{
        productsButtons.forEach(product => {
            const pkgId = product.dataset.pkgId;
            const btnAdd = product.querySelector('.btn-add');
            const btnRemove = product.querySelector('.btn-remove');

            btnRemove.addEventListener("click",async ()=>{
                const res= apiRequest({method: 'DELETE',url:`/api/cart/remove/${pkgId}`})

                const size = parseInt(cartSizeEl.innerText);
                cartSizeEl.innerText = String(size - 1);
                if (!btnAdd){
                    product.parentElement.remove()
                }
                else{
                    btnAdd.classList.remove('d-none');
                    btnRemove.classList.add('d-none');
                }
            })

            if (btnAdd){
                btnAdd.addEventListener("click",async ()=>{
                    const res= await apiRequest({method: 'POST',url:"/api/cart/add",data:{pkgId:pkgId}})
                    btnRemove.classList.remove('d-none');
                    btnAdd.classList.add('d-none');
                    const size = parseInt(cartSizeEl.innerText);
                    cartSizeEl.innerText = String(size + 1);

                })
            }
        })
    })
})()
