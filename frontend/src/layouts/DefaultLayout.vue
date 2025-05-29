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
  box-shadow: 0px 0px 23px 1px rgba(0,0,0,0.05);
}

::v-deep(.header) {
  position: relative;
  z-index: 1;
}
</style>
