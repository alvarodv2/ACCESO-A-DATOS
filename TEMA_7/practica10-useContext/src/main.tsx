import React, { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App';
import { UserProvider } from './context/UserContext';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

// Renderizamos el componente App envuelto en StrictMode y UserProvider
root.render(
  <StrictMode>
    <UserProvider>
      <App />
    </UserProvider>
  </StrictMode>
);