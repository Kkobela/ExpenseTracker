import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

export const register = async (userData: { username: string; email: string; password: string; }) => {
  return axios.post(API_URL + 'register', userData);
};

export const login = async (loginData: { username: string; password: string; }) => {
  return axios.post(API_URL + 'login', loginData);
};
