<template>
  <div class="NanumSquareRound">
    <div id="container" style="overflow: scroll">
        <div id="guide" style="display:flex; align-items: flex-end"> 
            <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                Community
            </div>
            <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0;">
                커뮤니티에서 자유롭게 소통하세요
            </div>
        </div>
        <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
        <div style="margin: 0 15px 60px 15px;">
            <div style="color: #777777; font-size: 14px; margin-bottom: -5px">글번호 : {{item.articleNo}} &nbsp;&nbsp; 조회수: {{item.hit}}</div>
			<div>
				<span style="font-weight: 600; font-size: 40px;">{{item.title}}</span>
				<span v-if="this.$store.state.logined_id == item.writer">
					<button id="editBtn" class="Btn" @click="editBtn()">수정</button>
					<button id="delBtn" class="Btn" @click="delBtn()">삭제</button>
				</span>
			</div>
            <div style="display: flex; align-items: center">
                <div>
                    <div v-if="profileChk != null"><img :src="profileChk" style="width: 40px; height: 40px; border-radius: 50%; border:none;"></div>
                    <div v-if="profileChk == null"><div style="width: 40px; height: 40px; border-radius: 50%; border:none; color: white; text-align:center; line-height: 40px; background: green">{{item.writer.substr(0,1)}}</div></div>
                </div>
                <div style="font-size: 14px; margin-left: 8px; margin-top: 10px">
                    <div>
                        작성자 : {{item.writer}}
                    </div>
                    <div>
                        {{item.regTime}}
                    </div>
                </div>
            </div>
            <hr>
            <div>
                {{item.content}}
            </div>
            <div style="display: flex; flex-wrap: wrap;">
                <div v-for="(p,idx) in pictures" :key="idx">
                    <img :src="p" style="height: 150px; margin: 5px">
                </div>
            </div>
        </div>
    댓글
    <hr style="margin-top: 2px">
	<comment-area :boardDto="item"></comment-area> 
    </div>
  </div>
</template>

<script>
import commentArea from "@/components/community/comment/commentArea.vue";
import axios from 'axios';

export default {
	data() {
		return {
			item: {},
            profileChk: null,
            pictures: []
		}
	},
	async created() {
		this.item = this.$route.params["info"];
		this.item.hit = this.item.hit + 1;
		await axios.post("http://localhost:9999/rest/board/hitting", {
			"articleNo": this.item.articleNo,
			"hit": this.item.hit
		})
        await axios.post("http://localhost:9999/rest/file/profileChk", this.item.writer, {headers: {'Content-Type': 'text/plain'}})
        .then(({data}) => {
            if (data == 1) {
                this.profileChk = require("@/profile/" + this.item.writer + ".png");
            }
        });
        // 글번호 보내서 글번호에 해당하는 디렉터리 있는지 확인
        // 확인해서 있다면 => 안에 있는 파일의 갯수 리턴
        // 확인해서 없다면 => 0 리턴
        // then => for(var i = 1; i <= response.data; i++) {require객체를 담은 배열(this.pictures)에 push}
        // 후에 그 배열을 돌며 <img>추가
        await axios.post("http://localhost:9999/rest/file/fileNum", this.item.articleNo, {headers: {'Content-Type': 'text/plain'}})
        .then(({data}) => {
            for (var i = 1; i <= data; i++) {
                this.pictures.push(require("@/article/" + this.item.articleNo + "/" + i + ".png"));
            }
        });
	},
    methods: {
		editBtn() {
			this.$router.push({name:"communityEdit", params: {
				articleNo: this.item.articleNo,
				title: this.item.title,
				content: this.item.content
			}})
		},
		async delBtn() {
			await axios.post("http://localhost:9999/rest/board/delete/"+this.item.articleNo)
			await axios.get("http://localhost:9999/rest/file/clearFolder/"+this.item.articleNo)
			alert("글이 정상 삭제 되었습니다.")
			this.$router.push({name:"community"});
		}
    },
	components: {
		commentArea
	}
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
	}
	#title {
		font-size: 25px;
	}
	#detail {
		font-size: 12px;
	}
	.Btn {
		margin: 0 0 0 10px;
		border: none;
		transition: all 0.1s;
		border-radius: 5px;
		color: white;
	}
	#editBtn {
		background-color: rgb(143, 159, 248)
	}
	#delBtn {
		background-color: rgb(255, 152, 152)
	}
	#editBtn:hover {
		background-color: rgb(108, 122, 202)
	}
	#delBtn:hover {
		background-color: rgb(202, 108, 108)
	}
</style>