import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import { Navbar } from './components/Navbar'
import { Home } from './components/pages/Home'
import { Client } from './components/pages/Client'
import { Admin } from './components/pages/Admin'
import { Login } from './components/pages/Logins/Login'
import { Registration } from './components/pages/Registrations/Registration'
import OAuth2Redirect from './components/pages/OAuthUtils/OAuth2Redirect'
import { Navigate } from 'react-router-dom';

import './App.css';
import { Footer } from './components/Footer';
import ProtectedRoutes from './hooks/ProtectedRoutes';

const ROLES = {
  "Admin": "ADMIN",
  "User": "USER"
}

function App() {

  return (
    <>
      <div className='main'>
        <Navbar/>
        <div className="">
          <Routes>
            <Route path='/' element={<Home />} />
            <Route element={<ProtectedRoutes allowedRoles={[ROLES.Admin, ROLES.User]} />}>
              <Route path="/client" element={<Client />} />
            </Route>
            <Route element={<ProtectedRoutes allowedRoles={[ROLES.Admin, ROLES.User]} />}>
              <Route path="/admin" element={<Admin />} />
            </Route>
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Registration />} />
            <Route path='/oauth2/redirect' element={<OAuth2Redirect />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </>
  )
}

export default App;
