<template>
  <div id="container">
    <get-pw-modal v-if="getPwModal" @closeModal="closeM"></get-pw-modal>
    <div class="alertBox" @click="closeAlert()" v-if="this.alert">
      <div id="alertWindow">
        <div id="alertImage">
          <div id="alertCircle"></div>
          <div id="alertX" style="font-family: 'Archivo Black', sans-serif">
            X
          </div>
        </div>
        <div id="alertMessage">
          <div style="font-family: BMJUA; font-size: 40px">로그인 실패</div>
          <div style="font-family: 'NanumSquareRound'">
            아이디와 비밀번호를 확인하세요
          </div>
        </div>
      </div>
    </div>
    <div id="loginTitle">
      <div id="welcomeBox" class="textCenter" style="font-family: BMJUA">
        어서오세요
      </div>
      <div
        id="welcomeMessage"
        class="textCenter"
        style="font-family: 'NanumSquareRound'"
      >
        가장 행복한 집을 찾는 놀라운 경험
      </div>
    </div>
    <div id="inputBox" style="font-family: 'NanumSquareRound'">
      <div class="guideLine">아이디</div>
      <div class="textCenter">
        <input id="id" class="inputData" v-model="id" />
      </div>
      <div class="guideLine">비밀번호</div>
      <div class="textCenter">
        <input id="pw" class="inputData" v-model="pw" />
      </div>
      <div class="guideLine">
        <label>
          <input
            id="rememberMe"
            type="checkbox"
            :checked="rememberMe"
            @click="rememberClick"
          />
          Remember me
        </label>
      </div>
    </div>
    <div class="textCenter" style="margin: 0 0 40px 0">
      <button id="loginBtn" @click="loginBtn()">Login</button>
      <!-- 카카오 로그인  by 지원 -->
      <br />
      <img
        src="@/assets/kakao_login.png"
        alt="카카오계정 로그인"
        @click="kakaoLogin()"
        class="kakaoBtn"
      />
    </div>
    <div class="textCenter NanumSquareRound" style="margin: 0 0 10px 0">
      아직 회원이 아니신가요?
      <router-link to="/register">Sign up</router-link> | <a @click="getPw" class="getPw">비밀번호</a>를 잊으셨나요?
    </div>
    <div
      id="mainLogo"
      class="textCenter"
      style="font-family: 'Righteous', cursive"
    >
      happyHouse
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapMutations } from "vuex";
import GetPwModal from "@/views/GetPwModal.vue"

