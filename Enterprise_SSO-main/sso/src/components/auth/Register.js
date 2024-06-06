import React, { useState } from 'react';
import { TextField, Button, Box, Typography, Snackbar, CircularProgress, Alert as MuiAlert } from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { keyframes } from '@emotion/react';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const Register = ({ onClose }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [Name,setName]=useState('');
  const [loading, setLoading] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const navigate = useNavigate();

  const handleRegister = async () => {
    setLoading(true);

    try {
      const response = await axios.post('http://localhost:8080/auth/register', {
        "name":Name,
        username,
        password,
        
      
      });

      if (response.status === 200) {
        setSnackbarMessage('Registration successful. Redirecting to login...');
        setSnackbarSeverity('success');
        setSnackbarOpen(true);
        setTimeout(() => {
          onClose(); // Close the dialog
          navigate('/');
        }, 2000);
      } else {
        setSnackbarMessage('Registration failed. Please try again.');
        setSnackbarSeverity('error');
        setSnackbarOpen(true);
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || 'An error occurred. Please try again.';
      setSnackbarMessage(errorMessage);
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    } finally {
      setLoading(false);
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  return (
    <Box sx={{ p: 3, animation: `${fadeIn} 0.5s` }}>
      <Typography variant="h5" gutterBottom>
        Register
      </Typography>
      <TextField
        label="name"
        value={Name}
        onChange={(e) => setName(e.target.value)}
        fullWidth
        margin="normal"
      />
       <TextField
        label="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        fullWidth
        margin="normal"
      />
   
      <TextField
        label="Password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        fullWidth
        margin="normal"
      />
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
        <Button
          variant="contained"
          color="primary"
          onClick={handleRegister}
          sx={{ backgroundColor: '#3f51b5', color: 'white', flexGrow: 1, mr: 1 }}
          disabled={loading}
        >
          {loading ? <CircularProgress size={24} sx={{ color: 'white' }} /> : 'Register'}
        </Button>
        <Button
          variant="outlined"
          color="secondary"
          onClick={onClose}
          sx={{ flexGrow: 1, ml: 1 }}
        >
          Cancel
        </Button>
      </Box>
      <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleCloseSnackbar}>
        <MuiAlert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarSeverity === 'success' ? <CheckCircleOutlineIcon sx={{ mr: 1 }} /> : <ErrorOutlineIcon sx={{ mr: 1 }} />}
          {snackbarMessage}
        </MuiAlert>
      </Snackbar>
    </Box>
  );
};

export default Register;
