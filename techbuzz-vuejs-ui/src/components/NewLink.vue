<template>
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2>Create New Link</h2>
        <form @submit.prevent="addNewLink">
          <p v-if="errors.length" class="alert alert-danger">
            <b>Please correct the following error(s):</b>
            <ul>
              <li v-for="error in errors" :key="error">{{ error }}</li>
            </ul>
          </p>
          <div class="form-group">
            <label>Title</label>
            <input type="text" class="form-control" v-model="newLink.title" placeholder="Title">
          </div>
          <div class="form-group">
            <label>URL</label>
            <input type="text" class="form-control" v-model="newLink.url" placeholder="URL">
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
      </div>
    </div>
</template>

<script>
import { HTTP } from '@/http-utils'
export default {
  name: 'NewLink',
  data () {
    return {
      newLink: {},
      errors: []
    }
  },
  methods: {
    addNewLink (e) {
      this.errors = []
      if (!this.newLink.title) this.errors.push('Title should not be empty')
      if (!this.newLink.url) this.errors.push('URL should not be empty')
      if (this.errors.length > 0) {
        e.preventDefault()
        return
      }
      HTTP.post('api/links', this.newLink)
        .then(response => {
          console.log('saved link successfully')
          this.$router.push('/')
        }, error => {
          console.error('Save failed', error)
          error.response.data.forEach(e => this.errors.push(e.message))
        })
    }
  }
}
</script>
