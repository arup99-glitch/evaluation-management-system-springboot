import React from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import './Header.css';

import { Link, useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';
import { HashLink } from 'react-router-hash-link';

function Header({ fullName, role ,profileId}) {
 
  const navigate = useNavigate();

  const handleLogout = () => {
    navigate('/logout');
  };

  return (
    <Navbar collapseOnSelect expand="lg" className="border bg-red navBar">
      <Container>
        <Navbar.Brand as={HashLink} to="/home">
          EMS
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
          {role === 'Admin' || role === 'Trainer' || role ==='Trainee' ? (
              <>
          <Nav.Link className="text-black" as={HashLink} to="/allBatch">
                  All Batch
           </Nav.Link>
          <Nav.Link className="text-black" as={HashLink} to="/allTrainer">
                  Trainer
          </Nav.Link>
          <NavDropdown title="View Marks" id="collasible-nav-dropdown" className="header">
              <NavDropdown.Item href="#action/3.1"><Nav.Link className="text-black" as={HashLink} to="/showMarks">
                  Daily Task
                </Nav.Link>
                </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">  <Nav.Link className="text-black" as={HashLink} to="/managerEvaluationAllMarks">
                  Manager Evaluation
                </Nav.Link>
                </NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">  <Nav.Link className="text-black" as={HashLink} to="/CEOEvaluationAllMarks">
                  CEO Evaluation
                </Nav.Link>
                </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4"> <Nav.Link className="text-black" as={HashLink} to="/showFinalScore">
                  Final Score 
                </Nav.Link></NavDropdown.Item>
            </NavDropdown>
            </>
            ) : null}
          
            <Link href="#"></Link>
            {role === 'Admin' || role === 'Trainer' ? (
              <>
                
               
                <Nav.Link className="text-black" as={HashLink} to="/createBatch">
                  Batch Create
                </Nav.Link>
                <Nav.Link className="text-black" as={HashLink} to="/task">
                  Task Create
                </Nav.Link>
             
                <NavDropdown title="Evaluation" id="collasible-nav-dropdown" className="header">
              <NavDropdown.Item href="#action/3.1"><Nav.Link className="text-black" as={HashLink} to="/taskEvaluation">
                  Task Evaluation
                </Nav.Link>
                </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">  <Nav.Link className="text-black" as={HashLink} to="/managerEvaluation">
                  Manager Evaluation
                </Nav.Link>
                </NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">  <Nav.Link className="text-black" as={HashLink} to="/CEOEvaluation">
                  CEO Evaluation
                </Nav.Link>
                </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4"> <Nav.Link className="text-black" as={HashLink} to="/finalScoreCreate">
                  Final Score Create
                </Nav.Link></NavDropdown.Item>
            </NavDropdown>
              </>
            ) : null}
           
          </Nav>
          <Nav>
          {role === 'Admin' || role==='Trainer' ? (
              <>
                 <NavDropdown title="Register" id="collasible-nav-dropdown" className="header">
              <NavDropdown.Item href="#action/3.1"><Nav.Link className="text-black" as={HashLink} to="/traineeRegister">
                  Trainee
                </Nav.Link>
                <Nav.Link className="text-black" as={HashLink} to="/trainerRegister">
                  Trainer
                </Nav.Link>
                </NavDropdown.Item>
              
              
              
            </NavDropdown>
              </>
            ) : null}
             {role === 'Admin' || role === 'Trainer' || role ==='Trainee' ? (
              <>
           
            <Nav.Link href="#deets">{fullName}</Nav.Link>
            <NavDropdown title={role} id="collasible-nav-dropdown" className="header">
            <NavDropdown.Item as={HashLink} to="/personalProfile">Profile</NavDropdown.Item>
            
              {/* <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item> */}
              <NavDropdown.Divider />
              
              <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
            </NavDropdown>
            </>
             ): null}
          </Nav>
          
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;