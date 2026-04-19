import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import { useAuthStore } from '../store/useAuthStore';
import { LogOut, Bell, Search, User as UserIcon } from 'lucide-react';

const MainLayout: React.FC = () => {
  const { logout, user } = useAuthStore();

  return (
    <div className="flex min-h-screen bg-gray-50 font-sans">
      <Sidebar />
      
      <div className="flex-1 flex flex-col min-w-0">
        <header className="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8 sticky top-0 z-10 shadow-sm shadow-gray-100">
          <div className="flex-1 flex items-center">
            <div className="max-w-md w-full relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
              <input 
                type="text" 
                placeholder="Search..." 
                className="w-full pl-10 pr-4 py-2 bg-gray-50 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all text-sm"
              />
            </div>
          </div>

          <div className="flex items-center gap-6">
            <button className="relative p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-full transition-all duration-200">
              <Bell className="w-5 h-5" />
              <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-red-500 rounded-full border-2 border-white"></span>
            </button>
            
            <div className="h-8 w-px bg-gray-200"></div>
            
            <div className="flex items-center gap-4 group">
              <div className="flex flex-col items-end">
                <p className="text-sm font-bold text-gray-800 leading-none">{user?.username}</p>
                <p className="text-xs font-medium text-gray-400 mt-1 uppercase tracking-wider">{user?.role}</p>
              </div>
              
              <div className="relative group">
                <button className="flex items-center gap-2 p-1.5 rounded-full bg-blue-50 border border-blue-100 group-hover:bg-blue-100 transition-all duration-200">
                  <UserIcon className="w-5 h-5 text-blue-600" />
                </button>
                
                {/* Dropdown would go here */}
              </div>

              <button
                onClick={logout}
                className="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-full transition-all duration-200"
                title="Logout"
              >
                <LogOut className="w-5 h-5" />
              </button>
            </div>
          </div>
        </header>

        <main className="p-8 overflow-y-auto flex-1">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default MainLayout;
