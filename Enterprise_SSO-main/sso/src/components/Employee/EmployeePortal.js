import React, { useState } from 'react';
import { Box, Typography, Button, Container, TextField, Paper } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import EmployeeNavbar from './EmployeeNavbar';
import { EMPLOYEE_TOKEN_KEY } from "../../config.js";

const EmployeePortal = ({ setIsLoggedIn, isLoggedIn }) => {
  const [employeeDetails, setEmployeeDetails] = useState(null);
  const [username, setUsername] = useState('');
  const [experience, setExperience] = useState('');
  const [department, setDepartment] = useState('');
  const [message, setMessage] = useState('');
  const token = localStorage.getItem(EMPLOYEE_TOKEN_KEY);
  const navigate = useNavigate();

  const fetchEmployeeDetails = async () => {
    try {
      const response = await axios.post(
        'http://localhost:8081/employee/details',
        { username },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setEmployeeDetails(response.data);
      setMessage('');
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setMessage('Employee not found');
      } else {
        setMessage('Error fetching employee details');
      }
    }
  };

  const registerEmployee = async () => {
    try {
      await axios.post(
        'http://localhost:8081/employee/register',
        { username, experience, department },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage('Employee registered successfully');
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setMessage('Employee already registered');
      } else {
        setMessage('Registration error');
      }
    }
  };

  const redirectToSalary = () => {
    navigate('/salary');
  };

  const handleLogout = async () => {
    if (!token) {
      localLogout();
      return;
    }

    try {
      await axios.post(
        'http://localhost:8081/employee/logout',
        {},
        {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      );
    } catch (error) {
      console.error('Error logging out from API:', error);
    } finally {
      localLogout();
    }
  };

  const localLogout = () => {
    localStorage.removeItem(EMPLOYEE_TOKEN_KEY);
    setIsLoggedIn(false);
    navigate('/portal-selection');
  };

  return (
    <Container component="main" maxWidth="md">
      <EmployeeNavbar isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
      <Paper elevation={6} sx={{ padding: 2 }}>
        <Typography component="h1" variant="h5">Instructor Portal</Typography>
        
        <Typography variant="h6" sx={{ mt: 2 }}>Register Instructor</Typography>
        <Box sx={{ mt: 2 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="Experience"
            value={experience}
            onChange={(e) => setExperience(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="Department"
            value={department}
            onChange={(e) => setDepartment(e.target.value)}
          />
          <Button onClick={registerEmployee} fullWidth variant="contained" color="primary" sx={{ mt: 2 }}>
            Register Instructor
          </Button>
          {message && <Typography color="error" variant="body2">{message}</Typography>}
        </Box>

        <Typography variant="h6" sx={{ mt: 2 }}>Get Instructor Details</Typography>
        <Box sx={{ mt: 2 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <Button onClick={fetchEmployeeDetails} fullWidth variant="contained" color="primary" sx={{ mt: 2 }}>
            Get Instructor Details
          </Button>
        </Box>

        {employeeDetails && (
          <Box sx={{ mt: 2 }}>
            <Typography variant="h6">Details:</Typography>
            <Typography>First Name: {employeeDetails.username}</Typography>
            <Typography>Experience: {employeeDetails.experience}</Typography>
            <Typography>Department: {employeeDetails.department}</Typography>
            {/* <Button onClick={redirectToSalary} fullWidth variant="contained" color="secondary" sx={{ mt: 2 }}>
              Go to Salary Portal
            </Button> */}
            {/* <Button
              onClick={handleLogout}
              fullWidth variant="contained" color="primary" sx={{ mt: 2 }}
            >
              Logout
            </Button> */}
          </Box>
        )}
      </Paper>
    </Container>
  );
};

export default EmployeePortal;
