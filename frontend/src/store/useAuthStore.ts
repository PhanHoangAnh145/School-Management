import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { AuthState, AuthResponse } from '../types/auth';

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      setAuth: (response: AuthResponse) => {
        set({
          user: response.user,
          token: response.token,
          isAuthenticated: true,
        });
      },
      setUser: (user) => set({ user }),
      logout: () => {
        set({
          user: null,
          token: null,
          isAuthenticated: false,
        });
        localStorage.removeItem('auth-storage');
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);
