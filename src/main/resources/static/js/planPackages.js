(()=>{
    const SUCCESS_TITLE = "Successfully added to cart!"
    const ERR_TITLE = "Error!"

    const toastEl = document.getElementById('main-toast');
    const toastTitle = document.getElementById('toast-title')
    const toastBody = document.getElementById('toast-body')
    const productsButtons = document.querySelectorAll(".product-buttons")
    const cartSizeEl = document.getElementById("cart-size")

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const showToast = (title, message,  isError = false) => {
        toastTitle.textContent = title;
        toastBody.textContent = message;
        toastEl.classList.toggle('bg-danger', isError);
        toastEl.classList.toggle('text-white', isError);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    }

    const restApiRequests = (() => {
        const apiRequest = async (request) => {

            const response = await axios({
                ...request,
                headers: {[csrfHeader]: csrfToken},
            });
            showToast(SUCCESS_TITLE, response.data)

        }

        return {
            apiRequest
        }
    })()



    document.addEventListener("DOMContentLoaded",()=>{
        productsButtons.forEach(product => {
            const pkgId = product.dataset.pkgId;
            const btnAdd = product.querySelector('.btn-add');
            const btnRemove = product.querySelector('.btn-remove');



            btnAdd.addEventListener("click",async ()=>{
                try{
                    const res= await restApiRequests.apiRequest({method: 'POST',url:"/api/cart/add",data:{pkgId:pkgId}})
                    btnRemove.classList.remove('d-none');
                    btnAdd.classList.add('d-none');
                    const size = parseInt(cartSizeEl.innerText);
                    cartSizeEl.innerText = String(size + 1);
                }
                catch (err){
                    showToast(ERR_TITLE, err.response?.data, true)
                }

            })
            btnRemove.addEventListener("click",async ()=>{
                try{
                    const res=restApiRequests.apiRequest({method: 'DELETE',url:`/api/cart/remove/${pkgId}`})
                    btnRemove.classList.add('d-none');
                    btnAdd.classList.remove('d-none');
                    const size = parseInt(cartSizeEl.innerText);
                    cartSizeEl.innerText = String(size - 1);
                }
                catch(err){
                    showToast(ERR_TITLE, err.response?.data, true)
                }

            })
        })
    })
})()
