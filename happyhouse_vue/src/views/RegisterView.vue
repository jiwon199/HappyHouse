<template>
  <div id="container">
    <div id="mainBox">
        <div class="mainContent">
            <div id="registerTitle">
                <div id="welcomeBox" class="textCenter BMJUA">
                    회원 가입
                </div>
                <div id="welcomeMessage" class="textCenter">
                    회원 가입을 통해 행복한 집을 찾아 보세요~
                </div>
            </div>
            
            <div id="inputBox">
                <div class="guideLine">
                    아이디
                </div>
                <div class="textCenter">
                    <input id="id" v-model="id" @keyup="confirmId" class="inputData">
                </div>
                <div id="confirmed" class="confirming">
                </div>
                <div class="guideLine">
                    비밀번호
                </div>
                <div class="textCenter" style="margin: 0 0 15px 0;">
                    <input id="pw" v-model="pw" @keyup="confirmPw" class="inputData">
                </div>
                <div class="textCenter">
                    <input id="pw2" v-model="pw2" @keyup="confirmPw" class="inputData">
                </div>
                <div id="confirmPw" class="confirming">
                </div>
            </div>
        </div>
        <div class="vertical"></div>
        <div class="mainContent">
            <div id="inputBox">
                <div class="guideLine">
                    이름
                </div>
                <div class="textCenter tempMargin">
                    <input id="name" v-model="name" class="inputData">
                </div>
                <div class="guideLine">
                    주소
                </div>
                <div class="textCenter tempMargin">
                    <input id="addr" v-model="addr" class="inputData">
                </div>
                <div class="guideLine">
                    전화번호
                </div>
                <div class="textCenter">
                    <input id="tel" v-model="tel" class="inputData">
                </div>
            </div>
            <div id="fileInputBox">
                <div class="guideLine">Profile</div>
                <div id="fileInput" style="height: 40px; margin: 20px;">
                    <div id="fileInputData" style="padding: 0 0 0 50px;">
                        <input type="file" accept="image/png, image/jpeg" @change="loaded" ref="fileBtn" style="display: none;">
                        <button @click="openFile()" class="imgSelectBtn cancelImg">사진 선택</button>
                    </div>
                    <div style="display: flex">
                        <div id="preivewDiv">
                            <img v-if="this.loadedImage != ''" id="previewImg" :src="loadedImage" style="width:50px; height: 50px; border-radius: 50%"/>
                            <div v-if="this.loadedImage == '' && this.name.length > 0" id="previewImg" :src="loadedImage" style="background-color:green; width:50px; height: 50px; border-radius: 50%; color:white; text-align:center; line-height: 50px;">{{name.substr(0,1)}}</div>
                        </div>
                        <div style="width: 80px; display:flex; align-items:center; margin: 0 0 0 10px"><button v-if="this.loadedImage != ''" @click="cancelImg()" id="cancelImage">취소</button></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="textCenter" style="margin: 30px 0 20px 0">
        <button id="registerBtn" @click="registerBtn(this)">회원가입</button>
          
    </div>
    <div class="textCenter">
        Already have account : <router-link to="/login">Login</router-link>
    </div>
    <div id="mainLogo" class="textCenter" style="font-family: 'Righteous', cursive; margin: 40px">
        happyHouse
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {
	data() {
		return {
			id: "",
			pw: "",
			pw2: "",
			name: "",
			addr: "",
			tel: "",
			idchk: false,
			pwchk: false,
            loadedImage: "",
            file: null
		}
	},
	methods: {
        cancelImg() {
            this.loadedImage="";
        },
        openFile() {
            this.$refs.fileBtn.click();
        },
        loaded(e) {
            this.file = e.target.files[0];
            this.loadedImage = URL.createObjectURL(e.target.files[0]);
        },
		confirmId() {
			var confirmed = document.querySelector("#confirmed");
			if (this.id == "") {
				confirmed.innerText = "";
				this.idchk = false;
				return;
			}
			axios.post("http://localhost:9999/rest/user/confirmId", this.id, {headers: {'Content-Type': 'text/plain'}})
			.then(({data}) => {
				if (data == 0) {
					confirmed.innerText = "사용 불가한 아이디 입니다.";
					confirmed.style.color = "red";
					this.idchk = false;
				} else {
					confirmed.innerText = "사용 가능"
					confirmed.style.color = "green";
					this.idchk = true;
				}
			})
		},
		confirmPw() {
			var confirmPw = document.querySelector("#confirmPw");
			if (this.pw == "" && this.pw2 == "") {
				confirmPw.innerText="";
				this.pwchk = false;
				return;
			}
			if (this.pw == this.pw2) {
				confirmPw.innerText="비밀번호가 일치합니다."
				confirmPw.style.color = "green";
				this.pwchk = true;
				return true;
			}else {
				confirmPw.innerText="비밀번호가 일치하지 않습니다."
				confirmPw.style.color = "red";
				this.pwchk = false;
				return 
			}
		},
		async registerBtn() {
			if (this.idchk && this.pwchk) {
				await axios.post("http://localhost:9999/rest/user/register", {
					"id": this.id,
					"pw": this.pw,
					"name": this.name,
					"addr": this.addr,
					"tel": this.tel
				});
                if (this.file != null) {
                    console.log(this.file)
                    
                    var formData = new FormData();
                    formData.append("file", this.file);
                    
                    await axios.post("http://localhost:9999/rest/file/upload/"+this.id, formData, {
                            headers: {'Content-Type': 'multipart/form-data'}
                        }
                    )
                }
				alert("완료");
				this.$router.push({name: "main"})
			} else {
				alert("아이디와 비밀번호를 확인하세요")
			}
		}
	},
}
</script>

