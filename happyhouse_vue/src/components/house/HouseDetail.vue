<template>
  <div id="hide">
     <div v-if="this.isLoad"   >
      <img  :src="this.image" alt="아파트의 사진"  @error="replaceByDefault" id="aptImg"  > </div>
     <div v-else style="width:100%; margin: 0 auto;  display: block; " ><b-skeleton-img  ></b-skeleton-img></div>
    <b-button variant="secondary" class="cancleBtn" style="border: 1px solid white; " @click="clickCancle()">X</b-button>
    <p class="aptName"> {{ apartmentName }}</p>
    <p class="avgPrice">평균 매매가: {{this.avgDeal}} 만원</p>
     
    <p class="newsStart">관련 뉴스</p>

    <div class="newsArea"> 
    <b-list-group v-for="(news, index) in newsList" :key="index" :news="news"  >
      <b-list-group-item :href="news.link" id="newsItem" target='_blank'>{{ news.title }}</b-list-group-item>
    </b-list-group>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
import axios from "axios";
export default {
  name: "HouseDetail",
  data() {
    return {
      apartmentName: "",
      aptCode:"",
      isHide: true,
      newsList: [],
      image: null,
      noImg:"https://t1.daumcdn.net/news/202203/23/hankooki/20220323043126895zsnj.jpg",
      isLoad:false,
      avgDeal:""
     
    };
  },
  methods: {
    //디테일 창 닫기
    clickCancle() {
      document.getElementById("detailInfo").id = "hide";
      this.isHide = true;
    },
    //디테일 창 열기. 이미 열려 있는 경우 아파트명만 갱신
    openDetail(aptName,aptCode) {
      if (this.isHide) {
        document.getElementById("hide").id = "detailInfo";
        this.isHide = false;
      }
      this.apartmentName = aptName;
      this.aptCode=aptCode;
      this.getAvgDeal(aptCode);
      this.getNewsList();
      this.getImage();
    },

    //평균 매매가 구하기
    getAvgDeal(aptCode){
       axios
        .get("http://localhost:9999/rest/house/avgDeal/" + aptCode )
        .then((response) => {
           this.avgDeal=response.data;
        })
        .catch((error) => {
          console.dir(error);
        });

      
    },
    getNewsList() {
      axios
        .get("http://localhost:9999/rest/news/getNews/" + this.selectOpt.dong + " " + this.apartmentName)
        .then((response) => {
          this.newsList = response.data.items;
          this.removeHtml();
          //console.log(response.data.items);
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    getImage(){
      axios
        .get("http://localhost:9999/rest/news/getImage/" + this.selectOpt.dong + " " + this.apartmentName)
        .then((response) => {
          this.image=response.data.items[0].thumbnail ;
          this.isLoad=true;
        
        })
        .catch((error) => {
          console.dir(error);

          //to-do: 없을때 대체이미지 설정
           this.image=this.noImg;
           
        });
    },
    removeHtml() {
      for (var i = 0; i < this.newsList.length; i++) {
        this.newsList[i].title = this.stripHTMLtag(this.newsList[i].title);
      }
    },
    stripHTMLtag(string) {
      var objStrip = new RegExp();
      objStrip = /[<][^>]*[>]/gi;
      var returnStr = string.replace(objStrip, "");
      returnStr = returnStr.replaceAll("&apos;", "");
      returnStr = returnStr.replaceAll("&gt;", "");
      returnStr = returnStr.replaceAll("&lt;", "");
      returnStr = returnStr.replaceAll("&quot;", "");
      returnStr = returnStr.replaceAll("&nbsp;", "");
      returnStr = returnStr.replaceAll("&amp;", "");

      if(returnStr.length>25) {
        returnStr=returnStr.substr(0, 25)+"...";

      } 
      return returnStr;
    },
    replaceByDefault(e){
      e.target.src = this.noImg;
    }
  },
  computed: {
    ...mapState(houseStore, ["selectOpt"]),
  },
};
</script>

<style>
#detailInfo {
  position: absolute;
  left: 17%;
  width: 20%;
  height: 75vh;
  background: white;
  border-left: 1px solid lightgray;
  overflow:hidden;
  z-index: 2;
}
.cancleBtn {
  position:absolute;top:10px;right:10px;
  
}
#hide {
  opacity: 0;
  visibility: hidden;
  position: absolute;
}
#newsItem {
  font-size: 13px;
  border-left: 0px;
  border-right: 0px;
  border-top: 0px; 
  border-radius: 0px;
  font-family: 'NanumSquareRound';
}
#aptImg{
  display: block; 
  margin: 0 auto; 
  width:100%; 
  max-height:300px;
 
}
.aptName{
  font-family:'BMJUA';
  font-size: 30px;
  margin-left: 20px;
  margin-top:15px;
  margin-bottom:0px;
}
.avgPrice{
  font-family: 'NanumSquareRound';
  margin-left: 20px;
  color:gray;
}
.newsStart{
   font-family: 'BMJUA';
   font-size: 20px;
   padding-left: 20px;
   margin-bottom:0px;
   
   border-bottom: 1px solid lightgray;
}
.newsArea{
  overflow: hidden;
  overflow-x: hidden;
  overflow-y: auto;
  height:300px;
}
</style>
