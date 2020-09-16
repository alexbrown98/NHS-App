import axios from "axios";
import store from "./store"

//Put your local IP ("localhost", or private IPV4 to test it on your smartphone) in a LOCAL env file
//See : https://cli.vuejs.org/guide/mode-and-env.html#environment-variables for more info
export const baseURL = 'http://' + process.env.VUE_APP_BASE_URL + `:8080/`;

 const API = axios.create({
  baseURL: baseURL,
  withCredentials: true,
})

API.interceptors.response.use(function (response) {
    return response
  }, function (error) {
    if(!error.response){
      console.log("No response from server")
      console.log(baseURL)
      return
    }
    if (error.response.data.status === 401) {
      console.log('access denied, disconnecting user')
      store.dispatch('session/logout') //Update auth status in store
      this.$router.push('/login')
    }
    return Promise.reject(error)
  })

  export default API