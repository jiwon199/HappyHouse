import Vue from "vue";
import Vuex from "vuex";
import houseStore from "@/store/modules/houseStore";
Vue.use(Vuex);

export default new Vuex.Store({
   
  state: {
    logined: false,
    logined_id: "",
    logined_name: "",
    logined_addr: "",
    logined_tel: "",
    profileSrc: null,
    kakaoId:null,
    whiteSpace: true,
	isAdmin: false
  },
  getters: {},
  mutations: {
    REGIST_TOKEN_INFO(state, user) {
      state.logined = true;
      state.logined_id = user["id"];
      state.logined_name = user["name"];
      state.logined_addr = user["addr"];
      state.logined_tel = user["tel"];
    },
    CLEAR_LOGIN_INFO(state) {
      state.logined = false;
      state.logined_id = "";
      state.logined_name = "";
      state.logined_addr = "";
      state.logined_tel = "";
      state.profileSrc = null;
		state.isAdmin = false;
    },
     
  },
  actions: {},
  modules: {houseStore},
});
