<template>
  <div id="main-box">
    <div id="area-box">
      <div
        class="item"
        v-for="area in areas"
        :key="area.id"
        @click="changeArea(area.id, area.areaDesc)"
      >
        <el-button type="info">{{ area.areaName }}</el-button>
      </div>
    </div>
    <div id="describe">
      <p>{{ describe }}</p>
    </div>
    <div id="selectArea">
      <el-button type="info" @click="selectArea">确定</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: "AreaSelect",
  data() {
    return {
      areas: [],
      describe: "",
      area: "",
    };
  },
  created() {
    this.axios.get("/user/areas").then((res) => {
      this.areas = res.data.data;
    });
  },
  methods: {
    changeArea(id, desc) {
      this.area = id;
      this.describe = desc;
    },
    selectArea() {
      if (this.area == "") {
        this.$message({
          message: "你还没有选择出生地",
          type: "warning",
        });
      } else {
        this.$emit("selectArea", this.area);
      }
    },
  },
};
</script>

<style lang="less" scoped>
#main-box {
  display: flex;
  flex-direction: column;
  height: 100vh;
  #area-box {
    display: flex;
    width: 100vw;
    flex-wrap: wrap;
    flex-direction: row;
    .item {
      padding-top: 10vh;
      width: 33vw;
    }
  }

  #describe {
    margin: 10px 10px;
    padding: 10px 10px;
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 15px;
    width: 90vw;
    height: 200px;
    p {
      opacity: 1;
      color: rgb(0, 0, 0);
      font-weight: bolder;
    }
  }

  #selectArea {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }
}
</style>
