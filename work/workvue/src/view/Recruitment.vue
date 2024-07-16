<template>
    <div class="admin">
        <el-container>
            <el-aside width="200px" height="400px">
                <el-menu
                    active-text-color="#ffd04b"
                    background-color="#545c64"
                    class="el-menu-vertical-demo"
                    default-active="/recruitment/recruitmentJob"
                    text-color="#fff"
                    router
                    @open="handleOpen"
                    @close="handleClose"
                >
                    <el-sub-menu index="/recruitment">
                        <template #title>
                            <span>职位信息</span>
                        </template>
                            <el-menu-item index="/recruitment/recruitmentJob">发布信息</el-menu-item>
                            <el-menu-item index="/recruitment/recruitmentApproval">审批信息</el-menu-item>
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
import {useRouter} from "vue-router"
let router = useRouter()
function handleOpen(key, keyPath){
  console.log(key, keyPath)
  router.replace("/recruitment/recruitmentJob")
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