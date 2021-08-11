<template>
  <div class="" id="main-box">
    <div class="" id="home-box">
      <router-link to="/">
        <el-button type="info">返回主页</el-button>
      </router-link>
    </div>
    <div class="register-box">
      <component
        :is="currentCom"
        @checkSuc="checkSucFun"
        @selectArea="selectAreaFun"
        @answerOver="answerOverFun"
      ></component>
    </div>
  </div>
</template>
<script>
import CheckUser from "../components/CheckUser.vue";
import AreaSelect from "../components/AreaSelect.vue";
import Answer from "../components/Answer.vue";

export default {
  name: "UserRegister",
  data() {
    return {
      currentCom: "CheckUser",
      users: {
        area: "",
        userName: "",
        prefixName: "",
        realName: "",
        userPassword: "",
      },
    };
  },
  components: {
    CheckUser,
    AreaSelect,
    Answer,
  },
  methods: {
    checkSucFun(userName, prefixName, realName, userPassword) {
      this.users.userName = userName;
      this.users.prefixName = prefixName;
      this.users.realName = realName;
      this.users.userPassword = userPassword;
      this.currentCom = "AreaSelect";
    },
    selectAreaFun(area) {
      this.currentCom = "Answer";
      this.users.area = area;
    },
    answerOverFun(user) {
      this.users = { ...this.users, ...user };
      this.axios.post("/user/register", JSON.stringify(this.users));
      this.$notify({
        title: "成功",
        message: "注册成功",
        type: "success",
      });
      this.$router.push("/");
    },
  },
};
</script>

<style lang="less" scoped>
#main-box {
}
</style>
