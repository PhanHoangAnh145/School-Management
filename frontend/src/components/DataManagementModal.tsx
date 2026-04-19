import React, { useState, useEffect } from 'react';
import { 
  X, 
  Plus, 
  Search, 
  Edit, 
  Trash2, 
  Loader2, 
  Users, 
  FileText, 
  BookOpen,
  UserPlus,
  ChevronRight
} from 'lucide-react';
import { toast } from 'sonner';
import apiClient from '../api/axios';

interface DataManagementModalProps {
  isOpen: boolean;
  onClose: () => void;
  type: 'parent' | 'record' | 'transcription' | 'logbook';
  filterStudentIds?: number[];
  filterClassId?: number;
}

interface Student {
  id: number;
  name: string;
}

const DataManagementModal: React.FC<DataManagementModalProps> = ({ isOpen, onClose, type, filterStudentIds, filterClassId }) => {
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [isEditing, setIsEditing] = useState<any | null>(null);
  const [isAdding, setIsAdding] = useState(false);
  const [students, setStudents] = useState<Student[]>([]);
  const [classes, setClasses] = useState<any[]>([]);
  const [selectedStudentId, setSelectedStudentId] = useState<number | string>('');
  const [selectedClassId, setSelectedClassId] = useState<number | string>('');
  
  // Grade Report States
  const [selectedTranscription, setSelectedTranscription] = useState<any | null>(null);
  const [gradeReports, setGradeReports] = useState<any[]>([]);
  const [isAddingGrade, setIsAddingGrade] = useState(false);
  const [isEditingGrade, setIsEditingGrade] = useState<any | null>(null);
  const [gradeFormData, setGradeFormData] = useState<any>({});
  
  // Form states
  const [formData, setFormData] = useState<any>({});

  const titles = {
    parent: 'Parent Information Management',
    record: 'Student Record Management',
    transcription: 'Transcription Management (Click row to view grades)',
    logbook: 'Class Logbook Management'
  };

  const icons = {
    parent: <Users className="w-6 h-6 text-blue-600" />,
    record: <FileText className="w-6 h-6 text-green-600" />,
    transcription: <BookOpen className="w-6 h-6 text-purple-600" />,
    logbook: <FileText className="w-6 h-6 text-orange-600" />
  };

  const endpoints = {
    parent: '/api/parent',
    record: '/api/student-record',
    transcription: '/api/transcription',
    logbook: '/api/class-logbook'
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await apiClient.get(endpoints[type]);
      if (response.data.status === 'success') {
        setData(response.data.data);
      }
    } catch (error) {
      toast.error(`Failed to fetch ${type} data`);
    } finally {
      setLoading(false);
    }
  };

  const fetchStudents = async () => {
    try {
      const response = await apiClient.get('/api/student');
      if (response.data.status === 'success') {
        setStudents(response.data.data);
      }
    } catch (error) {
      console.error('Failed to fetch students');
    }
  };

  const fetchClasses = async () => {
    try {
      const response = await apiClient.get('/api/class');
      if (response.data.status === 'success') {
        setClasses(response.data.data);
      }
    } catch (error) {
      console.error('Failed to fetch classes');
    }
  };

  useEffect(() => {
    if (isOpen) {
      fetchData();
      fetchStudents();
      fetchClasses();
      setIsAdding(false);
      setIsEditing(null);
      setFormData({});
      setSelectedStudentId('');
      setSelectedClassId('');
      setSelectedTranscription(null);
    }
  }, [isOpen, type]);

  const fetchGradeReports = async (transcriptionId: number) => {
    try {
      const response = await apiClient.get('/api/grade-report');
      if (response.data.status === 'success') {
        // Filter grades for this transcription as there might not be a specific endpoint for transcription grades
        const allGrades = response.data.data;
        const filteredGrades = allGrades.filter((g: any) => g.transcriptionId === transcriptionId);
        setGradeReports(filteredGrades);
      }
    } catch (error) {
      toast.error('Failed to fetch grade reports');
    }
  };

  const handleSaveGrade = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedTranscription) return;
    try {
      let response;
      if (isEditingGrade) {
        response = await apiClient.put(`/api/grade-report/${isEditingGrade.id}`, gradeFormData);
      } else {
        response = await apiClient.post(`/api/grade-report/${selectedTranscription.id}`, gradeFormData);
      }

      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success(isEditingGrade ? 'Grade updated' : 'Grade added');
        setIsAddingGrade(false);
        setIsEditingGrade(null);
        fetchGradeReports(selectedTranscription.id);
      }
    } catch (error) {
      toast.error('Failed to save grade');
    }
  };

  const handleDeleteGrade = async (id: number) => {
    if (!window.confirm('Delete this grade report?')) return;
    try {
      const response = await apiClient.delete(`/api/grade-report/${id}`);
      if (response.data.status === 'success') {
        toast.success('Grade deleted');
        if (selectedTranscription) fetchGradeReports(selectedTranscription.id);
      }
    } catch (error) {
      toast.error('Failed to delete grade');
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm('Are you sure you want to delete this record?')) return;
    try {
      const response = await apiClient.delete(`${endpoints[type]}/${id}`);
      if (response.data.status === 'success') {
        toast.success('Deleted successfully');
        fetchData();
      }
    } catch (error) {
      toast.error('Failed to delete');
    }
  };

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      let response;
      let payload = { ...formData };

      if (isEditing) {
        response = await apiClient.put(`${endpoints[type]}/${isEditing.id}`, payload);
      } else {
        if (type === 'logbook') {
          if (!selectedClassId) {
            toast.error('Please select a class');
            return;
          }
          response = await apiClient.post(`${endpoints[type]}/${selectedClassId}`, payload);
        } else {
          if (!selectedStudentId) {
            toast.error('Please select a student');
            return;
          }
          response = await apiClient.post(`${endpoints[type]}/${selectedStudentId}`, payload);
        }
      }

      if (response.data.status === 'success' || response.data.status === 'created') {
        toast.success(isEditing ? 'Updated successfully' : 'Added successfully');
        setIsAdding(false);
        setIsEditing(null);
        fetchData();
      }
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to save');
    }
  };

  const startEdit = (item: any) => {
    setIsEditing(item);
    setIsAdding(false);
    if (type === 'parent') {
      setFormData({ name: item.name, address: item.address, phoneNumber: item.phoneNumber });
    } else if (type === 'record' || type === 'logbook') {
      setFormData({ description: item.description });
    } else {
      setFormData({ year: item.year, rate: item.rate, mark: item.mark });
    }
  };

  const startAdd = () => {
    setIsAdding(true);
    setIsEditing(null);
    setSelectedStudentId('');
    setSelectedClassId('');
    
    // If filterStudentIds is provided and there's only one student, pre-select it
    if (filterStudentIds && filterStudentIds.length === 1) {
      setSelectedStudentId(filterStudentIds[0]);
    }

    // If filterClassId is provided, pre-select it
    if (filterClassId) {
      setSelectedClassId(filterClassId);
    }

    if (type === 'parent') {
      setFormData({ name: '', address: '', phoneNumber: '' });
    } else if (type === 'record' || type === 'logbook') {
      setFormData({ description: '' });
    } else {
      setFormData({ year: new Date().getFullYear(), rate: '', mark: 0 });
    }
  };

  const filteredData = data.filter(item => {
    // First filter by class ID if provided (for logbook)
    if (type === 'logbook' && filterClassId) {
      if (item.classId !== filterClassId) return false;
    }

    // First filter by student IDs if provided
    if (filterStudentIds && filterStudentIds.length > 0) {
      const studentId = item.studentId; 
      if (studentId && !filterStudentIds.includes(studentId)) return false;
    }

    const searchStr = searchTerm.toLowerCase();
    if (type === 'parent') {
      return item.name?.toLowerCase().includes(searchStr) || 
             item.studentName?.toLowerCase().includes(searchStr) || 
             item.phoneNumber?.includes(searchStr);
    }
    if (type === 'logbook') {
      return item.className?.toLowerCase().includes(searchStr) || 
             item.description?.toLowerCase().includes(searchStr);
    }
    return item.studentName?.toLowerCase().includes(searchStr) || 
           item.description?.toLowerCase().includes(searchStr) ||
           item.rate?.toLowerCase().includes(searchStr);
  });

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-[60] flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-in fade-in duration-300">
      <div className="bg-white w-full max-w-5xl h-[85vh] rounded-3xl shadow-2xl border border-gray-100 flex flex-col overflow-hidden animate-in zoom-in-95 duration-300">
        {/* Header */}
        <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-gray-50/50">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-white rounded-2xl shadow-sm border border-gray-100">
              {icons[type]}
            </div>
            <div>
              <h3 className="text-xl font-bold text-gray-900">{titles[type]}</h3>
              <p className="text-sm text-gray-500">Manage all records in the system</p>
            </div>
          </div>
          <button 
            onClick={onClose}
            className="p-2 hover:bg-gray-200 rounded-full transition-colors"
          >
            <X className="w-6 h-6 text-gray-400" />
          </button>
        </div>

        {/* Content */}
        <div className="flex-1 overflow-hidden flex flex-col p-6 space-y-6">
          <div className="flex flex-col md:flex-row gap-4 justify-between items-start md:items-center">
            <div className="relative w-full md:w-96">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
              <input 
                type="text"
                placeholder="Search records..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full pl-10 pr-4 py-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all"
              />
            </div>
            <button 
              onClick={startAdd}
              className="flex items-center gap-2 bg-blue-600 text-white px-5 py-2.5 rounded-xl font-bold hover:bg-blue-700 transition-all shadow-lg shadow-blue-100"
            >
              <Plus className="w-5 h-5" />
              Add New Record
            </button>
          </div>

          <div className="flex-1 overflow-y-auto rounded-2xl border border-gray-100 bg-white">
            {loading ? (
              <div className="h-full flex flex-col items-center justify-center text-gray-400 gap-3">
                <Loader2 className="w-10 h-10 animate-spin text-blue-600" />
                <p className="font-medium">Loading records...</p>
              </div>
            ) : filteredData.length > 0 ? (
              <table className="w-full text-left border-collapse">
                <thead className="sticky top-0 bg-gray-50 border-b border-gray-100 z-10">
                  <tr>
                    {type === 'parent' ? (
                      <>
                        <th className="p-4 font-bold text-gray-600 text-sm">Student Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Parent Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Phone Number</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Address</th>
                      </>
                    ) : type === 'record' ? (
                      <>
                        <th className="p-4 font-bold text-gray-600 text-sm">Student Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Description</th>
                      </>
                    ) : type === 'logbook' ? (
                      <>
                        <th className="p-4 font-bold text-gray-600 text-sm">Class Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Description</th>
                      </>
                    ) : (
                      <>
                        <th className="p-4 font-bold text-gray-600 text-sm">Student Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Year</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Rate</th>
                        <th className="p-4 font-bold text-gray-600 text-sm">Mark</th>
                      </>
                    )}
                    <th className="p-4 font-bold text-gray-600 text-sm text-right">Actions</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-50">
                  {filteredData.map((item) => (
                    <tr 
                      key={item.id} 
                      className={`transition-colors group ${type === 'transcription' ? 'cursor-pointer hover:bg-purple-50/50' : 'hover:bg-blue-50/30'}`}
                      onClick={() => {
                        if (type === 'transcription') {
                          setSelectedTranscription(item);
                          fetchGradeReports(item.id);
                        }
                      }}
                    >
                      {type === 'parent' ? (
                        <>
                          <td className="p-4 text-sm font-medium text-gray-900">{item.studentName || '---'}</td>
                          <td className="p-4 text-sm text-gray-600">{item.name}</td>
                          <td className="p-4 text-sm text-gray-600">{item.phoneNumber}</td>
                          <td className="p-4 text-sm text-gray-600">{item.address}</td>
                        </>
                      ) : type === 'record' ? (
                        <>
                          <td className="p-4 text-sm font-medium text-gray-900">{item.studentName || '---'}</td>
                          <td className="p-4 text-sm text-gray-600">{item.description}</td>
                        </>
                      ) : type === 'logbook' ? (
                        <>
                          <td className="p-4 text-sm font-medium text-gray-900">{item.className || '---'}</td>
                          <td className="p-4 text-sm text-gray-600">{item.description}</td>
                        </>
                      ) : (
                        <>
                          <td className="p-4 text-sm font-medium text-gray-900">{item.studentName || '---'}</td>
                          <td className="p-4 text-sm text-gray-600">{item.year}</td>
                          <td className="p-4 text-sm">
                            <span className={`px-2 py-1 rounded-full text-xs font-bold ${
                              item.rate === 'EXCELLENT' ? 'bg-green-100 text-green-700' :
                              item.rate === 'GOOD' ? 'bg-blue-100 text-blue-700' :
                              'bg-gray-100 text-gray-700'
                            }`}>
                              {item.rate}
                            </span>
                          </td>
                          <td className="p-4 text-sm font-bold text-blue-600">{item.mark}</td>
                        </>
                      )}
                      <td className="p-4 text-right">
                        <div className="flex justify-end items-center gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                          <button 
                            onClick={(e) => {
                              e.stopPropagation();
                              startEdit(item);
                            }}
                            className="p-2 text-blue-600 hover:bg-blue-100 rounded-lg transition-colors"
                          >
                            <Edit className="w-4 h-4" />
                          </button>
                          <button 
                            onClick={(e) => {
                              e.stopPropagation();
                              handleDelete(item.id);
                            }}
                            className="p-2 text-red-600 hover:bg-red-100 rounded-lg transition-colors"
                          >
                            <Trash2 className="w-4 h-4" />
                          </button>
                          {type === 'transcription' && <ChevronRight className="w-4 h-4 text-gray-400" />}
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <div className="h-full flex flex-col items-center justify-center text-gray-400 gap-2 p-8">
                <Search className="w-12 h-12 text-gray-200" />
                <p>No records found</p>
              </div>
            )}
          </div>
        </div>

        {/* Form Modal (Nested) */}
        {(isAdding || isEditing) && (
          <div className="absolute inset-0 z-[70] flex items-center justify-center p-4 bg-gray-900/40 backdrop-blur-sm animate-in fade-in duration-200">
            <div className="bg-white w-full max-w-lg rounded-2xl shadow-2xl border border-gray-100 overflow-hidden animate-in zoom-in-95 duration-200">
              <div className="p-6 border-b border-gray-50 flex justify-between items-center bg-gray-50/50">
                <h4 className="text-lg font-bold text-gray-900">
                  {isEditing ? 'Edit Record' : 'Add New Record'}
                </h4>
                <button 
                  onClick={() => { setIsAdding(false); setIsEditing(null); }}
                  className="p-1 hover:bg-gray-200 rounded-full transition-colors"
                >
                  <X className="w-5 h-5 text-gray-400" />
                </button>
              </div>

              <form onSubmit={handleSave} className="p-6 space-y-4">
                {!isEditing && (
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">
                      {type === 'logbook' ? 'Select Class' : 'Select Student'}
                    </label>
                    {type === 'logbook' ? (
                      <select
                        required
                        value={selectedClassId}
                        onChange={(e) => setSelectedClassId(e.target.value)}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">-- Choose a class --</option>
                        {classes
                          .filter(c => !filterClassId || c.id === filterClassId)
                          .map(c => (
                            <option key={c.id} value={c.id}>{c.name} (ID: {c.id})</option>
                          ))}
                      </select>
                    ) : (
                      <select
                        required
                        value={selectedStudentId}
                        onChange={(e) => setSelectedStudentId(e.target.value)}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">-- Choose a student --</option>
                        {students
                          .filter(s => !filterStudentIds || filterStudentIds.includes(s.id))
                          .map(s => (
                            <option key={s.id} value={s.id}>{s.name} (ID: {s.id})</option>
                          ))}
                      </select>
                    )}
                  </div>
                )}

                {type === 'parent' && (
                  <>
                    <div className="space-y-2">
                      <label className="text-sm font-bold text-gray-700">Parent Name</label>
                      <input 
                        type="text"
                        required
                        value={formData.name || ''}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                      />
                    </div>
                    <div className="space-y-2">
                      <label className="text-sm font-bold text-gray-700">Phone Number</label>
                      <input 
                        type="text"
                        required
                        value={formData.phoneNumber || ''}
                        onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                      />
                    </div>
                    <div className="space-y-2">
                      <label className="text-sm font-bold text-gray-700">Address</label>
                      <textarea 
                        required
                        value={formData.address || ''}
                        onChange={(e) => setFormData({ ...formData, address: e.target.value })}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-[100px]"
                      />
                    </div>
                  </>
                )}

                {type === 'record' && (
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Description</label>
                    <textarea 
                      required
                      value={formData.description || ''}
                      onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                      className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-[150px]"
                      placeholder="Enter student record details..."
                    />
                  </div>
                )}

                {type === 'logbook' && (
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-gray-700">Description</label>
                    <textarea 
                      required
                      value={formData.description || ''}
                      onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                      className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-[150px]"
                      placeholder="Enter class logbook details..."
                    />
                  </div>
                )}

                {type === 'transcription' && (
                  <>
                    <div className="grid grid-cols-2 gap-4">
                      <div className="space-y-2">
                        <label className="text-sm font-bold text-gray-700">Year</label>
                        <input 
                          type="number"
                          required
                          value={formData.year || ''}
                          onChange={(e) => setFormData({ ...formData, year: parseInt(e.target.value) })}
                          className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                      </div>
                      <div className="space-y-2">
                        <label className="text-sm font-bold text-gray-700">Mark</label>
                        <input 
                          type="number"
                          step="0.1"
                          required
                          value={formData.mark || ''}
                          onChange={(e) => setFormData({ ...formData, mark: parseFloat(e.target.value) })}
                          className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                      </div>
                    </div>
                    <div className="space-y-2">
                      <label className="text-sm font-bold text-gray-700">Rate</label>
                      <select
                        required
                        value={formData.rate || ''}
                        onChange={(e) => setFormData({ ...formData, rate: e.target.value })}
                        className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">-- Select Rate --</option>
                        <option value="EXCELLENT">EXCELLENT</option>
                        <option value="GOOD">GOOD</option>
                        <option value="AVERAGE">AVERAGE</option>
                        <option value="POOR">POOR</option>
                      </select>
                    </div>
                  </>
                )}

                <div className="flex gap-3 pt-4">
                  <button
                    type="button"
                    onClick={() => { setIsAdding(false); setIsEditing(null); }}
                    className="flex-1 py-3 bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold rounded-xl transition-all"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="flex-1 py-3 bg-blue-600 hover:bg-blue-700 text-white font-bold rounded-xl transition-all shadow-lg shadow-blue-100 flex items-center justify-center gap-2"
                  >
                    Save Changes
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        {/* Grade Reports View */}
        {selectedTranscription && (
          <div className="absolute inset-0 z-[65] flex items-center justify-center p-4 bg-gray-900/40 backdrop-blur-sm animate-in fade-in duration-300">
            <div className="bg-white w-full max-w-4xl h-[75vh] rounded-3xl shadow-2xl border border-gray-100 flex flex-col overflow-hidden animate-in zoom-in-95 duration-300">
              <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-purple-50/30">
                <div className="flex items-center gap-4">
                  <div className="p-3 bg-white rounded-2xl shadow-sm border border-purple-100 text-purple-600">
                    <FileText className="w-6 h-6" />
                  </div>
                  <div>
                    <h3 className="text-xl font-bold text-gray-900">Grade Reports: {selectedTranscription.studentName}</h3>
                    <p className="text-sm text-gray-500">Academic year: {selectedTranscription.year}</p>
                  </div>
                </div>
                <button 
                  onClick={() => setSelectedTranscription(null)}
                  className="p-2 hover:bg-gray-200 rounded-full transition-colors"
                >
                  <X className="w-6 h-6 text-gray-400" />
                </button>
              </div>

              <div className="flex-1 overflow-hidden flex flex-col p-6 space-y-4">
                <div className="flex justify-between items-center">
                  <h4 className="font-bold text-gray-700">Subject List</h4>
                  <button 
                    onClick={() => {
                      setIsAddingGrade(true);
                      setGradeFormData({ name: '', tenMark: 0 });
                    }}
                    className="flex items-center gap-2 bg-purple-600 text-white px-4 py-2 rounded-xl text-sm font-bold hover:bg-purple-700 transition-all shadow-lg shadow-purple-100"
                  >
                    <Plus className="w-4 h-4" /> Add Subject Grade
                  </button>
                </div>

                <div className="flex-1 overflow-y-auto rounded-2xl border border-gray-100 bg-white">
                  <table className="w-full text-left border-collapse">
                    <thead className="sticky top-0 bg-gray-50 border-b border-gray-100">
                      <tr>
                        <th className="p-4 font-bold text-gray-600 text-sm">Subject Name</th>
                        <th className="p-4 font-bold text-gray-600 text-sm text-center">Mark</th>
                        <th className="p-4 font-bold text-gray-600 text-sm text-right">Actions</th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-50">
                      {gradeReports.length > 0 ? gradeReports.map((grade) => (
                        <tr key={grade.id} className="hover:bg-purple-50/30 transition-colors group">
                          <td className="p-4 text-sm font-medium text-gray-900">{grade.name}</td>
                          <td className="p-4 text-sm font-bold text-purple-600 text-center">{grade.tenMark}</td>
                          <td className="p-4 text-right">
                            <div className="flex justify-end gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                              <button 
                                onClick={() => {
                                  setIsEditingGrade(grade);
                                  setGradeFormData({ name: grade.name, tenMark: grade.tenMark });
                                }}
                                className="p-2 text-blue-600 hover:bg-blue-100 rounded-lg"
                              >
                                <Edit className="w-4 h-4" />
                              </button>
                              <button 
                                onClick={() => handleDeleteGrade(grade.id)}
                                className="p-2 text-red-600 hover:bg-red-100 rounded-lg"
                              >
                                <Trash2 className="w-4 h-4" />
                              </button>
                            </div>
                          </td>
                        </tr>
                      )) : (
                        <tr>
                          <td colSpan={3} className="p-8 text-center text-gray-400">No grades recorded yet</td>
                        </tr>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Grade Form Modal (Nested) */}
        {(isAddingGrade || isEditingGrade) && (
          <div className="absolute inset-0 z-[75] flex items-center justify-center p-4 bg-gray-900/40 backdrop-blur-sm animate-in fade-in duration-200">
            <div className="bg-white w-full max-w-md rounded-2xl shadow-2xl border border-gray-100 overflow-hidden animate-in zoom-in-95 duration-200">
              <div className="p-6 border-b border-gray-50 flex justify-between items-center bg-gray-50/50">
                <h4 className="text-lg font-bold text-gray-900">
                  {isEditingGrade ? 'Edit Grade' : 'Add Subject Grade'}
                </h4>
                <button 
                  onClick={() => { setIsAddingGrade(false); setIsEditingGrade(null); }}
                  className="p-1 hover:bg-gray-200 rounded-full transition-colors"
                >
                  <X className="w-5 h-5 text-gray-400" />
                </button>
              </div>

              <form onSubmit={handleSaveGrade} className="p-6 space-y-4">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Subject Name</label>
                  <input 
                    type="text" required
                    value={gradeFormData.name || ''}
                    onChange={(e) => setGradeFormData({ ...gradeFormData, name: e.target.value })}
                    className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500"
                    placeholder="e.g. Mathematics, English..."
                  />
                </div>
                <div className="space-y-2">
                  <label className="text-sm font-bold text-gray-700">Mark</label>
                  <input 
                    type="number" step="0.1" min="0" max="10" required
                    value={gradeFormData.tenMark || 0}
                    onChange={(e) => setGradeFormData({ ...gradeFormData, tenMark: parseFloat(e.target.value) })}
                    className="w-full p-2.5 bg-gray-50 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500"
                  />
                </div>
                <div className="flex gap-3 pt-4">
                  <button
                    type="button"
                    onClick={() => { setIsAddingGrade(false); setIsEditingGrade(null); }}
                    className="flex-1 py-3 bg-gray-100 hover:bg-gray-200 text-gray-700 font-bold rounded-xl transition-all"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    className="flex-1 py-3 bg-purple-600 hover:bg-purple-700 text-white font-bold rounded-xl transition-all shadow-lg shadow-purple-100 flex items-center justify-center gap-2"
                  >
                    Save Grade
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default DataManagementModal;
