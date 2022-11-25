<template>
  <div id="container">
    <!--- 관심 지역 헤더 --->
    <div id="guide" style="display: flex; align-items: flex-end">
      <div style="font-size: 23px; margin-right: 5px; font-family: BMJUA">나의 관심 지역</div>
      <div style="color: #777777; font-size: 12px; margin-bottom: 6px; font-family: 'NanumSquareRound'">
        관심지역의 매물들을 비교해 보세요
      </div>
    </div>
    <!--- 관심 지역 헤더 끝 --->

    <div class="bottomLine"></div>
    <div v-if="likeList.length > 0">
      <div v-for="(item, i) in likeList" :key="i" class="item">
        <div class="itemInfo">{{ item.sidoName }} {{ item.gugunName }} {{ item.dongName }}</div>
        <div class="btnArea">
          <b-button variant="success" size="sm" @click="goHouse(item)" style="margin-right: 3px">GO</b-button>
          <b-button variant="danger" size="sm" @click="deleteArea(item,i)">X</b-button>
        </div>
      </div>
      <!--
      <div class="textCenter">
        <button @click="saveChange()">변경 사항 저장</button>
      </div> -->
    </div>
    <div v-if="likeList.length == 0">
      <div id="notice">관심지역을 추가해 보세요</div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapMutations } from "vuex";
const houseStore = "houseStore";
export default {
  data() {
    return {
      likeList: [],
      houseList: [],
      form: {
        sido: null,
        gugun: null,
        dong: null,
        year: null,
        month: null,
      },
    };
  },
  methods: {
    ...mapMutations(houseStore, ["SET_APT_LIST"]),
    ...mapMutations(houseStore, ["SET_SELECT_OPTION"]),

    likeChange(e) {
      e.chk = !e.chk;
    },
    goHouse(item) {
      var router = this.$router;

      this.form.sido = item.sidoName;
      this.form.gugun = item.gugunName;
      this.form.dong = item.dongName;
      this.form.year = "2021";
      this.form.month = "12";
      axios({
        method: "post",
        url: "http://localhost:9999/rest/house/list",
        data: {
          sido: this.form.sido,
          gugun: this.form.gugun,
          dong: this.form.dong,
          year: this.form.year,
          month: this.form.month,
        },
      }).then((response) => {
        this.houseList = response.data;
        this.SET_APT_LIST(this.houseList);
        this.SET_SELECT_OPTION(this.form);

        router.push("/house");
      });

      //console.log("나의 관심지역 in 마이페이지:",item);
      //router.push('/house');
    },
    deleteArea(item,i) {
      if (confirm("이 관심 지역을 삭제 할까요?")) {
        axios({
          method: "post",
          url: "http://localhost:9999/rest/user/delLike",
          data: {
            id: this.$store.state.logined_id,
            dongCode:item.dongCode
          },
        })
      .then(()=>{ 
        this.likeList.splice(i,1);
        this.$forceUpdate();
        console.log("삭제 처리 완료"); 
        })
      .catch(error => {
                    console.log(error);
                })
      }
    },
    async loadLikeArea(){
      await axios
      .post("http://localhost:9999/rest/user/getLikeArea", this.$store.state.logined_id, {
        headers: { "Content-Type": "text/plain" },
      })
      .then(({ data }) => {
        for (var i of data) {
          if (i != null) {
            axios
              .post("http://localhost:9999/rest/user/getDongInfo", i, { headers: { "Content-Type": "text/plain" } })
              .then(({ data }) => {
                data["dongCode"] = i;
                data["chk"] = true;
                this.likeList.push(data);
              });
          }
        }
        this.likeList.length;
        
      });
    },
    saveChange() {
      var temp = [];
      var del = [];
      for (var l of this.likeList) {
        console.log("**");
        console.log(l);
        if (l.chk == true) {
          temp.push(l);
        } else {
          del.push(l.dongCode);
        }
      }
      this.likeList = temp;
      console.log(this.likeList);
      console.log(del);
      const formData = new FormData();
      del.forEach((aa) => {
        formData.append("del[]", aa);
      });
      axios.post("http://localhost:9999/rest/user/delLike/" + this.$store.state.logined_id, formData);
    },
  },
  async created() {
     this.loadLikeArea();
  },
};
</script>

<style scoped>
.textCenter {
  text-align: center;
}
#container {
  width: 800px;
  margin: 20px auto;
  padding: 50px;
  background-color: white;
  box-shadow: 2px 2px 10px black;
  max-height: 700px;
  overflow: auto;
}
input:checked + span {
  color: #e2264d;
}

#notice {
  text-align: center;
  font-size: 20px;
  margin: 30px 0;
}
.bottomLine {
  width: 350px;
  background: #aaa;
  height: 1px;
  margin-top: 3px;
  margin-bottom: 20px;
}
.item {
  width: 70%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  font-family: "NanumSquareRound";

  border-bottom: 1px solid lightgray;
}
.itemInfo {
  vertical-align: middle;

  margin-top: 5px;
}
.btnArea {
  margin: 2px;
}
</style>
