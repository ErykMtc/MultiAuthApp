import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import { Navbar } from './components/Navbar'
import { Home } from './components/pages/Home'
import { Client } from './components/pages/Client'
import { Admin } from './components/pages/Admin'
import { Login } from './components/pages/Logins/Login'
import { Registration } from './components/pages/Registrations/Registration'
import Cookies from 'js-cookie';

import './App.css'
import { Footer } from './components/Footer'
import { PrivateRoute } from './components/pages/Utils/PrivateRoute'

function App() {
  const handleLogout = () => {
      Cookies.remove('AuthApp');
      window.location.reload();
  }

  return (
    <>
      <div className='main'>
        <Navbar/>
        <div className="">
          <Routes>
            <Route path='/' element={<Home />} />
            {PrivateRoute({ path: '/client', element: <Client />, roles: ['USER', 'ADMIN'] })}
            {PrivateRoute({ path: '/admin', element: <Admin />, roles: ['ADMIN'] })}
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
