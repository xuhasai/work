<template>
    <div class="login">
        <div class="position">
        <!-- <form action="/api/login" method="post"> -->
            <label>用户名：<input type="text" name="username" v-model="username"></label><br>
            <label>密  码：<input type="password" name="password" v-model="password"></label><br>
            <el-radio-group v-model="loginType" text-color="#409eff" fill="#ffffff">
                <el-radio value="admin">管理员登录</el-radio>
                <el-radio value="jobseekers">招工者登录</el-radio>
                <el-radio value="aaa">求职者登录</el-radio>
            </el-radio-group>
            <el-button @click="login" type="success" round>登录</el-button>
            

            <!-- <label>管理员登录：<input type="radio" v-model="loginType" name="loginType" value="admin" ></label><br>
            <label>求职者登录：<input type="radio" v-model="loginType" name="loginType" value="jobseekers"></label><br>
            <label>招工者登录<input type="radio" v-model="loginType" name="loginType" value="aaa"></label><br>
            <button @click="login">登录</button> -->
        <!-- </form> -->
        </div>
    </div>
    
</template>

<script setup lang="ts">
    import {ref} from "vue"
    import axios from "axios";
    let username = ref("");
    let password = ref("");
    let loginType = ref("");

    function login(){
        axios.post("/api/login",{
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
.login{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}
.position{
    width: 35%;
    height: 35%;
    background-color: aliceblue;
}

/* 修改单选框文字颜色 */
/* 
::v-deep .el-radio__label {
  color: red !important; 使用 !important 提高优先级 
}*/

/* 修改未选中状态下的单选框文字颜色 */
 ::v-deep .el-radio:not(.is-checked) .el-radio__label {
  color: rgb(0, 0, 0) !important; /*使用 !重要 提高优先级 */
}

/* 修改选中状态下的单选框文字颜色 */
::v-deep .el-radio.is-checked .el-radio__label {
  color: rgb(243, 2, 2) !important;  /*使用 !important 提高优先级 */
}

</style>