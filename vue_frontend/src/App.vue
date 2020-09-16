<template>
  <v-app>
    <MyAppBar/>
    <v-content>
      <div id="app">
        <router-view/>
      </div>
    </v-content>
    <MyNavBar/>
  </v-app>
</template>

<script>
import MyNavBar from './components/MyNavBar'
import MyAppBar from './components/MyAppBar'
export default {
  name: 'app',
  components: {
    'MyNavBar': MyNavBar,
    'MyAppBar': MyAppBar
  }
}
</script>
