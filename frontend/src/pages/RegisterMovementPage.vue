<template>
  <div class="top-bar">
    <BackButton :to="`/products/${productId}/movements`" label="Back" />
  </div>

  <h1>Register Movement</h1>

  <hr>

  <form @submit.prevent="handleSubmit">
    <div class="form-group">
      <label>Type</label>
      <select v-model="type" required>
        <option disabled value="">Select Type</option>
        <option value="IN">IN</option>
        <option value="OUT">OUT</option>
      </select>
    </div>

    <div class="form-group">
      <label>Sale Price</label>
      <input type="number" v-model.number="salePrice" min="0" required />
    </div>

    <div class="form-group">
      <label>Quantity</label>
      <input type="number" v-model.number="quantity" min="1" required />
    </div>

    <button type="submit" :disabled="loading">
      {{ loading ? 'Submitting...' : 'Submit' }}
    </button>
  </form>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { Notyf } from 'notyf'
import 'notyf/notyf.min.css'
import BackButton from '@/components/BackButton.vue'

const notyf = new Notyf()
const route = useRoute()
const router = useRouter()
const productId = parseInt(route.params.id)

const type = ref('')
const salePrice = ref(0)
const quantity = ref(1)
const loading = ref(false)

const handleSubmit = async () => {
  if (loading.value) return

  loading.value = true

  const payload = {
    product: { id: productId },
    type: type.value,
    salePrice: salePrice.value,
    saleDate: new Date().toISOString(),
    quantity: quantity.value
  }

  try {
    await axios.post('http://localhost:8080/movements', payload)
    notyf.success('Movement registered successfully')
    type.value = ''
    salePrice.value = 0
    quantity.value = 1

    setTimeout(() => {
      router.push(`/products/${productId}/movements`)
    }, 1000)
  } catch (err) {
    console.error(err)
    notyf.error(`Error registering movement: ${err.response?.data?.message || 'Unknown error'}`)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.top-bar {
  margin-bottom: 1rem;
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
}

button:disabled {
  background-color: #999;
  cursor: not-allowed;
}

input, select {
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
</style>
