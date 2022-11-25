<template>
	<div id="container">
		<div id="guide" style="display:flex; align-items: flex-end"> 
            <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                관리자 콘솔
            </div>
            <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0;">
                관리자 콘솔에서 완장질을 해보세요!
            </div>
        </div>
        <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
		<div id="containerTitle" class="BMJUA">유저 목록</div>
		<table style="margin: 0 auto; width: 1000px; text-align: center">
			<colgroup>
				<col style="width: 20%">
				<col style="width: 20%">
				<col style="width: 20%">
				<col style="width: 20%">
				<col style="width: 20%">
			</colgroup>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>주소</th>
				<th>전화번호</th>
				<th>권한</th>
			</tr>
			<user-unit v-for="(dto, i) in userList" :key="i" :dto="dto"></user-unit>
		</table>

	</div>
</template>

<script>
import axios from "axios"
import UserUnit from '@/components/user/UserUnit.vue';

export default {
	data() {
		return {
			userList: []
		}
	},
	created() {
		axios.get("http://localhost:9999/rest/user/userList").then(({data}) => this.userList = data)
	},
	components: {
		UserUnit
	}
};
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
	#containerTitle {
		text-align: center;
		margin: 10px;
		font-size: 30px;
	}
	th {
		background-color: #c5c5c5;
		height: 30px;
	}
</style>