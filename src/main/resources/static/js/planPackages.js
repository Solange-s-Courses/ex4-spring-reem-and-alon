(()=>{
    const SUCCESS_TITLE = "Successfully added to cart!"
    const ERR_TITLE = "Error!"

    const toastEl = document.getElementById('main-toast');
    const toastTitle = document.getElementById('toast-title')
    const toastBody = document.getElementById('toast-body')
    const addItemForms = document.querySelectorAll(".add-to-cart-form")

    const getCookie = (name) => {
        const cookies = document.cookie.split(';');
        for (let cookie of cookies) {
            const [cookieName, cookieValue] = cookie.trim().split('=');
            if (cookieName === name) {
                return cookieValue;
            }
        }
        return null;
    };

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
                const response = await fetch('/api/cart/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'datatype': 'json',
                        'X-XSRF-TOKEN': getCookie('XSRF-TOKEN')
                    },
                    body: `pkgId=${encodeURIComponent(pkgId)}`,
                    credentials: 'same-origin'
                });
                const data = await response.text();
                if (response.ok)
                    showToast(SUCCESS_TITLE, data)
            }
            catch (err){
                showToast(SUCCESS_TITLE, err.response.data, true)
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
