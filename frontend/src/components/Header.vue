<template>
  <header class="header">
    <RouterLink to="/" class="logo-link">
      <img src="@/assets/logo.png" alt="Inventory Logo" class="logo" />    
    </RouterLink>

    <nav>
      <RouterLink to="/report" class="link-button" title="View Reports">
        <i class="fas fa-chart-line"></i> View Reports
      </RouterLink>

      <a
        href="https://github.com/mathunes/inventory-management"
        target="_blank"
        rel="noopener"
        class="github-link"
        title="GitHub"
        aria-label="GitHub"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          role="img"
          viewBox="0 0 24 24"
          width="20"
          height="20"
          fill="white"
        >
          <path d="M12 0C5.37 0 0 5.37 0 12c0 5.3 3.438 9.8 8.205 11.385.6.11.82-.26.82-.577v-2.234c-3.338.724-4.042-1.61-4.042-1.61-.546-1.385-1.333-1.754-1.333-1.754-1.09-.745.083-.73.083-.73 1.205.085 1.84 1.236 1.84 1.236 1.07 1.832 2.807 1.303 3.492.996.11-.775.42-1.304.763-1.605-2.665-.3-5.467-1.332-5.467-5.93 0-1.31.467-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.4 3-.405 1.02.005 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.435.375.81 1.11.81 2.235v3.32c0 .315.21.69.825.57C20.565 21.795 24 17.295 24 12c0-6.63-5.37-12-12-12z"/>
        </svg>
      </a>
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
  flex-wrap: wrap;
  gap: 1rem;
}

.logo-link {
  display: flex;
  align-items: center;
}

.logo {
  height: 35px;
  object-fit: contain;
}

nav {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
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

@media (max-width: 768px) {
  .header {
    padding: 1rem 2rem;
    flex-direction: column;
    align-items: center;
  }

  nav {
    width: 100%;
    justify-content: center;
  }
}

.github-link {
  display: inline-flex;
  align-items: center;
  margin-left: 1rem;
}

.github-link img {
  display: block;
  max-width: 20px;
  max-height: 20px;
}

</style>
