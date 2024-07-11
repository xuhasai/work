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
    if(to.path == "/login"){
        next()
    }else{
        if(localStorage.getItem("user")){
            next()
        }else{
            router.replace("/login")
        }
    }
})


export default router