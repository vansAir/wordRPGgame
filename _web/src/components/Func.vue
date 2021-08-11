<template>
  <div id="func-box">
    <FuncInfo :userinfo="this.users"></FuncInfo>
  </div>
</template>
<script>
import FuncInfo from "../components/FuncInfo.vue";
export default {
  name: "Func",
  data() {
    return {
      users: this.userinfo,
    };
  },
  props: ["userinfo"],
  components: {
    FuncInfo,
  },
};
</script>

<style lang="less" scoped></style>
