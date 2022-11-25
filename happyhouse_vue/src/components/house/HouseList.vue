<template>
  <div id="houseArea">
      
    <!-- 아파트 목록 -->
    <div id="listbox">
       
      <house-detail ref="HouseDetail" />
      <select-info ref="selectInfo" class="select"/>
       
      <b-list-group v-for="(house, index) in houses" :key="index" :house="house">
        <b-list-group-item
          href="#"
           
          class="flex-column align-items-start"
          @click="clickMap(house.lat, house.lng, house.apartmentName,house.aptCode)"
          id="listItem"
        >
          <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1" style="font-family:'BMJUA';">{{ house.apartmentName }}</h5>
          </div>
          <p class="mb-1">{{ house.dealAmount }} 만원</p>
          <small>{{house.floor}}층, 면적 {{house.area}}, {{house.buildYear}}년 건축됨</small>
        </b-list-group-item>
      </b-list-group>
      
    </div>
     
    <!-- 지도 -->
    <house-map ref="HouseMap" />
  
    
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
import SelectInfo from "./SelectInfo.vue";
import HouseMap from "./HouseMap.vue";
import HouseDetail from "./HouseDetail.vue";
export default {
  name: "HouseList",
  data() {
    return {
      map: null,
      markers: [],
    };
  },
  components: {
    SelectInfo,
    HouseMap,
    HouseDetail
  },

  computed: {
    ...mapState(houseStore, ["houses"]),
  },
  watch: {
    //집을 조회할때마다 호출됨
    houses() {
      
      //관심 지역 여부 조회하기
      if (this.$store.state.logined_id) this.$refs.selectInfo.setInfo();
       
    },
  },
  methods: {
    clickMap(lat, lng, apartmentName,aptCode) {
      //지도에 찍고
      this.$refs.HouseMap.moveCenter(lat, lng, apartmentName);
      //디테일 페이지 열기
      this.$refs.HouseDetail.openDetail(apartmentName,aptCode);
      
    },
     
  },
};
</script>

<style>
body {
  margin: 0px;
}
#houseArea {
  width: 100%;
  
  border-top:1px solid lightgray;
  
}

#listbox {
  width:17%;
  height: 75vh;
  overflow-x: hidden;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  background: white;
  display: inline-block;
  -webkit-overflow-scrolling: auto;
   
}

.select { 
  position: sticky;
  top: 0;
  z-index: 3;
  background: white;
	}
::-webkit-scrollbar {
  display: none;
}

#listItem {
  border-left: 0px;
  border-right: 0px;
  border-top: 0px;
  border-radius: 0px;
  font-family: 'NanumSquareRound';
    
}
 
</style>
