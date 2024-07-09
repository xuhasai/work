import { createRouter ,createWebHistory} from "vue-router";

import Login from "@/view/Login.vue"
import Recuritment from "@/view/Recruitment.vue"
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path:"/login",
            component:Login
        },
        {
            name:"recuritment",
            path:"/recuritment",
            component:Recuritment,
            beforeEnter (to, from, next) {
                console.log("独享")
                if(localStorage.getItem("user")){
                    let user = JSON.parse(localStorage.getItem("user"))
                    if(user.data){
                        if("recruitment" == user.data.permissions){
                            next()
                        }else{
                            router.replace("/login")
                        }
                    }else{
                        router.replace("/login")
                    }
                }else{
                    router.replace("/login")
                }
                
            }
        },
        {
            path:'/',
            redirect:'/login'
        },
    ]
})
router.beforeEach((to, from, next) => {
    console.log("全局",JSON.parse(localStorage.getItem("user")).data)
    
    if(to.path == "/login"){
        console.log("全局5",JSON.parse(localStorage.getItem("user")).data)
        next()
    }else{
        console.log("全局2",JSON.parse(localStorage.getItem("user")).data)
        if(localStorage.getItem("user")){
            console.log("全局4",localStorage.getItem("user"))
            next()
        }else{
            console.log("全局3",localStorage.getItem("user"))
            router.replace("/login")
        }
    }
})


export default router