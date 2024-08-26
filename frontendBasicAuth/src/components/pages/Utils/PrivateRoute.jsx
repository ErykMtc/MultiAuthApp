import React from 'react';
import { Route, Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';

export const PrivateRoute = ({ element: Component, roles, ...rest }) => {
    const authCookie = Cookies.get('AuthApp');

    if (!authCookie) {
        return <Route {...rest} element={<Navigate to="/login1" />} />;
    }

    const user = JSON.parse(authCookie);
    if (roles && !roles.includes(user.role)) {
        // Redirect if the user doesn't have the necessary role
        return <Route {...rest} element={<Navigate to="/" />} />;
    }

    return <Route {...rest} element={Component} />;
};