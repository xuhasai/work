<template>
    <div class="admin">
        <el-container>
            <el-aside width="200px" height="400px">
                <el-menu
                    active-text-color="#ffd04b"
                    background-color="#545c64"
                    class="el-menu-vertical-demo"
                    text-color="#fff"
                    router
                    default-active="/jobseekers/jobseekersJob"
                    @open="handleOpen"
                    @close="handleClose"
                    >
                    <el-sub-menu index="/jobseekers">
                        <template #title>
                            <span>职位信息</span>
                        </template>
                            <el-menu-item index="/jobseekers/jobseekersJob">招聘信息</el-menu-item>
                            <el-menu-item index="/jobseekers/jobseekersApproval">申请信息</el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main><RouterView></RouterView></el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script setup lang="ts">
import Logout from '@/components/Logout.vue';
import {ref} from "vue"
import {useRouter} from "vue-router"
let router = useRouter()
function handleOpen(key, keyPath){
  console.log(key, keyPath)
  router.replace("/jobseekers/jobseekersJob")
}
function handleClose(key, keyPath){
  console.log(key, keyPath)
}

</script>

<style scoped>
.admin{
    background-color: rgb(201, 68, 168);
}
.el-menu-vertical-demo{
    height: 100%;
}
</style>