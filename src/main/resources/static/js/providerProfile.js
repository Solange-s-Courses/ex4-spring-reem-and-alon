(()=>{
    const openOfferBtn=document.getElementById("open-offer-btn")
    const offerForm=document.getElementById("form")

    //  const createOfferBtn=document.getElementById("create-offer-btn")

    const toggleHtmlElements = (()=>{

        const toggleForm = ()=>{
            offerForm.classList.toggle("d-none");
            openOfferBtn.classList.toggle("d-none")
        }
        return{
            toggleForm
        }
    })()


    document.addEventListener("DOMContentLoaded",()=>{
        openOfferBtn.addEventListener("click",()=>toggleHtmlElements.toggleForm())
    })
})()
