import vuex from 'vuex';
import vue from 'vue';

import * as getters from'./getters'
import * as mutations from'./mutations'
import * as actions from'./actions'


vue.use(vuex);
const store = new vuex.Store({
    state:{
        username:'stranger',
        allGame:[],
        token:''
    },
    getters,
    actions,
    mutations
})

export default store;