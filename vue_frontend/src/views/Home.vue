<template>
  <div class="home">
    <teaching-list/>
  </div>
</template>

<script>
import BedTeachingList from '../components/BedTeachingList.vue';
// @ is an alias to /src


export default {
  name: 'home',
  components: {
    'teachingList': BedTeachingList
  }
}
</script>
