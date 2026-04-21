import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Toaster } from 'sonner';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import AdminUserManagement from './pages/AdminUserManagement';
import AdminSchools from './pages/AdminSchools';
import Unauthorized from './pages/Unauthorized';
import ProtectedRoute from './routes/ProtectedRoute';
import MainLayout from './components/MainLayout';

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <Toaster position="top-right" richColors />
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/unauthorized" element={<Unauthorized />} />

        {/* Protected Routes with MainLayout */}
        <Route element={<ProtectedRoute />}>
          <Route element={<MainLayout />}>
            <Route path="/" element={<Dashboard />} />
            
            {/* Admin Only Routes */}
            <Route element={<ProtectedRoute allowedRoles={['ROLE_ADMIN']} />}>
              <Route path="/admin/users" element={<AdminUserManagement />} />
            </Route>

            {/* Admin + Teacher + Student (view-only for student enforced in UI + backend) */}
            <Route element={<ProtectedRoute allowedRoles={['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT']} />}>
              <Route path="/admin/schools" element={<AdminSchools />} />
            </Route>
          </Route>
        </Route>

        {/* Catch-all */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
