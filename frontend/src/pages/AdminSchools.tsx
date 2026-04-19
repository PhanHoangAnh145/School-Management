import React, { useState, useEffect } from 'react';
import { toast } from 'sonner';
import { 
  Plus, 
  Search, 
  Building2, 
  MapPin, 
  Phone, 
  Loader2,
  Trash2,
  Edit,
  Activity,
  School as SchoolIcon,
  ChevronRight,
  GraduationCap,
  Users,
  Calendar,
  ArrowLeft,
  X,
  Settings,
  MoreHorizontal,
  FileText,
  UserPlus,
  BookOpen
} from 'lucide-react';
import apiClient from '../api/axios';
import ConfirmDeleteModal from '../components/ConfirmDeleteModal';
import DataManagementModal from '../components/DataManagementModal';
import { useAuthStore } from '../store/useAuthStore';

interface School {
  id: number;
  name: string;
  address: string;
  phoneNumber: string;
  grade: number;
}

interface Clazz {
  id: number;
  name: string;
  grade: number;
  year: number;
  schoolName: string;
  studentCount?: number;
}

interface Student {
  id: number;
  name: string;
  dateOfBirth: string;
  className: string;
}

interface StudentDetail {
  id?: number;
  fullName: string;
  address: string;
  hobby: string;
  avatar?: any;
}

interface Parent {
  id?: number;
  fatherName: string;
  motherName: string;
  phoneNumber: string;
}

interface StudentRecord {
  id?: number;
  status: string;
  note: string;
}

interface Transcription {
  id?: number;
  year: number;
  rate: string;
  mark: number;
}

interface ClassLogbook {
  id?: number;
  description: string;
}

interface EmployeeDetail {
  id?: number;
  dayOfBirth: string;
  address: string;
  phoneNumber: string;
}

interface Subject {
  id: number;
  name: string;
}

interface TeacherDetails {
  id: number;
  clazzList: Clazz[];
  subjectList: Subject[];
}

interface Employee {
  id: number;
  name: string;
  role: string;
  teacher?: { 
    id: number;
    clazzList?: Clazz[];
    subjectList?: Subject[];
  };
}

