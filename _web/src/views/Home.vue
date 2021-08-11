<template>
  <div id="main-box">
    <div class="" id="regis-box">
      <router-link to="/UserRegister">
        <el-button type="info">注册通道</el-button>
      </router-link>
    </div>
    <div class="" id="logo-box">
      <img src="../assets/logo.png" alt="" srcset="" />
    </div>
    <div id="action-box" class="home">
      <el-input placeholder="请输入账号" v-model="users.userName" clearable>
      </el-input>
      <el-input
        placeholder="请输入密码"
        v-model="users.userPassword"
        show-password
      ></el-input>
      <el-button @click="login" type="info" round>登陆</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      users: {
        userName: "",
        userPassword: "",
      },
    };
  },
  methods: {
    login() {
      this.axios.post("/user/login", JSON.stringify(this.users)).then((res) => {
        if (res.data.code != 200) {
          this.$notify.error({
            title: "错误",
            message: res.data.message,
          });
        } else {
          this.Cookies.set("token", res.data.data);
          this.$router.push("/userInfo");
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
#main-box {
  height: 100vh;
  #logo-box {
    img {
      position: absolute;
      width: 200px;
      top: 17vh;
      left: 25vw;
    }
  }

  #action-box {
    position: absolute;
    top: 40vh;
    left: 25vw;
    width: 200px;
    height: 200px;
    border-radius: 15px;
    opacity: 0.3;
    background: #000;
    .el-input {
      margin: 10px 0;
      width: 80%;
    }
  }
}
</style>
