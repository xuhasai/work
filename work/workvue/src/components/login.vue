<template>
    <div>
        <!-- <form action="/api/login" method="post"> -->
            <label>用户名：<input type="text" name="username" v-model="username"></label><br>
            <label>密  码：<input type="password" name="password" v-model="password"></label><br>
            <label>管理员登录：<input type="radio" v-model="loginType" name="loginType" value="admin" ></label><br>
            <label>求职者登录：<input type="radio" v-model="loginType" name="loginType" value="jobseekes"></label><br>
            <label>招工者登录<input type="radio" v-model="loginType" name="loginType" value="aaa"></label><br>
            <button @click="login">登录</button>
        <!-- </form> -->
    </div>
</template>

<script setup lang="ts">
    import {ref} from "vue"
    import axios from "axios";
    let username = ref("");
    let password = ref("");
    let loginType = ref("");

    function login(){
        axios.post("/api/perform_login",{
            username:username.value,
            password:password.value,
            loginType:loginType.value
        },
        {
            headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
    ).then(resp => {
            console.log(resp.data)
        },err => {

        })
    }


</script>

<style scoped>

</style>