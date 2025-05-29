<template>
  <div class="top-bar">
    <BackButton to="/" label="Back" />
  </div>

  <h1>Register Product</h1>

  <hr>

  <form @submit.prevent="handleSubmit" class="product-form">
    <div class="form-group">
      <label>Code</label>
      <input v-model="form.code" required />
    </div>

    <div class="form-group">
      <label>Description</label>
      <input v-model="form.description" required />
    </div>

    <div class="form-group">
      <label>Price</label>
      <input v-model.number="form.price" type="number" min="0" required />
    </div>

    <div class="form-group">
      <label>Quantity</label>
      <input v-model.number="form.quantity" type="number" min="0" required />
    </div>

    <div class="form-group">
      <label>Type</label>
      <select v-model="form.type" required>
        <option disabled value="">Select Type</option>
        <option value="ELECTRONIC">Electronic</option>
        <option value="HOME_APPLIANCE">Home Appliance</option>
        <option value="FURNITURE">Furniture</option>
      </select>
    </div>

    <button type="submit" :disabled="loading">
      {{ loading ? 'Submitting...' : 'Create Product' }}
    </button>
  </form>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { Notyf } from 'notyf'
import 'notyf/notyf.min.css'
import BackButton from '@/components/BackButton.vue'

const notyf = new Notyf()
const router = useRouter()

const form = reactive({
  code: '',
  description: '',
  price: null,
  quantity: null,
  type: '',
})

const loading = ref(false)

const handleSubmit = async () => {
  if (loading.value) return

  loading.value = true
  try {
    await axios.post('http://localhost:8080/products', form)
    notyf.success('Product Created!')

    setTimeout(() => {
      router.push('/')
    }, 1000)
  } catch (err) {
    notyf.error('Error creating product')
    loading.value = false
  }
}
</script>

<style scoped>
.top-bar {
  margin-bottom: 1rem;
}

.product-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 1rem;
}

.form-group {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}

button {
  margin-top: 1rem;
  padding: 0.75rem 1.5rem;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  background-color: var(--primary-color, #007bff);
  color: white;
  cursor: pointer;
  width: 100%;
}

button:disabled {
  background-color: #999;
  cursor: not-allowed;
}

input,
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #aaa;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
}

label {
  font-weight: 600;
  margin-bottom: 0.25rem;
}

@media (min-width: 640px) {
  button {
    width: auto;
    align-self: flex-start;
  }
}
</style>
