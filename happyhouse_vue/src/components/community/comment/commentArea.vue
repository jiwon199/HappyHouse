<template>
  <div>
  <div v-if="this.$store.state.logined">
   <add-comment :boardDto="boardDto" v-on:addComment="callLoadFunc()"/> 
   </div>
   
  <div v-else style="text-align: center">
    로그인 사용자에 한하여 댓글 작성이 가능합니다.
  </div>
   <comment-list :boardDto="boardDto" ref="commentList"/>
  </div>
</template>

<script>
import addComment from "@/components/community/comment/addComment.vue";
import commentList from "@/components/community/comment/commentList.vue";
export default {
  name: "CommentArea",
  props: ['boardDto'],
  data() {
    return {
    };
  },
  components : {
    addComment,
    commentList
  },
  methods:{
    callLoadFunc(){
      console.log("부모가 로드 함수를 다시 호출");
      this.$refs.commentList.loadComment();
    }
  }
  
};
</script>

<style src="@/assets/css/styles.css"></style>
<style>
 
</style>
