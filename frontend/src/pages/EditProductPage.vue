<template>
  <div class="top-bar">
    <BackButton to="/" label="Back" />
  </div>

  <h1>Edit Product</h1>

  <hr>

  <form @submit.prevent="onSubmit" class="product-form">
    <div class="form-group">
      <label for="code">Code</label>
      <input id="code" v-model="form.code" type="text" required />
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <input id="description" v-model="form.description" type="text" required />
    </div>

    <div class="form-group">
      <label for="type">Type</label>
      <select id="type" v-model="form.type" required>
        <option value="" disabled>Select Type</option>
        <option value="ELECTRONIC">ELECTRONIC</option>
        <option value="HOME_APPLIANCE">HOME_APPLIANCE</option>
        <option value="FURNITURE">FURNITURE</option>
      </select>
    </div>

    <div class="form-group">
      <label for="price">Price</label>
      <input id="price" v-model.number="form.price" type="number" min="0" step="0.01" required />
    </div>

    <button type="submit" :disabled="loading">
      {{ loading ? 'Updating...' : 'Update Product' }}
    </button>
  </form>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'
import { Notyf } from 'notyf'
import 'notyf/notyf.min.css'
import BackButton from '@/components/BackButton.vue'

const notyf = new Notyf()

const router = useRouter()
const route = useRoute()
const productId = route.params.id

const form = ref({
  code: '',
  description: '',
  type: '',
  price: 0,
  quantity: 0,
})

const loading = ref(false)

const fetchProduct = async () => {
  try {
    const res = await axios.get(`http://localhost:8080/products/${productId}`)
    const p = res.data
    form.value = {
      code: p.code,
      description: p.description,
      type: p.type,
      price: p.price,
      quantity: p.quantity,
    }
  } catch (err) {
    console.error('Error fetching product:', err)
  }
}

const onSubmit = async () => {
  if (loading.value) return

  loading.value = true
  try {
    await axios.put(`http://localhost:8080/products/${productId}`, form.value)
    notyf.success('Product Updated')
    setTimeout(() => {
      router.push('/')
    }, 1000)
  } catch (err) {
    console.error('Error updating product:', err)
    notyf.error('Failed to update product')
    loading.value = false
  }
}

onMounted(fetchProduct)
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.link-button {
  background: var(--light-bg);
  color: var(--primary-color);
  padding: 0.5rem 1rem;
  text-decoration: none;
  border-radius: 4px;
  font-weight: bold;
}

.top-bar {
  margin: 1rem 0;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 0.25rem;
}

input, select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #aaa;
  border-radius: 4px;
  font-size: 1rem;
  display: block;
  box-sizing: border-box;
}

button {
  background-color: var(--primary-color);
  color: var(--light-bg);
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
}

button:hover {
  background-color: #005fa3;
}

.success-message {
  margin-top: 1rem;
  color: green;
  font-weight: bold;
}
</style>
