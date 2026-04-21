export type Role = 'ROLE_ADMIN' | 'ROLE_TEACHER' | 'ROLE_STUDENT';

export type User = {
  id: number;
  username: string;
  email?: string;
  role: string;
  firstname?: string | null;
  lastname?: string | null;
  enabled?: boolean;
  /** Raw Base64 from API (entity avatar blob) */
  avatarBase64?: string | null;
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
  setUser: (user: User) => void;
  logout: () => void;
};
