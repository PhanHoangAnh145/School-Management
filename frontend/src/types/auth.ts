export type Role = 'ROLE_ADMIN' | 'ROLE_TEACHER' | 'ROLE_STUDENT';

export type User = {
  id: number;
  username: string;
  email: string;
  role: string; // Keep it string to be flexible with multiple roles if needed, or just use Role
};

export type AuthResponse = {
  token: string;
  user: User;
};

export type AuthState = {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  setAuth: (response: AuthResponse) => void;
  logout: () => void;
};
