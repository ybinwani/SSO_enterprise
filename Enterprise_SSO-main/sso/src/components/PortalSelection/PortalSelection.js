import React from 'react';
import { Container, Typography, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import styled from '@emotion/styled';

const StyledContainer = styled(Container)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color:#bdbdbd; /* Change background color to grey */
`;

const PortalSelection = () => {
  const navigate = useNavigate();

  return (
    <StyledContainer>
      <Typography variant="h4" gutterBottom>
        Select a Portal
      </Typography>
      <Box sx={{ display: 'flex', gap: 2, mt: 3 }}>
        <Button variant="contained" color="primary" onClick={() => navigate('/employee/login')}>
          Employee Portal
        </Button>
        {/* <Button variant="contained" color="secondary" onClick={() => navigate('/salary/login')}>
          Salary Portal
        </Button> */}
      </Box>
    </StyledContainer>
  );
};

export default PortalSelection;
