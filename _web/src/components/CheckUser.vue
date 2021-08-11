<template>
  <div id="check-box">
    <p style="color:red">任何地方都不允许出现空格</p>
    <p style="color:red">密码至少6位,其他至少4位</p>
    <p style="color:red">用户名不能为中文</p>
    <el-input
      maxlength="12"
      v-model="userName"
      placeholder="用户名(登陆账号)"
    ></el-input
    ><el-input
      maxlength="12"
      v-model="prefixName"
      placeholder="请输入角色称号"
    ></el-input
    ><el-input
      maxlength="12"
      v-model="realName"
      placeholder="请输入角色名"
    ></el-input>
    <el-input
      maxlength="12"
      v-model="userPassword"
      placeholder="请输入密码"
      show-password
    ></el-input>
    <el-button id="check-over" @click="checkName" plain>验证</el-button>
  </div>
</template>
<script>
export default {
  name: "CheckUser",
  data() {
    return {
      userName: "",
      prefixName: "",
      realName: "",
      userPassword: "",
    };
  },
  methods: {
    checkName() {
      this.userName.replace(/\s+/g, "");
      this.prefixName.replace(/\s+/g, "");
      this.realName.replace(/\s+/g, "");
      this.userPassword.replace(/\s+/g, "");

      var patrn = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
      if (patrn.exec(this.userName)) {
        this.$notify.error({
          title: "哦豁",
          message: "用户名无法夹杂中文",
        });
        return;
      }
      this.axios
        .get(
          "/user/checkname?userName=" +
            this.userName +
            "&prefixName=" +
            this.prefixName +
            "&realName=" +
            this.realName +
            "&userPassword=" +
            this.userPassword
        )
        .then((res) => {
          if (res.data.code == 200) {
            this.$emit(
              "checkSuc",
              this.userName,
              this.prefixName,
              this.realName,
              this.userPassword
            );
          } else {
            this.$notify.error({
              title: "错误",
              message: res.data.message,
            });
          }
        });
    },
  },
};
</script>

<style lang="less" scoped>
.el-input {
  width: 50%;
  margin: 10px 10px;
}
#check-over {
  position: fixed;
  right: 10px;
  bottom: 10px;
}
</style>
