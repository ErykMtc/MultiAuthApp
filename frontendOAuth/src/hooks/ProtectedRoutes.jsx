import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "./useAuth";
  

const ProtectedRoutes = ({allowedRoles}) => {
  const { auth } = useAuth();
  const location = useLocation();
  return (
      allowedRoles?.includes(auth?.role)
          ? <Outlet />
          : auth?.accessToken
              ? <Navigate to="/unauthorized" state={{ from: location }} replace />
              : <Navigate to="/login" state={{ from: location }} replace />
  );
}

export default ProtectedRoutes;