import React, { useState, useEffect } from 'react';
import { useAuthStore } from '../store/useAuthStore';
import { 
  Building2, 
  GraduationCap, 
  Users, 
  ShieldCheck, 
  TrendingUp, 
  Calendar,
  Activity
} from 'lucide-react';
import { getDashboardStats, type DashboardStats } from '../api/dashboardApi';

const Dashboard: React.FC = () => {
  const { user } = useAuthStore();
  const [stats, setStats] = useState<DashboardStats>({
    totalSchools: 0,
    activeClasses: 0,
    totalStudents: 0,
    systemHealth: '99.9%'
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const data = await getDashboardStats();
        setStats(data);
      } catch (error) {
        console.error('Failed to fetch dashboard stats:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  const statsData = [
    { label: 'Total Schools', value: stats.totalSchools.toLocaleString(), icon: <Building2 className="w-5 h-5" />, color: 'bg-blue-500' },
    { label: 'Active Classes', value: stats.activeClasses.toLocaleString(), icon: <GraduationCap className="w-5 h-5" />, color: 'bg-green-500' },
    { label: 'Total Students', value: stats.totalStudents.toLocaleString(), icon: <Users className="w-5 h-5" />, color: 'bg-purple-500' },
    { label: 'System Health', value: stats.systemHealth, icon: <Activity className="w-5 h-5" />, color: 'bg-orange-500' },
  ];

  return (
    <div className="space-y-8 animate-in fade-in duration-700">
      <div>
        <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-3">
          <ShieldCheck className="w-8 h-8 text-blue-600" />
          System Overview
        </h1>
        <p className="text-gray-500 mt-1">Welcome back, <span className="text-blue-600 font-bold">{user?.username}</span>. Here's what's happening today.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {loading ? (
          // Loading skeleton
          Array.from({ length: 4 }).map((_, i) => (
            <div key={i} className="bg-white p-6 rounded-2xl shadow-xl shadow-gray-100 border border-gray-100 flex items-center gap-4">
              <div className="bg-gray-200 p-3 rounded-xl animate-pulse w-12 h-12"></div>
              <div className="flex-1">
                <div className="h-3 bg-gray-200 rounded animate-pulse mb-2 w-20"></div>
                <div className="h-6 bg-gray-200 rounded animate-pulse w-16"></div>
              </div>
            </div>
          ))
        ) : (
          statsData.map((stat, i) => (
            <div key={i} className="bg-white p-6 rounded-2xl shadow-xl shadow-gray-100 border border-gray-100 flex items-center gap-4 hover:shadow-blue-50 transition-all">
              <div className={`${stat.color} p-3 rounded-xl text-white shadow-lg`}>
                {stat.icon}
              </div>
              <div>
                <p className="text-xs font-bold text-gray-400 uppercase tracking-widest">{stat.label}</p>
                <p className="text-2xl font-bold text-gray-900">{stat.value}</p>
              </div>
            </div>
          ))
        )}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 bg-white p-8 rounded-2xl shadow-xl shadow-gray-100 border border-gray-100">
          <div className="flex justify-between items-center mb-8">
            <h3 className="text-xl font-bold text-gray-900 flex items-center gap-2">
              <TrendingUp className="w-5 h-5 text-blue-600" />
              Enrollment Trends
            </h3>
            <select className="bg-gray-50 border border-gray-100 text-xs font-bold rounded-lg px-3 py-1.5 focus:outline-none focus:ring-2 focus:ring-blue-500">
              <option>Last 7 Days</option>
              <option>Last 30 Days</option>
            </select>
          </div>
          <div className="h-64 flex items-end justify-between gap-2">
            {[40, 70, 45, 90, 65, 85, 55].map((h, i) => (
              <div key={i} className="flex-1 bg-blue-100 rounded-t-lg relative group transition-all hover:bg-blue-600">
                <div style={{ height: `${h}%` }} className="w-full bg-blue-500 rounded-t-lg transition-all group-hover:bg-blue-600"></div>
                <div className="absolute -top-10 left-1/2 -translate-x-1/2 bg-gray-900 text-white text-[10px] font-bold px-2 py-1 rounded opacity-0 group-hover:opacity-100 transition-opacity">
                  {h * 10}
                </div>
              </div>
            ))}
          </div>
          <div className="flex justify-between mt-4">
            {['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'].map(day => (
              <span key={day} className="text-xs font-bold text-gray-400 uppercase">{day}</span>
            ))}
          </div>
        </div>

        <div className="bg-white p-8 rounded-2xl shadow-xl shadow-gray-100 border border-gray-100">
          <h3 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
            <Calendar className="w-5 h-5 text-blue-600" />
            System Status
          </h3>
          <div className="space-y-6">
            <div className="flex items-center gap-4">
              <div className="w-2 h-2 rounded-full bg-green-500 animate-pulse"></div>
              <div className="flex-1">
                <p className="text-sm font-bold text-gray-800">Database Connection</p>
                <p className="text-xs text-gray-400">Stable - 24ms latency</p>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <div className="w-2 h-2 rounded-full bg-green-500 animate-pulse"></div>
              <div className="flex-1">
                <p className="text-sm font-bold text-gray-800">API Gateway</p>
                <p className="text-xs text-gray-400">Operational - 100% Uptime</p>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <div className="w-2 h-2 rounded-full bg-blue-500 animate-pulse"></div>
              <div className="flex-1">
                <p className="text-sm font-bold text-gray-800">Mail Server</p>
                <p className="text-xs text-gray-400">Syncing - 2.4k pending</p>
              </div>
            </div>
          </div>
          
          <div className="mt-10 p-4 bg-blue-50 rounded-xl border border-blue-100">
            <p className="text-xs font-bold text-blue-600 uppercase tracking-widest mb-1">Current Role</p>
            <p className="text-lg font-bold text-blue-800">{user?.role}</p>
            <p className="text-xs text-blue-600 mt-2">All administrative privileges enabled.</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
