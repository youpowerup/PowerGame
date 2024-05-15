import axios from "axios";
//调用mutations
export const setUser = ({commit},user) => {
    commit("userStatus",user);
}
export const setToken = ({commit},token) =>{
    commit("setToken",token);
}
export const setUsername = ({commit},username) => {
    commit("setUsername",username);
}
export const setAllGame = ({commit},allGame) => {
    commit("setAllGame",allGame);
}

export const fetchGameData = ({ commit }) => {
    axios.get('/customer') // 假设后端接口为 /api/data
      .then(response => {
        commit('setAllGame', response.data.data); // 将从后端获取的数据存入 allGame 中
      })
      .catch(error => {
        console.error('Error fetching game data:', error);
      });
  }