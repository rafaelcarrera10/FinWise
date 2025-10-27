import tailwindcss from '@tailwindcss/vite';
import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [tailwindcss(), sveltekit()],
	server: {
		port: 5173,  // define a porta do frontend (opcional)
		proxy: {
			'/api': {
				target: 'http://localhost:8080',  // backend Spring Boot
				changeOrigin: true,
				secure: false,
				rewrite: (path) => path.replace(/^\/api/, '') // opcional: remove '/api' do path
			}
		}
	}
});


