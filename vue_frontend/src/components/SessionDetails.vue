<template>
  <v-container>
    <v-card
    class="mx-auto"
    max-width="80%"
    >
      <v-card-text>

        <div v-if="!sessionDetails.completed">{{sessionDetails.time}}</div>
        <div v-else>Completed</div>

        <p class="display-1 text--primary">
          {{sessionDetails.title}}
        </p>
        <p>{{sessionDetails.location}}</p>
        <div class="text--primary">
          {{sessionDetails.description}}
        </div>

        <div v-if="sessionDetails.doctor !== null">
          <span>{{sessionDetails.doctor.firstName}}</span><br>
          <span>Contact: {{ sessionDetails.doctor.email }}</span>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn
          text
          color="deep-purple accent-4"
          v-on:click="bookSession"
        >
          Book
        </v-btn>
        <v-btn
          text
          color="deep-purple accent-4"
          v-on:click="setCompleted"
        >
          Mark as completed
        </v-btn>
      </v-card-actions>
    </v-card>

    <div class="bookings">
      <div class="details">
        <h1>Bookings</h1>
        <v-btn color="pink"  v-on:click="requestBookings">
          <v-icon>mdi-restore</v-icon>
        </v-btn>
      </div>

      <v-list three-line>
        <template v-for="(booking, i) in bookings">
          <v-list-item :key="i">

            <v-list-item-content>
              <v-list-item-title v-html="booking.id"></v-list-item-title>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-subtitle v-html="convertDate(booking.time)"></v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-subtitle>{{booking.user.firstname}} {{booking.user.lastname}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </div>

    <div class="bookings" v-if="sessionDetails.completed">
      <div class="details">
        <h1>Feedback</h1>
        <v-btn color="pink"  v-on:click="requestFeedback">
          <v-icon>mdi-restore</v-icon>
        </v-btn>
      </div>

      <v-list three-line>
        <template v-for="(feedback, i) in feedbacks">
          <v-list-item :key="i">

            <v-list-item-content>
              <v-list-item-title v-html="feedback.content"></v-list-item-title>
            </v-list-item-content>
            
            <v-list-item-content>
              <v-list-item-subtitle>{{feedback.rating }} stars</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-subtitle>{{feedback.username}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </div>

  </v-container>
</template>

<script>

import API from "../api.js";
/*
import store from "../store"
*/
  export default {
    data: () => ({
      sessionId : 0,
      bookings: {},
      feedbacks: {},
      sessionDetails: {},
    }),

    methods: {
      requestBookings: function() {
        console.log("requestBookings function");
        const url = "api/bookingsbysession/"+this.sessionId;
        console.log('url requested : ', url)
        let self = this;
        API.get(url).then(function(response) {
          self.bookings = response.data;
          console.log(response.data)
        });
      },
      requestFeedback: function() {
        console.log("requestFeedback function");
        const url = "api/feedbacksbysession/"+this.sessionId;
        console.log('url requested : ', url)
        let self = this;
        API.get(url).then(function(response) {
          self.feedbacks = response.data;
          console.log(response.data)
        });
      },
      requestSessionDetails: function() {
        console.log("request session details function");
        const url = "api/session/"+this.sessionId;
        console.log('url requested : ', url)
        let self = this;
        API.get(url).then(function(response) {
          self.sessionDetails = response.data;
          console.log(response.data)
        });
      },
      bookSession() {
        let booking_url = 'api/addbooking';
        const params = new URLSearchParams();
        params.append('sessionID', this.sessionId);
        let self = this;
        API({
          method: 'post',
          url: booking_url,
          data: params
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
      setCompleted() {
        let session_url = 'api/editsession/' + this.sessionId;
        const data = this.sessionDetails;
        data.completed = true;
        let self = this;
        API({
          method: 'put',
          url: session_url,
          data: data,
        })
        .then(function (response) {
                //handle success
                self.sessionDetails.completed = true;
                console.log(response);
            })
            .catch(function (response) {
                //handle error
                console.log(response);
          });
      },
      convertDate: function(date){
        return date.toString().substring(0, 10);
      }
    },

    created () {
      this.sessionId = this.$route.params.id;
      this.requestBookings();
      this.requestFeedback();
      this.requestSessionDetails();
    },
  }
</script>

<style>
  .bookings .details {
    display : flex;
    justify-content: space-between;
  }

</style>