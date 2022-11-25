<template>
  <div>
    <div>
      <textarea    v-model="comment" id="commentTextArea" placeholder="댓글을 작성하세요."></textarea>
       
      <b-button variant="success" id="writeBtn" @click="submitComment">작성하기 </b-button>
       
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "addComment",
  props: ['boardDto'],
  data() {
    return {   
      comment:null,
      
    };
  },
   
  methods: {
    //댓글을 등록
    submitComment(){
       //axios-post를 호출
       axios({
        method: "post",
        url: "http://localhost:9999/rest/comment/addComment",
        data: {
          
           comment:this.comment,
           articleNo:this.boardDto.articleNo,
           writer:this.$store.state.logined_id
           
        },
      })
      .then( ()=>{ this.$emit("addComment");
       }
        
        );
       this.comment=null;
    }
  }

};
</script>

 
<style>
#commentTextArea{
  width:100%; 
  height: 80px;
  border:0.5px solid lightgray;
  border-radius:3px;
   font-family: 'NanumSquareRound';
   padding:10px;
}
#commentTextArea:focus{
  outline: none;
}
#writeBtn{
  float:right;
  padding:10px;
  margin-bottom:5px;
   font-family: 'NanumSquareRound';
}
 
</style>
