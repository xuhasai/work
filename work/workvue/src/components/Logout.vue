<template>
    <div>
        <el-button @click="logout" type="success" round>退出登录</el-button>
    </div>
</template>

<script setup lang="ts">
    import axios from "axios";
    import {useRouter} from "vue-router"
    import { ElMessage } from 'element-plus'
    let router = useRouter()

    function logout(){
        axios.post("/api/logout",{},
            {
                headers: {
                'Authorization': JSON.parse(localStorage.getItem("user")).token
                }
            }
            
            ).then(resp => {
                console.log(resp.data)
                ElMessage({
                    message: resp.data.message,
                    type: 'success',
                    plain: true,
                })
                if(resp.data.status == "200"){
                    localStorage.removeItem("user")
                    router.replace("/login")
                }
            },err => {

            })
    }

</script>

<style scoped>

</style>