export default {
  data() {
    return {
      alert: false,
      rememberMe: false,
      id: "",
      pw: "",
      kakaoToken: "",
      kakaoName:null,
      getPwModal: false
    };
  },
  methods: {
    ...mapMutations({ registTokenInfo: "REGIST_TOKEN_INFO" }),

    closeM() {
        this.getPwModal = false;
    },
    getPw() {
        this.getPwModal = true;
    },

    rememberClick() {
      this.rememberMe = !this.rememberMe;
    },
    closeAlert() {
      this.alert = false;
    },
    loginBtn() {
      //로그인 버튼이 눌리면
      var chk = this.rememberMe;

      axios
        .post("http://localhost:9999/rest/user/login", {
          // 아이디와 비번을 가지고 통신 시작
          id: this.id,
          pw: this.pw,
        })
        .then(({ data }) => {
          if (data == "0") {
            this.alert = true;
          } else {
            if (this.rememberMe == true) {
              var date = new Date();
              date.setTime(date.getTime() + 30 * 24 * 60 * 60 * 1000);
              document.cookie =
                "id=" + this.id + ";expires=" + date.toUTCString() + ";path=/";
              document.cookie =
                "chk=" + chk + ";expires=" + date.toUTCString() + ";path=/";
            } else {
              document.cookie =
                "id= " + "; expires=" + new Date().toUTCString() + "; path=/";
              document.cookie =
                "chk= " + "; expires=" + new Date().toUTCString() + "; path=/";
            }
            axios
              .get("http://localhost:9999/rest/user/isAdmin/" + this.id)
              .then(({ data }) => {
                if (data == 1) {
                  console.log("관리자입니다.");
                  this.$store.state.isAdmin = true;
                } else {
                  console.log("관리자가 아닙니다.");
                }
              });
            alert("로그인 성공");
            sessionStorage.setItem("token", data);

            axios
              .get("http://localhost:9999/rest/user/getTokenInfo", {
                //있다면 토큰이 유효한지 확인함
                headers: {
                  Authentication: sessionStorage.getItem("token"),
                },
              })
              .then((response) => {
                this.registTokenInfo(response.data);

                //db 상의 아이디는 카카오토큰이지만, 웹 상에서는 토큰 대신 닉넴으로 보이도록 설정
                //지원 추가
                if(this.kakaoName!=null) this.$store.state.logined_id=this.kakaoName; 

                axios
                  .post(
                    "http://localhost:9999/rest/file/download",
                    this.$store.state.logined_id,
                    {
                      headers: { "Content-Type": "text/plain" },
                    }
                  )
                  .then(({ data }) => {
                    if (data == 1) {
                      console.log("프로필 사진 있음");
                      this.$store.state.profileSrc = require("@/profile/" +
                        this.$store.state.logined_id +
                        ".png");
                    } else {
                      console.log("프로필 사진 없음");
                    }
                  });
              }); //유효하다면 vuex에 토큰 정보입력 메서드실행해서 state의 로그인 정보 입력해줌
            this.$router.push({ name: "main" });
          }
        });
    },

    //-----카카오 로그인 함수 by 지원 ---------
    //카카오 로그인 버튼이 눌리면
    kakaoLogin() {
      //토큰을 받아온다.
      if (window.Kakao.Auth.getAccessToken()) {
        window.Kakao.API.request({
          url: "/v1/user/unlink",
          success: function (response) {
            this.kakaoToken = response.data;
          },
          fail: function (error) {
            console.log(error);
          },
        });
        window.Kakao.Auth.setAccessToken(undefined);
      }

      //카카오 유저 정보를 읽어오기
      this.makePromise().then((res) => {
        console.log("회원가입 및 로그인 진행")
        this.id=res.id;
        this.pw=res.kakao_account.email; //카카오 유저는 회원 정보 수정시 카카오 이메일로 진행
        this.kakaoName=res.kakao_account.profile.nickname;
        this.$store.state.kakaoId=res.id;

        //해당 카카오토큰 가진 유저가 db에 없으면 회원가입 진행
        axios
          .post("http://localhost:9999/rest/user/confirmId", this.id, {
            headers: { "Content-Type": "text/plain" },
          })
          .then(({ data }) => {
            if (data != 0) {
              console.log("회원가입을 진행합니다.")
              axios.post("http://localhost:9999/rest/user/register", {
                id: this.id,
                pw: this.pw,
                name: this.kakaoName,
                addr: "",
                tel: "",
              }).then(()=>{
                  //회원가입 진행이 끝났으면 로그인 함수 호출
                  this.loginBtn();
              });
            }
            else{
              //이미 카카오로 가입한 유저면 바로 로그인 진행
              this.loginBtn();

            }
            
          });
      });
    },
    //카카오 유저 정보를 읽어와서 promise로 반환하는 함수
    makePromise() {
      return new Promise((resolve, reject) => {
        window.Kakao.Auth.login({
          success: function () {
            window.Kakao.API.request({
              url: "/v2/user/me",
              data: {
                property_keys: ["kakao_account."],
              },
              success(authObj) {
                console.log(authObj);
                resolve(authObj);
              },
              fail: function (error) {
                console.log(error);
              },
            });
          },
          fail(error) {
            reject(error);
          },
        });
      });
    },
 
    //----------------------------
  },
  components: {
    GetPwModal
  },
  created() {
    window.Kakao.init("서비스키"); //카카오 key로 초기화
    try {
      if (document.cookie.split("; ")[1].split("=")[1]) {
        this.rememberMe = true;
        console.log(document.cookie.split("; ")[0].split("=")[1]);
        this.id = document.cookie.split("; ")[0].split("=")[1];
      }
    } catch (e) {
      console.log("쿠키 기록없음");
    }
  },
};
</script>

