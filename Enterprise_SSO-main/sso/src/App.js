import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Register from './components/auth/Register';
import PortalSelection from './components/PortalSelection/PortalSelection';
import Login from './components/auth/Login'; // Updated to use the new AuthLogin component
import EmployeeLogin from './components/Employee/EmployeeLogin'; // Updated to use the new EmployeeLogin component
import EmployeePortal from './components/Employee/EmployeePortal';
import SalaryPortal from './components/Salary/SalaryPortal';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import './App.css';

import ResponsiveNavbar from './components/auth/Menu';
import HomePage from './components/auth/Home';
import SalaryLogin from './components/Salary/salarylogin';


const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

const App = () => {
  const [token, setToken] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <ResponsiveNavbar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
        <div className="App">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/portal-selection" element={<PortalSelection />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login setToken={setToken} setIsLoggedIn={setIsLoggedIn} />} />
            <Route path="/employee/login" element={<EmployeeLogin setIsLoggedIn={setIsLoggedIn} />} />
            <Route path="/employee" element={<EmployeePortal setIsLoggedIn={setIsLoggedIn} isLoggedIn={isLoggedIn} />} />
            <Route path="/salary/login" element={<SalaryLogin setIsLoggedIn={setIsLoggedIn} />} />
        <Route path="/salary" element={<SalaryPortal setIsLoggedIn={setIsLoggedIn} />} />
   
          </Routes>
        </div>
      </Router>
    </ThemeProvider>
  );
};

export default App;
