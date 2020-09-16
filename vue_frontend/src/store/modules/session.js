const state = {
  isAuthenticated : false,
}

const getters = {
  authStatus: state => state.isAuthenticated
}

const mutations = {}

const actions = {
  login() {
    state.isAuthenticated = true
  },
  logout() {
    state.isAuthenticated = false
  }
}

export default {
    namespaced : true,
    state,
    getters,
    actions,
    mutations
}