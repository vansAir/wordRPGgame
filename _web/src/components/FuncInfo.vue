<template>
  <div>
    <img src="@/assets/equ/info.png" alt="" srcset="" @click="userInfo()" />
    <div id="equ-detail-box" @click.stop="infoDialogVisible = false">
      <el-dialog
        title="人物详情"
        :visible.sync="infoDialogVisible"
        width="80%"
        :fullscreen="true"
        v-if="users.attack"
        center
      >
        <div id="pro-detail">
          <div class="prop-detail">
            <span>攻击力:{{ users.attack.toFixed(2) }} </span>
          </div>
          <div class="prop-detail">
            <span>防御力:{{ users.defense.toFixed(2) }} </span>
          </div>
          <div class="prop-detail">
            <span>生命值:{{ users.health.toFixed(2) }} </span>
          </div>
          <div class="prop-detail">
            <span>幸运:{{ users.lucky.toFixed(2) }} </span>
          </div>
          <div class="prop-detail">
            <span>速度:{{ users.speed.toFixed(2) }} </span>
          </div>
          <div class="prop-detail">
            <span>偷窃率:{{ users.thief.toFixed(2) + "%" }} </span>
          </div>
          <div class="prop-detail">
            <span>反击率:{{ users.strikeBack.toFixed(2) + "%" }} </span>
          </div>
          <div class="prop-detail">
            <span>即死判定:{{ users.killProbability.toFixed(2) + "%" }} </span>
          </div>
          <div class="prop-detail">
            <span>闪避率:{{ users.missProbability.toFixed(2) + "%" }} </span>
          </div>
          <div class="prop-detail">
            <span
              >必中几率:{{ users.attackProbability.toFixed(2) + "%" }}
            </span>
          </div>
          <div class="prop-detail">
            <span>暴击率:{{ users.criticalHit.toFixed(2) + "%" }} </span>
          </div>
          <div class="prop-detail">
            <span>连击率:{{ users.doubleHit.toFixed(2) + "%" }} </span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="infoDialogVisible = false"
            >我真是太猛了</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  name: "Func",
  data() {
    return {
      users: this.userinfo,
      infoDialogVisible: false,
    };
  },

  watch: {
    userinfo: function(n) {
      this.users = n;
    },
  },
  props: ["userinfo"],
  methods: {
    userInfo() {
      this.infoDialogVisible = true;
    },
  },
};
</script>

<style lang="less" scoped>
img {
  position: fixed;
  top: 50px;
  right: 0;
  width: 64px;
  height: 64px;
  margin: 10px 10px;
}
</style>
