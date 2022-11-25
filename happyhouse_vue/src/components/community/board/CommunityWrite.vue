<template>
  <div>
	<div id="container" class="textCenter">
        <div id="guide" style="display:flex; align-items: flex-end"> 
            <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                Community
            </div>
            <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0; font-family: 'NanumSquareRound';">
                커뮤니티에서 자유롭게 소통하세요
            </div>
        </div>
        <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
        <div style="display: flex; justify-content: center">
            <div class="contentBox contentLeft">
                <input id="title" class="inputedText BMJUA" placeholder="글 제목을 입력해 주세요" v-model="title">
                <textarea id="mainContent" class="inputedText NanumSquareRound" v-model="content" placeholder="글 내용을 입력해 주세요

커뮤니티 이용 준수 사항을 지켜주세요
욕설 및 음란 게시물 업로드를 자제해 주세요
무지성 비방을 주의해 주세요
특정 집단에 대한 혐오 조장성 글은 관리자에 의해 삭제됩니다.
"></textarea>
            </div>
            <div class="contentBox contentRight">
                <input type="file" accept="image/png, image/jpeg" @change="loaded" ref="fileBtn" style="display: none;" multiple/>
                <b-button @click="fileBtn()">사진 선택</b-button>
                <div style="display: flex; width: 100%; flex-wrap: wrap; margin-bottom: 40px">
                    <div v-for="(i, idx) in loadedImage" :key="idx">
                        <img :src="i" style="height: 100px; margin: 5px">
                    </div>
                </div>
                <b-button variant="outline-primary" @click="upload()">올리기</b-button>
            </div>
        </div>
	</div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
	data() {
		return {
			"title": "",
			"content": "",
            loadedImage: [],
            files: [],
		}
	},
	methods: {
        async upload() {
            await axios.post("http://localhost:9999/rest/board/write", {
				"title": this.title,
				"content": this.content,
				"writer": this.$store.state.logined_id
			}).then((response) => {
                if (this.files.length == 0) {
                    alert("파일없는 글이 정상 등록");
                    this.$router.push({name:"community"});
                    return;
                }
                console.log("파일있음");
                var formData = new FormData();
                for (var f of this.files) {
                    formData.append("file", f);
                }

                axios.post("http://localhost:9999/rest/file/articleFile/"+response.data, formData, {headers: {'Content-Type': 'multipart/form-data'}})
                alert("글이 정상 등록 되었습니다.");
                this.$router.push({name:"community"});
            });
        },
        loaded(e) {
            console.log(e.target.files);
            // this.file = e.target.files[0];
            this.loadedImage = [];
            this.files = [];
            for (var el of e.target.files) {
                this.files.push(el);
                this.loadedImage.push(URL.createObjectURL(el))
            }
            console.log(this.files)
        },
        fileBtn() {
            this.$refs.fileBtn.click();
        },
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
	}
    .contentBox {
        height: 540px;
    }
    .contentLeft {
        width: 55%;
    }
    .contentRight {
        padding: 30px;
        width: 40%;
        overflow: auto;
    }
    .inputedText {
        width: 95%;
        margin: 10px;
        padding: 20px;
    }
    #title {
        height: 50px;
        padding-left: 15px;
        font-size: 20px;
    }
    #mainContent {
        resize: none;
        height: 440px;
    }
    b-button {
        margin: 0 auto;
    }
</style>