<template>
  <RouterLink :to="to" class="back-link">
    <i class="fas fa-arrow-left"></i>
    {{ label }}
  </RouterLink>
</template>

<script setup>
import { RouterLink } from 'vue-router'

defineProps({
  to: {
    type: String,
    default: '/'
  },
  label: {
    type: String,
    default: 'Back'
  }
})
</script>

<style scoped>
.back-link {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
  color: var(--primary-color);
}

.back-link i {
  font-size: 1rem;
}
</style>