<template>
  <div id="selectbar">
     
    <b-form-select class="form-select"
      v-model="form.sido"
      :options="sidoList"
      style="width: 10%; margin: 5px;  "
      @change="getGugun"
    ></b-form-select>
    <b-form-select
      v-model="form.gugun"
      :options="gugunList"
      style="width: 10%; margin: 5px"
      @change="getDong"
    ></b-form-select>
    <b-form-select v-model="form.dong" :options="dongList" style="width: 10%; margin: 5px"></b-form-select>
    <b-form-select v-model="form.year" :options="yearList" style="width: 10%; margin: 5px"></b-form-select>
    <b-form-select v-model="form.month" :options="monthList" style="width: 10%; margin: 5px"></b-form-select>


    
    <b-button variant="outline-primary" @click="submit">찾기</b-button>
    
  </div>
</template>
<script>
import axios from "axios";
import { mapMutations } from "vuex";
const houseStore = "houseStore";
export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoList: [],
      gugunList: [],
      dongList: [],
      //하드코딩 나중에 고치기
      yearList: [2015, 2016, 2017, 2018, 2019, 2020, 2021],
      monthList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],

      form: {
        sido: null,
        gugun: null,
        dong: null,
        year: null,
        month: null,
      },
      houseList: [],
      dongCode:null,
   
    };
  },

  created() {
    axios
      .get("http://localhost:9999/rest/house/optionSido")
      .then((response) => {
        this.sidoList = response.data;
      })
      .catch((error) => {
        console.dir(error);
      });
  },
  methods: {
    ...mapMutations(houseStore,["SET_APT_LIST"]),
    ...mapMutations(houseStore,["SET_SELECT_OPTION"]),

    getGugun() {
      axios
        .get("http://localhost:9999/rest/house/optionGugun/" + this.form.sido)
        .then((response) => {
          this.gugunList = response.data.slice(1);
        })
        .catch((error) => {
          console.dir(error);
        });
    },

    getDong() {
      axios
        .get("http://localhost:9999/rest/house/optionDong/" + this.form.gugun + "/" + this.form.sido)
        .then((response) => {
          this.dongList = response.data.slice(1);
        })
        .catch((error) => {
          console.dir(error);
        });
    },

    //조회 버튼을 누르면
    submit() {
      //리스트를 먼저 찾기
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
      })
      .then((response)=>{
        this.houseList=response.data;
        this.SET_APT_LIST(this.houseList);
        this.SET_SELECT_OPTION(this.form);
      });
       
    },
    
  },
};
</script>

<style>
#selectbar {
  text-align: center;
  padding: 10px;
  
  font-family: 'NanumSquareRound';
}
 
 
</style>
