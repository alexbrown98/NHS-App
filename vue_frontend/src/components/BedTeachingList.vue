<template>
  <v-card class="mx-auto">
    <v-toolbar color="dark" dark dense>
      <v-toolbar-title>Next Teaching Sessions</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>

    <v-list three-line>
      <template v-for="(item, i) in items">
        <v-list-item :key="i" v-bind:to="'sessionDetails/'+item.id">
          <v-list-item-avatar>
            <v-img :src="avatar"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.title"></v-list-item-title>
            <v-list-item-subtitle class="text--primary" v-html="item.location"></v-list-item-subtitle>
            <v-list-item-subtitle v-html="item.description"></v-list-item-subtitle>
            
            <v-list-item-subtitle v-if="item.doctor !== null" v-html="item.doctor.firstName"></v-list-item-subtitle>
          </v-list-item-content>

          <v-list-item-content>
            <v-list-item-subtitle>{{item.time}}</v-list-item-subtitle>
          </v-list-item-content>

        </v-list-item>
      </template>
    </v-list>
    <v-btn color="pink" dark absolute bottom left fab v-on:click="requestSessions">
      <v-icon>mdi-restore</v-icon>
    </v-btn>


    <v-dialog v-model="dialog">
      <template v-slot:activator="{ on }">
        <v-btn color="pink" dark absolute bottom right fab v-on="on">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">New Session</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="form" v-model="form_valid">
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field label="Title*" required v-model="userdata.title"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field
                    label="Location"
                    required
                    v-model="userdata.location"
                    hint="Please enter the location of the session"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Description" v-model="userdata.description"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  
                <v-dialog
                  ref="dialog"
                  v-model="modal"
                  :return-value.sync="userdata.time"
                  persistent
                  width="290px"
                >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    v-model="userdata.time"
                    label="Session Date"
                    readonly
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker v-model="date" color="pink" scrollable>
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="modal = false">Cancel</v-btn>
                  <v-btn text color="primary" @click="$refs.dialog.save(date)">OK</v-btn>
                </v-date-picker>
              </v-dialog>
              <v-select
                  v-model="userdata.doctor"
                  :items="doctors"
                  item-value="id"
                  item-text="firstName"
                  label="Doctor"
              ></v-select>
            </v-col>
                   
              </v-row>
            </v-form>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog=false">Close</v-btn>
          <v-btn color="blue darken-1" text v-on:click="addSession" @click="dialog = false" :disabled="!form_valid">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import API from "../api.js";

export default {
  data: () => ({
    modal: false,
    date: "",
    popup: false,
    dialog: false,
    form_valid: true,
    avatar:
      "https://img.medscapestatic.com/thumbnail_library/dt_140605_serious_male_doctor_hospital_800x600.jpg",
    items: [],
    doctors: [],
    userdata: {
      title: "",
      description: "",
      location: "",
      time:"",
      completed:false,
      sessionID: "",
      doctor: null,
    }
  }),

  computed: {
      functionEvents () {
        return this.month ? this.monthFunctionEvents : this.dateFunctionEvents
      },
    },
  
  methods: {
      setCompleted(item) {
        let session_url = 'api/editsession/' + item.id;
        const data = this.userdata;
        this.userdata.title = item.title;
        this.userdata.location = item.location;
        this.userdata.time = item.time;
        this.userdata.completed = true;
        item.completed = true;
        let self = this;
        API({
          method: 'put',
          url: session_url,
          data: data,
        })
        .then(function (response) {
                //handle success
                self.requestSessions();
                self.$refs.form.reset();
                console.log(response);
            })
            .catch(function (response) {
                //handle error
                console.log(response);
          });
    },
    closePopup: function () {
        this.popup = "false"
      },
    dateFunctionEvents (date) {
        const [,, day] = date.split('-')
        if ([12, 17, 28].includes(parseInt(day, 10))) return true
        if ([1, 19, 22].includes(parseInt(day, 10))) return ['red', '#00f']
        return false
      },
      monthFunctionEvents (date) {
        const month = parseInt(date.split('-')[1], 10)
        if ([1, 3, 7].includes(month)) return true
        if ([2, 5, 12].includes(month)) return ['error', 'purple', 'rgba(0, 128, 0, 0.5)']
        return false
      },
    
    requestSessions: function() {
      console.log("requestSessions function");
      const url = "api/nextsessions";
      let self = this;
      API.get(url).then(function(response) {
        self.items = response.data;
        console.log(response.data)
      });
    },
    requestDoctors: function() {
      console.log("requestDoctors function");
      const url = "api/alldoctors";
      let self = this;
      API.get(url).then(function(response) {
        self.doctors = response.data;
        console.log(response.data)
      });
    },
    addSession: function () {
        const data = this.userdata;
        let self = this;
        API({
          method: 'post',
          url: 'api/addsession',
          data: data
          })
          .then(function (response) {
              //handle success
              self.requestSessions();
              self.$refs.form.reset();
              console.log(response);
          })
          .catch(function (response) {
              //handle error
              console.log(response);
        });
      }
  },
  mounted: function() {
    this.requestSessions();
    this.requestDoctors();
  }
};
</script>