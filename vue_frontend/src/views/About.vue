<template>
  <v-container>


    <v-toolbar color="dark" dark dense>
      <v-toolbar-title>Completed Sessions</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>

    
  <v-flex v-for="(item,i) in items" :key=i>
  
    <v-card color ="light gray" margin="10px">
      <v-list-item three-line  v-bind:to="'sessionDetails/'+item.id">
      <v-list-item-content>
        <v-list-item-title class="headline mb-1">{{item.title}}</v-list-item-title>
        <v-list-item-subtitle>{{item.time}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-card-actions>
      <v-dialog v-model="item.dialog">
      <template v-slot:activator="{ on }">
        <v-btn color="pink" dark absolute bottom right v-on="on">
          Give Feedback
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Fedeback Form</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="form">
              <v-textarea v-model="userdata.content" label="Feedback"></v-textarea>
              <div allign="center" width="20">
              <v-rating
                v-model="userdata.rating"
                background-color="blue"
                color="blue"
                dense
                half-increments
                hover
                size="25"
                position="center"
              >
                </v-rating>
              </div>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="item.dialog=false">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="item.dialog=false;userdata.sessionID=item.id;" :disabled="!form_valid" v-on:click="submitFeedback">Submit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    </v-card-actions>
    </v-card>
  </v-flex>

    <v-btn color="pink" dark absolute bottom left fab v-on:click="requestSessions">
      <v-icon>mdi-restore</v-icon>
    </v-btn>
   
  </v-container>
</template>

<script>
import API from "../api.js";
export default {
  data: () => ({
    rating: '',
    dialog: false,
    form_valid: true,
    avatar:
      "https://img.medscapestatic.com/thumbnail_library/dt_140605_serious_male_doctor_hospital_800x600.jpg",
    items: [],
    userdata: {
      sessionID: 0,
      content: "",
      rating: 0,
    }
  }),
  methods: {
    
    requestSessions: function() {
      console.log("requestSessions function");
      const url = "api/completedsessions";
      let self = this;
      API.get(url).then(function(response) {
        self.items = response.data;
        console.log(response.data)
      });
    },

    submitFeedback: function() {
      console.log("submitting feedback");
      console.log(this.userdata);
      const url = "api/addfeedback";
      const data = this.userdata;
      API(url, {method: "post", data: data}).then(function() {
        // submitted
      });
    }

  },
  mounted: function() {
    this.requestSessions();
  }
};
</script>