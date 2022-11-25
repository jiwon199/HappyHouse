<template>

<div class="content"> 
  <div class="selectArea">
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
      
    ></b-form-select>
    <b-button variant="outline-primary" @click="submit">찾기</b-button>
  </div>
  <Bar
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs/legacy'
import axios from "axios"
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'BarChart',
  components: {
    Bar
  },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    width: {
      type: Number,
      default: 400
    },
    height: {
      type: Number,
      default: 700
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => {}
    },
    plugins: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      form: {
        sido: null,
        gugun: null,
      },
      sidoList:[],
      gugunList:[],
      dongList:[],
      houseList: [],
      avgList:[],
      chartData: {
        labels: [
        ],
        datasets: [
          {
            label: '동별 부동산 평균 매매 가격',
            fillColor: "rgba(220,220,220,0.5)", 
            strokeColor: "rgba(220,220,220,0.8)", 
            highlightFill: "rgba(220,220,220,0.75)",
            highlightStroke: "rgba(220,220,220,1)",
            backgroundColor:[],
            data: []
          }
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
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
     
    //조회 버튼을 누르면
    submit() {
      this.chartData.labels=[];
      this.chartData.datasets[0].data=[];
      axios
        .get("http://localhost:9999/rest/house/dongAvg/" + this.form.sido+"/"+this.form.gugun)
        .then((response) => {
          this.avgList=response.data;
          this.makeChart();
          
        })
        .catch((error) => {
          console.dir(error);
        });
    },
    makeChart(){
      for(var i=0;i<this.avgList.length;i++){
        this.chartData.labels.push(this.avgList[i].dongName);
        this.chartData.datasets[0].data.push(this.avgList[i].avg);
        this.chartData.datasets[0].backgroundColor.push("#"+Math.floor(Math.random()*16777215).toString(16));
      }

    }
    
  },
}
</script>
<style>
.content{
  padding: 10px;
   font-family: 'NanumSquareRound';
}
.selectArea{
  
  text-align:center;
}
</style>
