<template>
    <div class="admin">
        <el-container>
            <el-aside width="200px" height="400px">
                <el-menu
                    active-text-color="#ffd04b"
                    background-color="#545c64"
                    class="el-menu-vertical-demo"
                    default-active="1-1"
                    text-color="#fff"
                    router
                    @open="handleOpen"
                    @close="handleClose"
                >
                    <el-sub-menu index="/user">
                        <template #title>
                            <span>用户管理</span>
                        </template>
                            <el-menu-item index="/admin/manageRecruitment">招聘用户</el-menu-item>
                            <el-menu-item index="/admin/manageJobseekers">求职用户</el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="/company">
                        <template #title>
                            职位管理
                        </template>
                        <el-menu-item index="/admin/manageCompany">职位信息</el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <el-container>
                    <Logout></Logout>
                <el-main><RouterView></RouterView></el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script setup lang="ts">
import Logout from '@/components/Logout.vue';
function handleOpen(key, keyPath){
  console.log(key, keyPath)
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