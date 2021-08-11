<template>
  <div id="luck-box">
    <img
      src="@/assets/func/luck_draw.png"
      alt=""
      srcset=""
      @click.stop="openLuck()"
    />
    <div id="luck-detail" @click.stop.stop="luckDialogVisible = false">
      <el-dialog
        title="抽奖"
        :visible.sync="luckDialogVisible"
        width="80%"
        :fullscreen="true"
        center
      >
        <span id="gouba">你狗屎运也太勾八好了!</span>
        <p v-for="luck in lucklist" :key="luck.index">
          {{ luck[0] }}x{{ luck[1] }}
        </p>
        <span slot="footer" class="dialog-footer">
          <el-button @click.stop="luckDialogVisible = false"
            >下次再来</el-button
          >
          <el-button type="primary" @click.stop="happyLuck">快乐十连</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  name: "LuckDraw",
  data() {
    return {
      luckDialogVisible: false,
      lucklist: [],
    };
  },
  methods: {
    openLuck() {
      this.luckDialogVisible = true;
    },
    happyLuck() {
      this.axios.get("/luckdraw/10happy").then((res) => {
        if (res.data.code == 200) {
          this.lucklist = this.map2list(res.data.data);
        } else {
          this.$notify.error({
            title: "哦豁",
            message: res.data.message,
          });
        }
      });
    },
    map2list: function(map) {
      var list = [];
      for (var key in map) {
        list.push([key, map[key]]);
      }
      return list;
    },
  },
};
</script>

<style lang="less" scoped>
#luck-box {
  img {
    position: fixed;
    top: 120px;
    right: 0;
    width: 64px;
    height: 64px;
    margin: 10px 10px;
  }
  #luck-detail {
    #gouba {
      font-size: 30px;
      color: rgb(255, 217, 0);
    }
  }
  .dialog-footer {
    position: fixed;
    left: 0;
    width: 100vw;
    bottom: 20px;
  }
}
</style>
