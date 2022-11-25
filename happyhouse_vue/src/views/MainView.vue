<template>
	<div class="home">
        <!-- <button @click="doMail()">메일</button> -->
		<show-notice-modal style="z-index: 999" v-if="noticeView" :title="this.noticeTitle" :content="this.noticeContent" @closeNotice="closeN()"></show-notice-modal>
		<div id="content">
			<div class="vertical" :class="{ vm: mm }"></div>
			<div class="messageBox" :class="{ move: mm }">
				<div style="height: 30px"></div>
				<div class="mainMessage">Discover your</div>
				<div class="mainMessage">Happiest House Ever</div>
				<div style="height: 40px"></div>
				<table id="selectBox">
					<tr>
						<td>
							<b-form-select
								v-model="form.sido"
								:options="sidoList"
								@change="getGugun"
								class="selectBox"
								><option :value="null">
									시도 선택
								</option></b-form-select
							>
						</td>
						<td>
							<b-form-select
								v-model="form.gugun"
								:options="gugunList"
								@change="getDong"
								class="selectBox"
								><option :value="null">
									구군 선택
								</option></b-form-select
							>
						</td>
						<td>
							<b-form-select
								v-model="form.dong"
								:options="dongList"
								class="selectBox"
								><option :value="null">
									동 선택
								</option></b-form-select
							>
						</td>
						<td>
							<b-form-select
								v-model="form.year"
								:options="yearList"
								class="selectBox"
								><option :value="null">
									년도 선택
								</option></b-form-select
							>
						</td>
						<td>
							<b-form-select
								v-model="form.month"
								:options="monthList"
								class="selectBox"
								><option :value="null">
									월 선택
								</option></b-form-select
							>
						</td>
						<td>
							<button
								class="selectBox"
								@click="submit()"
								style="font-family: 'Arial Black'"
							>
								GO
							</button>
						</td>
					</tr>
                    
                    <tr>
                        <td colspan="6">
                            <div style="margin-top: 40px">
                                <div class="noticeBox" v-for="(notice, i) in noticeList" :key="i" style="color: white; display:flex; align-items:center;">
                                    <div class="notice Band hoverPointer" @click="showModal(notice)">new</div><div class="notice Message hoverPointer" @click="showModal(notice)">{{notice.title}}</div><div v-if="$store.state.isAdmin" class="noticeEdit" @click="noticeDelete(notice.num)">x</div>
                                </div>
                            </div>
                        </td>
                    </tr>
				</table>
			</div>
		</div>
	</div>
</template>

<script>
import axios from "axios";
import { mapMutations } from "vuex";
import ShowNoticeModal from '@/views/ShowNoticeModal';

const houseStore = "houseStore";

