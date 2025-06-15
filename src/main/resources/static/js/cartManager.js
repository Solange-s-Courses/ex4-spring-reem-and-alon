(()=>{
    const forms = document.querySelectorAll(".add-to-cart-form")
    const toastEl = document.getElementById('toast-feedback')
    const toastBody = document.getElementById('toast-body-msg')

    // הפונקציה שמציגה toast
    function showToast(msg, success=true) {
        toastBody.innerText = msg;
        toastEl.classList.remove('bg-success', 'bg-danger');
        toastEl.classList.add(success ? 'bg-success' : 'bg-danger');
        const toast = new bootstrap.Toast(toastEl, {delay: 3000});
        toast.show();
    }

    const processInput = (() => {
        const handleSubmit = async (e) => {
            e.preventDefault()
            const form = e.target;
            const pkgId = form.querySelector('input[name="pkgId"]').value;
            try {
                const response = await axios.post('/cart/add', {pkgId}, {
                    headers: { 'Content-Type': 'application/json' }
                })
                showToast(response.data, true);
            } catch (err) {
                const errorMsg =  err.response && err.response.data ? err.response.data : "Unknown error!";
                showToast(errorMsg, false);
            }
        }
        return { handleSubmit }
    })()

    document.addEventListener("DOMContentLoaded",()=>{
        console.log("Found forms:", forms.length);
        forms.forEach(form => form.addEventListener('submit',(e) =>processInput.handleSubmit(e)))
    })
})()
