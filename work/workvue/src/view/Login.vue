<template>
    <div class="login">
        <div class="position">
            <div><label>用户名：<input type="text" name="username" v-model="username"></label></div>
            <div><label>密&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" v-model="password"></label></div>
                <el-radio-group v-model="loginType" text-color="#409eff" fill="#ffffff">
                    <el-radio value="admin">管理员登录</el-radio>
                    <el-radio value="jobseekers">招工者登录</el-radio>
                    <el-radio value="aaa">求职者登录</el-radio>
                </el-radio-group>
            <div id="botton">
                <el-button @click="login" type="success" round>登录</el-button>
                <el-button @click="registered" type="success" round>注册</el-button>
            </div>
            
            
            
            
            
            <!-- <form action="/api/login" method="post"> -->
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
    import {useRouter} from "vue-router"
    let router = useRouter()
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
        let userData = resp.data
            if(userData.status == "200"){
                localStorage.setItem("user",JSON.stringify(userData))
                console.log(localStorage.getItem("user"))
                router.replace({
                    name:"recuritment"
                })
            }
        },err => {

        })
    }

    function registered(){

    }


</script>

<style scoped>
.login{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: rgb(51, 133, 148);
}
.position{
    display: grid;
    grid-template-columns: auto;
    grid-template-rows:  17% 17% 17% 17%;
    justify-content: center;
    align-content: center;
    width: 30rem;
    height: 20rem;
    background-color: aliceblue;
}

.position div{
    display: flex;
    justify-content: center;
    align-items: center;
}


input{
    height: 1.8rem;
    border: 3px solid red;
    border-radius: 10px;
}

#botton{
    display: grid;
    grid-template-columns: auto auto;
    grid-template-rows:  auto;
    grid-column-gap: 8%;
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
  color: rgb(72, 209, 17) !important;  /*使用 !important 提高优先级 */
}

</style>