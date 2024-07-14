<template>
    <div class="login">
        <div class="position">
            <div><label>用户名：<input type="text" v-model="userRegistered.username"></label></div>
            <div><label>密&nbsp;&nbsp;&nbsp;码：<input type="password" v-model="userRegistered.password"></label></div>
            <div><label>姓&nbsp;&nbsp;&nbsp;名：<input type="text" v-model="userRegistered.name"></label></div>
            <div><label>年&nbsp;&nbsp;&nbsp;龄：<input type="number" v-model="userRegistered.age"></label></div>
            <div>
                性&nbsp;&nbsp;&nbsp;别：
                    <el-radio-group v-model="userRegistered.gender" text-color="#409eff" fill="#ffffff">
                        <el-radio value="nan">男</el-radio>
                        <el-radio value="nv">女</el-radio>
                    </el-radio-group>
            </div>    
            <el-radio-group v-model="userRegistered.permissions" text-color="#409eff" fill="#ffffff">
                    <el-radio value="recruitment">招工者注册</el-radio>
                    <el-radio value="jobseekers">求职者注册</el-radio>
            </el-radio-group>
            <div id="botton">
                <el-button @click="router.replace('/login')" type="success" round>返回登录</el-button>
                <el-button @click="registered" type="success" round>注册</el-button>
            </div>
        </div>
    </div>
    
</template>

<script setup lang="ts">
    import {reactive} from "vue"
    import axios from "axios";
    import {useRouter} from "vue-router"
    import { ElMessage } from 'element-plus'
    let router = useRouter()
    let userRegistered = reactive({})

    function registered(){
        axios.post("/api/registered",{
            username:userRegistered.username,
            password:userRegistered.password,
            name:userRegistered.name,
            age:userRegistered.age, 
            gender:userRegistered.gender,
            permissions:userRegistered.permissions
        }
    ).then(resp => {
            ElMessage({
                message: resp.data.message,
                type: 'success',
                plain: true,
            })
            if(resp.data.status == "200"){
                router.replace("/login")
            }
        },err => {

        })
    }

    // function login(){
        
    // }


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