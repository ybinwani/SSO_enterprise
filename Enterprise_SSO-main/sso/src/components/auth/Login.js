import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, TextField, Button, Typography, Paper, Box, Snackbar } from '@mui/material';
import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { CSSTransition } from 'react-transition-group';
import MuiAlert from '@mui/material/Alert';
import { AUTH_TOKEN_KEY } from '../../config';

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const slideIn = keyframes`
  from {
    transform: translateY(-20px);
  }
  to {
    transform: translateY(0);
  }
`;

const StyledContainer = styled(Container)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(to right, #4facfe, #00f2fe);
`;

const ContentWrapper = styled(Box)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  padding-bottom: 4rem; // Space for footer
`;

const StyledPaper = styled(Paper)`
  padding: 2rem;
  animation: ${fadeIn} 1s ease-in, ${slideIn} 1s ease-in;
  border-radius: 10px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
`;

const StyledButton = styled(Button)`
  background-color: #4facfe;
  &:hover {
    background-color: #00f2fe;
  }
`;

const Footer = styled.footer`
  width: 100%;
  padding: 1rem 0;
  background-color: #f8f8f8;
  text-align: center;
  position: absolute;
  bottom: 0;
  left: 0;
`;

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const Login = ({ setToken, setIsLoggedIn }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [inProp, setInProp] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const navigate = useNavigate();

  useEffect(() => {
    setInProp(true);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/auth/login', { username, password });
      if (response.status === 200) {
        const token = response.data.jwt;
        localStorage.setItem(AUTH_TOKEN_KEY, token);
        setToken(token);
        setIsLoggedIn(true); // Store JWT token in localStorage
  
        setSnackbarMessage('Login successful. Redirecting to portal...');
        setSnackbarSeverity('success');
        setSnackbarOpen(true);
  
        setTimeout(() => {
          navigate('/portal-selection'); // Redirect to portal selection page
        }, 2000);
      } else {
        setSnackbarMessage('Login failed. Please try again.');
        setSnackbarSeverity('error');
        setSnackbarOpen(true);
      }
    } catch (error) {
      setSnackbarMessage('Login error. Please check your credentials and try again.');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  return (
    <StyledContainer component="main" maxWidth="xs">
      <ContentWrapper>
        <CSSTransition in={inProp} timeout={500} classNames="fade" onEntered={() => setInProp(true)}>
          <StyledPaper elevation={6}>
            <Typography component="h1" variant="h5" sx={{ textAlign: 'center', marginBottom: '1rem' }}>
              Auth Portal Login
            </Typography>
            <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                label="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                css={css`animation: ${fadeIn} 1s ease-in;`}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                label="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                css={css`animation: ${fadeIn} 1s ease-in;`}
              />
              <StyledButton
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                css={css`animation: ${fadeIn} 1s ease-in;`}
              >
                Login
              </StyledButton>
            </Box>
          </StyledPaper>
        </CSSTransition>
      </ContentWrapper>
      <Footer>
        <Typography variant="body2" color="textSecondary">
          © {new Date().getFullYear()} Yash Company. All rights reserved.
        </Typography>
      </Footer>
      <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleCloseSnackbar}>
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </StyledContainer>
  );
};

export default Login;
