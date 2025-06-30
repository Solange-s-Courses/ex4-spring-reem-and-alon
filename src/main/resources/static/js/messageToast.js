(()=>{
    const SUCCESS_TITLE = "Action success!"
    const ERR_TITLE = "Error!"

    const addToCartForms = document.querySelectorAll(".add-to-cart-form")
    const removeFromCartForms=document.querySelectorAll(".remove-from-cart-form")
    const isCartPage = window.location.pathname.includes('/checkout');
    const cartSizeEl = document.getElementById("cart-size")

    const toastEl = document.getElementById('main-toast');
    const toastTitle = document.querySelector('.toast-title')
    const toastBody = document.querySelector('.toast-body')
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const showToast = (title, message,  isError = false) => {
        toastTitle.textContent = title;
        toastBody.innerHTML = `
                <i class="${isError ? 'bi bi-exclamation-triangle-fill' : 'bi bi-check-circle-fill'}"></i>
                <span>${message}</span>
        `;
        toastEl.classList.remove('text-bg-danger', 'text-bg-success');
        toastEl.classList.add(isError ? 'text-bg-danger' : 'text-bg-success');
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    }

    const cartActions = {
        add: async (form) => {
            const response = await fetch('/api/cart/add', {
                method: 'POST',
                headers: {
                    [csrfHeader]: csrfToken,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                        pkgId: form.querySelector("input[name='pkgId']").value,
                        subPkgName: form.querySelector("input[name='subPkgName']").value
                })
            });

            if (!response.ok) throw new Error(await response.text());

            const data = await response.text();
            showToast(SUCCESS_TITLE, data);
            cartSizeEl.innerText = String(parseInt(cartSizeEl.innerText) + 1);

         // UI updates
            form.querySelector('.btn-add')?.classList.add('d-none');
            form.querySelector('.btn-remove')?.classList.remove('d-none');
        },

        remove: async (form) => {
            const response = await fetch('/api/cart/remove', {
                method: 'DELETE',
                headers: {
                    [csrfHeader]: csrfToken,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    pkgId: form.querySelector("input[name='pkgId']").value,
                    subPkgName: form.querySelector("input[name='subPkgName']").value
                })
            });

            if (!response.ok) throw new Error(await response.text());

            const data = await response.text();
            showToast(SUCCESS_TITLE, data);
            cartSizeEl.innerText = String(parseInt(cartSizeEl.innerText) - 1);

            if (isCartPage) {
                form.closest(".card").remove();
            } else {
                form.querySelector('.btn-remove')?.classList.add('d-none');
                form.querySelector('.btn-add')?.classList.remove('d-none');
            }
        }
    };

    const handleCartRequest = async (e) => {
        e.preventDefault();

        try {
            const hasRemoveBtn = e.target.querySelector('.btn-remove:not(.d-none)');
            const action = hasRemoveBtn ? 'remove' : 'add';
            await cartActions[action](e.target);
        } catch (err) {
            showToast(ERR_TITLE, err.message, true);
        }
    };


    document.addEventListener("DOMContentLoaded",()=>{
        addToCartForms.forEach(form => {
            form.addEventListener("submit",  (e)=> handleCartRequest(e))
        })
        removeFromCartForms.forEach(form=>{
            form.addEventListener("submit",(e)=> handleCartRequest(e))
        })
    })
})()