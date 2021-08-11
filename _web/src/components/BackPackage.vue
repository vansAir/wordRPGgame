<template>
  <div id="good-box">
    <img
      src="@/assets/func/back_package.png"
      alt=""
      srcset=""
      @click.stop="openBack()"
    />
    <div id="good-detail-box" @click.stop.stop="backDialogVisible = false">
      <el-dialog
        title="背包"
        :visible.sync="backDialogVisible"
        width="80%"
        :fullscreen="true"
        center
      >
        <span id="gouba">你就这些勾八东西!</span>
        <div id="good-detail-style">
          <div class="good-detail" v-for="good in goods" :key="good.id">
            <span :class="good.goodsRank">{{ good.goodsName }}</span
            >x <span>{{ good.nums }}</span>
            <el-button v-if="good.canUse" type="primary" class="use-btn" round
              >立马使用</el-button
            >
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click.stop="backDialogVisible = false"
            >拉上拉丝</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  name: "BackPackage",
  data() {
    return {
      backDialogVisible: false,
      goods: [],
    };
  },
  methods: {
    openBack() {
      this.axios.get("/backpack/all").then((res) => {
        this.goods = res.data.data;
      });
      this.backDialogVisible = true;
    },
  },
};
</script>

<style lang="less" scoped>
#good-box {
  img {
    position: fixed;
    top: 190px;
    right: 0;
    width: 64px;
    height: 64px;
    margin: 10px 10px;
  }
  #good-detail-box {
    #gouba {
      font-size: 30px;
      color: rgb(255, 217, 0);
    }
    #good-detail-style {
      .good-detail {
        position: relative;
        height: 50px;
        margin: 10px 10px;
        font-size: 15px;
        .use-btn {
          position: absolute;
          right: 0;
          height: 30px;
          width: 80px;
          padding: 0;
        }
      }
      .EX {
        background: linear-gradient(
          to right,
          rgb(231, 203, 110),
          rgb(238, 27, 27)
        );
        -webkit-background-clip: text;
        color: transparent;
      }
      //
      .Z {
        background: linear-gradient(
          to right,
          rgb(134, 231, 110),
          rgb(238, 189, 27)
        );
        -webkit-background-clip: text;
        color: transparent;
      }
      .SSS {
        background: linear-gradient(
          to right,
          rgb(156, 110, 231),
          rgb(238, 27, 80)
        );
        -webkit-background-clip: text;
        color: transparent;
      }
      .SS {
        color: rgb(255, 180, 41);
      }
      .S {
        color: rgb(255, 251, 0);
      }
      .A {
        color: rgb(255, 0, 221);
      }
      .B {
        color: rgb(0, 255, 13);
      }
      .R {
        color: rgb(0, 102, 255);
      }
      .N {
        // animation-name: flash; //flash为要使用的动画效果名，在这里不需要加animate前缀
        // animation-duration: 3s;
        color: aqua;
      }
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
