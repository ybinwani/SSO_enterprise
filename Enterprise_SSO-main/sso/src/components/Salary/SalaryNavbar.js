import React from 'react';
import { AppBar, Toolbar, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const SalaryNavbar = ({ isLoggedIn, handleLogout }) => {
  const navigate = useNavigate();

  return (
    <AppBar position="static">
      <Toolbar>
        <Button color="inherit" onClick={() => navigate('/salary')}>
          Home
        </Button>
        {isLoggedIn ? (
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
        ) : (
          <Button color="inherit" onClick={() => navigate('/salary/login')}>
            Login
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default SalaryNavbar;
