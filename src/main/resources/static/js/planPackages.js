(()=>{
    const SUCCESS_TITLE = "Successfully added to cart!"
    const ERR_TITLE = "Error!"

    const toastEl = document.getElementById('main-toast');
    const toastTitle = document.getElementById('toast-title')
    const toastBody = document.getElementById('toast-body')
    const addItemForms = document.querySelectorAll(".add-to-cart-form")

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // axios.post('/your-url', data, {
    //     headers: {
    //         [csrfHeader]: csrfToken
    //     }
    // });

    //
    // const getCookie = (name) => {
    //     const cookies = document.cookie.split(';');
    //     for (let cookie of cookies) {
    //         const [cookieName, cookieValue] = cookie.trim().split('=');
    //         if (cookieName === name) {
    //             return cookieValue;
    //         }
    //     }
    //     return null;
    // };

    const showToast = (title, message,  isError = false) => {
        toastTitle.textContent = title;
        toastBody.textContent = message;
        toastEl.classList.toggle('bg-danger', isError);
        toastEl.classList.toggle('text-white', isError);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    }

    const restApiRequests = (() => {
        const postRequest = async (e) => {
            e.preventDefault()
            const form = e.target;
            const pkgId = form.querySelector('input[name="pkgId"]').value;
            console.log('pkgId value:', pkgId);
            try {
                const response = await axios.post('/api/cart/add', new URLSearchParams({ pkgId: pkgId }).toString(), {
                    headers: {[csrfHeader]: csrfToken},
                    // body: new URLSearchParams({pkgId: pkgId}).toString(),
                });
                showToast(SUCCESS_TITLE, response.data)
            }
            catch (err){
                showToast(ERR_TITLE, err.response?.data, true)
            }

        }

        return {
            postRequest
        }
    })()



    document.addEventListener("DOMContentLoaded",()=>{
        addItemForms.forEach(form => form.addEventListener('submit', (e) => restApiRequests.postRequest(e)))
    })
})()