<style scoped>
	#container {
        width: 1000px;
        height: 700px;
        border: none;
        background: white;
        box-shadow: 2px 2px 10px black;
        margin: 60px auto 0;
        padding: 40px;
        animation-name: sss;
        animation-iteration-count: 1;
        animation-duration: 0.5s;
    }
    @keyframes sss{
        0% {transform: scale(0)}
        50% {transform: scale(1.2)}
        85% {transform: scale(0.9)}
    }
    #mainBox {
        width: 100%;
        height: 68%;
        display: flex;
        align-items: stretch;
    }
    .mainContent {
        width: 50%;
        padding: 20px 0;
    }
    .vertical {
        width: 2px;
        height: 95%;
        background: #656ddf;
    }
    #fileInput {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    #registerTitle {
        text-align: center;
    }

    #welcomeBox {
        font-size: 40px;
        margin: 0 0 -10px 0;
    }
    #welcomeMessage {
        color: #797979
    }
    #registerTitle {
        margin: 0 0 30px 0;
    }
    #inputBox {
        width: 75%;
        margin: 0px auto 30px;
        color: #929292
    }
    .inputData {
        width: 95%;
        height: 45px;
        border-radius: 8px;
        margin: 0 auto 0px;
        outline: none;
        border: solid 2px rgb(92, 92, 92);
        padding: 0 0 0 25px;
    }
    .guideLine {
        margin: 0 30px;
    }
    .confirming {
        height: 30px;
        margin: 0 0 10px;
        text-align: right;
        padding: 0 40px;
    }
    #registerBtn {
        width: 150px;
        height: 50px;
        background: #5B9BD5;
        border: none;
        border-radius: 5px;
        outline: none;
        box-shadow: 2px 2px 4px black;
        color: white;
    }
    #registerBtn:hover {
        background: #38638b;
    }
    .tempMargin {
        margin: 0 0 12px 0;
    }
    #fileInputBox {
        width: 75%;
        margin: 0 auto;
    }
    .textCenter {
        text-align: center;
    }
    .imgSelectBtn {
        border: solid rgb(60, 110, 168);
        border-radius: 10px;
        background-color: white;
    }
    .imgSelectBtn:hover {
        border: solid rgb(60, 110, 168);
        border-radius: 10px;
        background-color: rgb(60, 110, 168);
        color: white;
        outline: none;
    }
    #cancelImage {
        border: solid rgb(190, 64, 64);
        border-radius: 10px;
        background-color: white;
    }
    #cancelImage:hover {
        border: solid rgb(190, 64, 64);
        border-radius: 10px;
        background-color: rgb(190, 64, 64);
        color: white;
        outline: none;
    }
    button {
        transition: all 0.1s;
    }
</style>