<template>
  <div class="top-bar">
    <BackButton to="/" label="Back" />
  </div>

  <h1>Report</h1>

  <hr>

  <div class="tabs">
    <button
      @click="activeTab = 'byType'"
      :class="['tab-button', { active: activeTab === 'byType' }]"
    >
      Report by Product Type
    </button>
    <button
      @click="() => { activeTab = 'profit'; fetchProfits(); }"
      :class="['tab-button', { active: activeTab === 'profit' }]"
    >
      Products Profit
    </button>
  </div>

  <div v-if="activeTab === 'byType'">
    <div class="filter-wrapper">
      <label for="type-select" class="filter-label">
        <i class="fas fa-filter"></i>
        Filter by Type
      </label>
      <select id="type-select" v-model="selectedType" @change="fetchByType" class="filter-select">
        <option disabled value="">Select</option>
        <option value="ELECTRONIC">ELECTRONIC</option>
        <option value="HOME_APPLIANCE">HOME_APPLIANCE</option>
        <option value="FURNITURE">FURNITURE</option>
      </select>
    </div>

    <p v-if="!selectedType">Please select a product type.</p>

    <table v-if="productsByType.length">
      <thead>
        <tr>
          <th>Code</th>
          <th>Description</th>
          <th>Available Quantity</th>
          <th>OUT Quantity</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in productsByType" :key="product.id">
          <td>{{ product.code }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.quantityAvailable }}</td>
          <td>{{ product.quantityOut }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="selectedType && productsByType.length === 0">No products found for selected type.</p>
  </div>

  <div v-else-if="activeTab === 'profit'">
    <table v-if="profitReports.length">
      <thead>
        <tr>
          <th>Code</th>
          <th>Description</th>
          <th>OUT Quantity</th>
          <th>Total Profit</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in profitReports" :key="product.productId">
          <td>{{ product.code }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.totalQuantityOut }}</td>
          <td>{{ formatCurrency(product.totalProfit) }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else>No products found.</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import BackButton from '@/components/BackButton.vue'

const activeTab = ref('byType')
const selectedType = ref('')
const productsByType = ref([])
const profitReports = ref([])

const fetchByType = async () => {
  if (!selectedType.value) return
  const res = await axios.get(`http://localhost:8080/products/by-type?type=${selectedType.value}`)
  productsByType.value = res.data
}

const fetchProfits = async () => {
  const res = await axios.get('http://localhost:8080/products/profit')
  profitReports.value = res.data
}

const formatCurrency = (val) => val.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
</script>

<style scoped>
.top-bar {
  margin-bottom: 1rem;
}

.tabs {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

.tab-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.4rem;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  background-color: #e0e7ff;
  color: #1e3a8a;
  cursor: pointer;
  transition: background-color 0.2s ease, transform 0.1s ease;
}

.tab-button:hover {
  background-color: #c7d2fe;
}

.tab-button:active {
  transform: scale(0.97);
}

.tab-button.active {
  background-color: var(--primary-color);
  color: white;
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

.filter-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: end;
  margin-bottom: 1rem;
}

.filter-label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #333;
  margin-right: 1rem;
}

.filter-label i {
  color: #0077cc;
}

.filter-select {
  padding: 0.5rem;
  border: 1px solid #aaa;
  border-radius: 4px;
  font-size: 1rem;
  background-color: white;
  appearance: none;
  background-image: url("data:image/svg+xml;charset=US-ASCII,%3Csvg%20fill%3D%22gray%22%20height%3D%2220%22%20viewBox%3D%220%200%2024%2024%22%20width%3D%2220%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%3E%3Cpath%20d%3D%22M7%2010l5%205%205-5z%22/%3E%3Cpath%20d%3D%22M0%200h24v24H0z%22%20fill%3D%22none%22/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 0.75rem center;
  background-size: 1rem;
}
</style>
