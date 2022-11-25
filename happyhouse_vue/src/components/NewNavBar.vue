<template>
	<div id="navBar" class="flexCenter">
        <div id="navContent" class="flexCenter">
            <li><router-link to="/" id="mainLogo" class="navList">HappyHouse</router-link></li>
            <li><router-link to="/house" class="navList bmjua" style="font-size: 20px; font-family: 'NanumSquareRound';">매물찾기</router-link></li>
            <li><router-link to="/chart" class="navList bmjua" style="font-size: 20px;font-family: 'NanumSquareRound';">차트 보기</router-link></li>
            <li><router-link to="/community" class="navList bmjua" style="font-size: 20px;font-family: 'NanumSquareRound';">커뮤니티</router-link></li>
             
        </div>
        <div id="navLogin" class="flexCenter">
            <li v-if="!this.$store.state.logined"><router-link to="/login" class="navList" style="font-family: 'NanumSquareRound';">Sign in</router-link></li>
            <li v-if="!this.$store.state.logined"><router-link to="/register" class="navList signup" style="font-family: 'NanumSquareRound';">Sign up</router-link></li>
            <div v-if="this.$store.state.logined" @click="modalTogle" class="profileBox">
                <li v-if="this.$store.state.profileSrc != null"><img :src="this.$store.state.profileSrc" style="width: 50px; height: 50px; border-radius: 50%; border:none;"></li>
                <li v-if="this.$store.state.profileSrc == null"><div style="width: 50px; height: 50px; border-radius: 50%; border:none; color: white; text-align:center; line-height: 50px; background: green">{{this.$store.state.logined_id.substr(0,1)}}</div></li>
            </div>
            <div class="myModal" v-if="this.$store.state.logined" :class="{noHeight:!modalView}">
                <div id="modalDetail">
                    <div style="display: flex">
                        <div v-if="this.$store.state.logined" style="display: flex; align-items: center; margin: 0 15px;">
                            <li v-if="this.$store.state.profileSrc != null"><img :src="this.$store.state.profileSrc" style="width: 50px; height: 50px; border-radius: 50%; border:none;"></li>
                            <li v-if="this.$store.state.profileSrc == null"><div style="width: 50px; height: 50px; border-radius: 50%; border:none; color: white; text-align:center; line-height: 50px; background: green">{{this.$store.state.logined_id.substr(0,1)}}</div></li>
                        </div>
                        <div>
                            <div>{{this.$store.state.logined_name}}<span v-if="this.$store.state.isAdmin" class="adm">(관리자)</span><span v-else class="adm">(일반유저)</span></div>
                            <div>ID : {{this.$store.state.logined_id}}</div>
                        </div>
                    </div>
                </div>
                <div class="modalMenu" @click="goLike()">관심 지역</div>
                <div class="modalMenu" @click="goMyPage()">회원 정보 관리</div>
                <div class="modalMenu" @click="logoutBtn()">로그아웃</div>
				<div class="modalMenu" v-if="this.$store.state.isAdmin" @click="adminConsole()">관리자 콘솔</div>
            </div>

            <!-- <li v-if="this.$store.state.logined"><a class="navList"> 환영합니다 {{loadName}}님</a></li>
            <li v-if="this.$store.state.logined"><a @click="goMyPage()" class="navList">마이페이지</a></li> -->
            <!-- <li v-if="this.$store.state.logined"><a @click="logoutBtn()" class="navList">로그아웃</a></li> -->
        </div>
	</div>
</template>

<script>
import axios from 'axios';
import {mapMutations} from "vuex";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {
		loginUser: {},
        modalView: false
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
    modalTogle() {
        this.modalView = !this.modalView;
    },
	confirmLogin() {
		if (sessionStorage.getItem("token") == null) return; //토큰있는지 확인하고
		axios.get("http://localhost:9999/rest/user/getTokenInfo", { //있다면 토큰이 유효한지 확인함
			headers: {
				"Authentication": sessionStorage.getItem("token"),
			}
		})
		.then(response => {
            this.registTokenInfo(response.data);
            axios.post("http://localhost:9999/rest/file/download", this.$store.state.logined_id, {headers: {'Content-Type': 'text/plain'}})
                        .then(({data}) => {
                            if(data == 1) {
                                console.log("프로필 사진 있음");
                                this.$store.state.profileSrc = require("@/profile/" + this.$store.state.logined_id + ".png");
                            } else {
                                console.log("프로필 사진 없음")
                            }
                        })
			axios.get("http://localhost:9999/rest/user/isAdmin/" + this.$store.state.logined_id).then(({data}) => {
						if (data == 1) {
							this.$store.state.isAdmin = true;
						}
					})
        }) //유효하다면 vuex에 토큰 정보입력 메서드실행해서 state의 로그인 정보 입력해줌
		.catch(() => {
			alert("토큰이 만료됨"); //유효하지 않다면 경고창
			sessionStorage.removeItem("token");
			this.clearLoginInfo();
		}) 
	},
	logoutBtn() {
		sessionStorage.removeItem('token');
		this.clearLoginInfo();
        this.modalView = false;
		this.$router.push({name: "main"})
	},
	goMyPage() {
		this.confirmLogin();
		if (this.$store.state.logined == true) {
			this.modalView = false;
			this.$router.push({name: "edituser"})
		}
	},
    goLike() {
		this.modalView = false;
        this.$router.push({name: "interestarea"})
    },
	adminConsole() {
		this.modalView = false;
		console.log("관리자콘솔로")
		this.$router.push({name: "adminConsole"})
	}
  },
  computed: {
	loadName() {
		return this.$store.state.logined_name;
	}
  }
};
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Righteous&display=swap');
    #mainLogo {
        font-family: 'Righteous', cursive;
        font-size: 30px
    }
    .signup {
        border: solid;
        border-radius: 10px;
        padding: 10px;
    }
    .signup:hover {
        color:black;
        background-color: white;
        outline: none;
    }
    .navList {
        margin: 0 20px;
    }
    .flexCenter {
        display: flex;
        align-items: center;
        font-size: 20px;
    }
    #navBar {
        padding: 30px 150px;
        justify-content: space-between;
        min-width: 1120px;
    }
    li {
        list-style:none;
    }
    a {
        color: white;
        text-decoration: none;
        transition: all 0.2s;
    }
    a:hover {
        outline: none;
        text-decoration: none;
    }
    .myModal {
        z-index: 999;
        visibility: visible;
        color: #000000;
        position: absolute;
        top: 90px;
        transform: translateX(-200px);
        width: 250px;
        background-color: rgba(255, 255, 255, 0.8);
        border-radius: 10px;
        transition: all 0.5s;
         font-family: 'NanumSquareRound'; 
    }
    .noHeight {
        visibility: hidden;
        opacity: 0;
        top: 60px;
    }
    #modalDetail {
        padding: 15px 0;
        background-color: rgba(255, 255, 255, 0.8);
        border-radius: 10px;
        font-size: 18px;
    }
    .modalMenu {
        padding: 15px 0;
        text-align: center;
        border-radius: 10px;
        font-size: 15px;
        
    }
    .modalMenu:hover {
        background-color: rgba(0, 0, 255, 0.541);
        color: white;
        cursor: pointer;
    }
    .profileBox:hover {
        cursor: pointer;
    }
	.adm {
		font-size: 15px;
	}
</style>