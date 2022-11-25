<template>
  <div class="dongInfo">
    <div v-if="!selectOpt.dong" class="infoText">원하는 조건으로 조회해보세요.</div>
    <div v-else class="infoText">
      {{ selectOpt.gugun }} {{ selectOpt.dong }}  
       
      <!-- @change는 이동하가 작성함 -->
      <!-- <b-form-checkbox v-if="this.$store.state.logined_id" switch size="lg" :checked="likeChk" @change="likeChange()"> 관심 지역으로 설정하기</b-form-checkbox> -->
      <div class="likeTextArea" v-if="this.$store.state.logined_id" >
        <input id="heart" type="checkbox"   :checked="likeChk" @change="likeChange()"  style=" margin: 0rem;  padding-top:3px;" />
        관심지역설정
        <label for="heart" style=" margin: 0rem; vertical-align:middle; ">❤</label>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
import axios from "axios";
export default {
  name: "SelectInfo",
  data() {
    return {
      likeChk: false,
      
    };
  },
  computed: {
    ...mapState(houseStore, ["selectOpt"]),
    
  },

  mounted: function () {
    if (this.$store.state.logined_id)  this.setInfo();
  },
  methods: {
    //관심지역 등록-삭제(by 이동하)
    likeChange() {
      this.likeChk = !this.likeChk;
      if (this.likeChk == true) {
        console.log("관심지역을 등록합니다. ",this.dongCode);
        axios.post("http://localhost:9999/rest/house/addLikeArea", {
          id: this.$store.state.logined_id,
          dongCode: this.dongCode,
        });
      } else {
        console.log("관심지역에서 등록 해제 합니다. ",this.dongCode);
        axios.post("http://localhost:9999/rest/house/deleteLikeArea", {
          id: this.$store.state.logined_id,
          dongCode: this.dongCode,
        });
      }
    },

    setInfo() {
       
      //선택 지역의 동코드 조회
      axios
        .get(
          "http://localhost:9999/rest/house/dongCode/" +
            this.selectOpt.sido +
            "/" +
            this.selectOpt.gugun +
            "/" +
            this.selectOpt.dong
        )
        .then((response) => {
          this.dongCode = response.data;

          //----관심지역 여부 조회(by 이동하)-----
          var userId = this.$store.state.logined_id;
 

          axios.get(`http://localhost:9999/rest/house/isLike/${userId}/${this.dongCode}`).then(({ data }) => {
            console.log("관심지역이 맞다면 1, 아니라면 0");
            if (data == "1") {
              console.log("관심지역이네요");
              this.likeChk = true;
            } else {
              console.log("관심지역이 아니네요");
              this.likeChk = false;
            }
          });
          //------------------------------
        })
        .catch((error) => {
          console.dir(error);
        });
    },
  },
};
</script>

<style>
[id="heart"] {
  position: absolute;
  left: -100vw;
}

[for="heart"] {
  color: #aab8c2;
  cursor: pointer;
  font-size: 1.5em;
  align-self: center;
  transition: color 0.2s ease-in-out;
}

[for="heart"]:hover {
  color: grey;
}

[for="heart"]::selection {
  color: none;
  background: transparent;
}

[for="heart"]::moz-selection {
  color: none;
  background: transparent;
}

[id="heart"]:checked + label {
  color: #e2264d;
  will-change: font-size;
}

@keyframes heart {
  0%,
  17.5% {
    font-size: 0;
  }
}
.dongInfo {
  margin: 0;
  
  justify-content: center;
  height: 100px;
  padding: 10px;
  display: flex;
  border-bottom: 0.5px solid lightgray;
 
}
.infoText {
 font-family:'BMJUA';
  font-size: 20px;
   
  margin: auto;
  
}
.likeTextArea {
  font-family:'NanumSquareRound';
  font-size: 16px;
  font-weight: normal;
   text-align: center;
}
 
</style>
