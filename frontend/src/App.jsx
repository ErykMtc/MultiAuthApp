import { useState } from 'react'
import { Route, Routes } from 'react-router-dom'
import { Navbar } from './components/Navbar'
import { Register } from './components/pages/Register'
import { Home } from './components/pages/Home'
import { Client } from './components/pages/Client'
import { Admin } from './components/pages/Admin'
import { Login1 } from './components/pages/Logins/Login1'
import { Registration1 } from './components/pages/Registrations/Registration1'

import './App.css'
import { Footer } from './components/Footer'
import { LoginSection } from './components/pages/LoginSection'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div className='main'>
        <Navbar />
        <div className="">
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/client' element={<Client />} />
            <Route path='/admin' element={<Admin />} />
            <Route path="/logins" element={<LoginSection />} />
            <Route path='/registers' element={<Register />} />
            <Route path='/login1' element={<Login1 />} />
            <Route path='/register1' element={<Registration1 />} />
          </Routes>
        </div>
        <Footer></Footer>
      </div>
    </>
  )
}

export default App
