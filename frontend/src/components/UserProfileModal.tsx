import React, { useCallback, useEffect, useState } from 'react';
import { toast } from 'sonner';
import {
  X,
  User as UserIcon,
  Mail,
  UserCircle,
  Lock,
  Loader2,
  Pencil,
  ShieldCheck,
  Camera,
} from 'lucide-react';
import apiClient from '../api/axios';
import { useAuthStore } from '../store/useAuthStore';
import type { User } from '../types/auth';
import { avatarSrcFromBase64 } from '../utils/avatar';
import { changePassword } from '../api/userApi';

type ProfilePayload = {
  id: number;
  username: string;
  email?: string | null;
  firstname?: string | null;
  lastname?: string | null;
  role?: string | null;
  enabled?: boolean;
  avatarBase64?: string | null;
};

interface UserProfileModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const UserProfileModal: React.FC<UserProfileModalProps> = ({ isOpen, onClose }) => {
  const setUser = useAuthStore((s) => s.setUser);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [editing, setEditing] = useState(false);
  const [profile, setProfile] = useState<ProfilePayload | null>(null);
  const [form, setForm] = useState({
    firstname: '',
    lastname: '',
    email: '',
  });
  const [passwordForm, setPasswordForm] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [showPasswordChange, setShowPasswordChange] = useState(false);
  const [changingPassword, setChangingPassword] = useState(false);
  const [avatarBase64, setAvatarBase64] = useState<string | null>(null);
  const [avatarRemoved, setAvatarRemoved] = useState(false);

  const mapToStoreUser = useCallback((p: ProfilePayload): User => {
    return {
      id: p.id,
      username: p.username,
      email: p.email ?? undefined,
      firstname: p.firstname,
      lastname: p.lastname,
      role: p.role ?? '',
      enabled: p.enabled,
      avatarBase64: p.avatarBase64 ?? null,
    };
  }, []);

  const loadProfile = useCallback(async () => {
    setLoading(true);
    try {
      const res = await apiClient.get('/api/user/profile');
      if (res.data.status === 'success' && res.data.data) {
        const p = res.data.data as ProfilePayload;
        setProfile(p);
        setForm({
          firstname: p.firstname ?? '',
          lastname: p.lastname ?? '',
          email: p.email ?? '',
        });
        setPasswordForm({
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        });
        setShowPasswordChange(false);
        setAvatarBase64(p.avatarBase64 ?? null);
        setAvatarRemoved(false);
      }
    } catch {
      toast.error('Could not load profile');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (!isOpen) return;
    setEditing(false);
    loadProfile();
  }, [isOpen, loadProfile]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setPasswordForm((prev) => ({ ...prev, [name]: value }));
  };

  const submitPasswordChange = async () => {
    if (passwordForm.newPassword !== passwordForm.confirmPassword) {
      toast.error('New passwords do not match');
      return;
    }
    if (passwordForm.newPassword.length < 6) {
      toast.error('Password must be at least 6 characters');
      return;
    }

    setChangingPassword(true);
    try {
      await changePassword({
        oldPassword: passwordForm.oldPassword,
        newPassword: passwordForm.newPassword,
      });

      toast.success('Password changed successfully');
      setPasswordForm({
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      });
      setShowPasswordChange(false);
    } catch (err: unknown) {
      const msgFromResponse =
        err && typeof err === 'object' && 'response' in err
          ? (err as { response?: { data?: { message?: string } } }).response?.data?.message
          : undefined;
      const msg = msgFromResponse ?? (err instanceof Error ? err.message : undefined);
      toast.error(msg || 'Password change failed');
    } finally {
      setChangingPassword(false);
    }
  };

  const handlePasswordKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;
    e.preventDefault();
    if (changingPassword) return;
    void submitPasswordChange();
  };

