(()=>{





    document.addEventListener("DOMContentLoaded",()=>{
        document.querySelectorAll('.toast').forEach((toastEl) => {
            const delay = toastEl.classList.contains('bg-success') ? 2000 : 4000;
            const toast = new bootstrap.Toast(toastEl, { delay: delay });
            toast.show();
        });
    })
})()
