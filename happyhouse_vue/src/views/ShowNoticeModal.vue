<template>
  <div id="modalBox">
    <div id="noticeBox">
      <div id="guide" style="display: flex; align-items: flex-end">
        <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">Notice</div>
        <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0">최신 공지 입니다.</div>
      </div>
      <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px"></div>
      <div style="padding: 0 30px">
        <div>공지 제목 : {{ title }}</div>
        <hr />
        <div>{{ content }}</div>
      </div>
      <div style="text-align: center"><button @click="confirm()" class="confirmBtn">확인</button></div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["title", "content"],
  methods: {
    confirm() {
      this.$emit("closeNotice");
    },
  },
};
</script>

<style scoped>
#modalBox {
  position: absolute;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background: #0000008c;
  display: flex;
  align-items: center;
  justify-content: center;
}
#noticeBox {
  width: 900px;
  padding: 50px;
  background: white;
}
.confirmBtn {
  margin: 50px 0 0 0;
  border: none;
  border-radius: 10px;
  padding: 5px 15px;
  background: #7d6fff;
  color: white;
}
.confirmBtn:hover {
  background: #4b41a5;
}
</style>
