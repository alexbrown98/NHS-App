<template>
  <v-container>
    <v-alert type="error" v-if="loginerror">Incorrect username or password supplied.</v-alert>
    <v-form
      ref="form"
      lazy-validation
    >
      <v-text-field
        v-model="username"
        :counter="20"
        label="Username"
        required
      ></v-text-field>

      <v-text-field
        v-model="password"
        label="Password"
        required
      ></v-text-field>

      <v-btn
        color="success"
        class="mr-4"
        @click="login"
      >
        Validate
      </v-btn>
    </v-form>
  </v-container>
</template>

<script>

import API from "../api.js";
import store from "../store"

  export default {
    data: () => ({
      username: '',
      password: '',
      loginerror: false
    }),

    methods: {
      login: function () {
        //const url = "http://localhost:8080/login";
        var bodyFormData = new FormData();
        bodyFormData.set('username', this.username);
        bodyFormData.set('password', this.password);
        API({
          method: 'post',
          url: '/login',
          data: bodyFormData,
          headers: {'Content-Type': 'multipart/form-data' }
          })
          .then((response) => {
            //handle success
            store.dispatch('session/login')
            this.loginerror = false;
            this.$router.push('/');
            console.log(response);
          })
          .catch((response) => {
            //handle error
            this.loginerror = true;
            console.log(response);
        });
      }
    }
  }
</script>