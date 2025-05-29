<template>
  <div class="page-wrapper">
    <AppHeader />
    <main class="container">
      <slot />
    </main>
  </div>
</template>

<script setup>
import AppHeader from '../components/Header.vue'
</script>

<style scoped>
.page-wrapper {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.container {
  background-color: white;
  flex-grow: 1;
  margin: 0 10rem;
  padding: 2rem;
  overflow-y: auto;
  box-shadow: 0px 0px 23px 1px rgba(0, 0, 0, 0.05);
}

::v-deep(.header) {
  position: relative;
  z-index: 1;
}

@media (max-width: 1024px) {
  .container {
    margin: 0 5rem;
    padding: 1.5rem;
  }
}

@media (max-width: 768px) {
  .container {
    margin: 0 2rem;
    padding: 1rem;
  }
}

@media (max-width: 480px) {
  .container {
    margin: 0 1rem;
    padding: 1rem;
  }
}
</style>
