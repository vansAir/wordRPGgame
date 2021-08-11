<template>
  <div id="main-box">
    <div id="equ-box">
      <img
        src="@/assets/equ/head.png"
        alt=""
        srcset=""
        @click.stop="equDetail(users.head)"
      />
      <img
        src="@/assets/equ/body.png"
        alt=""
        srcset=""
        @click.stop="equDetail(users.body)"
      />
      <img
        src="@/assets/equ/shoes.png"
        alt=""
        srcset=""
        @click.stop="equDetail(users.shoes)"
      />
      <img
        src="@/assets/equ/arms.png"
        alt=""
        srcset=""
        @click.stop="equDetail(users.arms)"
      />
      <img
        src="@/assets/equ/ornaments.png"
        alt=""
        srcset=""
        @click.stop="equDetail(users.ornaments)"
      />
    </div>
    <!--装备dialog-->
    <div id="equ-detail-box" @click.stop="equDialogVisible = false">
      <el-dialog
        title="装备详情"
        :visible.sync="equDialogVisible"
        width="80%"
        :fullscreen="true"
        center
      >
        <div id="name-rank">
          <span v-if="equ.level > 0" :class="equ.level > 9 ? 'overten' : ''"
            >+{{ equ.level }} </span
          ><span :class="equ.equRank"
            >{{ equ.equipmentName }}-{{ equ.equRank }}</span
          >
        </div>
        <div id="pro-detail">
          <div class="prop-detail">
            <span>攻击力:{{ parseFloat(equ.attack).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.attack) }}</span
            >
          </div>

          <div class="prop-detail">
            <span>防御力:{{ parseFloat(equ.defense).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.defense) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>生命值:{{ parseFloat(equ.health).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.health) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>幸运:{{ parseFloat(equ.lucky).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.lucky) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>速度:{{ parseFloat(equ.speed).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.speed) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>偷窃率:{{ parseFloat(equ.thief).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.thief) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>反击率:{{ parseFloat(equ.strikeBack).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.strikeBack) }}</span
            >
          </div>
          <div class="prop-detail">
            <span
              >即死判定:{{ parseFloat(equ.killProbability).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.killProbability) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>闪避率:{{ parseFloat(equ.missProbability).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.missProbability) }}</span
            >
          </div>
          <div class="prop-detail">
            <span
              >必中几率:{{
                parseFloat(equ.attackProbability).toFixed(2)
              }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.attackProbability) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>暴击率:{{ parseFloat(equ.criticalHit).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.criticalHit) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>连击率:{{ parseFloat(equ.doubleHit).toFixed(2) }} </span
            ><span style="color:#0066ff" v-if="equ.isStr"
              >+{{ strDetail(equ.doubleHit) }}</span
            >
          </div>
          <div class="prop-detail">
            <span>{{ equ.equDescribe }}</span>
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click.stop="equDialogVisible = false">下次一腚</el-button>
          <el-button type="primary" @click.stop="luckEqu()">换一把</el-button>
          <el-button type="primary" @click.stop="strEqu(0)">磨磨刀</el-button>
          <el-button type="primary" @click.stop="strEqu(11)">保级磨</el-button>
          <el-button type="primary" @click.stop="strEqu(12)">保碎磨</el-button>
          <el-button type="primary" @click.stop="strEqu(13)">强化磨</el-button>
          <el-button type="primary" @click.stop="strEqu(14)">玉碎磨</el-button>
          <el-button type="primary" @click.stop="strEqu(15)">随机磨</el-button>
          <el-button type="primary" @click.stop="strEqu(17)">上10磨</el-button>
          <el-button type="primary" @click.stop="strEqu(18)">双保磨</el-button>
          <el-button type="primary" @click.stop="strEqu(19)">上12磨</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  name: "Equ",
  data() {
    return {
      users: {},
      //装备实体
      equ: {},
      //装备diglog
      equDialogVisible: false,
    };
  },
  created() {
    this.users = this.userinfoEqu;
  },

  watch: {
    userinfoEqu: function(n) {
      this.users = n;
    },
    equDialogVisible: function(n) {
      if (n == false) {
        setTimeout(() => {
          this.$emit("changeEqu");
        }, 1000);
      }
    },
  },
  methods: {
    //装备详情
    equDetail(equid) {
      this.axios.get("/equ/" + equid).then((res) => {
        if (res.data.code == 200) {
          this.equ = res.data.data;
          this.equ.isStr = this.equ.level > 0 ? true : false;
          this.equDialogVisible = true;
        }
      });
    },
    //强化因子
    strDetail(num) {
      var realNum = num * 1 * (Math.pow(0.04 * this.equ.level + 1, 2) - 1);
      return parseFloat(realNum).toFixed(2);
    },
    luckEqu() {
      this.axios.get("/luckdraw/change/" + this.equ.equType).then((res) => {
        if (res.data.code == 200) {
          this.equ = res.data.data;
          this.$emit("changeEqu");
        } else {
          this.$notify.error({
            title: "哦豁",
            message: res.data.message,
          });
        }
      });
    },
    strEqu(magic) {
      this.axios
        .get("/luckdraw/strengthen/" + this.equ.equType + "/" + magic)
        .then((res) => {
          if (res.data.code == 200) {
            this.equ = res.data.data;
          } else if (res.data.code == 30001 || res.data.code == 30003) {
            this.equDialogVisible = false;
            this.$notify.error({
              title: "哦豁",
              message: res.data.message,
            });
          } else {
            this.equ = res.data.data;
            this.$notify.error({
              title: "哦豁",
              message: res.data.message,
            });
          }
        });
    },
  },
  props: ["userinfoEqu"],
};
</script>

<style lang="less" scoped>
#equ-box {
  margin: 0;
  padding: 0;
  position: fixed;
  top: 50px;
  width: 140px;
  height: 4350px;
  display: flex;
  flex-direction: column;
  img {
    width: 64px;
    height: 64px;
    margin: 10px 10px;
  }
}
#equ-detail-box {
  text-align: center;
  #name-rank {
    text-align: center;
    font-size: 24px;
    font-weight: bolder;
    text-align: center;
    .overten {
      background: linear-gradient(
        to right,
        rgb(231, 203, 110),
        rgb(255, 238, 0)
      );
      -webkit-background-clip: text;
      color: transparent;
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
  #pro-detail {
    .prop-detail {
      color: rgb(255, 173, 21);
      font-size: 20px;
    }
  }
}

.dialog-footer {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
  button {
    width: 30%;
    margin-left: 0;
    margin-bottom: 10px;
  }
}
</style>
