 

const houseStore = {
  namespaced: true,
  state: {
    houses: [],
    selectOpt:[],
    
  },
  getters: {},
  mutations: {
    SET_APT_LIST(state, houseList) {
      state.houses=houseList;
    },
    SET_SELECT_OPTION(state,form){
      state.selectOpt=form;
    },
    
  },
  actions: {
    
  },
};

export default houseStore;
