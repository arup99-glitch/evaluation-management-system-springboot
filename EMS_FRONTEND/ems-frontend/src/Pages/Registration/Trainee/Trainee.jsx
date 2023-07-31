import React,{useState,useEffect} from 'react';
import { Form, Button, Container, Row, Col, Toast } from 'react-bootstrap';
import axios from 'axios';
import "./Trainee.css";

const Trainee = () => {
  const token = localStorage.getItem('token');
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        fullName: '',
        degreeName: '',
        educationalInstitute: '',
        cgpa: '',
        passingYear: '',
        presentAddress: '',
        profilePicture: '',
        designation: '',
        contactNumber: '',
        dateOfBirth: '',
        gender: '',
      });
      const [showSuccessMessage, setShowSuccessMessage] = useState(false);
    
      const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({
          ...prevFormData,
          [name]: value,
        }));
      };
      useEffect(() => {
        if (showSuccessMessage) {
          const timer = setTimeout(() => {
            setShowSuccessMessage(false);
          }, 3000); // Auto hide after 3 seconds
    
          return () => {
            clearTimeout(timer);
          };
        }
      }, [showSuccessMessage]);
    
      const handleSubmit = (event) => {
        event.preventDefault();
        axios.post('http://localhost:8088/register/trainee', formData,{
          headers : {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
            setShowSuccessMessage(true); // Show success message
          })
          .catch((error) => {
            console.error(error); // Handle error
          });
      };
    return (
        <div className='traineeCreate'>
             <Toast
        show={showSuccessMessage}
        onClose={() => setShowSuccessMessage(false)}
        delay={3000} // Auto hide after 3 seconds
        autohide
        style={{ position: 'absolute', top: 20, right: 20 }} // Customize position as needed
      >
        <Toast.Header>
          <strong className="mr-auto">Success</strong>
        </Toast.Header>
        <Toast.Body>
          Trainee Created Successfully!
        </Toast.Body>
      </Toast>
        <div className="trainee-registration">
      <h4 className="text-center">Trainee Registration</h4>
      <Container className='traineeForm'>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={6}>
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
              <Form.Group controlId="dateOfBirth">
                <Form.Label>Date of Birth</Form.Label>
                <Form.Control
                    type="date"
                    name="dateOfBirth"
                    value={formData.dateOfBirth}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
              <Form.Group controlId="educationalInstitute">
                <Form.Label>University</Form.Label>
                <Form.Control
                  type="text"
                  name="educationalInstitute"
                  value={formData.educationalInstitute}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
              <Form.Group controlId="degreeName">
                <Form.Label>Degree</Form.Label>
                <Form.Control
                  type="tesxt"
                  name="degreeName"
                  value={formData.degreeName}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
              {/* First column */}
              <Form.Group controlId="cgpa">
                <Form.Label>CGPA</Form.Label>
                <Form.Control
                  type="number"
                  name="cgpa"
                  value={formData.cgpa}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group controlId="passingYear">
                <Form.Label>Passing Year</Form.Label>
                <Form.Control
                    type="date"
                    name="passingYear"
                    value={formData.passingYear}
                    onChange={handleChange}
                    required
                />
              </Form.Group>

             
            </Col>

            <Col md={6}>
              {/* Second column */}
              <Form.Group controlId="gender">
                <Form.Label>Gender</Form.Label>
                <Form.Control
                  type="text"
                  name="gender"
                  value={formData.gender}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group controlId="contactNumber">
                <Form.Label>Contact Number</Form.Label>
                <Form.Control
                  type="text"
                  name="contactNumber"
                  value={formData.contactNumber}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
              <Form.Group controlId="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="text"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
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
              </Form.Group>
              <Form.Group controlId="presentAddress">
                <Form.Label>Address</Form.Label>
                <Form.Control
                  type="text"
                  name="presentAddress"
                  value={formData.presentAddress}
                  onChange={handleChange}
                  required
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

          <Button className='traineeButton' variant="success" type="submit">
            Register
          </Button>
        </Form>
      </Container>
    </div>
    </div>
    );
};

export default Trainee;
