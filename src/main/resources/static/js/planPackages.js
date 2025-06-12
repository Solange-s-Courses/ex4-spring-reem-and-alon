(()=>{
    const WEEKLY_MONTH="once a week per month"
    const ONE_TIME="one time"
    const CUSTOM="custom";
    const openOfferBtn=document.getElementById("open-offer-btn")
    const form=document.getElementById("pkg-form")
    const titleInput= document.getElementById('title')
    const descriptionInput= document.getElementById('description')
    const priceInput= document.getElementById('price')
    const packageTypeInput= document.getElementById('packageType')


   const processInput = (()=>{

       const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
       const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


       const handleSubmit= async(e)=>{
           e.preventDefault()
           const inputs = {
               title: titleInput.value.trim(),
               description: descriptionInput.value.trim(),
               packageType: packageTypeInput.value.trim(),
               price: priceInput.value.trim(),
           };
           try {
               await axios.post('/package-plan/add-package', inputs, {
                   headers: {
                       'Content-Type': 'application/json',
                       [csrfHeader]: csrfToken
                   }
               })
               window.location = "/admin";
           } catch (err) {
               console.log("error", err);
           }

       }
       return{
           handleSubmit
       }
    })()



    document.addEventListener("DOMContentLoaded",()=>{
        form.addEventListener('submit',processInput.handleSubmit)
    })
})()
