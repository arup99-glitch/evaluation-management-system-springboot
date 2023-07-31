import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Toast } from 'react-bootstrap';
import axios from 'axios';
import './Trainer.css';


const Trainer = () => {
  const token = localStorage.getItem('token');
    const [formData, setFormData] = useState({
        fullName: '',
        email: '',
        password: '',
        profilePicture: '',
        designation: '',
        joiningDate: '',
        yearsOfExperience: '',
        expertises: '',
        contactNumber: '',
        presentAddress: '',
      });
      const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    
      const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({
          ...prevFormData,
          [name]: value,
        }));
      };
    
      const handleSubmit = (event) => {
        event.preventDefault();
        axios.post('http://localhost:8088/register/trainer', formData,{
          headers : {
            Authorization: `Bearer ${token}`,
          },
        })
          .then((response) => {
            setShowSuccessMessage(true); // Show success message
            // Reset form data after successful submission
            setFormData({
              fullName: '',
              email: '',
              password: '',
              profilePicture: '',
              designation: '',
              joiningDate: '',
              yearsOfExperience: '',
              expertises: '',
              contactNumber: '',
              presentAddress: '',
            });
          })
          .catch((error) => {
            console.error('Error registering trainer:', error);
            // Handle error if registration fails
          });
      };
      const formatJoiningDate = (timestamp) => {
        const date = new Date(timestamp);
        return date.toDateString();
      };
    return (
        <div className='trainerCreate'>
              <Toast
        show={showSuccessMessage}
        onClose={() => setShowSuccessMessage(false)}
        delay={3000} // Auto hide after 3 seconds
        autohide
        style={{ position: 'absolute', bottom: 20, right: 20 }} // Customize position as needed
      >
        <Toast.Header>
          <strong className="mr-auto">Success</strong>
        </Toast.Header>
        <Toast.Body>
          Trainer Registered Successfully!
        </Toast.Body>
      </Toast>
             <div className="trainer-registration">
      <h5 className="text-center mb-4">Trainer Registration</h5>
      <Container className='trainerForm'>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={6}>
              {/* First column */}
              <Form.Group controlId="fullName">
                <Form.Label>Full Name</Form.Label>
                <Form.Control
                  type="text"
                  name="fullName"
                  value={formData.fullName}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group controlId="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
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

              <Form.Group controlId="contactNumber">
                <Form.Label>Contact Number</Form.Label>
                <Form.Control
                  type="number"
                  name="contactNumber"
                  value={formData.contactNumber}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
              <Form.Group controlId="presentAddress">
                <Form.Label>Present Address</Form.Label>
                <Form.Control
                  type="text"
                  name="presentAddress"
                  value={formData.presentAddress}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={6}>
              <Form.Group controlId="designation">
                <Form.Label>Designation </Form.Label>
                <Form.Control
                  type="text"
                  name="designation"
                  value={formData.designation}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="expertises">
                <Form.Label>Expertises</Form.Label>
                <Form.Control
                  type="text"
                  name="expertises"
                  value={formData.expertises}
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group controlId="joiningDate">
                <Form.Label>Joining Date</Form.Label>
                <Form.Control
                    type="date"
                    name="joiningDate"
                    value={formData.joiningDate}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
              <Form.Group controlId="yearsOfExperience">
                <Form.Label>Experience</Form.Label>
                <Form.Control
                  type="number"
                  name="yearsOfExperience"
                  value={formData.yearsOfExperience}
                  onChange={handleChange}
                />
              </Form.Group>
              {/* <Form.Group controlId="profilePicture">
                <Form.Label>Picture</Form.Label>
                <Form.Control
                  type="text"
                  name="profilePicture"
                  value={formData.profilePicture}
                  onChange={handleChange}
                  required
                />
              </Form.Group> */}



            </Col>
          </Row>

          {/* Add more Form.Group components for the rest of the properties */}
          {/* ... */}

          <Button className='traineeButton' variant="success" type="submit">
            Register
          </Button>
        </Form>
      </Container>

    </div>
        </div>
    );
};

export default Trainer;