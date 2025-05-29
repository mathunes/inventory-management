<template>
  <div class="top-bar">
    <BackButton to="/" label="Back" />
  </div>

  <div class="header">
    <h1>Movements of Product {{ product?.code }}</h1>
    <RouterLink :to="`/products/${productId}/movements/new`" class="link-button" title="Add New Movement">
      <i class="fas fa-plus"></i> New Movement
    </RouterLink>
  </div>

  <hr>

  <p v-if="movements.length === 0">No movements found.</p>

  <table v-else>
    <thead>
      <tr>
        <th>Type</th>
        <th>Sale Price</th>
        <th>Sale Date</th>
        <th>Quantity</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="movement in movements" :key="movement.id">
        <td>{{ movement.type }}</td>
        <td>{{ formatCurrency(movement.salePrice) }}</td>
        <td>{{ formatDate(movement.saleDate) }}</td>
        <td>{{ movement.quantity }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import BackButton from '@/components/BackButton.vue'

const route = useRoute()
const productId = route.params.id

const product = ref(null)
const movements = ref([])

onMounted(async () => {
  try {
    const productRes = await axios.get(`http://localhost:8080/products/${productId}`)

    movements.value = productRes.data.movements
  } catch (err) {
    console.error('Error loading product or movements', err)
  }
})

const formatCurrency = (value) =>
  value?.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

const formatDate = (value) =>
  new Date(value).toLocaleString()
</script>

<style scoped>
.link-button {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  background-color: var(--primary-color, #0077cc);
  color: var(--light-bg, #ffffff);
  padding: 0.5rem 1.25rem;
  border-radius: 6px;
  font-weight: 600;
  text-decoration: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.2s ease, box-shadow 0.2s ease, transform 0.1s ease;
}

.link-button:hover {
  background-color: var(--secondary-color, #005fa3);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

.link-button:active {
  transform: scale(0.97);
}

.top-bar {
  margin: 1rem 0;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

th, td {
  border: 1px solid #ccc;
  padding: 0.5rem;
  text-align: left;
}
</style>
