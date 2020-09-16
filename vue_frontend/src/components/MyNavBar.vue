<template>
  <v-bottom-navigation
    color="deep-purple"
    app
    shift
  >
    <v-container>
      <v-row
       justify="center"
      >
        <v-btn to="/">
          <span>Next Sessions</span>
          <v-icon>mdi-timetable</v-icon>
        </v-btn>

        <v-btn to="/about">
          <span>Reviews</span>
          <v-icon>mdi-clipboard-text</v-icon>
        </v-btn>

        <v-btn to="/Opportunities">
          <span>Opportunities</span>
          <v-icon>mdi-stethoscope</v-icon>
        </v-btn>

        <v-btn to="/signup">
          <span>Signup</span>
          <v-icon>mdi-stethoscope</v-icon>
        </v-btn>
      </v-row>
    </v-container>
  </v-bottom-navigation>
</template>

<script>
  export default {
    data () {
      return {
        activeBtn: 0
      }
    },
  }
</script>