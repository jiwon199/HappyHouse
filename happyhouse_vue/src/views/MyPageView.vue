<template>
  <div>
	<div id="mynav">
		<router-link to="/mypage/interestarea">관심지역</router-link> | 
		<router-link to="/mypage/edituser">회원정보 수정</router-link> | 
		<router-link to="/mypage/deleteuser">회원 탈퇴</router-link>
	</div>
	<router-view></router-view>
  </div>
</template>

<script>
export default {

}
</script>

<style>
	#mynav {
		margin: 10px;
		text-align: center;
	}
</style>