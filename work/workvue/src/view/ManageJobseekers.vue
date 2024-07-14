<template>
    <div class="Jobseekers">
        <el-container>
            <el-header>
                <el-row>
                    <el-col :span="24">
                        <el-button @click="showForm" type="primary">添加</el-button>
                        <el-button type="primary" @click="deleteJobseekers">删除</el-button>
                    </el-col>
                </el-row>
            </el-header>
            <el-main>
                <el-table @selection-change="handleSelectionChange" :data="tableData" border style="width: 100%;overflow: auto;" row-key="id">
                    <el-table-column type="selection" label="选择" />
                    <el-table-column type="index" label="编号" />
                    <el-table-column prop="username" label="用户名" />
                    <el-table-column prop="password" label="密码" />
                    <el-table-column prop="name" label="姓名" />
                    <el-table-column prop="gender" label="性别" />
                    <el-table-column prop="age" label="年龄" />
                    <el-table-column label="Operations">
                        <template #default="scope">
                            <el-button @click="query(scope.row)" >
                                查看
                            </el-button>
                            <el-button @click="edit(scope.row)" >
                                修改
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>



        <div v-if="formIsShow" class="cover">
            <div class="form">
                <div><label>用户名：<input v-model="jobseekers.username" type="text" :disabled="isDisabled"></label></div>
                <div><label>密码：<input v-model="jobseekers.password" type="text" :disabled="isDisabled"></label></div>
                <div><label>姓名：<input v-model="jobseekers.name" type="text" :disabled="isDisabled"></label></div>
                <div>
                    性&nbsp;&nbsp;&nbsp;别：
                    <el-radio-group :disabled="isDisabled" v-model="jobseekers.gender" text-color="#409eff" fill="#ffffff">
                        <el-radio value="男">男</el-radio>
                        <el-radio value="女">女</el-radio>
                    </el-radio-group>
                </div>
                <div><label>年龄：<input v-model="jobseekers.age" type="number" :disabled="isDisabled"></label></div>
                <div>
                    <el-button @click="formIsShow = false" type="success">取消</el-button>
                    <el-button @click="addJobseekers" type="success" :disabled="isDisabled">确定</el-button>
                </div>
               
                
                
                
                
                
                
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
    import {ref , reactive , onMounted} from "vue"
    import {useRouter} from "vue-router"
    import axios from 'axios';
    let router = useRouter()
    let formIsShow = ref(false);
    let tableData = reactive([]);
    let jobseekers = reactive({})
    let selectJobseekers = reactive([])
    let isDisabled = ref(false)
    let status = ref()
    onMounted(()=>{
        getAllRecruitment()
        
    })

    function showForm(){
        Object.assign(jobseekers, {"id":"","username":"","password":"","name":"","gender":"","age":""})
        formIsShow.value = true
        isDisabled.value = false
        status.value=1
    }

    function handleSelectionChange(newSelection){
        selectJobseekers.splice(0,selectJobseekers.length)
        if(newSelection.length>0){
            Object.assign(selectJobseekers,newSelection)
        }
    }

    function addJobseekers(){
        if(status.value == 1){
            axios.post("/api/registered",{
                username:jobseekers.username,
                password:jobseekers.password,
                name:jobseekers.name,
                age:jobseekers.age, 
                gender:jobseekers.gender,
                permissions: "jobseekers"
            },
            {
                headers: {
                'Authorization': JSON.parse(localStorage.getItem("user")).token
                }
            }
            
            ).then(resp => {
                
            },err => {

            })
        }else if(status.value == 2){
            axios.post("/api/updateJobSeekers",{
                id:jobseekers.id,
                username:jobseekers.username,
                password:jobseekers.password,
                name:jobseekers.name,
                age:jobseekers.age, 
                gender:jobseekers.gender,
                permissions: "jobseekers"
            },
            {
                headers: {
                'Authorization': JSON.parse(localStorage.getItem("user")).token
                }
            }
            
            ).then(resp => {
                
            },err => {

            })
        }
        
        formIsShow.value = false
        router.go(0);
        //location.reload()
    }

    function deleteJobseekers(){
        let deleteId = []
        for(let i=0;i<selectJobseekers.length;i++){
            deleteId.push(selectJobseekers[i].id)
        }

         var ajax = new XMLHttpRequest();
            ajax.open("post","/api/deleteJobSeekers");
            //ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            ajax.setRequestHeader("Content-Type","application/json");
            ajax.setRequestHeader("Authorization",JSON.parse(localStorage.getItem("user")).token);
            ajax.send(JSON.stringify(deleteId));
            ajax.onreadystatechange=function(){
                if(ajax.readyState == 4){
                    if(ajax.status == 200){
                        router.go(0);
                    }
                }
            } 
    }

    function edit(row){
        formIsShow.value = true
        Object.assign(jobseekers,row)
        isDisabled.value = false
        status.value = 2
    }

    function query(row){
        formIsShow.value = true
        Object.assign(jobseekers,row)
        console.log(jobseekers)
        isDisabled.value = true
    }


    function getAllRecruitment(){
        axios.get("/api/getAllJobSeekers",{
            headers: {
            'Authorization': JSON.parse(localStorage.getItem("user")).token
            },
            params: {
                start: 0,
                end: 20,
            }
        }
        ).then(resp => {
            console.log(resp)
            Object.assign(tableData,resp.data)
        },err => {

        })
    }

</script>

<style scoped>
.Jobseekers{
    background-color: rgb(73, 126, 126);
}
input{
    width: 120px;
    height: 55px;
    font-size: 30px;
}
.cover{
    position: absolute;
    display: flex;
    justify-content:center;
    align-items: center;
    left: 0;
    top: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(78, 67, 67, 0.671);
    z-index: 3;
}

.form{
    display: grid;
    justify-content: center;
    align-content: space-evenly;
    width: 45rem;
    height: 50rem;
    background-color: rgb(29, 166, 190)
    
}

textarea {
  resize: none;
  font-size: 35px;
}

/* 修改未选中状态下的单选框文字颜色 */
::v-deep .el-radio:not(.is-checked) .el-radio__label {
  color: rgb(0, 0, 0) !important; /*使用 !重要 提高优先级 */
}

/* 修改选中状态下的单选框文字颜色 */
::v-deep .el-radio.is-checked .el-radio__label {
  color: rgb(72, 209, 17) !important;  /*使用 !important 提高优先级 */
}

</style>