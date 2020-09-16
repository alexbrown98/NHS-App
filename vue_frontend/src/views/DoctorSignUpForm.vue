<template>
  <v-card>
    <v-card-title>
      <span class="headline">Create Doctor Account</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form ref="form">
          <v-row>
            <v-col cols="12">
              <v-text-field label="Name*" required v-model="userdata.firstName"></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field label="NHS Email*" required v-model="userdata.email"></v-text-field>
            </v-col>
          </v-row>
        </v-form>
      </v-container>
      <small>*indicates required field</small>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text>Close</v-btn>
      <v-btn color="blue darken-1" text v-on:click="addDoctor">Save</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import API from "../api.js";
  export default {
    data: () => ({
      userdata: {
      firstName: "",
      email: ""
    }
  }),

  methods: {
    addDoctor: function() {
      console.log("addDoctor function");
      const url = "api/adddoctor";
      const myData = this.userdata;
      API(url, {method: "post", data: myData}).then(function() {
        /*self.requestSessions();*/
        /*self.$refs.form.reset();*/
      });
    },
  }
};
</script>