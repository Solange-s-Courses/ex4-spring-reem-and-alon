import axios from 'axios'
(()=>{
    const WEEKLY_MONTH="once a week per month"
    const ONE_TIME="one time"
    const CUSTOM="custom";
    const openOfferBtn=document.getElementById("open-offer-btn")
    const form=document.querySelector('form')

    //  const createOfferBtn=document.getElementById("create-offer-btn")

   const processInput = (()=>{
       const titleInput= document.getElementById('title')
       const descriptionInput= document.getElementById('description')
       const priceInput= document.getElementById('price')
       const packageTypeInput= document.getElementById('packageType')

       const handleSubmit= async(e)=>{
           e.preventDefault()
           const inputs={
               title:titleInput.value.trim(),
               description:descriptionInput.value.trim(),
               price:priceInput.value.trim(),
               packageType:packageTypeInput.value.trim()
           }

           axios.post(form.action,inputs)
               .then(res=>{window.location="/admin"})
               .catch(()=>console.log("error"))

       }
       return{
           handleSubmit
       }
    })()



    document.addEventListener("DOMContentLoaded",()=>{
        form.addEventListener('submit',(e)=>processInput.handleSubmit(e))
    })
})()
