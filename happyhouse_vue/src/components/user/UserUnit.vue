<template>
	<tr>
		<td>{{dto.id}}</td>
		<td>{{dto.name}}</td>
		<td>{{dto.addr}}</td>
		<td>{{dto.tel}}</td>
		<td v-if="isAdmin">관리자</td>
		<td v-else><span>일반유저</span><button style="margin-left: 10px;" @click="promotion(dto.id)">승격</button></td>
	</tr>
</template>

<script>
import axios from 'axios'

export default {
	data() {
		return {
			isAdmin: false
		}
	},
	props: ["dto"],
	created() {
		axios.get("http://localhost:9999/rest/user/isAdmin/" + this.dto.id).then(({data}) => {
			if (data == 1) {
				this.isAdmin = true;
			}
		})
	},
	methods: {
		async promotion(id) {
			await axios.get("http://localhost:9999/rest/user/promotion/" + id)
			this.isAdmin = true;
		}
	},
};
</script>

<style scoped>
	button {
		padding: 3px 8px;
		border-radius: 8px;
		border: none;
		background: #769bff;
		color: white;
	}
	td {
		border-top: solid 1px #c5c5c5;
		height: 40px;
	}
</style>