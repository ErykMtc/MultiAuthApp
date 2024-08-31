import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import './assets/grid.css'
import { BrowserRouter } from "react-router-dom";
import axios from 'axios';
import { AuthProvider } from './context/AuthProvider.jsx'


axios.defaults.baseURL = `http://localhost:8080`;

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <App />
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>,
)
