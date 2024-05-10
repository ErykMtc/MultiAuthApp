import React from "react";
import { Link, useMatch, useResolvedPath } from "react-router-dom";
import "./Navbar.css"

export const Navbar = () => {

    return(
        <>
          <div className="navbar">
            <div className="navbar-pages">
              logo
              <CustomLink to="/">Strona Główna</CustomLink>
              <CustomLink to="/client">Panel Klienta</CustomLink>
              <CustomLink to="/admin">Panel Admina</CustomLink>
            </div>
            <div className="navbar-login">
              <CustomLink to="/logins">Logowanie</CustomLink>
              <CustomLink to="/registers">Rejestracja</CustomLink>
            </div>

          </div>
        </>
    )
}

function CustomLink({ to, children, ...props }) {
    const resolvedPath = useResolvedPath(to)
    const isActive = useMatch({ path: resolvedPath.pathname, end: true })
  
    return (
      <li className={isActive ? "active" : ""}>
        <Link to={to} {...props}>
          {children}
        </Link>
      </li>
    )
  }