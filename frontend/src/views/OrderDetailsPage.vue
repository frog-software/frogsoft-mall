<script setup lang="ts">
import {getOrderDetailsCustomer} from "../services/order/customer";
import {ref} from "vue";
import {OrderDetailsInfo} from "../types/order";

const prop = defineProps<{
  id: string
}>();
let order = ref<OrderDetailsInfo>();
getOrderDetailsCustomer(prop.id).then(res => {
  order.value = res
})
console.log(order)
</script>
<template>
  <div style="background: white">
    {{ order }}
  </div>
</template>

<script lang="ts">
export default {
  name: "OrderDetails"
}
</script>

<style scoped>

</style>
