<template>
  <div id="main-box">
    <!--天命盒子-->
    <div id="username">
      <div id="fate-box" @click="showFate">
        <span>{{ users.fate }}</span>
      </div>
      <el-dialog title="提示" :visible.sync="fateVisible" width="80%">
        <span>{{ users.fateDesc }}</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="fateVisible = false">行!</el-button>
        </span>
      </el-dialog>
      <div id="name-box">
        <span
          >{{ users.area }} {{ users.prefixName }} {{ users.realName }}
        </span>
      </div>
    </div>
    <!--装备盒子-->
    <Equ :userinfoEqu="users" @changeEqu="getUserInfo"></Equ>
    <!--用户信息-->
    <FuncInfo :userinfo="users"></FuncInfo>
    <!--抽奖盒子-->
    <LuckDraw></LuckDraw>
    <!--用户背包-->
    <BackPackage></BackPackage>
    <div id="logout-btn">
      <el-button @click="logout" type="info">退出登录</el-button>
    </div>
  </div>
</template>
<script>
import Equ from "../components/Equ.vue";
import FuncInfo from "../components/FuncInfo.vue";
import LuckDraw from "../components/LuckDraw.vue";
import BackPackage from "../components/BackPackage.vue";
export default {
  name: "UserInfo",
  data() {
    return {
      //用户实体
      users: {},
      fateVisible: false,
    };
  },
  created: function() {
    this.getUserInfo();
  },
  components: {
    Equ,
    FuncInfo,
    LuckDraw,
    BackPackage,
  },
  methods: {
    //展示天命
    showFate() {
      this.fateVisible = true;
    },
    //注销用户
    logout() {
      this.Cookies.remove("token");
      this.$router.push("/");
    },
    //获取用户基础信息
    getUserInfo() {
      this.axios.get("/user/info").then((res) => {
        this.users = res.data.data;
      });
    },
  },
};
</script>

<style lang="less" scoped>
#main-box {
  #username {
    height: 100px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 10px;
    #fate-box {
      margin: 10px;
      border-radius: 15px;
      height: 20px;
      width: 100px;
      background-color: rgba(243, 47, 47, 0.7);
      font-weight: bolder;
      color: aliceblue;
    }
    #name-box {
    }
  }

  #logout-btn {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }
}
</style>
