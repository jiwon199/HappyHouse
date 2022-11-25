<template>
	<div id="Box">
		<div id="modalBox">
			<div id="guide" style="display:flex; align-items: flex-end"> 
				<div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
					공지사항 작성
				</div>
				<div style="color: #777777; font-size: 12px; padding: 0 0 2px 0;">
					방문자들에게 알려주고 싶은 사항을 작성해 보세요
				</div>
			</div>
			<div style="width: 400px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
			<div id="inputedBox">
                <input id="mainTitle" class="inputedText" placeholder="글 제목을 입력해 주세요" v-model="title"><br>
                <textarea id="mainContent" class="inputedText" v-model="content" placeholder="내용을 입력해 주세요"></textarea>
            </div>
			<div style="text-align: center;">
				<button id="writeBtn" class="commitBtn" @click="doWrite()">작성</button><button id="cancelBtn" class="commitBtn" @click="doCancel()">취소</button>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	data() {
		return {
			title: "",
			content: ""
		}
	},
	methods: {
		async doWrite() {
			await axios.post("http://localhost:9999/rest/user/writeNotice", {
				"title": this.title,
				"content": this.content
			});
			this.$router.push({name: "main"})
		},
		doCancel() {
			this.$emit("doCancel")
		}
	},
}
</script>

<style scoped>
	#Box {
		position: absolute;
		top: 0;
		left: 0;
		height: 100vh;
		width: 100vw;
		background: #00000059;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	#modalBox {
		width: 1000px;
		padding: 50px;
		background: white;
		position: absolute;
		box-shadow: 2px 2px 8px black;
	}
	#inputedBox {
		text-align: center;
	}
	.inputedText {
		width: 90%;
		padding: 10px 20px;
		margin: 0 0 20px 0;
	}
	#mainTitle {
		height: 40px;
	}
	#mainContent {
		height: 200px;
	}
	.commitBtn {
		border: none;
		border-radius: 5px;
		margin: 0 10px;
		padding: 3px 15px;
		color: white;
		transition: all 0.1s;
	}
	#writeBtn {
		background: #7187ff;
	}
	#writeBtn:hover {
		background: #4554a8;
	}
	#cancelBtn {
		background: #ff7777;
	}
	#cancelBtn:hover {
		background: #b14d4d;
	}
</style>