<template>
  <div id="question-box">
    <p>{{ currentQuestion.questionSon }}</p>
    <div class="ans-detail">
      <el-radio v-model="radio" label="1">{{
        currentQuestion.answer1
      }}</el-radio>
    </div>
    <div class="ans-detail">
      <el-radio v-model="radio" label="2">{{
        currentQuestion.answer2
      }}</el-radio>
    </div>
    <div class="ans-detail">
      <el-radio v-model="radio" label="3">{{
        currentQuestion.answer3
      }}</el-radio>
    </div>
    <div class="ans-detail">
      <el-radio v-model="radio" label="4">{{
        currentQuestion.answer4
      }}</el-radio>
    </div>
    <div class="ans-detail">
      <el-radio v-model="radio" label="5">{{
        currentQuestion.answer5
      }}</el-radio>
    </div>
    <el-button id="answer-over" @click="nextQue" plain>确定</el-button>
  </div>
</template>
<script>
export default {
  name: "Answer",
  data() {
    return {
      question: [],
      questionIndex: 0,
      currentQuestion: {},
      user: {
        lucky: 0,
        strikeBack: 0.0,
        doubleHit: 0.0,
        thief: 0.0,
        criticalHit: 0.0,
      },
      radio: "1",
    };
  },
  created() {
    this.question = [
      {
        questionSon: "你希望自己是一个?",
        answer1: "富贵公子",
        answer2: "乡下长大的孩子",
        answer3: "名门世家",
        answer4: "疯子",
        answer5: "拾荒者",
      },
      {
        questionSon: "如果能实现一个愿望,你希望自己?",
        answer1: "拥有无尽的财富",
        answer2: "拥有永恒的爱情",
        answer3: "拥有极快的反应速度",
        answer4: "拥有无敌的武功",
        answer5: "拥有至高无上的权力",
      },
      {
        questionSon: "你觉得以下哪件事你最不能接受?",
        answer1: "调戏妇女",
        answer2: "欺软怕硬",
        answer3: "偷奸耍滑",
        answer4: "切JJ练神功",
        answer5: "有美女不泡",
      },
      {
        questionSon: "如果你在街上看到一个女孩子正在被欺负,你会?",
        answer1: "冲上去给予他们贵族的掌掴",
        answer2: "假装不在意拉上女孩就跑",
        answer3: "找个地方躲起来直接报警",
        answer4: "冲上去大吼一声吸引他们注意力",
        answer5: "和他们一起",
      },
      {
        questionSon: "你最想变成以下哪位角色?",
        answer1: "张无忌",
        answer2: "郭靖",
        answer3: "韦小宝",
        answer4: "令狐冲",
        answer5: "林平之",
      },
    ];
    this.currentQuestion = this.question[this.questionIndex];
  },
  methods: {
    nextQue() {
      if (this.questionIndex < this.question.length) {
        this.questionIndex++;
        this.addPro(this.radio);
        this.currentQuestion = this.question[this.questionIndex];
        this.radio = "1";
      } else {
        this.$emit("answerOver", this.user);
      }
    },
    addPro(radio) {
      switch (radio) {
        case "1":
          this.user.thief += 0.5;
          return "";
        case "2":
          this.user.strikeBack += 0.5;
          return "";
        case "3":
          this.user.doubleHit += 0.5;
          return "";
        case "4":
          this.user.criticalHit += 0.5;
          return "";
        case "5":
          this.user.lucky += 5;
          return "";
      }
    },
  },
};
</script>

<style lang="less" scoped>
#question-box {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  .ans-detail {
    padding: 20px;
  }
  #answer-over {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }
}
</style>
