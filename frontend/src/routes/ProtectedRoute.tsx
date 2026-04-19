import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';
import type { Role } from '../types/auth';

interface ProtectedRouteProps {
  allowedRoles?: Role[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
  const { user, isAuthenticated } = useAuthStore();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && user) {
    // Backend roles are often space-separated strings (e.g. "ROLE_ADMIN ROLE_USER")
    const userRoles = user.role.split(' ');
    
    // Check if any of the allowed roles match the user's roles
    // We check for exact match or match without ROLE_ prefix to be safe
    const hasPermission = allowedRoles.some(allowedRole => 
      userRoles.some(userRole => 
        userRole === allowedRole || 
        userRole === `ROLE_${allowedRole}` || 
        `ROLE_${userRole}` === allowedRole
      )
    );

    if (!hasPermission) {
      return <Navigate to="/unauthorized" replace />;
    }
  }

  return <Outlet />;
};

export default ProtectedRoute;
