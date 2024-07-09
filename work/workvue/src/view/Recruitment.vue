<template>
    <div class="recruitment">
        <el-container>
            <el-header>
                <el-row>
                    <el-col :span="24">
                        <el-button @click="formIsShow = true" type="primary">添加</el-button>
                        <el-button type="primary">删除</el-button>
                    </el-col>
                </el-row>
            </el-header>
            <el-main>
                <el-table @selection-change="handleSelectionChange" :data="tableData" border style="width: 100%" row-key="name">
                    <el-table-column type="selection" />
                    <el-table-column prop="date" label="Date" />
                    <el-table-column prop="name" label="Name" />
                    <el-table-column prop="address" label="Address" />
                    <el-table-column label="Operations">
                        <template #default="scope">
                            <el-button >
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
                <label>公司名称：<input v-model="company.name" type="text" name="" id=""></label>
                <label>公司地址：<input v-model="company.address" type="text" name="" id=""></label>
                <label>招聘职位：<input v-model="company.job" type="text" name="" id=""></label>
                <label>每月薪资：<input v-model="company.salary" type="number" name="" id=""></label>
                <label>公司简介：
                    <textarea v-model="company.detail" name="message" rows="10" cols="15" >
                        
                    </textarea>
                </label>
                <el-button @click="formIsShow = false" type="primary">取消</el-button>
                <el-button @click="addCompany" type="primary">确定</el-button>
            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
    import {ref , reactive} from "vue"
    import {useRouter} from "vue-router"
    import axios from 'axios';
    let formIsShow = ref(true);
    let test = ref()
    let tableData = reactive({});
    let company = reactive({"name":1,"address":2,"job":3,"salary":4,"detail":5})
    function handleSelectionChange(newSelection){
        console.log(newSelection)
    }

    function edit(row){
        console.log(row)
    }


    function addCompany(){
        console.log(company)
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
        formIsShow.value = false
    }



    // 测试表格数据
    tableData = [
  {
    date: '2016-05-03',
    name: '1',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: '2',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: '3',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: '4',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

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
    width: 45rem;
    height: 50rem;
    background-color: rgb(216, 6, 6)
    
}

textarea {
  resize: none;
  font-size: 35px;
}

</style>