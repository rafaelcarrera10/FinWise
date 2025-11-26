
<script lang="ts"> 
// Ex: src/routes/home/+page.ts
import { protectRoute } from '$lib/utils/auth';

export const load = async () => {
  protectRoute();
  return {};
};

</script>
