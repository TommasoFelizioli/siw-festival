import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(({ command }) => ({
  plugins: [react()],
  base: command === 'build' ? '/app/' : '/',
  build: {
    outDir: '../src/main/resources/static/app',
    emptyOutDir: true,
  },
}))