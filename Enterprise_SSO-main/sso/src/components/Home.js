import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div>
      <h2>Home</h2>
      <nav>
        <ul>
          <li><Link to="/login">Login</Link></li>
          <li><Link to="/register">Register</Link></li>
          <li><Link to="/employee/details">Employee Details</Link></li>
          <li><Link to="/employee/register">Register Employee</Link></li>
          <li><Link to="/salary/details">Salary Details</Link></li>
          <li><Link to="/salary/register">Register Salary</Link></li>
        </ul>
      </nav>
    </div>
  );
};

export default Home;
