<template>
  <div>
    <div id="container">
        <div id="guide" style="display:flex; align-items: flex-end"> 
            <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                Community
            </div>
            <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0; font-family: 'NanumSquareRound';">
                커뮤니티에서 자유롭게 소통하세요
            </div>
        </div>
        <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
        <div class="textCenter" style="margin: 30px">
            <select id="searchOption" v-model="option" class="NanumSquareRound">
                <option value="null">선택</option>
                <option value="title">제목</option>
                <option value="writer">작성자</option>
            </select>
            <input id="searchInput" style="margin: 0 20px" v-model="input"/>
            <button id="searchBtn" @click="searchBtn()">검색</button>
            <button id="cancelBtn" @click="cancelBtn()">초기화</button>
        </div>
        <table class="textCenter" style="margin: 15px auto; width: 1000px; font-family: BMJUA">
            <colgroup>
                <col width="10%">
                <col width="50%">
                <col width="20%">
                <col width="10%">
                <col width="10%">
            </colgroup>
            <tr class="textCenter" style="background: #111; color: white; height: 40px">
                <th>번호</th>
                <th>글 제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회</th>
            </tr>
            <tr v-for="(item, i) in boardList" :key="i" style="border-top: solid #777777 1px">
                <td class="textCenter">
                    {{item.articleNo}}
                </td>
                <td>
                    <router-link :to="{name: 'communityDetail', params: {'info': item}}">{{item.title}}</router-link>
                </td>
                <td class="textCenter">
                    {{item.writer}}
                </td>
                <td class="textCenter">
                    {{item.regTime}}
                </td>
                <td class="textCenter">
                    {{item.hit}}
                </td>
            </tr>
        </table>
        <div class="textCenter" style="margin-bottom: 60px">
            <span v-if="here-3 > 0" class="port hoverPointer" @click="changeHere(here-3)">&lt;</span>
            <span v-if="here-2 > 0" class="port hoverPointer" @click="changeHere(here-2)">{{here-2}}</span>
            <span v-if="here-1 > 0" class="port hoverPointer" @click="changeHere(here-1)">{{here-1}}</span>
            <span style="background:black; color: white;" class="port">{{here}}</span>
            <span v-if="here+1 <= totalPage" class="port hoverPointer" @click="changeHere(here+1)">{{here+1}}</span>
            <span v-if="here+2 <= totalPage" class="port hoverPointer" @click="changeHere(here+2)">{{here+2}}</span>
            <span v-if="here+3 <= totalPage" class="port hoverPointer" @click="changeHere(here+3)">&gt;</span>
        </div>
        <button id="writeBtn" @click="goWrite()" class="BMJUA">글쓰기</button>
    </div>
    
  </div>
</template>

<script>
import axios from 'axios'

export default {
	data() {
		return {
            totalPage: 0,
			boardList: [],
            here: 1,
			option: null,
			input: "",
			comfirmOption: null,
			comfirmInput: "",
		}
	},
    methods: {
        async makeList() {
            this.boardList = await axios.post("http://localhost:9999/rest/board/getList/"+this.here, {
				"option": this.confirmOption==null?"null":this.confirmOption,
				"input": this.confirmInput==null?"null":this.confirmInput,
			}).then(({data}) => data);
            for (var b in this.boardList) {
                var t = (Date.now() - new Date(this.boardList[b].regTime))/60000;
                if (t < 60) {
                    this.boardList[b].regTime = Math.round(t) + "분 전";
                } else if (t < 1440) {
                    this.boardList[b].regTime = Math.round(t / 60) + "시간 전"
                } else {
                    var d = new Date(this.boardList[b].regTime);
                    this.boardList[b].regTime = d.getMonth() + "월 " + d.getDate() + "일";
                }
            }
        },
        goWrite() {
            this.$router.push({name:"communityWrite"});
        },
		changeHere(i) {
			this.here = i;
			this.makeList();
		},
		searchBtn() {
			if (this.option == null || this.input == "") {
				alert("옵션과 검색어를 확인하세요");
				return;
			}
			this.confirmOption = this.option;
			this.confirmInput = this.input;
			this.makeList();
			this.here = 1;
			axios.post("http://localhost:9999/rest/board/totalPage",{
				"option": this.confirmOption,
				"input": this.confirmInput,
			}).then(({data}) => this.totalPage = parseInt((data+4)/5));
		},
		cancelBtn() {
			this.option = null;
			this.input = "";
			this.confirmOption = this.option;
			this.confirmInput = this.input;
			this.here = 1;
			this.makeList();
			axios.get("http://localhost:9999/rest/board/totalPage").then(({data}) => this.totalPage = parseInt((data+4)/5));
		}
    },
	created() {
		this.makeList();
        axios.get("http://localhost:9999/rest/board/totalPage").then(({data}) => this.totalPage = parseInt((data+4)/5));
	},
}
</script>

<style scoped>
	#container {
		width: 1200px;
		margin: 20px auto;
		padding: 50px;
        background-color: white;
        box-shadow: 2px 2px 10px black;
        height: 700px;
        overflow: auto;
        animation-name: sss;
        animation-iteration-count: 1;
        animation-duration: 0.5s;
	}
    @keyframes sss {
        0% {opacity: 0;}
        60% {transform: translateY(-30px);}
        85% {transform: translateY(10px);}
    }
    .textCenter {
        text-align: center;
    }
    .port {
        margin: 3px;
        padding: 4px;
    }
	tr {
		height: 50px;
	}
	#writeBtn {
		padding: 8px 40px;
		border: none;
		border-radius: 10px;
		background: rgb(91, 155, 213);
		color: white;
		box-shadow: 2px 2px 8px black;
		display: block;
		margin: 0 auto;
		transition: all 0.1s;
	}
	#writeBtn:hover {
		background: rgb(53, 97, 138);
	}
	#searchOption {
		height: 30px;
	}
	#searchInput {
		height: 30px;
		width: 300px;
	}
	#searchBtn {
		width: 60px;
		border: solid 1px #4281c9;
		border-radius: 5px;
		background: white;
		transition: all 0.1s;
		margin-right: 15px;
	}
	#searchBtn:hover {
		background: #4281c9;
		color: white;
	}
	#cancelBtn {
		padding: 2px;
		width: 80px;
		border-radius: 5px;
		border: none;
		background: rgb(247, 130, 130);
		color: white;
		transition: all 0.1s;
	}
	#cancelBtn:hover {
		background: rgb(184, 92, 92);
	}
</style>