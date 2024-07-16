<template>
    <div class="recruitment">
        <el-container>
            <el-header>
                <el-row>
                    <Logout></Logout>
                    <label>公司名称：<input v-model="searchCondition.name" type="text" ></label>
                    <label>公司地址：<input v-model="searchCondition.address" type="text"></label>
                    <label>招聘职位：<input v-model="searchCondition.job" type="text"></label>
                    <label>
                        每月薪资：
                        <input v-model="searchCondition.salary1" type="number">-------
                        <input v-model="searchCondition.salary2" type="number">
                    </label>
                    <el-button @click="searchCompany" type="primary">搜索</el-button>
                    <el-button @click="reset" type="primary">重置</el-button>
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
                    <el-table-column prop="detail" label="简介" />
                    <el-table-column prop="status" label="审批状态">
                        <template #default="scope">
                            <span v-if="scope.row.status == 1" style="color: #afa51c;">待审批</span>
                            <span v-else-if="scope.row.status == 2" style="color: #10c02d;">已通过</span>
                            <span v-else-if="scope.row.status == 3" style="color: #be0e0e;">已拒绝</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template #default="scope">
                            <el-button @click="query(scope.row)" >
                                查看
                            </el-button>
                            <el-button v-if="!scope.row.approval" @click="request(scope.row)" >
                                申请
                            </el-button>
                            <el-button v-if="scope.row.approval" :disabled="scope.row.approval">
                                已申请
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
                <div>
                    <label>公司简介：
                        <textarea :disabled="isDisabled" v-model="company.detail" name="message" rows="10" cols="15" >
                            
                        </textarea>
                    </label>
                </div>
                <div><el-button @click="formIsShow = false" type="primary">关闭</el-button></div>
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
    import {ref , reactive , onMounted} from "vue"
    import {useRouter} from "vue-router"
    import axios from 'axios';
    import Logout from '@/components/Logout.vue';
    let router = useRouter()
    let formIsShow = ref(false);
    let tableData = reactive([]);
    let company = reactive({})
    let selectCompany = reactive([])
    let isDisabled = ref(false)
    let searchCondition= reactive({})
    let approval = reactive({})
    let token = JSON.parse(localStorage.getItem("user")).token
    let ws = new WebSocket("ws:localhost:8080/socket?token="+token);

    ws.onopen = ()=>{
        
    }
    onMounted(()=>{
        getAllCompany()
    })

    function handleSelectionChange(newSelection){
        selectCompany.splice(0,selectCompany.length)
        if(newSelection.length>0){
            Object.assign(selectCompany,newSelection)
        }
    }

    function query(row){
        console.log(row)
        formIsShow.value = true
        Object.assign(company,row)
        isDisabled.value = true
    }

    function reset(){
        Object.assign(searchCondition,{"name":"","address":"","job":"","salary1":"","salary2":""})
        getAllCompany()
    }
    //ByStatus
    function getAllCompany(){
        axios.post("/api/getCompanyByApproval",
        {
            start: 0,
            end: 20,
            jobseekersId:JSON.parse(localStorage.getItem("user")).data.id
        },
        {
            headers: {
            'Authorization': JSON.parse(localStorage.getItem("user")).token
            }
        }
        ).then(resp => {
            console.log(resp.data)
            Object.assign(tableData,resp.data)
        },err => {

        })
    }

    function searchCompany(){
        axios.post("/api/searchCompany",
        {
            name:searchCondition.name,
            address:searchCondition.address,
            job:searchCondition.job,
            salary1:searchCondition.salary1,
            salary2:searchCondition.salary2,
            start: 0,
            end: 20,
        },
        {
            headers: {
            'Authorization': JSON.parse(localStorage.getItem("user")).token
            }
        }
        ).then(resp => {
            tableData.splice(0,tableData.length)
            Object.assign(tableData,resp.data)
            console.log(tableData)
            console.log(resp.data)
        },err => {

        })
    }

    function request(row){
        Object.assign(approval,{"companyId":"","recruitmentId":"","jobseekersId":"","status":""})
        console.log(row.recruitmentuserId)
        axios.post("/api/addApproval",
        {
            companyId:row.id,
            recruitmentId:row.recruitmentuserId,
            jobseekersId:JSON.parse(localStorage.getItem("user")).data.id,
            status:"1"
        },
        {
            headers: {
            'Authorization': JSON.parse(localStorage.getItem("user")).token
            }
        }
        ).then(resp => {

        },err => {

        })
        router.go(0);
    }

</script>

<style scoped>
.recruitment{
    background-color: aqua;
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