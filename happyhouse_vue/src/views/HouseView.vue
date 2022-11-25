<template>
  <div  > 
    <!-- search drop down-->
      <div id="whiteBox">
    <house-search-bar ></house-search-bar>
    
        <house-list/> 
    </div>
     
  </div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";

import HouseList from "@/components/house/HouseList.vue";
 
export default {
  name: "AppHouse",
   
  components: {
    HouseSearchBar,
    HouseList, 
  },
  
};

 </script>
<style  >
.container{
  width:100%;
  text-align: center;
  
}
#whiteBox {
  background: white;
  width:80vw;
  box-shadow: 0 0px 10px 0 rgba(0, 0, 0, 0.5);
  border-radius:5px;
  margin-left: calc(-40vw + 50%);
  position:fixed;
  height:85vh;
  border-radius:5px;
}
@font-face {
    font-family: 'NanumSquareRound';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'BMJUA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>