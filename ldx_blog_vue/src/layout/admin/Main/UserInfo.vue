<template>
    <div style="padding: 5px; display: flex;flex-direction: column;align-items: center;width: 100%;height: 100%;">
        <el-upload class="avatar-uploader" :action="UPLOAD_SERVER_URL" :show-file-list="false"
            :headers="{ litubao_authentication: getAccessToken() }" :on-success="uploadUserAvatar">
            <img :src="user.avatarImgUrl" class="avatar">
        </el-upload>
        <!-- <i>粉丝数:{{ user.fans }}</i> -->
        <el-form ref="formRef" :model="user" label-width="100px" label-position="left">
            <el-form-item label="昵称">
                <el-input type="text" v-model="user.username" />
            </el-form-item>
            <el-form-item label="注册时间">
                <el-input type="text" v-model="user.createTime" disabled />
            </el-form-item>
            <el-form-item label="手机号">
                <el-input type="text" v-model="user.phone" />
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input type="text" v-model="user.email" />
            </el-form-item>
            <el-form-item label="个人简介">
                <el-input type="textarea" v-model="user.personalBrief" />
            </el-form-item>
            <el-form-item label="密码">
                <div style="display: flex;" v-if="!pwIptVisible">
                    <el-input type="input" v-model="user.password" disabled />&nbsp;&nbsp;<el-link icon="el-icon-edit"
                        @click="pwIptVisible = true" />
                </div>
                <el-input v-else type="input" v-model="user.newPassword" />
            </el-form-item>
            <el-form-item label="性别">
                <el-radio-group v-model="user.gender">
                    <el-radio :label="false">男生</el-radio>
                    <el-radio :label="true">女生</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="updateUserInfo">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import {
    getAccessToken,
} from '@/utils/accessToken'
import { mapActions, mapState } from 'pinia';
import { useUserStore } from '@/stores/user'
export default {
    data() {
        return {
            UPLOAD_SERVER_URL: process.env.VUE_APP_UPLOAD_SERVER,
            pwIptVisible: false,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            }
        }
    },
    created() {
        this.getUserInfo()
    },
    computed: {
        ...mapState(useUserStore, ['user'])
    },
    methods: {
        ...mapActions(useUserStore, ['getUserInfo', 'updateUserInfo', 'uploadUserAvatar']),
        getAccessToken,
    },
}
</script>
<style  scoped>
.avatar {
    width: 15vw;
    height: 15vw;
    border-radius: 50%;
    background-color: transparent;
}
</style>