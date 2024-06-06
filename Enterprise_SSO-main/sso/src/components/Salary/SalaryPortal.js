import React, { useState } from 'react';
import axios from 'axios';
import { Container, TextField, Button, Typography, Paper, Box } from '@mui/material';
import './SalaryPortal.css';
import { useNavigate } from 'react-router-dom';
import SalaryNavbar from './SalaryNavbar';
import { SALARY_TOKEN_KEY } from "../../config.js";

const SalaryPortal = ({ setIsLoggedIn, isLoggedIn }) => {
  const [salaryDetails, setSalaryDetails] = useState(null);
  const [username, setUsername] = useState('');
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState('');
  const [message, setMessage] = useState('');
  const token = localStorage.getItem(SALARY_TOKEN_KEY);
  const navigate = useNavigate();

  const handleLogout = async () => {
    const token = localStorage.getItem(SALARY_TOKEN_KEY);
    if (!token) {
      localLogout();
      return;
    }
    try {
      await axios.post(
        'http://localhost:8082/salary/logout',
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
    localStorage.removeItem(SALARY_TOKEN_KEY);
    setIsLoggedIn(false);
    navigate('/portal-selection');
  };

  const fetchSalaryDetails = async () => {
    try {
      const response = await axios.post(
        'http://localhost:8082/salary/details',
        { username },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setSalaryDetails(response.data);
      setMessage('');
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setMessage('Salary details not found');
      } else {
        setMessage('Error fetching salary details');
      }
    }
  };

  const registerSalary = async () => {
    try {
      await axios.post(
        'http://localhost:8082/salary/register',
        { username, amount, currency },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage('Salary registered successfully');
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setMessage('Salary already registered');
      } else {
        setMessage('Registration error');
      }
    }
  };

  return (
    <Container component="main" maxWidth="md">
      <SalaryNavbar isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
      <Paper elevation={6} sx={{ padding: 2 }}>
        <Typography component="h1" variant="h5">Salary Portal</Typography>
        
        <Typography variant="h6" sx={{ mt: 2 }}>Register Salary</Typography>
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
            label="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="Currency"
            value={currency}
            onChange={(e) => setCurrency(e.target.value)}
          />
          <Button onClick={registerSalary} fullWidth variant="contained" color="primary" sx={{ mt: 2 }}>
            Register Salary
          </Button>
          {message && <Typography color="error" variant="body2">{message}</Typography>}
        </Box>

        <Typography variant="h6" sx={{ mt: 2 }}>Get Salary Details</Typography>
        <Box sx={{ mt: 2 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <Button onClick={fetchSalaryDetails} fullWidth variant="contained" color="primary" sx={{ mt: 2 }}>
            Get Salary Details
          </Button>
        </Box>

        {salaryDetails && (
          <Box sx={{ mt: 2 }}>
            <Typography variant="h6">Details:</Typography>
            <Typography>Amount: {salaryDetails.amount}</Typography>
            <Typography>Currency: {salaryDetails.currency}</Typography>
          </Box>
        )}
      </Paper>
    </Container>
  );
};

export default SalaryPortal;
