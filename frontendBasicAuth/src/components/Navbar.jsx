import React from "react";
import { Link, useMatch, useResolvedPath } from "react-router-dom";
import "./Navbar.css";
import { BsPersonBoundingBox } from "react-icons/bs";
import logo from '../assets/PK.png';

export const Navbar = () => {
    return (
        <div className="navbar">
            <div className="navbar-pages">
                <img src={logo} alt="Logo" />
                <CustomLink to="/">Strona Główna</CustomLink>
                <CustomLink to="/client">Panel Klienta</CustomLink>
                <CustomLink to="/admin">Panel Admina</CustomLink>
            </div>
            <div className="navbar-login">
                <CustomLink to="/registers" className="icon-link"><BsPersonBoundingBox className="login-icon" /></CustomLink>
                <CustomLink to="/logins" className="login-button">Logowanie</CustomLink>
            </div>
        </div>
    );
}

function CustomLink({ to, children, className, ...props }) {
    const resolvedPath = useResolvedPath(to);
    const isActive = useMatch({ path: resolvedPath.pathname, end: true });

    return (
        <li className={isActive ? `active ${className}` : className}>
            <Link to={to} {...props}>
                {children}
            </Link>
        </li>
    );
}
