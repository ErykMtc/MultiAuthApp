import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import { Navbar } from './components/Navbar'
import { Register } from './components/pages/Register'
import { Home } from './components/pages/Home'
import { Client } from './components/pages/Client'
import { Admin } from './components/pages/Admin'
import { Login } from './components/pages/Logins/Login'
import { Registration } from './components/pages/Registrations/Registration'
import { Navigate } from 'react-router-dom';

import './App.css'
import { Footer } from './components/Footer'
import { LoginSection } from './components/pages/LoginSection'

function App() {

  const ProtectedRoute = ({ role, children }) => {
    const userRole = localStorage.getItem('userRole');
    if (!role.includes(userRole)) {
        return <Navigate to="/unauthorized" />;
    }
    return children;
  };

  return (
    <>
      <div className='main'>
        <Navbar/>
        <div className="">
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path="/admin" element={<ProtectedRoute role={['ADMIN']}><Admin /></ProtectedRoute>} />
            <Route path="/client" element={<ProtectedRoute role={['USER', 'ADMIN']} ><Client /></ProtectedRoute>} />
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Registration />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </>
  )
}

export default App;
