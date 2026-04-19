import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  LayoutDashboard, 
  School as SchoolIcon, 
  GraduationCap, 
  Users, 
  UserPlus, 
  Settings,
  ChevronRight
} from 'lucide-react';
import { useAuthStore } from '../store/useAuthStore';

const Sidebar: React.FC = () => {
  const { user } = useAuthStore();
  const location = useLocation();

  const isAdmin = user?.role.includes('ROLE_ADMIN');

  const menuItems = [
    {
      title: 'Dashboard',
      icon: <LayoutDashboard className="w-5 h-5" />,
      path: '/',
      roles: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT']
    },
    {
      title: 'Schools & Classes',
      icon: <SchoolIcon className="w-5 h-5" />,
      path: '/admin/schools',
      roles: ['ROLE_ADMIN']
    },
    {
      title: 'Manage Users',
      icon: <UserPlus className="w-5 h-5" />,
      path: '/admin/users',
      roles: ['ROLE_ADMIN']
    }
  ];

  const filteredItems = menuItems.filter(item => 
    item.roles.some(role => user?.role.includes(role))
  );

  return (
    <div className="w-64 bg-white border-r border-gray-200 h-screen sticky top-0 flex flex-col">
      <div className="p-6 border-b border-gray-100">
        <h2 className="text-xl font-bold text-blue-600 flex items-center gap-2">
          <SchoolIcon className="w-6 h-6" />
          SMS Admin
        </h2>
      </div>
      
      <nav className="flex-1 overflow-y-auto p-4 space-y-2">
        {filteredItems.map((item) => {
          const isActive = location.pathname === item.path;
          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center justify-between px-4 py-3 rounded-lg transition-all duration-200 group ${
                isActive 
                  ? 'bg-blue-600 text-white shadow-md shadow-blue-200' 
                  : 'text-gray-600 hover:bg-blue-50 hover:text-blue-600'
              }`}
            >
              <div className="flex items-center gap-3">
                {item.icon}
                <span className="font-medium">{item.title}</span>
              </div>
              <ChevronRight className={`w-4 h-4 transition-transform duration-200 ${
                isActive ? 'translate-x-1' : 'opacity-0 group-hover:opacity-100 group-hover:translate-x-1'
              }`} />
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-gray-100">
        <div className="flex items-center gap-3 px-4 py-3 bg-gray-50 rounded-xl">
          <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold">
            {user?.username.charAt(0).toUpperCase()}
          </div>
          <div className="flex-1 min-w-0">
            <p className="text-sm font-semibold text-gray-900 truncate">{user?.username}</p>
            <p className="text-xs text-gray-500 truncate">{user?.role}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
