<template>
  <p>&copy; {{year}}. All rights reserved. </p>
</template>

<script>
export default {
    name: "footer",
    props: ['year']
}
</script>

<style scoped>
  p{
    margin:2%;
    font-size: 1rem;
    text-align: center;
  }
</style>