<style scoped>
.alertBox {
  position: absolute;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(0, 0, 0, 0.521);
}
#alertWindow {
  position: absolute;
  height: 280px;
  width: 50%;
  min-width: 600px;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  top: 0;
  bottom: 0;
  margin-top: auto;
  margin-bottom: auto;
  background: white;
  padding: 20px;
  animation-name: biggy;
  animation-iteration-count: 1;
  animation-duration: 1s;
}
@keyframes biggy {
  50% {
    transform: scale(1.2);
  }
}
#alertImage {
  height: 130px;
  width: 130px;
  margin: 0 auto 10px;
}
#alertCircle {
  position: absolute;
  height: 130px;
  width: 130px;
  border-radius: 50%;
  border: solid 10px red;
  animation-name: tik;
  animation-iteration-count: 1;
  animation-duration: 1s;
}
@keyframes tik {
  50% {
    transform: scale(1.4);
  }
  65% {
    transform: scale(0.7);
  }
  80% {
    transform: scale(1.2);
  }
  90% {
    transform: scale(0.85);
  }
  95% {
    transform: scale(1.1);
  }
}
#alertX {
  position: absolute;
  width: 130px;
  height: 130px;
  line-height: 130px;
  text-align: center;
  color: red;
  font-size: 80px;
  font-weight: 800;
  animation-name: tak;
  animation-iteration-count: 1;
  animation-duration: 1s;
}
@keyframes tak {
  60% {
    transform: translateX(0);
  }
  65% {
    transform: translateX(-5px);
  }
  70% {
    transform: translateX(5px);
  }
  75% {
    transform: translateX(-5px);
  }
  80% {
    transform: translateX(5px);
  }
  85% {
    transform: translateX(-5px);
  }
  90% {
    transform: translateX(5px);
  }
  95% {
    transform: translateX(-5px);
  }
}
#alertMessage {
  text-align: center;
}
#container {
  width: 550px;
  height: 600px;
  border: none;
  background: white;
  box-shadow: 2px 2px 10px black;
  margin: 60px auto 0;
  padding: 30px;
  animation-name: sss;
  animation-iteration-count: 1;
  animation-duration: 0.5s;
}
@keyframes sss {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.2);
  }
  85% {
    transform: scale(0.9);
  }
}
#tableForm {
  margin: 20px auto;
}
.blockCenter {
  display: block;
  margin: 0 auto;
}
.textCenter {
  text-align: center;
}
#welcomeBox {
  font-size: 40px;
  margin: 0 0 -10px 0;
  font-family: "Archivo Black", sans-serif;
}
#welcomeMessage {
  color: #797979;
}
#loginTitle {
  margin: 0 0 50px 0;
}
#inputBox {
  width: 75%;
  margin: 0 auto 30px;
  color: #929292;
}
.inputData {
  width: 95%;
  height: 45px;
  border-radius: 8px;
  margin: 0 auto 20px;
  outline: none;
  border: solid 2px rgb(92, 92, 92);
  padding: 0 0 0 25px;
}
.guideLine {
  margin: 0 30px;
}
#loginBtn {
  width: 180px;
  height: 40px;
  background: #5b9bd5;
  border: none;
  border-radius: 5px;
  outline: none;
  box-shadow: 2px 2px 4px black;
  color: white;
  margin-bottom: 10px;
}
#loginBtn:hover {
  background: #38638b;
}
.getPw{
    color: #4e76fa;
}
.getPw:hover{
    cursor: pointer;
}


/*-----소설 로그인 by 지원 ------- */
.kakaoBtn {
  cursor: pointer;
  box-shadow: 2px 2px 4px black;
}
</style>