  const handleAvatarFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file || !file.type.startsWith('image/')) {
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      const dataUrl = reader.result as string;
      setAvatarBase64(dataUrl);
      setAvatarRemoved(false);
    };
    reader.readAsDataURL(file);
    e.target.value = '';
  };

  const clearAvatar = () => {
    setAvatarBase64(null);
    setAvatarRemoved(true);
  };

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!profile) return;
    setSaving(true);
    try {
      const body: Record<string, string | undefined> = {
        firstname: form.firstname,
        lastname: form.lastname,
        email: form.email,
      };
      if (avatarRemoved) {
        body.avatarBase64 = '';
      } else if (avatarBase64?.startsWith('data:')) {
        body.avatarBase64 = avatarBase64;
      }

      const res = await apiClient.put('/api/user/profile', body);
      if (res.data.status === 'success' && res.data.data) {
        const updated = res.data.data as ProfilePayload;
        setProfile(updated);
        setUser(mapToStoreUser(updated));
        toast.success('Profile saved');
        setEditing(false);
        setPasswordForm({
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        });
        setShowPasswordChange(false);
        setAvatarBase64(updated.avatarBase64 ?? null);
        setAvatarRemoved(false);
      } else {
        toast.error(res.data.message || 'Save failed');
      }
    } catch (err: unknown) {
      const msg =
        err && typeof err === 'object' && 'response' in err
          ? (err as { response?: { data?: { message?: string } } }).response?.data?.message
          : undefined;
      toast.error(msg || 'Save failed');
    } finally {
      setSaving(false);
    }
  };

  const displayAvatarSrc = avatarRemoved
    ? undefined
    : avatarBase64
      ? avatarBase64.startsWith('data:')
        ? avatarBase64
        : avatarSrcFromBase64(avatarBase64)
      : undefined;

  if (!isOpen) return null;

  return (
    <div
      className="fixed inset-0 z-[60] flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-in fade-in duration-200"
      role="dialog"
      aria-modal="true"
      aria-labelledby="profile-modal-title"
      onClick={(ev) => {
        if (ev.target === ev.currentTarget) onClose();
      }}
    >
      <div
        className="bg-white rounded-2xl shadow-xl shadow-gray-100 border border-gray-100 w-full max-w-lg max-h-[90vh] overflow-y-auto animate-in zoom-in-95 duration-200"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between px-6 py-4 border-b border-gray-100">
          <h2 id="profile-modal-title" className="text-lg font-bold text-gray-900 flex items-center gap-2">
            <UserIcon className="w-5 h-5 text-blue-600" />
            Profile
          </h2>
          <button
            type="button"
            onClick={onClose}
            className="p-2 rounded-lg text-gray-400 hover:text-gray-700 hover:bg-gray-100 transition-colors"
            aria-label="Close"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {loading ? (
          <div className="flex justify-center py-16">
            <Loader2 className="w-8 h-8 text-blue-600 animate-spin" />
          </div>
        ) : profile ? (
          <form onSubmit={handleSave} className="p-6 space-y-6">
            <div className="flex flex-col items-center gap-3">
              <div className="relative">
                <div className="w-24 h-24 rounded-full bg-blue-50 border border-blue-100 flex items-center justify-center overflow-hidden">
                  {displayAvatarSrc ? (
                    <img src={displayAvatarSrc} alt="" className="w-full h-full object-cover" />
                  ) : (
                    <UserIcon className="w-10 h-10 text-blue-600" />
                  )}
                </div>
                {editing && (
                  <label className="absolute bottom-0 right-0 p-2 rounded-full bg-blue-600 text-white shadow-md cursor-pointer hover:bg-blue-700 transition-colors">
                    <Camera className="w-4 h-4" />
                    <input type="file" accept="image/*" className="hidden" onChange={handleAvatarFile} />
                  </label>
                )}
              </div>
              {editing && (avatarBase64 || profile.avatarBase64) && !avatarRemoved && (
                <button
                  type="button"
                  onClick={clearAvatar}
                  className="text-xs text-red-600 hover:underline"
                >
                  Remove photo
                </button>
              )}
              <p className="text-sm font-bold text-gray-900 text-center">
                {[profile.firstname, profile.lastname].filter(Boolean).join(' ') || profile.username}
              </p>
              <p className="text-xs font-medium text-gray-400 uppercase tracking-wider text-center max-w-full truncate">
                {profile.role ?? '—'}
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700">First name</label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <UserCircle className="h-5 w-5 text-gray-400" />
                  </div>
                  {editing ? (
                    <input
                      name="firstname"
                      value={form.firstname}
                      onChange={handleChange}
                      className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                    />
                  ) : (
                    <p className="pl-10 py-2 text-sm text-gray-900">{profile.firstname || '—'}</p>
                  )}
                </div>
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700">Last name</label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <UserCircle className="h-5 w-5 text-gray-400" />
                  </div>
                  {editing ? (
                    <input
                      name="lastname"
                      value={form.lastname}
                      onChange={handleChange}
                      className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                    />
                  ) : (
                    <p className="pl-10 py-2 text-sm text-gray-900">{profile.lastname || '—'}</p>
                  )}
                </div>
              </div>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">Username</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <UserIcon className="h-5 w-5 text-gray-400" />
                </div>
                <p className="pl-10 py-2 text-sm text-gray-600">{profile.username}</p>
              </div>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-gray-700">Email</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Mail className="h-5 w-5 text-gray-400" />
                </div>
                {editing ? (
                  <input
                    name="email"
                    type="email"
                    value={form.email}
                    onChange={handleChange}
                    className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                  />
                ) : (
                  <p className="pl-10 py-2 text-sm text-gray-900">{profile.email || '—'}</p>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700 flex items-center gap-1">
                  <ShieldCheck className="w-4 h-4 text-gray-400" />
                  Role
                </label>
                <p className="text-sm text-gray-600 py-2">{profile.role || '—'}</p>
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700">Account status</label>
                <p className="text-sm text-gray-600 py-2">
                  {profile.enabled === false ? 'Disabled' : 'Active'}
                </p>
              </div>
            </div>

            {/* Password Change Section */}
            <div className="border-t border-gray-100 pt-4">
              {!showPasswordChange ? (
                <button
                  type="button"
                  onClick={() => setShowPasswordChange(true)}
                  className="text-sm text-blue-600 hover:text-blue-700 font-medium flex items-center gap-2"
                >
                  <Lock className="w-4 h-4" />
                  Change Password
                </button>
              ) : (
                <div className="space-y-4">
                  <div className="space-y-3">
                    <div className="space-y-2">
                      <label className="text-sm font-medium text-gray-700">Current Password</label>
                      <div className="relative">
                        <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                          <Lock className="h-5 w-5 text-gray-400" />
                        </div>
                        <input
                          name="oldPassword"
                          type="password"
                          value={passwordForm.oldPassword}
                          onChange={handlePasswordChange}
                          onKeyDown={handlePasswordKeyDown}
                          required
                          className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                      </div>
                    </div>
                    <div className="space-y-2">
                      <label className="text-sm font-medium text-gray-700">New Password</label>
                      <div className="relative">
                        <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                          <Lock className="h-5 w-5 text-gray-400" />
                        </div>
                        <input
                          name="newPassword"
                          type="password"
                          value={passwordForm.newPassword}
                          onChange={handlePasswordChange}
                          onKeyDown={handlePasswordKeyDown}
                          required
                          minLength={6}
                          className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                      </div>
                    </div>
                    <div className="space-y-2">
                      <label className="text-sm font-medium text-gray-700">Confirm New Password</label>
                      <div className="relative">
                        <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                          <Lock className="h-5 w-5 text-gray-400" />
                        </div>
                        <input
                          name="confirmPassword"
                          type="password"
                          value={passwordForm.confirmPassword}
                          onChange={handlePasswordChange}
                          onKeyDown={handlePasswordKeyDown}
                          required
                          minLength={6}
                          className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="flex gap-2">
                    <button
                      type="button"
                      onClick={() => {
                        setShowPasswordChange(false);
                        setPasswordForm({
                          oldPassword: '',
                          newPassword: '',
                          confirmPassword: '',
                        });
                      }}
                      className="px-3 py-1.5 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
                    >
                      Cancel
                    </button>
                    <button
                      type="button"
                      onClick={() => void submitPasswordChange()}
                      disabled={changingPassword}
                      className="px-3 py-1.5 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
                    >
                      {changingPassword ? <Loader2 className="w-4 h-4 animate-spin inline" /> : null}
                      Change Password
                    </button>
                  </div>
                </div>
              )}
            </div>

            <div className="flex flex-wrap gap-3 justify-end pt-2 border-t border-gray-100">
              {!editing ? (
                <>
                  <button
                    type="button"
                    onClick={onClose}
                    className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
                  >
                    Close
                  </button>
                  <button
                    type="button"
                    onClick={(e) => {
                      e.preventDefault();
                      setEditing(true);
                      setForm({
                        firstname: profile.firstname ?? '',
                        lastname: profile.lastname ?? '',
                        email: profile.email ?? '',
                      });
                      setAvatarBase64(profile.avatarBase64 ?? null);
                      setAvatarRemoved(false);
                    }}
                    className="inline-flex items-center gap-2 px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors"
                  >
                    <Pencil className="w-4 h-4" />
                    Edit
                  </button>
                </>
              ) : (
                <>
                  <button
                    type="button"
                    onClick={() => {
                      setEditing(false);
                      setForm({
                        firstname: profile.firstname ?? '',
                        lastname: profile.lastname ?? '',
                        email: profile.email ?? '',
                      });
                      setAvatarBase64(profile.avatarBase64 ?? null);
                      setAvatarRemoved(false);
                    }}
                    className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    disabled={saving}
                    className="inline-flex items-center gap-2 px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
                  >
                    {saving ? <Loader2 className="w-4 h-4 animate-spin" /> : null}
                    Save
                  </button>
                </>
              )}
            </div>
          </form>
        ) : (
          <p className="p-8 text-center text-gray-500">No profile data</p>
        )}
      </div>
    </div>
  );
};

export default UserProfileModal;
