<template>
	<div>
		<b-list-group
			v-for="(comment, index) in commentList"
			:key="index"
			class="listArea"
		>
			<b-list-group-item class="commentItem">
				<!-- <div class="profileImg">
					{{ comment.writer.substr(0, 1) }}
				</div> -->
				<comment-profile class="profileImg" :idChk="comment.writer"></comment-profile>
				<span class="writerName"> {{ comment.writer }} </span>
				<br />
				<p class="commentText">{{ comment.comment }}</p>
			</b-list-group-item>
		</b-list-group>
	</div>
</template>

<script>
import axios from "axios";
import CommentProfile from '@/components/community/comment/commentProfile.vue';

export default {
	name: "commentList",
	props: ["boardDto"],
	data() {
		return {
			commentList: [],
			profileSrc: null,
		};
	},
	components: {
		CommentProfile
	},
	methods: {
		loadComment() {
			axios
				.get(
					"http://localhost:9999/rest/comment/list/" +
						this.boardDto.articleNo
				)
				.then((response) => {
					this.commentList = response.data;

					this.$forceUpdate();
				})
				.catch((error) => {
					console.dir(error);
				});
		},
	},

	mounted() {
		this.loadComment();
	},
	// async created() {
		//comment.writer.png가 있는지 확인하고
		//있다면 this.profileSrc를 require("@/profile/${comment.writer}.png")로 수정하고
		//없다면 그냥나가고
		//후에 이미지영역에 소스바인딩해서 표현
		
	// },
};
</script>

<style src="@/assets/css/styles.css"></style>
<style>
.listArea {
	width: 100%;
}
.profileImg {
	width: 40px;
	height: 40px;
	display: inline-block;
	margin-top: 5px;
}
.writerName {
	font-family: "BMJUA";
	font-size: 20px;
	margin-left: 5px;
}
.commentText {
	font-family: "NanumSquareRound";
	margin-left: 50px;
	margin-top: 10px;
}
.commentItem {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
}
</style>
