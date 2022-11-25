<template>
  <div id="container" class="textCenter">
	<div id="goodbye">
		이렇게 떠난다니 아쉬어요.<br>
		정말 탈퇴 하실 건가요?
	</div>
	<img src="@/assets/goodbye.png" style="width: 200px" /><br>
	<button @click="deleteUser()">정말로 탈퇴하기</button>
  </div>
</template>

<script>
import axios from "axios"
import {mapMutations} from "vuex";

export default {
	methods: {
		...mapMutations({
			clearLoginInfo: "CLEAR_LOGIN_INFO"
		}),
		async deleteUser() {
			await axios.post("http://localhost:9999/rest/user/delete", this.$store.state.logined_id, {headers: {'Content-Type': 'text/plain'}})
			alert("탈퇴 완료되었습니다.");
			sessionStorage.removeItem("token");
			this.clearLoginInfo();
			axios.post("http://localhost:9999/rest/file/deleteFile", this.$store.state.logined_id, {headers: {'Content-Type': 'text/plain'}})
			this.$router.push({name: "main"})
		}
	},
}
</script>

<style scoped>
	#container {
		width: 600px;
		background: white;
		margin: 30px auto 0;
		padding: 40px;
		box-shadow: 2px 2px 8px black;
	}
	.textCenter {
		text-align: center;
	}
	button {
		transition: all 0.1s;
		border: none;
		border-radius: 10px;
		background: #ff7d7d;
		color: white;
		padding: 10px 20px;
	}
	button:hover {
		background: #d16060;
	}
</style>