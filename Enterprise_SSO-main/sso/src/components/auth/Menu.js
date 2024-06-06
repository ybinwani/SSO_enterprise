// import React, { useState } from 'react';
// import { AppBar, Toolbar, Button, Dialog, DialogActions, DialogContent, DialogTitle, Box, Typography } from '@mui/material';
// import { useNavigate } from 'react-router-dom';
// import Register from './Register';

// const AuthNavbar = ({ isLoggedIn, setIsLoggedIn }) => {
//   const navigate = useNavigate();
//   const [openDialog, setOpenDialog] = useState(false);

//   const handleDialogOpen = () => {
//     setOpenDialog(true);
//   };

//   const handleDialogClose = () => {
//     setOpenDialog(false);
//   };

//   const handleLogin = () => {
//     navigate('/login');
//   };

//   const handleLogout = () => {
//     setIsLoggedIn(false);
//     localStorage.removeItem('jwt');
//     navigate('/');
//   };

//   const handleNavigateHome = () => {
//     navigate('/');
//   };

//   return (
//     <Box sx={{ flexGrow: 1 }}>
//       <AppBar position="">
//         <Toolbar>
//           <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
//             Auth Service
//           </Typography>
//           <Button color="inherit" onClick={handleNavigateHome}>
//             Home
//           </Button>
//           {!isLoggedIn && (
//             <Button
//               color="inherit"
//               onClick={handleDialogOpen}
//               sx={{ backgroundColor: '#3f51b5', color: 'white', marginRight: 1 }}
//             >
//               Register
//             </Button>
//           )}
//           {!isLoggedIn ? (
//             <Button
//               color="inherit"
//               onClick={handleLogin}
//               sx={{ backgroundColor: '#4caf50', color: 'white' }}
//             >
//               Login
//             </Button>
//           ) : (
//             <Button
//               color="inherit"
//               onClick={handleLogout}
//               sx={{ backgroundColor: '#f44336', color: 'white' }}
//             >
//               Logout
//             </Button>
//           )}
//         </Toolbar>
//       </AppBar>
//       <Dialog
//         open={openDialog}
//         onClose={handleDialogClose}
//         maxWidth="xs"
//         fullWidth
//         PaperProps={{
//           sx: {
//             overflow: 'hidden',
//             borderRadius: '10px'
//           }
//         }}
//       >
//         <DialogTitle>Register</DialogTitle>
//         <DialogContent>
//           <Register onClose={handleDialogClose} />
//         </DialogContent>
//         <DialogActions>
//           <Button onClick={handleDialogClose} color="primary">
//             Close
//           </Button>
//         </DialogActions>
//       </Dialog>
//     </Box>
//   );
// };

// export default AuthNavbar;
import React, { useState } from 'react';
import { AppBar, Toolbar, Button, Dialog, DialogActions, DialogContent, DialogTitle, Box, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import Register from './Register';

const AuthNavbar = ({ isLoggedIn, setIsLoggedIn }) => {
  const navigate = useNavigate();
  const [openDialog, setOpenDialog] = useState(false);

  const handleDialogOpen = () => {
    setOpenDialog(true);
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
  };

  const handleLogin = () => {
    navigate('/login');
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    localStorage.removeItem('jwt');
    navigate('/');
  };

  const handleNavigateHome = () => {
    navigate('/');
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed">
        <Toolbar sx={{ backgroundColor: 'white' }}> {/* Update background color here */}
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, color: 'blue' }}> {/* Update color here */}
            Instructor portal
          </Typography>
          <Button color="inherit" onClick={handleNavigateHome} sx={{ mr: 1 }}>
            Home
          </Button>
          {!isLoggedIn && (
            <Button
              color="inherit"
              onClick={handleDialogOpen}
              sx={{ bgcolor: "white", color: 'blue', mr: 1 }}
            >
              Register
            </Button>
          )}
          {!isLoggedIn ? (
            <Button
              color="inherit"
              onClick={handleLogin}
              sx={{ bgcolor: "white", color: 'blue' }}
            >
              Login
            </Button>
          ) : (
            <Button
              color="inherit"
              onClick={handleLogout}
              sx={{ bgcolor: "white", color: 'blue' }}
            >
              Logout
            </Button>
          )}
        </Toolbar>
      </AppBar>
      <Dialog
        open={openDialog}
        onClose={handleDialogClose}
        maxWidth="xs"
        fullWidth
        PaperProps={{
          sx: {
            overflow: 'hidden',
            borderRadius: '10px'
          }
        }}
      >
        <DialogTitle>Register</DialogTitle>
        <DialogContent>
          <Register onClose={handleDialogClose} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default AuthNavbar;
