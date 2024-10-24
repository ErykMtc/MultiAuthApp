import React, { useContext, useEffect, useState } from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import useAuth from '../../../hooks/useAuth'
import { parseJwt } from './OAuthHelper'

function OAuth2Redirect() {
  const [redirectTo, setRedirectTo] = useState('/login')
  const { setAuth } = useAuth();

  const location = useLocation()

  useEffect(() => {
    const accessToken = extractUrlParameter('token')
    if (accessToken) {
      handleLogin(accessToken)
      const redirect = '/'
      setRedirectTo(redirect)
    }
  }, [])

  const extractUrlParameter = (key) => {
    return new URLSearchParams(location.search).get(key)
  }

  const handleLogin = (accessToken) => {
    const data = parseJwt(accessToken);
    const role = data?.rol[0];
    const username = data?.preferred_username;
    setAuth({ username, role, accessToken });
  };

  return <Navigate to={redirectTo} />
}

export default OAuth2Redirect