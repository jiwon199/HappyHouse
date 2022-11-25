<template>
<div id="whiteBox"> 
  <BarChart />
</div>
</template>

<script>
import BarChart from "@/components/house/Bar.vue";
export default {
  name: "AppHouse",
   
  components: {
    BarChart,
  },
  
};
</script>
<style scoped>
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
</style>