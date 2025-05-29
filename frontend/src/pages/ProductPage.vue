<template>
  <section>
    <header class="page-header">
      <h1>Products</h1>
      <RouterLink to="/products/new" class="link-button" title="Add New Product">
        <i class="fas fa-plus"></i> New Product
      </RouterLink>
    </header>

    <hr>

    <div v-if="loading">Loading products...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="products.length === 0">
      <p>No products found.</p>
    </div>

    <table v-else class="product-table">
      <thead>
        <tr>
          <th>Code</th>
          <th>Description</th>
          <th>Type</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.code">
          <td>{{ product.code }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.type }}</td>
          <td>{{ formatCurrency(product.price) }}</td>
          <td>{{ product.quantity }}</td>
          <td class="actions">
            <button @click="goToMovements(product.id)" title="See Movements" class="button-action">
              <i class="fas fa-boxes"></i> See Movements
            </button>
            <button @click="goToEdit(product.id)" title="Edit" class="button-action button-edit">
              <i class="fas fa-edit"></i>
            </button>
            <button @click="deleteProduct(product.id)" title="Delete" class="button-action button-delete">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import Swal from 'sweetalert2'

const router = useRouter()
const products = ref([])
const loading = ref(true)
const error = ref(null)

const fetchProducts = async () => {
  try {
    const res = await axios.get('http://localhost:8080/products')
    products.value = res.data
  } catch (err) {
    console.error(err)
    error.value = 'Failed to load products.'
  } finally {
    loading.value = false
  }
}

const deleteProduct = async (id) => {
  const confirmDelete = {
    title: 'Are you sure?',
    text: 'This action cannot be undone!',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Yes, delete it!',
  }

  const result = await Swal.fire(confirmDelete)

  if (result.isConfirmed) {
    try {
      await axios.delete(`http://localhost:8080/products/${id}`)
      await fetchProducts()
      Swal.fire('Deleted!', 'The product has been deleted.', 'success')
    } catch (err) {
      console.error(err)
      Swal.fire('Error', 'Failed to delete the product.', 'error')
    }
  }
}

const goToEdit = (id) => router.push(`/products/${id}/edit`)
const goToMovements = (id) => router.push(`/products/${id}/movements`)
const formatCurrency = (val) =>
  val.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

onMounted(fetchProducts)
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

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

.product-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.product-table th,
.product-table td {
  border: 1px solid #ccc;
  padding: 0.75rem;
  text-align: left;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.button-action {
  display: inline-flex;
  align-items: center;
  gap: 0.4rem;
  padding: 0.4rem 0.75rem;
  font-size: 0.875rem;
  border: none;
  border-radius: 6px;
  background-color: #e0e7ff;
  color: #1e40af;
  cursor: pointer;
  transition: background-color 0.2s ease, transform 0.1s ease;
}

.button-action:hover {
  background-color: #c7d2fe;
}

.button-action:active {
  transform: scale(0.97);
}

.button-edit {
  background-color: #dbeafe;
  color: #1d4ed8;
}

.button-edit:hover {
  background-color: #bfdbfe;
}

.button-delete {
  background-color: #fee2e2;
  color: #b91c1c;
}

.button-delete:hover {
  background-color: #fecaca;
}

.error {
  color: #b91c1c;
  font-weight: bold;
  margin-top: 1rem;
}
</style>
