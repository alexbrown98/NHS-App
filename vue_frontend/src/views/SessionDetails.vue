<template>
  <div class="SessionDetails">
    <sessiondetails/>
  </div>
</template>

<script>
import sessiondetails from '../components/SessionDetails.vue';


export default {
  name: 'SessionDetails',
  components: {
    'sessiondetails': sessiondetails
  }
}
</script>