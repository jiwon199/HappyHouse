<template>
	<div id="container">
		<notice-write v-if="showModal" @doCancel="canceling()"></notice-write>
		<div id="guide" style="display:flex; align-items: flex-end"> 
            <div style="font-size: 22px; margin-right: 5px; font-family: BMJUA">
                관리자 콘솔
            </div>
            <div style="color: #777777; font-size: 12px; padding: 0 0 2px 0;">
                관리자 콘솔에서 완장질을 해보세요!
            </div>
        </div>
        <div style="width: 350px; background: #aaa; height: 1px; margin-top: 3px; margin-bottom: 20px;"></div>
		<div style="display: flex; justify-content:center;">
			<div class="menu">
				<div class="box hoverPointer" @click="userList()">
					<span class="menuWhat BMJUA">회원 목록</span><br>
					<img class="menuImg" src="@/assets/users.svg"/>
				</div>
			</div>
			<div style="width:20px"></div>
			<div class="menu">
				<div class="box hoverPointer" @click="noticeWrite()">
					<span class="menuWhat BMJUA">공지 사항</span><br>
					<img class="menuImg" src="@/assets/pen.svg"/>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import noticeWrite from "@/components/user/NoticeWrite.vue";

export default {
	data() {
		return {
			showModal: false
		}
	},
	methods: {
		userList() {
			this.$router.push({name: "userList"})
		},
		noticeWrite() {
			this.showModal = true;
		},
		canceling() {
			this.showModal = false;
		}
	},
	components: {
		noticeWrite
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
	.box {
		border: solid rgb(158, 158, 158);
		border-radius: 20px;
		text-align: center;
		padding: 50px;
		transition: all 0.1s;
	}
	.box:hover {
		background: rgb(196, 196, 196);
	}
	.menu {
		display:flex;
		justify-content:center;
		align-items: center;
		width: 40%;
		height: 500px;
	}
	.menuWhat {
		font-size: 30px;
	}
	.menuImg {
		height: 180px;
		margin: 30px;
	}
</style>