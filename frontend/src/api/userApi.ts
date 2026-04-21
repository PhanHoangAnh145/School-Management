import apiClient from './axios';

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
}

export const changePassword = async (data: ChangePasswordRequest): Promise<void> => {
  const response = await apiClient.put('/api/user/change-password', data);
  if (response.data && response.data.status === 'success') {
    return;
  }
  throw new Error(response.data?.message || 'Password change failed');
};
