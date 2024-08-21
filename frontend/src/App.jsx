import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import { Navbar } from './components/Navbar'
import { Register } from './components/pages/Register'
import { Home } from './components/pages/Home'
import { Client } from './components/pages/Client'
import { Admin } from './components/pages/Admin'
import { Login1 } from './components/pages/Logins/Login1'
import { Registration1 } from './components/pages/Registrations/Registration1'
import Cookies from 'js-cookie';

import './App.css'
import { Footer } from './components/Footer'
import { LoginSection } from './components/pages/LoginSection'
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
            <Route path="/logins" element={<LoginSection />} />
            <Route path='/registers' element={<Register />} />
            <Route path='/login1' element={<Login1 />} />
            <Route path='/register1' element={<Registration1 />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </>
  )
}

export default App;