const AdminSchools: React.FC = () => {
  const [schools, setSchools] = useState<School[]>([]);
  const [loading, setLoading] = useState(true);
  const [isAddingSchool, setIsAddingSchool] = useState(false);
  const [newSchool, setNewSchool] = useState({ name: '', address: '', phoneNumber: '', grade: 1 });
  const [searchTerm, setSearchTerm] = useState('');
  
  // View states
  const [selectedSchool, setSelectedSchool] = useState<School | null>(null);
  const [selectedClass, setSelectedClass] = useState<Clazz | null>(null);
  const [viewMode, setViewMode] = useState<'schools' | 'school-options' | 'classes' | 'employees' | 'students'>('schools');
  const [classes, setClasses] = useState<Clazz[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [allSubjects, setAllSubjects] = useState<Subject[]>([]);
  const [allClasses, setAllClasses] = useState<Clazz[]>([]);
  const [viewLoading, setViewLoading] = useState(false);

  // Modal states
  const [isAddingClass, setIsAddingClass] = useState(false);
  const [isAddingStudent, setIsAddingStudent] = useState(false);
  const [isAddingEmployee, setIsAddingEmployee] = useState(false);
  const [isEditingEmployee, setIsEditingEmployee] = useState<Employee | null>(null);
  const [isEditingStudentDetail, setIsEditingStudentDetail] = useState<Student | null>(null);
  const [isEditingEmployeeDetail, setIsEditingEmployeeDetail] = useState<Employee | null>(null);
  const [isEditingParent, setIsEditingParent] = useState<Student | null>(null);
  const [isEditingRecord, setIsEditingRecord] = useState<Student | null>(null);
  const [isEditingTranscription, setIsEditingTranscription] = useState<Student | null>(null);
  const [isEditingLogbook, setIsEditingLogbook] = useState<Clazz | null>(null);
  
  const [studentDetail, setStudentDetail] = useState<StudentDetail | null>(null);
  const [employeeDetail, setEmployeeDetail] = useState<EmployeeDetail | null>(null);
  const [parentData, setParentData] = useState<Parent | null>(null);
  const [recordData, setRecordData] = useState<StudentRecord | null>(null);
  const [transcriptionData, setTranscriptionData] = useState<Transcription | null>(null);
  const [logbookData, setLogbookData] = useState<ClassLogbook | null>(null);
  const [isManagingSubjects, setIsManagingSubjects] = useState<Employee | null>(null);
  const [isManagingClasses, setIsManagingClasses] = useState<Employee | null>(null);
  const [isViewingClassTeachers, setIsViewingClassTeachers] = useState<Clazz | null>(null);
  
  const [newClass, setNewClass] = useState({ name: '', grade: 1, year: new Date().getFullYear() });
  const [newStudent, setNewStudent] = useState({ name: '', dateOfBirth: '' });
  const [newEmployee, setNewEmployee] = useState({ name: '', role: '' });
  const [newSubject, setNewSubject] = useState({ name: '' });
  const [isAddingSubject, setIsAddingSubject] = useState(false);
  const [detailLoading, setDetailLoading] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [managementModal, setManagementModal] = useState<{
    isOpen: boolean;
    type: 'parent' | 'record' | 'transcription';
  }>({
    isOpen: false,
    type: 'parent'
  });
  
  const [deleteModal, setDeleteModal] = useState({ 
    isOpen: false, 
    type: '' as 'school' | 'class' | 'student' | 'employee' | 'subject' | 'parent' | 'record' | 'transcription',
    id: null as number | null,
    name: '' 
  });
  
  const { user } = useAuthStore();

  const fetchSchools = async () => {
    try {
      setLoading(true);
      const response = await apiClient.get('/api/school');
      if (response.data.status === 'success') {
        setSchools(response.data.data);
      }
    } catch (error: any) {
      toast.error('Failed to fetch schools');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSchools();
  }, []);

  // Sync managing states when employees list updates
  useEffect(() => {
    if (isManagingSubjects) {
      const updated = employees.find(e => e.id === isManagingSubjects.id);
      if (updated) setIsManagingSubjects(updated);
    }
  }, [employees]);

  useEffect(() => {
    if (isManagingClasses) {
      const updated = employees.find(e => e.id === isManagingClasses.id);
      if (updated) setIsManagingClasses(updated);
    }
  }, [employees]);

  useEffect(() => {
    if (isEditingEmployeeDetail) {
      const updated = employees.find(e => e.id === isEditingEmployeeDetail.id);
      if (updated) setIsEditingEmployeeDetail(updated);
    }
  }, [employees]);

  useEffect(() => {
    if (isEditingStudentDetail) {
      const updated = students.find(s => s.id === isEditingStudentDetail.id);
      if (updated) setIsEditingStudentDetail(updated);
    }
  }, [students]);

  const handleAddSchool = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await apiClient.post('/api/school', {
        ...newSchool,
        grade: Number(newSchool.grade)
      });
      if (response.data.status === 'success') {
        toast.success('School added successfully!');
        setIsAddingSchool(false);
        setNewSchool({ name: '', address: '', phoneNumber: '', grade: 1 });
        fetchSchools();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to add school');
    }
  };

  const fetchClasses = async (schoolId: number) => {
    try {
      setViewLoading(true);
      const response = await apiClient.get('/api/class');
      const allStudentsRes = await apiClient.get('/api/student');
      
      if (response.data.status === 'success' && allStudentsRes.data.status === 'success') {
        const allStudents = allStudentsRes.data.data;
        
        // Filter classes for this school and count students
        const filteredClasses = response.data.data
          .filter((c: any) => c.schoolName === selectedSchool?.name)
          .map((c: any) => ({
            ...c,
            studentCount: allStudents.filter((s: any) => s.className === c.name).length
          }));
          
        setClasses(filteredClasses);
      }
    } catch (error: any) {
      toast.error('Failed to fetch classes');
    } finally {
      setViewLoading(false);
    }
  };

  const fetchStudents = async (className: string) => {
    try {
      setViewLoading(true);
      const response = await apiClient.get('/api/student');
      if (response.data.status === 'success') {
        const filteredStudents = response.data.data.filter((s: any) => s.className === className);
        setStudents(filteredStudents);
      }
    } catch (error: any) {
      toast.error('Failed to fetch students');
    } finally {
      setViewLoading(false);
    }
  };

  const fetchEmployees = async () => {
    try {
      setViewLoading(true);
      const response = await apiClient.get('/api/employee');
      if (response.data.status === 'success') {
        // Fetch all teachers to get their class and subject lists
        // Note: The backend DTO might not return these lists, so we might need
        // to handle that if the API doesn't provide them.
        setEmployees(response.data.data);
      }
    } catch (error: any) {
      toast.error('Failed to fetch employees');
    } finally {
      setViewLoading(false);
    }
  };

  const fetchAllSubjects = async () => {
    try {
      const response = await apiClient.get('/api/subject');
      if (response.data.status === 'success') {
        setAllSubjects(response.data.data);
      }
    } catch (error: any) {
      toast.error('Failed to fetch subjects');
    }
  };

  const fetchAllClasses = async () => {
    try {
      const response = await apiClient.get('/api/class');
      if (response.data.status === 'success') {
        setAllClasses(response.data.data);
      }
    } catch (error: any) {
      toast.error('Failed to fetch all classes');
    }
  };

  const handleAddSubject = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isManagingSubjects?.teacher?.id) return;
    try {
      const response = await apiClient.post(`/api/subject/${isManagingSubjects.teacher.id}`, newSubject);
      if (response.data.status === 'success') {
        toast.success('Subject added successfully!');
        setIsAddingSubject(false);
        setNewSubject({ name: '' });
        fetchAllSubjects();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to add subject');
    }
  };

  const handleUpdateTeacherSubjects = async (teacherId: number, subjectIds: number[]) => {
    try {
      const response = await apiClient.put(`/api/teacher/${teacherId}/subject`, subjectIds);
      if (response.data.status === 'success') {
        toast.success('Teacher subjects updated successfully!');
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error('Failed to update teacher subjects');
    }
  };

  const handleUpdateTeacherClasses = async (teacherId: number, classIds: number[]) => {
    try {
      const response = await apiClient.put(`/api/teacher/${teacherId}/class`, classIds);
      if (response.data.status === 'success') {
        toast.success('Teacher classes updated successfully!');
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error('Failed to update teacher classes');
    }
  };

  const handleAddEmployee = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedSchool) return;
    try {
      const response = await apiClient.post(`/api/employee/${selectedSchool.id}`, newEmployee);
      if (response.data.status === 'success') {
        toast.success('Employee added successfully!');
        setIsAddingEmployee(false);
        setNewEmployee({ name: '', role: '' });
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to add employee');
    }
  };

  const handleUpdateEmployee = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingEmployee) return;
    try {
      const response = await apiClient.put(`/api/employee/${isEditingEmployee.id}`, {
        name: isEditingEmployee.name,
        role: isEditingEmployee.role
      });
      if (response.data.status === 'success') {
        toast.success('Employee updated successfully!');
        setIsEditingEmployee(null);
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to update employee');
    }
  };

  const handleAssignTeacher = async (employeeId: number) => {
    try {
      const response = await apiClient.post(`/api/teacher/${employeeId}`, {});
      if (response.data.status === 'success') {
        toast.success('Employee assigned as teacher successfully!');
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to assign teacher');
    }
  };

  const fetchStudentDetail = async (studentId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/student-detail/${studentId}`);
      if (response.data.status === 'success' && response.data.data) {
        setStudentDetail(response.data.data);
      } else {
        setStudentDetail({ fullName: '', address: '', hobby: '' });
      }
    } catch (error: any) {
      setStudentDetail({ fullName: '', address: '', hobby: '' });
    } finally {
      setDetailLoading(false);
    }
  };

  const handleSaveStudentDetail = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingStudentDetail || !studentDetail) return;
    try {
      let response;
      if (studentDetail.id) {
        response = await apiClient.put(`/api/student-detail/${studentDetail.id}`, studentDetail);
      } else {
        response = await apiClient.post(`/api/student-detail/${isEditingStudentDetail.id}`, studentDetail);
      }
      
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Student details updated successfully!');
        setIsEditingStudentDetail(null);
        setStudentDetail(null);
        if (selectedClass) fetchStudents(selectedClass.name);
      }
    } catch (error: any) {
      toast.error('Failed to update student details');
    }
  };

  const fetchParent = async (studentId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/parent/${studentId}`);
      if (response.data.status === 'success' && response.data.data) {
        setParentData(response.data.data);
      } else {
        setParentData({ name: '', address: '', phoneNumber: '' });
      }
    } catch (error: any) {
      setParentData({ name: '', address: '', phoneNumber: '' });
    } finally {
      setDetailLoading(false);
    }
  };

  const handleSaveParent = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingParent || !parentData) return;
    try {
      let response;
      const payload = {
        name: parentData.name,
        address: parentData.address,
        phoneNumber: parentData.phoneNumber
      };

      if (parentData.id) {
        response = await apiClient.put(`/api/parent/${parentData.id}`, payload);
      } else {
        response = await apiClient.post(`/api/parent/${isEditingParent.id}`, payload);
      }
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Parent details updated successfully!');
        setIsEditingParent(null);
        setParentData(null);
      }
    } catch (error: any) {
      toast.error('Failed to update parent details');
    }
  };

  const fetchStudentRecord = async (studentId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/student-record/${studentId}`);
      if (response.data.status === 'success' && response.data.data) {
        setRecordData(response.data.data);
      } else {
        setRecordData({ description: '' });
      }
    } catch (error: any) {
      setRecordData({ description: '' });
    } finally {
      setDetailLoading(false);
    }
  };

  const handleSaveStudentRecord = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingRecord || !recordData) return;
    try {
      let response;
      const payload = {
        description: recordData.description
      };

      if (recordData.id) {
        response = await apiClient.put(`/api/student-record/${recordData.id}`, payload);
      } else {
        response = await apiClient.post(`/api/student-record/${isEditingRecord.id}`, payload);
      }
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Student record updated successfully!');
        setIsEditingRecord(null);
        setRecordData(null);
      }
    } catch (error: any) {
      toast.error('Failed to update student record');
    }
  };

  const fetchTranscription = async (studentId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/transcription/${studentId}`);
      if (response.data.status === 'success' && response.data.data) {
        setTranscriptionData(response.data.data);
      } else {
        setTranscriptionData({ year: new Date().getFullYear(), rate: '', mark: 0 });
      }
    } catch (error: any) {
      setTranscriptionData({ year: new Date().getFullYear(), rate: '', mark: 0 });
    } finally {
      setDetailLoading(false);
    }
  };

  const fetchClassLogbook = async (classId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/class-logbook/class/${classId}`);
      if (response.data.status === 'success' && response.data.data) {
        setLogbookData(response.data.data);
      } else {
        setLogbookData({ description: '' });
      }
    } catch (error: any) {
      setLogbookData({ description: '' });
    } finally {
      setDetailLoading(false);
    }
  };

  const handleSaveTranscription = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingTranscription || !transcriptionData) return;
    try {
      let response;
      if (transcriptionData.id) {
        response = await apiClient.put(`/api/transcription/${transcriptionData.id}`, transcriptionData);
      } else {
        response = await apiClient.post(`/api/transcription/${isEditingTranscription.id}`, transcriptionData);
      }
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Transcription updated successfully!');
        setIsEditingTranscription(null);
        setTranscriptionData(null);
      }
    } catch (error: any) {
      toast.error('Failed to update transcription');
    }
  };

  const handleSaveClassLogbook = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingLogbook || !logbookData) return;
    try {
      let response;
      if (logbookData.id) {
        response = await apiClient.put(`/api/class-logbook/${logbookData.id}`, logbookData);
      } else {
        response = await apiClient.post(`/api/class-logbook/${isEditingLogbook.id}`, logbookData);
      }
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Class logbook updated successfully!');
        setIsEditingLogbook(null);
        setLogbookData(null);
      }
    } catch (error: any) {
      toast.error('Failed to update class logbook');
    }
  };

  const fetchEmployeeDetail = async (employeeId: number) => {
    try {
      setDetailLoading(true);
      const response = await apiClient.get(`/api/employee-detail/${employeeId}`);
      if (response.data.status === 'success' && response.data.data) {
        setEmployeeDetail(response.data.data);
      } else {
        setEmployeeDetail({ dayOfBirth: '', address: '', phoneNumber: '' });
      }
    } catch (error: any) {
      setEmployeeDetail({ dayOfBirth: '', address: '', phoneNumber: '' });
    } finally {
      setDetailLoading(false);
    }
  };

  const handleSaveEmployeeDetail = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isEditingEmployeeDetail || !employeeDetail) return;
    try {
      let response;
      if (employeeDetail.id) {
        response = await apiClient.put(`/api/employee-detail/${employeeDetail.id}`, employeeDetail);
      } else {
        response = await apiClient.post(`/api/employee-detail/${isEditingEmployeeDetail.id}`, employeeDetail);
      }
      
      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success('Employee details updated successfully!');
        setIsEditingEmployeeDetail(null);
        setEmployeeDetail(null);
        fetchEmployees();
      }
    } catch (error: any) {
      toast.error('Failed to update employee details');
    }
  };

  const handleAddClass = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedSchool) return;
    try {
      const response = await apiClient.post(`/api/class/${selectedSchool.id}`, newClass);
      if (response.data.status === 'success') {
        toast.success('Class added successfully!');
        setIsAddingClass(false);
        setNewClass({ name: '', grade: 1, year: new Date().getFullYear() });
        fetchClasses(selectedSchool.id);
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to add class');
    }
  };

  const handleAddStudent = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedClass) return;
    try {
      const response = await apiClient.post(`/api/student/${selectedClass.id}`, newStudent);
      if (response.data.status === 'success') {
        toast.success('Student added successfully!');
        setIsAddingStudent(false);
        setNewStudent({ name: '', dateOfBirth: '' });
        fetchStudents(selectedClass.name);
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to add student');
    }
  };

  const handleDeleteConfirm = async (password: string) => {
    try {
      await apiClient.post('/auth/login', {
        username: user?.username,
        password: password
      });

      let endpoint = '';
      if (deleteModal.type === 'school') endpoint = `/api/school/${deleteModal.id}`;
      else if (deleteModal.type === 'class') endpoint = `/api/class/${deleteModal.id}`;
      else if (deleteModal.type === 'student') endpoint = `/api/student/${deleteModal.id}`;
      else if (deleteModal.type === 'employee') endpoint = `/api/employee/${deleteModal.id}`;

      const response = await apiClient.delete(endpoint);
      if (response.data.status === 'success') {
        toast.success('Deleted successfully');
        if (deleteModal.type === 'school') fetchSchools();
        else if (deleteModal.type === 'class' && selectedSchool) fetchClasses(selectedSchool.id);
        else if (deleteModal.type === 'student' && selectedClass) fetchStudents(selectedClass.name);
        else if (deleteModal.type === 'employee') fetchEmployees();
      }
    } catch (error: any) {
      toast.error(error.response?.status === 401 ? 'Invalid password' : 'Delete failed');
      throw error;
    }
  };

  // Views
  if (viewMode === 'students' && selectedClass && selectedSchool) {
    return (
      <div className="space-y-6 animate-in fade-in duration-500">
        <button 
          onClick={() => { setViewMode('classes'); setStudents([]); }}
          className="flex items-center text-gray-500 hover:text-blue-600 transition-colors"
        >
          <ArrowLeft className="w-4 h-4 mr-2" /> Back to Classes
        </button>

        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-3">
              <Users className="w-8 h-8 text-blue-600" />
              Class: {selectedClass.name}
            </h1>
            <p className="text-gray-500 mt-1">Grade {selectedClass.grade} • Year {selectedClass.year}</p>
          </div>
          <button 
            onClick={() => setIsAddingStudent(true)}
            className="bg-blue-600 text-white px-6 py-2.5 rounded-xl font-semibold shadow-lg hover:bg-blue-700 flex items-center gap-2"
          >
            <Plus className="w-5 h-5" /> Enroll Student
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <button 
            onClick={() => setManagementModal({ isOpen: true, type: 'parent' })}
            className="bg-white p-4 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-all flex items-center gap-3 text-left group"
          >
            <div className="p-2 bg-blue-50 text-blue-600 rounded-lg group-hover:bg-blue-600 group-hover:text-white transition-colors">
              <Users className="w-5 h-5" />
            </div>
            <div>
              <p className="font-bold text-gray-900 text-sm">Parent Information</p>
              <p className="text-xs text-gray-500">Manage all parents in this class</p>
            </div>
          </button>

          <button 
            onClick={() => setManagementModal({ isOpen: true, type: 'record' })}
            className="bg-white p-4 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-all flex items-center gap-3 text-left group"
          >
            <div className="p-2 bg-green-50 text-green-600 rounded-lg group-hover:bg-green-600 group-hover:text-white transition-colors">
              <FileText className="w-5 h-5" />
            </div>
            <div>
              <p className="font-bold text-gray-900 text-sm">Student Records</p>
              <p className="text-xs text-gray-500">Academic & behavior notes</p>
            </div>
          </button>

          <button 
            onClick={() => setManagementModal({ isOpen: true, type: 'transcription' })}
            className="bg-white p-4 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-all flex items-center gap-3 text-left group"
          >
            <div className="p-2 bg-purple-50 text-purple-600 rounded-lg group-hover:bg-purple-600 group-hover:text-white transition-colors">
              <BookOpen className="w-5 h-5" />
            </div>
            <div>
              <p className="font-bold text-gray-900 text-sm">Transcriptions</p>
              <p className="text-xs text-gray-500">Grades & annual ratings</p>
            </div>
          </button>

          <button 
            onClick={() => setManagementModal({ isOpen: true, type: 'logbook' })}
            className="bg-white p-4 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-all flex items-center gap-3 text-left group"
          >
            <div className="p-2 bg-orange-50 text-orange-600 rounded-lg group-hover:bg-orange-600 group-hover:text-white transition-colors">
              <FileText className="w-5 h-5" />
            </div>
            <div>
              <p className="font-bold text-gray-900 text-sm">Class Logbook</p>
              <p className="text-xs text-gray-500">Manage daily class diary</p>
            </div>
          </button>
        </div>

        {isAddingStudent && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">New Student Enrollment</h2>
              <button onClick={() => setIsAddingStudent(false)}><X className="w-5 h-5" /></button>
            </div>
            <form onSubmit={handleAddStudent} className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <input
                type="text" required placeholder="Full Name"
                value={newStudent.name}
                onChange={(e) => setNewStudent({...newStudent, name: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <input
                type="date" required
                value={newStudent.dateOfBirth}
                onChange={(e) => setNewStudent({...newStudent, dateOfBirth: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <button type="submit" className="md:col-span-2 bg-blue-600 text-white py-3 rounded-xl font-bold">Enroll Student</button>
            </form>
          </div>
        )}

        {isEditingParent && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-bottom mt-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Parent Information: {isEditingParent.name}</h2>
              <button onClick={() => setIsEditingParent(null)}><X className="w-5 h-5" /></button>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : parentData && (
              <form onSubmit={handleSaveParent} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Name</label>
                  <input
                    type="text" required placeholder="Name"
                    value={parentData.name || ''} 
                    onChange={(e) => setParentData({...parentData, name: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                  />
                </div>
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Phone Number</label>
                  <input
                    type="text" required placeholder="Phone Number"
                    value={parentData.phoneNumber}
                    onChange={(e) => setParentData({...parentData, phoneNumber: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                  />
                </div>
                <div className="md:col-span-2 space-y-2">
                  <label className="text-sm font-bold text-gray-700">Address</label>
                  <textarea
                    required placeholder="Address"
                    value={parentData.address}
                    onChange={(e) => setParentData({...parentData, address: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    rows={2}
                  />
                </div>
                <button type="submit" className="md:col-span-2 bg-blue-600 text-white py-3 rounded-xl font-bold">
                  {parentData.id ? 'Update Parent Details' : 'Create Parent Details'}
                </button>
              </form>
            )}
          </div>
        )}

        {isEditingRecord && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-bottom mt-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Student Record: {isEditingRecord.name}</h2>
              <button onClick={() => setIsEditingRecord(null)}><X className="w-5 h-5" /></button>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : recordData && (
              <form onSubmit={handleSaveStudentRecord} className="space-y-4">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Description</label>
                  <textarea
                    required
                    placeholder="Enter student record details..."
                    value={recordData.description || ''} 
                    onChange={(e) => setRecordData({...recordData, description: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    rows={4}
                  />
                </div>
                <button type="submit" className="w-full bg-green-600 text-white py-3 rounded-xl font-bold">
                  {recordData.id ? 'Update Record' : 'Create Record'}
                </button>
              </form>
            )}
          </div>
        )}

        {isEditingTranscription && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-bottom mt-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Student Transcription: {isEditingTranscription.name}</h2>
              <button onClick={() => setIsEditingTranscription(null)}><X className="w-5 h-5" /></button>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : transcriptionData && (
              <form onSubmit={handleSaveTranscription} className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Year</label>
                  <input
                    type="number" required
                    value={transcriptionData.year}
                    onChange={(e) => setTranscriptionData({...transcriptionData, year: Number(e.target.value)})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                  />
                </div>
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Mark</label>
                  <input
                    type="number" step="0.1" required
                    value={transcriptionData.mark}
                    onChange={(e) => setTranscriptionData({...transcriptionData, mark: Number(e.target.value)})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                  />
                </div>
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Rate</label>
                  <select
                    required
                    value={transcriptionData.rate}
                    onChange={(e) => setTranscriptionData({...transcriptionData, rate: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="">-- Select Rate --</option>
                    <option value="EXCELLENT">EXCELLENT</option>
                    <option value="GOOD">GOOD</option>
                    <option value="AVERAGE">AVERAGE</option>
                    <option value="POOR">POOR</option>
                  </select>
                </div>
                <button type="submit" className="md:col-span-3 bg-purple-600 text-white py-3 rounded-xl font-bold">
                  {transcriptionData.id ? 'Update Transcription' : 'Create Transcription'}
                </button>
              </form>
            )}
          </div>
        )}

        {isEditingLogbook && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-bottom mt-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Class Logbook: {isEditingLogbook.name}</h2>
              <button onClick={() => setIsEditingLogbook(null)}><X className="w-5 h-5" /></button>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : logbookData && (
              <form onSubmit={handleSaveClassLogbook} className="space-y-4">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Description</label>
                  <textarea
                    required
                    placeholder="Enter class logbook details..."
                    value={logbookData.description || ''} 
                    onChange={(e) => setLogbookData({...logbookData, description: e.target.value})}
                    className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    rows={4}
                  />
                </div>
                <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-xl font-bold">
                  {logbookData.id ? 'Update Logbook' : 'Create Logbook'}
                </button>
              </form>
            )}
          </div>
        )}

        {isEditingStudentDetail && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Student Profile Details: {isEditingStudentDetail.name}</h2>
              <div className="flex items-center gap-2">
                {!isEditMode && studentDetail && (
                  <div className="relative group">
                    <button 
                      className="p-2 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors"
                      title="More Options"
                    >
                      <MoreHorizontal className="w-5 h-5" />
                    </button>
                    
                    {/* Hover Menu */}
                    <div className="absolute right-0 top-full mt-1 w-48 bg-white rounded-xl shadow-xl border border-gray-100 py-2 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all z-50">
                      <button 
                        onClick={() => { setIsEditingParent(isEditingStudentDetail); fetchParent(isEditingStudentDetail.id); }}
                        className="w-full text-left px-4 py-2 text-sm hover:bg-blue-50 text-gray-700 flex items-center gap-2"
                      >
                        <UserPlus className="w-4 h-4 text-blue-600" /> Parent Information
                      </button>
                      <button 
                        onClick={() => { setIsEditingRecord(isEditingStudentDetail); fetchStudentRecord(isEditingStudentDetail.id); }}
                        className="w-full text-left px-4 py-2 text-sm hover:bg-blue-50 text-gray-700 flex items-center gap-2"
                      >
                        <FileText className="w-4 h-4 text-green-600" /> Student Record
                      </button>
                      <button 
                        onClick={() => { setIsEditingTranscription(isEditingStudentDetail); fetchTranscription(isEditingStudentDetail.id); }}
                        className="w-full text-left px-4 py-2 text-sm hover:bg-blue-50 text-gray-700 flex items-center gap-2"
                      >
                        <BookOpen className="w-4 h-4 text-purple-600" /> Transcription
                      </button>
                    </div>
                  </div>
                )}
                {!isEditMode && studentDetail && (
                  <button 
                    onClick={() => setIsEditMode(true)}
                    className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                    title="Edit Details"
                  >
                    <Edit className="w-5 h-5" />
                  </button>
                )}
                <button onClick={() => { 
                  setIsEditingStudentDetail(null); 
                  setIsEditMode(false);
                  setIsEditingParent(null);
                  setIsEditingRecord(null);
                  setIsEditingTranscription(null);
                  setIsEditingLogbook(null);
                }}><X className="w-5 h-5" /></button>
              </div>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : studentDetail && (
              isEditMode ? (
                <form onSubmit={handleSaveStudentDetail} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Full Name</label>
                    <input
                      type="text" required placeholder="Full Name"
                      value={studentDetail.fullName}
                      onChange={(e) => setStudentDetail({...studentDetail, fullName: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    />
                  </div>
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Address</label>
                    <input
                      type="text" required placeholder="Address"
                      value={studentDetail.address}
                      onChange={(e) => setStudentDetail({...studentDetail, address: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    />
                  </div>
                  <div className="md:col-span-2 space-y-2">
                    <label className="text-sm font-bold text-gray-700">Hobby</label>
                    <textarea
                      placeholder="Hobbies and interests..."
                      value={studentDetail.hobby}
                      onChange={(e) => setStudentDetail({...studentDetail, hobby: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                      rows={3}
                    />
                  </div>
                  <div className="md:col-span-2 flex gap-4">
                    <button type="submit" className="flex-1 bg-blue-600 text-white py-3 rounded-xl font-bold">
                      {studentDetail.id ? 'Update Details' : 'Create Details'}
                    </button>
                    <button type="button" onClick={() => setIsEditMode(false)} className="px-6 py-3 bg-gray-100 text-gray-600 rounded-xl font-bold">
                      Cancel
                    </button>
                  </div>
                </form>
              ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Full Name</p>
                    <p className="text-lg font-semibold text-gray-900">{studentDetail.fullName || 'Not set'}</p>
                  </div>
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Address</p>
                    <p className="text-lg font-semibold text-gray-900">{studentDetail.address || 'Not set'}</p>
                  </div>
                  <div className="md:col-span-2 space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Hobby</p>
                    <p className="text-lg font-semibold text-gray-900">{studentDetail.hobby || 'Not set'}</p>
                  </div>
                </div>
              )
            )}
          </div>
        )}

        <div className="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
          <table className="w-full text-left">
            <thead className="bg-gray-50 border-b">
              <tr>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase">Student Name</th>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase">Date of Birth</th>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {viewLoading ? (
                <tr><td colSpan={3} className="p-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></td></tr>
              ) : students.map(s => (
                <tr 
                  key={s.id} 
                  className={`hover:bg-blue-50/30 group cursor-pointer transition-colors ${isEditingStudentDetail?.id === s.id ? 'bg-blue-50' : ''}`}
                  onClick={() => { setIsEditingStudentDetail(s); fetchStudentDetail(s.id); setIsEditMode(false); }}
                >
                  <td className="px-8 py-4 font-bold">{s.name}</td>
                  <td className="px-8 py-4 text-gray-500">{s.dateOfBirth}</td>
                  <td className="px-8 py-4 text-right">
                    <button 
                      onClick={(e) => { e.stopPropagation(); setDeleteModal({ isOpen: true, type: 'student', id: s.id, name: s.name }); }}
                      className="p-2 text-red-500 opacity-0 group-hover:opacity-100 transition-opacity"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <ConfirmDeleteModal
          isOpen={deleteModal.isOpen}
          onClose={() => setDeleteModal({ isOpen: false, type: 'school', id: null, name: '' })}
          onConfirm={handleDeleteConfirm}
          title={`Delete ${deleteModal.type}`}
          itemName={deleteModal.name}
        />
        <DataManagementModal 
          isOpen={managementModal.isOpen}
          onClose={() => setManagementModal(prev => ({ ...prev, isOpen: false }))}
          type={managementModal.type}
          filterStudentIds={students.map(s => s.id)}
        />
      </div>
    );
  }

  if (viewMode === 'employees' && selectedSchool) {
    return (
      <div className="space-y-6 animate-in fade-in duration-500">
        <button 
          onClick={() => { setViewMode('school-options'); setEmployees([]); }}
          className="flex items-center text-gray-500 hover:text-blue-600 transition-colors"
        >
          <ArrowLeft className="w-4 h-4 mr-2" /> Back to School Options
        </button>

        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-3">
              <Users className="w-8 h-8 text-blue-600" />
              Manage Employees
            </h1>
            <p className="text-gray-500 mt-1">Personnel management for {selectedSchool.name}</p>
          </div>
          <button 
            onClick={() => setIsAddingEmployee(true)}
            className="bg-blue-600 text-white px-6 py-2.5 rounded-xl font-semibold shadow-lg hover:bg-blue-700 flex items-center gap-2"
          >
            <Plus className="w-5 h-5" /> Add Employee
          </button>
        </div>

        {isAddingEmployee && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">New Employee Profile</h2>
              <button onClick={() => setIsAddingEmployee(false)}><X className="w-5 h-5" /></button>
            </div>
            <form onSubmit={handleAddEmployee} className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <input
                type="text" required placeholder="Full Name"
                value={newEmployee.name}
                onChange={(e) => setNewEmployee({...newEmployee, name: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <input
                type="text" required placeholder="Role (e.g. Staff, Janitor, Guard)"
                value={newEmployee.role}
                onChange={(e) => setNewEmployee({...newEmployee, role: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <button type="submit" className="md:col-span-2 bg-blue-600 text-white py-3 rounded-xl font-bold">
                Create Employee
              </button>
            </form>
          </div>
        )}

        {isEditingEmployee && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Edit Employee: {isEditingEmployee.name}</h2>
              <button onClick={() => setIsEditingEmployee(null)}><X className="w-5 h-5" /></button>
            </div>
            <form onSubmit={handleUpdateEmployee} className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <input
                type="text" required placeholder="Full Name"
                value={isEditingEmployee.name}
                onChange={(e) => setIsEditingEmployee({...isEditingEmployee, name: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <input
                type="text" required placeholder="Role"
                value={isEditingEmployee.role}
                onChange={(e) => setIsEditingEmployee({...isEditingEmployee, role: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <button type="submit" className="md:col-span-2 bg-blue-600 text-white py-3 rounded-xl font-bold">
                Update Employee
              </button>
            </form>
          </div>
        )}

        {isManagingSubjects && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Quản lý môn học: {isManagingSubjects.name}</h2>
              <button onClick={() => setIsManagingSubjects(null)}><X className="w-5 h-5" /></button>
            </div>
            <div className="space-y-6">
              <div className="flex justify-between items-center">
                <h3 className="font-bold text-gray-700">Assign Existing Subjects</h3>
                <button 
                  onClick={() => setIsAddingSubject(!isAddingSubject)}
                  className="text-blue-600 text-sm font-bold flex items-center gap-1"
                >
                  {isAddingSubject ? 'Cancel' : <><Plus className="w-4 h-4" /> Create New Subject</>}
                </button>
              </div>

              {isAddingSubject && (
                <form onSubmit={handleAddSubject} className="flex gap-4 animate-in slide-in-from-top">
                  <input
                    type="text" required placeholder="Subject Name (e.g. Mathematics)"
                    value={newSubject.name}
                    onChange={(e) => setNewSubject({ name: e.target.value })}
                    className="flex-1 px-4 py-2 bg-gray-50 border rounded-xl text-sm"
                  />
                  <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-bold">Create</button>
                </form>
              )}

              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {allSubjects.map(sub => {
                  const isAssigned = isManagingSubjects.teacher?.subjectList?.some(s => s.id === sub.id);
                  return (
                    <button
                      key={sub.id}
                      onClick={() => {
                        const currentIds = isManagingSubjects.teacher?.subjectList?.map(s => s.id) || [];
                        const newIds = isAssigned 
                          ? currentIds.filter(id => id !== sub.id)
                          : [...currentIds, sub.id];
                        
                        // Update local state first for immediate UI feedback
                        setIsManagingSubjects({
                          ...isManagingSubjects,
                          teacher: {
                            ...isManagingSubjects.teacher!,
                            subjectList: newIds.map(id => allSubjects.find(s => s.id === id)!)
                          }
                        });
                        handleUpdateTeacherSubjects(isManagingSubjects.teacher!.id, newIds);
                      }}
                      className={`px-4 py-2 rounded-xl text-sm font-medium transition-all ${
                        isAssigned 
                          ? 'bg-green-600 text-white shadow-md' 
                          : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                      }`}
                    >
                      {sub.name}
                    </button>
                  );
                })}
              </div>
            </div>
          </div>
        )}

        {isManagingClasses && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Phân lớp giảng dạy: {isManagingClasses.name}</h2>
              <button onClick={() => setIsManagingClasses(null)}><X className="w-5 h-5" /></button>
            </div>
            <div className="space-y-4">
              <h3 className="font-bold text-gray-700">Select Classes</h3>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {allClasses
                  .filter(cls => cls.schoolName === selectedSchool?.name)
                  .map(cls => {
                    const isAssigned = isManagingClasses.teacher?.clazzList?.some(c => c.id === cls.id);
                    return (
                      <button
                        key={cls.id}
                        onClick={() => {
                          const currentIds = isManagingClasses.teacher?.clazzList?.map(c => c.id) || [];
                          const newIds = isAssigned 
                            ? currentIds.filter(id => id !== cls.id)
                            : [...currentIds, cls.id];
                          
                          setIsManagingClasses({
                            ...isManagingClasses,
                            teacher: {
                              ...isManagingClasses.teacher!,
                              clazzList: newIds.map(id => allClasses.find(c => c.id === id)!)
                            }
                          });
                          handleUpdateTeacherClasses(isManagingClasses.teacher!.id, newIds);
                        }}
                        className={`px-4 py-2 rounded-xl text-sm font-medium transition-all ${
                          isAssigned 
                            ? 'bg-green-600 text-white shadow-md' 
                            : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                        }`}
                      >
                        {cls.name}
                      </button>
                    );
                  })}
              </div>
            </div>
          </div>
        )}

        {isEditingEmployeeDetail && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Employee Profile Details: {isEditingEmployeeDetail.name}</h2>
              <div className="flex items-center gap-2">
                {!isEditMode && employeeDetail && (
                  <button 
                    onClick={() => setIsEditMode(true)}
                    className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                    title="Edit Details"
                  >
                    <Edit className="w-5 h-5" />
                  </button>
                )}
                <button onClick={() => { setIsEditingEmployeeDetail(null); setIsEditMode(false); }}><X className="w-5 h-5" /></button>
              </div>
            </div>
            {detailLoading ? (
              <div className="py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
            ) : employeeDetail && (
              isEditMode ? (
                <form onSubmit={handleSaveEmployeeDetail} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Phone Number</label>
                    <input
                      type="text" required placeholder="Phone Number"
                      value={employeeDetail.phoneNumber}
                      onChange={(e) => setEmployeeDetail({...employeeDetail, phoneNumber: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    />
                  </div>
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Date of Birth</label>
                    <input
                      type="date" required
                      value={employeeDetail.dayOfBirth}
                      onChange={(e) => setEmployeeDetail({...employeeDetail, dayOfBirth: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    />
                  </div>
                  <div className="md:col-span-2 space-y-2">
                    <label className="text-sm font-bold text-gray-700">Address</label>
                    <input
                      type="text" required placeholder="Address"
                      value={employeeDetail.address}
                      onChange={(e) => setEmployeeDetail({...employeeDetail, address: e.target.value})}
                      className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
                    />
                  </div>
                  <div className="md:col-span-2 flex gap-4">
                    <button type="submit" className="flex-1 bg-blue-600 text-white py-3 rounded-xl font-bold">
                      {employeeDetail.id ? 'Update Details' : 'Create Details'}
                    </button>
                    <button type="button" onClick={() => setIsEditMode(false)} className="px-6 py-3 bg-gray-100 text-gray-600 rounded-xl font-bold">
                      Cancel
                    </button>
                  </div>
                </form>
              ) : (
                <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Phone Number</p>
                    <p className="text-lg font-semibold text-gray-900">{employeeDetail.phoneNumber || 'Not set'}</p>
                  </div>
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Date of Birth</p>
                    <p className="text-lg font-semibold text-gray-900">{employeeDetail.dayOfBirth || 'Not set'}</p>
                  </div>
                  <div className="space-y-1">
                    <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Address</p>
                    <p className="text-lg font-semibold text-gray-900">{employeeDetail.address || 'Not set'}</p>
                  </div>
                </div>
              )
            )}
          </div>
        )}

        <div className="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
          <table className="w-full text-left">
            <thead className="bg-gray-50 border-b">
              <tr>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase">Employee Name</th>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase">Role</th>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase">Status</th>
                <th className="px-8 py-4 text-xs font-bold text-gray-400 uppercase text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {viewLoading ? (
                <tr><td colSpan={4} className="p-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></td></tr>
              ) : employees.length === 0 ? (
                <tr><td colSpan={4} className="p-10 text-center text-gray-400">No employees found</td></tr>
              ) : employees.map(emp => (
                <tr 
                  key={emp.id} 
                  className={`hover:bg-blue-50/30 group cursor-pointer transition-colors ${isEditingEmployeeDetail?.id === emp.id ? 'bg-blue-50' : ''}`}
                  onClick={() => { setIsEditingEmployeeDetail(emp); fetchEmployeeDetail(emp.id); setIsEditMode(false); }}
                >
                  <td className="px-8 py-4 font-bold">{emp.name}</td>
                  <td className="px-8 py-4 text-gray-500">{emp.role}</td>
                  <td className="px-8 py-4">
                    {emp.teacher ? (
                      <div className="space-y-2">
                        <span className="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-bold">Teacher</span>
                        <div className="flex flex-wrap gap-1">
                          {emp.teacher.subjectList?.map(s => (
                            <span key={s.id} className="px-2 py-0.5 bg-blue-50 text-blue-600 rounded-md text-[10px] font-medium border border-blue-100">
                              {s.name}
                            </span>
                          ))}
                        </div>
                        <div className="flex flex-wrap gap-1">
                          {emp.teacher.clazzList?.map(c => (
                            <span key={c.id} className="px-2 py-0.5 bg-indigo-50 text-indigo-600 rounded-md text-[10px] font-medium border border-indigo-100">
                              {c.name}
                            </span>
                          ))}
                        </div>
                      </div>
                    ) : (
                      <span className="px-3 py-1 bg-gray-100 text-gray-500 rounded-full text-xs font-bold">Staff</span>
                    )}
                  </td>
                  <td className="px-8 py-4 text-right space-x-2">
                    {emp.teacher ? (
                      <>
                        <button 
                          onClick={(e) => { e.stopPropagation(); setIsManagingSubjects(emp); fetchAllSubjects(); }}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                          title="Manage Subjects"
                        >
                          <Search className="w-4 h-4" />
                        </button>
                        <button 
                          onClick={(e) => { e.stopPropagation(); setIsManagingClasses(emp); fetchAllClasses(); }}
                          className="p-2 text-indigo-600 hover:bg-indigo-50 rounded-lg transition-colors"
                          title="Assign Classes"
                        >
                          <Building2 className="w-4 h-4" />
                        </button>
                      </>
                    ) : (
                      <button 
                        onClick={(e) => { e.stopPropagation(); handleAssignTeacher(emp.id); }}
                        className="p-2 text-green-600 hover:bg-green-50 rounded-lg transition-colors"
                        title="Assign as Teacher"
                      >
                        <GraduationCap className="w-4 h-4" />
                      </button>
                    )}
                    <button 
                      onClick={(e) => { e.stopPropagation(); setIsEditingEmployee(emp); }}
                      className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                    >
                      <Edit className="w-4 h-4" />
                    </button>
                    <button 
                      onClick={(e) => { e.stopPropagation(); setDeleteModal({ isOpen: true, type: 'employee', id: emp.id, name: emp.name }); }}
                      className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <ConfirmDeleteModal
          isOpen={deleteModal.isOpen}
          onClose={() => setDeleteModal({ isOpen: false, type: 'school', id: null, name: '' })}
          onConfirm={handleDeleteConfirm}
          title={`Delete ${deleteModal.type}`}
          itemName={deleteModal.name}
        />
      </div>
    );
  }

  if (viewMode === 'classes' && selectedSchool) {
    return (
      <div className="space-y-6 animate-in fade-in duration-500">
        <button 
          onClick={() => { setViewMode('school-options'); setClasses([]); }}
          className="flex items-center text-gray-500 hover:text-blue-600 transition-colors"
        >
          <ArrowLeft className="w-4 h-4 mr-2" /> Back to School Options
        </button>

        <div className="flex justify-between items-center">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-3">
              <Building2 className="w-8 h-8 text-blue-600" />
              {selectedSchool.name}
            </h1>
            <p className="text-gray-500 mt-1">{selectedSchool.address} • {selectedSchool.phoneNumber}</p>
          </div>
          <button 
            onClick={() => setIsAddingClass(true)}
            className="bg-blue-600 text-white px-6 py-2.5 rounded-xl font-semibold shadow-lg hover:bg-blue-700 flex items-center gap-2"
          >
            <Plus className="w-5 h-5" /> New Class
          </button>
        </div>

        {isAddingClass && (
          <div className="bg-white p-6 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-bold">Create New Class</h2>
              <button onClick={() => setIsAddingClass(false)}><X className="w-5 h-5" /></button>
            </div>
            <form onSubmit={handleAddClass} className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <input
                type="text" required placeholder="Class Name (e.g. 10A1)"
                value={newClass.name}
                onChange={(e) => setNewClass({...newClass, name: e.target.value})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <input
                type="number" required placeholder="Grade" min="1" max="12"
                value={newClass.grade}
                onChange={(e) => setNewClass({...newClass, grade: Number(e.target.value)})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <input
                type="number" required placeholder="Year"
                value={newClass.year}
                onChange={(e) => setNewClass({...newClass, year: Number(e.target.value)})}
                className="w-full px-4 py-3 bg-gray-50 border rounded-xl"
              />
              <button type="submit" className="md:col-span-3 bg-blue-600 text-white py-3 rounded-xl font-bold">Create Class</button>
            </form>
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {viewLoading ? (
            <div className="col-span-full py-10 text-center"><Loader2 className="w-8 h-8 animate-spin mx-auto text-blue-600" /></div>
          ) : classes.map(c => (
            <div 
              key={c.id} 
              className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-all cursor-pointer group"
              onClick={() => { setSelectedClass(c); setViewMode('students'); fetchStudents(c.name); }}
            >
              <div className="flex justify-between items-start mb-4">
                <div className="w-10 h-10 bg-blue-50 text-blue-600 rounded-lg flex items-center justify-center">
                  <GraduationCap className="w-5 h-5" />
                </div>
                <div className="flex gap-1">
                  <button 
                    onClick={(e) => { 
                      e.stopPropagation(); 
                      setIsViewingClassTeachers(c); 
                      if (employees.length === 0) fetchEmployees(); 
                    }}
                    className="p-1.5 text-blue-400 hover:text-blue-600 opacity-0 group-hover:opacity-100 transition-opacity"
                    title="View Teachers"
                  >
                    <Users className="w-4 h-4" />
                  </button>
                  <button 
                    onClick={(e) => { e.stopPropagation(); setDeleteModal({ isOpen: true, type: 'class', id: c.id, name: c.name }); }}
                    className="p-1.5 text-red-400 hover:text-red-600 opacity-0 group-hover:opacity-100 transition-opacity"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              </div>
              <h3 className="font-bold text-lg">{c.name}</h3>
              <p className="text-sm text-gray-500">Grade {c.grade} • {c.year}</p>
              <div className="mt-2 flex items-center gap-2 text-xs font-medium text-gray-400">
                <Users className="w-3 h-3" />
                {c.studentCount || 0} Students enrolled
              </div>
              <div className="mt-4 flex items-center text-blue-600 text-sm font-bold">
                View Students <ChevronRight className="w-4 h-4 ml-1" />
              </div>
            </div>
          ))}
        </div>

        {isViewingClassTeachers && (
          <div className="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-3xl shadow-2xl w-full max-w-lg overflow-hidden animate-in zoom-in-95 duration-200">
              <div className="p-6 border-b flex justify-between items-center">
                <h2 className="text-xl font-bold flex items-center gap-2">
                  <Users className="w-6 h-6 text-blue-600" />
                  Giáo viên dạy lớp {isViewingClassTeachers.name}
                </h2>
                <button onClick={() => setIsViewingClassTeachers(null)} className="p-2 hover:bg-gray-100 rounded-full transition-colors">
                  <X className="w-5 h-5" />
                </button>
              </div>
              <div className="p-6">
                <div className="space-y-4 max-h-[400px] overflow-y-auto">
                  {employees
                    .filter(emp => emp.teacher?.clazzList?.some(cls => cls.id === isViewingClassTeachers.id))
                    .map(teacher => (
                      <div key={teacher.id} className="flex items-center gap-4 p-4 bg-gray-50 rounded-2xl">
                        <div className="w-12 h-12 bg-blue-100 text-blue-600 rounded-xl flex items-center justify-center font-bold text-lg">
                          {teacher.name.charAt(0)}
                        </div>
                        <div>
                          <p className="font-bold text-gray-900">{teacher.name}</p>
                          <p className="text-sm text-gray-500">{teacher.role}</p>
                        </div>
                      </div>
                    ))}
                  {employees.filter(emp => emp.teacher?.clazzList?.some(cls => cls.id === isViewingClassTeachers.id)).length === 0 && (
                    <div className="text-center py-10 text-gray-400">
                      Chưa có giáo viên nào được phân công dạy lớp này
                    </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        )}
        <ConfirmDeleteModal
          isOpen={deleteModal.isOpen}
          onClose={() => setDeleteModal({ isOpen: false, type: 'school', id: null, name: '' })}
          onConfirm={handleDeleteConfirm}
          title={`Delete ${deleteModal.type}`}
          itemName={deleteModal.name}
        />
      </div>
    );
  }

  if (viewMode === 'school-options' && selectedSchool) {
    return (
      <div className="space-y-8 animate-in fade-in duration-500">
        <button 
          onClick={() => { setSelectedSchool(null); setViewMode('schools'); }}
          className="flex items-center text-gray-500 hover:text-blue-600 transition-colors"
        >
          <ArrowLeft className="w-4 h-4 mr-2" /> Back to All Schools
        </button>

        <div className="text-center space-y-4">
          <h1 className="text-4xl font-bold text-gray-900">{selectedSchool.name}</h1>
          <p className="text-gray-500 max-w-2xl mx-auto">Manage classes, students, and employees for this institution</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto pt-8">
          <div 
            onClick={() => { setViewMode('classes'); fetchClasses(selectedSchool.id); fetchEmployees(); }}
            className="bg-white p-10 rounded-3xl shadow-sm border border-gray-100 hover:shadow-xl hover:border-blue-200 transition-all cursor-pointer group text-center space-y-6"
          >
            <div className="w-20 h-20 bg-blue-50 text-blue-600 rounded-2xl flex items-center justify-center mx-auto group-hover:scale-110 transition-transform">
              <GraduationCap className="w-10 h-10" />
            </div>
            <div>
              <h3 className="text-2xl font-bold text-gray-900">Chỉnh sửa lớp học</h3>
              <p className="text-gray-500 mt-2">Manage classes and student enrollments</p>
            </div>
          </div>

          <div 
            onClick={() => { setViewMode('employees'); fetchEmployees(); }}
            className="bg-white p-10 rounded-3xl shadow-sm border border-gray-100 hover:shadow-xl hover:border-blue-200 transition-all cursor-pointer group text-center space-y-6"
          >
            <div className="w-20 h-20 bg-green-50 text-green-600 rounded-2xl flex items-center justify-center mx-auto group-hover:scale-110 transition-transform">
              <Users className="w-10 h-10" />
            </div>
            <div>
              <h3 className="text-2xl font-bold text-gray-900">Chỉnh sửa nhân viên</h3>
              <p className="text-gray-500 mt-2">Manage staff and teacher assignments</p>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-8 animate-in fade-in duration-500">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-3">
            <SchoolIcon className="w-8 h-8 text-blue-600" />
            Schools & Classes
          </h1>
          <p className="text-gray-500 mt-1">Select a school to manage its classes and students</p>
        </div>
        
        <button 
          onClick={() => setIsAddingSchool(!isAddingSchool)}
          className={`flex items-center gap-2 px-6 py-2.5 rounded-xl font-semibold shadow-lg transition-all active:scale-95 ${
            isAddingSchool ? 'bg-gray-100 text-gray-600' : 'bg-blue-600 text-white hover:bg-blue-700'
          }`}
        >
          {isAddingSchool ? <X className="w-5 h-5" /> : <Plus className="w-5 h-5" />}
          {isAddingSchool ? 'Cancel' : 'Add New School'}
        </button>
      </div>

      {isAddingSchool && (
        <div className="bg-white p-8 rounded-2xl shadow-xl border border-gray-100 animate-in slide-in-from-top">
          <h2 className="text-xl font-bold mb-6 flex items-center gap-2"><Plus className="w-5 h-5 text-blue-600" /> New School Profile</h2>
          <form onSubmit={handleAddSchool} className="grid grid-cols-1 md:grid-cols-4 gap-6">
            <input type="text" required placeholder="School Name" value={newSchool.name} onChange={(e) => setNewSchool({...newSchool, name: e.target.value})} className="px-4 py-3 bg-gray-50 border rounded-xl" />
            <input type="text" required placeholder="Address" value={newSchool.address} onChange={(e) => setNewSchool({...newSchool, address: e.target.value})} className="px-4 py-3 bg-gray-50 border rounded-xl" />
            <input type="text" required placeholder="Phone" value={newSchool.phoneNumber} onChange={(e) => setNewSchool({...newSchool, phoneNumber: e.target.value})} className="px-4 py-3 bg-gray-50 border rounded-xl" />
            <input type="number" required placeholder="Level (1-3)" min="1" max="3" value={newSchool.grade} onChange={(e) => setNewSchool({...newSchool, grade: Number(e.target.value)})} className="px-4 py-3 bg-gray-50 border rounded-xl" />
            <button type="submit" className="md:col-span-4 bg-blue-600 text-white py-3 rounded-xl font-bold">Create School</button>
          </form>
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {loading ? (
          <div className="col-span-full py-20 flex flex-col items-center justify-center gap-4">
            <Loader2 className="w-10 h-10 text-blue-600 animate-spin" />
            <p className="text-gray-500 font-medium">Loading educational institutions...</p>
          </div>
        ) : schools.map((school) => (
          <div 
            key={school.id} 
            className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-blue-50 hover:border-blue-100 transition-all cursor-pointer group"
            onClick={() => { setSelectedSchool(school); setViewMode('school-options'); }}
          >
            <div className="flex justify-between items-start mb-6">
              <div className="w-12 h-12 bg-blue-600 text-white rounded-xl flex items-center justify-center shadow-lg shadow-blue-100">
                <Building2 className="w-6 h-6" />
              </div>
              <button 
                onClick={(e) => { e.stopPropagation(); setDeleteModal({ isOpen: true, type: 'school', id: school.id, name: school.name }); }}
                className="p-2 text-red-400 hover:text-red-600 opacity-0 group-hover:opacity-100 transition-opacity"
              >
                <Trash2 className="w-5 h-5" />
              </button>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">{school.name}</h3>
            <div className="space-y-2 text-sm text-gray-500 mb-6">
              <p className="flex items-center gap-2"><MapPin className="w-4 h-4" /> {school.address}</p>
              <p className="flex items-center gap-2"><Phone className="w-4 h-4" /> {school.phoneNumber}</p>
              <p className="flex items-center gap-2"><Activity className="w-4 h-4" /> Level {school.grade}</p>
            </div>
            <div className="flex items-center text-blue-600 font-bold">
              Manage School <ChevronRight className="w-5 h-5 ml-1 group-hover:translate-x-1 transition-transform" />
            </div>
          </div>
        ))}
      </div>

      <ConfirmDeleteModal
        isOpen={deleteModal.isOpen}
        onClose={() => setDeleteModal({ isOpen: false, type: 'school', id: null, name: '' })}
        onConfirm={handleDeleteConfirm}
        title={`Delete ${deleteModal.type}`}
        itemName={deleteModal.name}
      />
      <DataManagementModal 
        isOpen={managementModal.isOpen}
        onClose={() => setManagementModal(prev => ({ ...prev, isOpen: false }))}
        type={managementModal.type}
        filterStudentIds={viewMode === 'students' ? students.map(s => s.id) : undefined}
        filterClassId={viewMode === 'students' ? selectedClass?.id : undefined}
      />
    </div>
  );
};

export default AdminSchools;
