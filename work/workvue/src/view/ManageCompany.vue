<template>
    <div class="company">
        <el-container>
            <el-header>
                <el-row>
                    <el-col :span="24">
                        <el-button @click="showForm" type="primary">添加</el-button>
                        <el-button type="primary" @click="deleteCompany">删除</el-button>
                    </el-col>
                </el-row>
            </el-header>
            <el-main>
                <el-table @selection-change="handleSelectionChange" :data="tableData" border style="width: 100%;overflow: auto;" row-key="id">
                    <el-table-column type="selection" label="选择" />
                    <el-table-column type="index" label="编号" />
                    <el-table-column prop="name" label="名称" />
                    <el-table-column prop="address" label="地址" />
                    <el-table-column prop="job" label="职位" />
                    <el-table-column prop="salary" label="月薪" />
                    <el-table-column style="overflow: hidden;" prop="detail" label="简介" />
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
                <div><label>公司名称：<input v-model="company.name" type="text" :disabled="isDisabled"></label></div>
                <div><label>公司地址：<input v-model="company.address" type="text" :disabled="isDisabled"></label></div>
                <div><label>招聘职位：<input v-model="company.job" type="text" :disabled="isDisabled"></label></div>
                <div><label>每月薪资：<input v-model="company.salary" type="number" :disabled="isDisabled"></label></div>
                <div style="vertical-align: top;">
                    <label><span style="vertical-align: top;">公司简介：</span>
                        <textarea :disabled="isDisabled" v-model="company.detail" name="message" rows="10" cols="15" >
                            
                        </textarea>
                    </label>
                </div>
                <div>
                    <el-button @click="formIsShow = false" type="success">取消</el-button>
                    <el-button @click="addCompany" type="success" :disabled="isDisabled">确定</el-button>
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
    let company = reactive({})
    let selectCompany = reactive([])
    let isDisabled = ref(false)
    let status = ref()
    onMounted(()=>{
        axios.get("/api/getAllCompany",{
            headers: {
            'Authorization': JSON.parse(localStorage.getItem("user")).token
            },
            params: {
                start: 0,
                end: 20,
                //recruitmentuserId:JSON.parse(localStorage.getItem("user")).data.id
            }
        }
        ).then(resp => {
            console.log(resp)
            Object.assign(tableData,resp.data)
        },err => {

        })
    })

    function handleSelectionChange(newSelection){
        selectCompany.splice(0,selectCompany.length)
        if(newSelection.length>0){
            Object.assign(selectCompany,newSelection)
        }
    }

    function showForm(){
        Object.assign(company, {"id":"","name":"","address":"","job":"","salary":"","detail":""})
        formIsShow.value = true
        isDisabled.value = false
        status.value=1
    }

    function addCompany(){
        if(status.value == 1){
            axios.post("/api/addCompany",{
                name:company.name,
                address:company.address,
                job:company.job,
                salary:company.salary,
                detail:company.detail,
                recruitmentuserId:JSON.parse(localStorage.getItem("user")).data.id
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
            axios.post("/api/updateCompany",{
                id:company.id,
                name:company.name,
                address:company.address,
                job:company.job,
                salary:company.salary,
                detail:company.detail,
                recruitmentuserId:JSON.parse(localStorage.getItem("user")).data.id
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

    function deleteCompany(){
        let deleteId = []
        for(let i=0;i<selectCompany.length;i++){
            deleteId.push(selectCompany[i].id)
        }

         var ajax = new XMLHttpRequest();
            ajax.open("post","/api/deleteCompany");
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
        Object.assign(company,row)
        isDisabled.value = false
        status.value = 2
    }

    function query(row){
        formIsShow.value = true
        Object.assign(company,row)
        console.log(company)
        isDisabled.value = true
    }

</script>

<style scoped>
.company{
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
    width: 45rem;
    height: 50rem;
    background-color: rgb(29, 166, 190)
    
}

textarea {
  resize: none;
  font-size: 35px;
}


</style>