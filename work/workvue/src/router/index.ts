import { createRouter ,createWebHistory} from "vue-router";

import Login from "@/view/Login.vue"
import Recuritment from "@/view/Recruitment.vue"
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path:"/",
            component:Login
        },
        {
            name:"recuritment",
            path:"/recuritment",
            component:Recuritment
        }
    ]
})

export default router