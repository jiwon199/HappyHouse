<template>
    <div id="modalBox">
        <div id="modalWindow">
            <div id="guide" style="display:flex; align-items: flex-end"> 
                <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                    비밀 번호 찾기
                </div>
                <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0;">
                    당신의 비밀번호를 찾아보세요
                </div>
            </div>
            <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
            <div style="width: 90%; margin: 0 auto;">
                <div>
                    <div class="mainGuide">
                        아이디
                    </div>
                    <div>
                        <input class="inputedText" type="text" v-model="id">
                    </div>
                </div>
                <div style="height: 20px"></div>
                <div>
                    <div class="mainGuide">
                        이메일
                    </div>
                    <div>
                        <input class="inputedText" type="text" v-model="addr">
                    </div>
                </div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
                <button class="mainBtn find" @click="finding()">찾기</button><button class="mainBtn cancel" @click="canceling()">취소</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            id: "",
            addr: ""
        }
    },
    methods: {
        finding() {
            if (this.id=="" || this.addr=="") {
                alert("아이디와 이메일을 확인해 주세요");
                return;
            }
            //아이디와 이메일이 회원정보와 일치하는지 확인
            //일치한다면 메일을 보내고
            //일치하지않는다면 경고창을 띄움
            axios.post("http://localhost:9999/rest/user/idEmail", {
                "id": this.id,
                "addr": this.addr
            }).then(({data}) => {
                //0 => 아이디를 못찾음
                //1 => 아이디를 찾았으나 이메일과 일치하지 않음
                //2 => 아이디를 찾았고 이메일과 일치함
                if (data.status == 0) {
                    alert("찾는 아이디는 회원이 아닙니다.");
                } else if (data.status == 1) {
                    alert("계정 정보가 이메일과 일치하지 않습니다.")
                } else {
                    alert("비밀번호를 이메일로 발송합니다.")
                    axios.post("http://localhost:9999/rest/user/mailing", {
                        addr: this.addr,
                        pw: data.pw
                    });
                }
            })
        },
        canceling() {
            this.$emit("closeModal");
        }
    }
};
</script>

<style scoped>
    #modalBox {
        position: absolute;
        left: 0;
        top: 0;
        height: 100vh;
        width: 100vw;
        background: #0000006c;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #modalWindow {
        width: 500px;
        padding: 50px;
        background: white;
        box-shadow: 2px 2px 8px black;
    }
    .mainGuide {
        margin-left: 15px;
    }
    .inputedText {
        height: 35px;
        width: 100%;
        padding: 0 15px 0;
    }
    .mainBtn {
        border: none;
        border-radius: 6px;
        padding: 5px 15px;
        margin: 0 10px 0;
        color: white;
        transition: all 0.5s;
    }
    .find {
        background: #7a7cff;
    }
    .find:hover {
        background: #4f51b8;
    }
    .cancel {
        background: #ff8585;
    }
    .cancel:hover {
        background: #a54b4b;
    }
</style>