import { createRouter, createWebHistory } from 'vue-router'

import ProductPage from '../pages/ProductPage.vue'
import RegisterProductPage from '../pages/RegisterProductPage.vue'
import EditProductPage from '../pages/EditProductPage.vue'
import MovementProductPage from '../pages/MovementProductPage.vue'
import RegisterMovementPage from '../pages/RegisterMovementPage.vue'
import ReportPage from '../pages/ReportPage.vue'

const routes = [
  { path: '/', name: 'Home', component: ProductPage },
  { path: '/products/new', name: 'NewProduct', component: RegisterProductPage },
  { path: '/products/:id/edit', name: 'EditProduct', component: EditProductPage, props: true },
  { path: '/products/:id/movements', name: 'ProductMovements', component: MovementProductPage, props: true },
  { path: '/products/:id/movements/new', name: 'NewMovement', component: RegisterMovementPage, props: true },
  { path: '/report', name: 'Report', component: ReportPage },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
