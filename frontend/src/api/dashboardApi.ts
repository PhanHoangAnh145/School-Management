import apiClient from './axios';

export interface DashboardStats {
  totalSchools: number;
  activeClasses: number;
  totalStudents: number;
  systemHealth: string;
}

export const getDashboardStats = async (): Promise<DashboardStats> => {
  try {
    // Fetch data from all three endpoints in parallel
    const [schoolsResponse, classesResponse, studentsResponse] = await Promise.all([
      apiClient.get('/api/school'),
      apiClient.get('/api/class'),
      apiClient.get('/api/student')
    ]);

    const totalSchools = schoolsResponse.data.data?.length || 0;
    const activeClasses = classesResponse.data.data?.length || 0;
    const totalStudents = studentsResponse.data.data?.length || 0;

    return {
      totalSchools,
      activeClasses,
      totalStudents,
      systemHealth: '99.9%' // This can be dynamic if you have a health check endpoint
    };
  } catch (error) {
    console.error('Error fetching dashboard stats:', error);
    // Return default values on error
    return {
      totalSchools: 0,
      activeClasses: 0,
      totalStudents: 0,
      systemHealth: '99.9%'
    };
  }
};
