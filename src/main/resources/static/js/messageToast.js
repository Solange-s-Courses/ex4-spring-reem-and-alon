const SUCCESS_TITLE = "Action success!"
const ERR_TITLE = "Error!"

const toastEl = document.getElementById('main-toast');
const toastTitle = document.getElementById('toast-title')
const toastBody = document.getElementById('toast-body')
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

export const showToast = (title, message,  isError = false) => {
    toastTitle.textContent = title;
    toastBody.textContent = message;
    toastEl.classList.toggle('bg-danger', isError);
    toastEl.classList.toggle('text-white', isError);
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
}

export const apiRequest = async (request) => {
    try{
        const response = await axios({
            ...request,
            headers: {[csrfHeader]: csrfToken},
        });
        showToast(SUCCESS_TITLE, response.data)
    }
    catch (err){
        showToast(ERR_TITLE, err.response?.data, true)
    }

}