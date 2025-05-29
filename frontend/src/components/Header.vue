<template>
  <header class="header">
    <RouterLink to="/">
      <img src="@/assets/logo.png" alt="Inventory Logo" class="logo" />    
    </RouterLink>

    <nav>
      <RouterLink to="/report" class="link-button" title="View Reports">
        <i class="fas fa-chart-line"></i> View Reports
      </RouterLink>
    </nav>
  </header>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--primary-color);
  color: white;
  padding: 1.5rem 5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
}

nav a {
  color: white;
  font-weight: bold;
}

.logo {
  height: 35px;
  object-fit: contain;
}

.link-button {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  background-color: white;
  color: var(--primary-color, #0077cc);
  padding: 0.5rem 1.25rem;
  border-radius: 6px;
  font-weight: 600;
  text-decoration: none;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease-in-out;
}

.link-button:hover {
  background-color: var(--secondary-color);
  color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.link-button:active {
  transform: scale(0.98);
}
</style>
