import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Toast } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './UserLogin.css';

const UserLogin = () => {
  const token = localStorage.getItem('token');
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    axios
      .post('http://localhost:8088/user/login', formData)
      .then((response) => {
        console.log( response.data);
        // Extract specific user information from the response
        const { email, id,role, authority } = response.data.user;
        
        // Store specific user information in localStorage (if needed)
        localStorage.setItem('userId',id);
        
        localStorage.setItem('email', email);
        localStorage.setItem('role', role);
        console.log(localStorage.getItem('userId'));
        localStorage.setItem('authority', authority);
        if (role === 'Trainee') {
          const {fullName,id} = response.data.trainee;
          localStorage.setItem('id', id);
          localStorage.setItem('fullName', fullName);
        }
        if (role === 'Trainer') {
          const {fullName,id} = response.data.trainer;
          localStorage.setItem('id', id);
          localStorage.setItem('fullName', fullName);
        }
        if (role === 'Admin') {
          const {fullName,id} = response.data.admin;
          localStorage.setItem('id', id);
          localStorage.setItem('fullName', fullName);
        }
        
       
        // Set the JWT token in an HttpOnly cookie
        const token = response.data.token;
        console.log(token);
        window.localStorage.setItem('token',token);
        
       setShowSuccessMessage(true); // Show success message
        setShowErrorMessage(false); // Hide error message

        navigate('/home');
        // Redirect the user to the home page after successful login
      })
      .catch((error) => {
        setShowErrorMessage(true); // Show error message
        setShowSuccessMessage(false); // Hide success message
        // Handle error if login fails
      });
      
      

  };

  return (
    <div className='loginPatrent'>
      <Toast
        show={showErrorMessage}
        onClose={() => setShowErrorMessage(false)}
        delay={3000}
        autohide
        bg="danger"
        style={{ position: 'absolute', bottom: 20, right: 20 }}
      >
        <Toast.Header>
          <strong className="mr-auto">Error</strong>
        </Toast.Header>
        <Toast.Body>
          Login Failed. Please check your email and password.
        </Toast.Body>
      </Toast>
      <div className="login">
        <Container className='loginForm'>
          <Row className="justify-content-center">
            <Col xs={12} md={6}>
              <h5 className="text-center mb-4">BJIT Academy</h5>
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="email">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="text"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />
                </Form.Group>

                <Form.Group controlId="password">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                  />
                </Form.Group>

                <Button className="loginButton" variant="success" type="submit">
                  Login
                </Button>
              </Form>
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
};

export default UserLogin;