<template>
  <div class="login">
    <my-login/>
  </div>
</template>

<script>
import Login from '../components/Login.vue';
// @ is an alias to /src


export default {
  name: 'login',
  components: {
    'my-login': Login
  }
}
</script>