export default {
	name: "HouseView",
	data() {
		return {
			mm: false,

            isAdmin: this.$store.state.isAdmin,
			sidoList: [],
			gugunList: [],
			dongList: [],
			yearList: [2015, 2016, 2017, 2018, 2019, 2020, 2021],
			monthList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],

			form: {
				sido: null,
				gugun: null,
				dong: null,
				year: null,
				month: null,
			},
			houseList: [],
			dongCode: null,

			noticeList: [],
			noticeView: false,
			noticeTitle: "",
			noticeContent: "",
		};
	},
	created() {
		axios
			.get("http://localhost:9999/rest/house/optionSido")
			.then((response) => {
				this.sidoList = response.data;
			})
			.catch((error) => {
				console.dir(error);
			});
		axios.get("http://localhost:9999/rest/user/getNotice").then(({data}) => this.noticeList = data);
	},
	mounted() {
		this.mm = false;
		setTimeout(() => (this.mm = true), 1);
	},
	methods: {
		...mapMutations(houseStore, ["SET_APT_LIST"]),
		...mapMutations(houseStore, ["SET_SELECT_OPTION"]),

        async noticeDelete(num) {
            await axios.get("http://localhost:9999/rest/user/noticeDelete/" + num);
            await axios.get("http://localhost:9999/rest/user/getNotice").then(({data}) => this.noticeList = data);
        },
		showModal(item) {
			this.noticeView = true;
			this.noticeTitle = item.title;
			this.noticeContent = item.content;
		},
		getGugun() {
			axios
				.get(
					"http://localhost:9999/rest/house/optionGugun/" +
						this.form.sido
				)
				.then((response) => {
					this.gugunList = response.data.slice(1);
				})
				.catch((error) => {
					console.dir(error);
				});
		},

		getDong() {
			axios
				.get(
					"http://localhost:9999/rest/house/optionDong/" +
						this.form.gugun +
						"/" +
						this.form.sido
				)
				.then((response) => {
					this.dongList = response.data.slice(1);
				})
				.catch((error) => {
					console.dir(error);
				});
		},

		//조회 버튼을 누르면
		submit() {
			var router = this.$router;
			//리스트를 먼저 찾기
			axios({
				method: "post",
				url: "http://localhost:9999/rest/house/list",
				data: {
					sido: this.form.sido,
					gugun: this.form.gugun,
					dong: this.form.dong,
					year: this.form.year,
					month: this.form.month,
				},
			}).then((response) => {
				this.houseList = response.data;
				this.SET_APT_LIST(this.houseList);
				this.SET_SELECT_OPTION(this.form);
				router.push("/house");
			});
		},
		closeN() {
			this.noticeView = false;
		}
	},
	beforeRouteEnter(to, from, next) {
		next((vm) => {
			vm.$store.state.whiteSpace = false;
		});
	},
	beforeRouteLeave(to, from, next) {
		this.$store.state.whiteSpace = true;
		next();
	},
	components: {
		ShowNoticeModal
	}
};
</script>

<style scoped>
@font-face {
	font-family: "NanumSquareRound";
	src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff")
		format("woff");
	font-weight: normal;
	font-style: normal;
}
.home {
	min-width: 1100px;
}
#content {
	display: flex;
	align-items: start;
	color: white;
	margin: 100px auto;
	border: none;
	width: 70%;
	height: 500px;
}
.vertical {
	width: 3px;
	height: 0;
	background: white;
	transition: all 1s;
}
.vm {
	height: 110%;
}
.messageBox {
	opacity: 0;
	font-size: 60px;
	font-family: "Arial Black";
	text-shadow: 5px 5px 5px black;
	transition: all 1s;
}
.move {
	opacity: 1;
	transform: translateX(80px);
}
.start {
	background: red;
}
#selectBox {
	text-shadow: none;
	color: black;
	font-size: 20px;
	font-family: "NanumSquareRound";
}
.dropDownBox {
	width: 130px;
	font-size: 13px;
	border: solid 1px #777777;
	border-radius: 8px;
	padding: 3px 10px;
	margin: 0 10px;
	background: white;
}
td {
	padding: 0 30px 0 0;
	width: 170px;
}
.selectBox {
	box-shadow: 5px 5px 10px black;
}
td > button {
	font-size: 18px;
	padding: 2px 15px;
	border: none;
	border-radius: 10px;
	transition: all 0.2s;
}
td > button:hover {
	background-color: blue;
	color: white;
}
.Band {
	animation-name: twinkle;
	animation-iteration-count: infinite;
	animation-duration: 1s;
	border-radius: 5px;
	padding: 1px 5px;
}
@keyframes twinkle {
	30% {background: #6272ff;}
    80% {background: #6272ff;}
}
.Message {
	margin-left: 10px;
}
.noticeBox {
	margin-bottom: 5px;
}
.noticeEdit {
    margin-left: 10px;
    border: none;
    border-radius: 5px;
    background: #ff6c6c;
    font-size: 15px;
    color: white;
    padding: 0 5px
}
.noticeEdit:hover {
    background: #c44b4b;
    cursor: pointer;
}
</style>