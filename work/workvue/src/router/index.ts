import { createRouter ,createWebHistory} from "vue-router";

import Login from "@/view/Login.vue"
import Recuritment from "@/view/Recruitment.vue"
import Jobseekers from "@/view/Jobseekers.vue";
import Registered from "@/view/Registered.vue";
import Admin from "@/view/Admin.vue";
import ManageRecruitment from "@/view/ManageRecruitment.vue";
import ManageJobseekers from "@/view/ManageJobseekers.vue";
import ManageCompany from "@/view/ManageCompany.vue";
import JobseekersApproval from "@/view/JobseekersApproval.vue"
import JobseekersJob from "@/view/JobseekersJob.vue"
import RecruitmentJob from "@/view/RecruitmentJob.vue"
import RecruitmentApproval from "@/view/RecruitmentApproval.vue"
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path:"/login",
            component:Login
        },
        {
            path:"/registered",
            component:Registered
        },
        {
            path:"/admin",
            component:Admin,
            children:[
                {
                    path:"manageRecruitment",
                    component:ManageRecruitment
                },
                {
                    path:"manageJobseekers",
                    component:ManageJobseekers
                },
                {
                    path:"manageCompany",
                    component:ManageCompany
                }
            ],
            beforeEnter (to, from, next) {
                if(localStorage.getItem("user")){
                    let user = JSON.parse(localStorage.getItem("user"))
                    if(user.data){
                        if("admin" == user.data.permissions){
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
            name:"recruitment",
            path:"/recruitment",
            component:Recuritment,
            children:[
                {
                    path:"recruitmentJob",
                    component:RecruitmentJob
                },
                {
                    path:"recruitmentApproval",
                    component:RecruitmentApproval
                }
                
            ],
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
            name:"jobseekers",
            path:"/jobseekers",
            component:Jobseekers,
            children:[
                {
                    path:"jobseekersJob",
                    component:JobseekersJob
                },
                {
                    path:"jobseekersApproval",
                    component:JobseekersApproval
                }
                
            ],
            beforeEnter (to, from, next) {
                if(localStorage.getItem("user")){
                    let user = JSON.parse(localStorage.getItem("user"))
                    if(user.data){
                        if("jobseekers" == user.data.permissions){
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
    if(to.path == "/login" || to.path== "/registered"){
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