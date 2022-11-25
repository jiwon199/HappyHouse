<template>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container px-5">
		<router-link to="/" class="navbar-brand">Start Bootstrap</router-link>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
				<li class="nav-item"><router-link to="/" class="nav-link">메인</router-link></li>
				<li class="nav-item"><router-link to="/house" class="nav-link">매물찾기</router-link></li>
				<li class="nav-item"><router-link to="/community" class="nav-link">QnA</router-link></li>
				<li class="nav-item" v-if="!this.$store.state.logined"><router-link to="/login" class="nav-link">로그인</router-link></li>
				<li class="nav-item" v-if="!this.$store.state.logined"><router-link to="/register" class="nav-link">회원가입</router-link></li>
				<li class="nav-item" v-if="this.$store.state.logined"><a class="nav-link">환영합니다 {{loadName}}님</a></li>
				<li class="nav-item" v-if="this.$store.state.logined"><a class="nav-link" @click="goMyPage()">마이페이지</a></li>
				<li class="nav-item" v-if="this.$store.state.logined"><a class="nav-link" @click="logoutBtn()">로그아웃</a></li>
			</ul>
		</div>
	</div>
</nav>
</template>

<script>
import axios from 'axios';
import {mapMutations} from "vuex";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {
		loginUser: {},
	};
  },
  created() { //시작하면
	this.confirmLogin();
  },
  methods: {
	...mapMutations({
		registTokenInfo: "REGIST_TOKEN_INFO",
		clearLoginInfo: "CLEAR_LOGIN_INFO"
	}),
	confirmLogin() {
		if (sessionStorage.getItem("token") == null) return; //토큰있는지 확인하고
		axios.get("http://localhost:9999/rest/user/getTokenInfo", { //있다면 토큰이 유효한지 확인함
			headers: {
				"Authentication": sessionStorage.getItem("token"),
			}
		})
		.then(response => {this.registTokenInfo(response.data);}) //유효하다면 vuex에 토큰 정보입력 메서드실행해서 state의 로그인 정보 입력해줌
		.catch(() => {
			alert("토큰이 만료됨"); //유효하지 않다면 경고창
			sessionStorage.removeItem("token");
			this.clearLoginInfo();
		}) 
	},
	logoutBtn() {
		sessionStorage.removeItem('token');
		this.clearLoginInfo();
		this.$router.push({name: "main"})
	},
	goMyPage() {
		this.confirmLogin();
		if (this.$store.state.logined == true) {
			this.$router.push({name: "mypage"})
		}
	}
  },
  computed: {
	loadName() {
		return this.$store.state.logined_name;
	}
  }
};
 </script>

<style src="@/assets/css/styles.css">

</style>