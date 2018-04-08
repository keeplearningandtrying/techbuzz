<template>
  <div class="container">
    <h2>{{msg}}</h2>
    <div v-for="link in links" :key="link.id">
      <h5><a v-bind:href="link.url" target="_blank">{{link.title}}</a></h5>
      <p>{{link.createdOn}} by {{link.createdUserName}}</p>
    </div>
  </div>
</template>

<script>
import { HTTP } from '@/http-utils'

export default {
  name: 'Home',
  data () {
    return {
      msg: 'Latest tech articles, news',
      links: []
    }
  },
  created () {
    this.fetchLinks()
  },
  methods: {
    fetchLinks: function () {
      HTTP.get('api/links')
        .then((response) => {
          this.links = response.data
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  computed: {
  }
}
</script>

<style scoped>

</style>
