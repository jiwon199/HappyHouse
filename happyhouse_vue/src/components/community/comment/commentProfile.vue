<template>
	<div>
		<div v-if="profileChk != null"><img :src="profileChk" style="width: 40px; height: 40px; border-radius: 50%; border:none;"></div>
        <div v-if="profileChk == null"><div style="width: 40px; height: 40px; border-radius: 50%; border:none; color: white; text-align:center; line-height: 40px; background: green">{{idChk.substr(0,1)}}</div></div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	data() {
		return {
			profileChk: null,
		}
	},
	props: ["idChk"],
	async created() {
		console.log(this.idChk);
		//comment.writer.png가 있는지 확인하고
		//있다면 this.profileSrc를 require("@/profile/${comment.writer}.png")로 수정하고
		//없다면 그냥나가고
		//후에 이미지영역에 소스바인딩해서 표현
		await axios.post("http://localhost:9999/rest/file/profileChk", this.idChk, {headers: {'Content-Type': 'text/plain'}})
        .then(({data}) => {
            if (data == 1) {
                this.profileChk = require("@/profile/" + this.idChk + ".png");
            }
        });
	}
};
</script>

<style>
</style>