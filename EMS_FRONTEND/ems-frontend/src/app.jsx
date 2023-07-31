import { Route, Routes, useParams } from 'react-router-dom'
import AllBatch from './Pages/Batch/AllBatches/AllBatch'
import BatchCreate from './Pages/Batch/BatchCreate/BatchCreate'
import Header from './Pages/Header/Header'
import UserLogin from './Pages/Login/UserLogin'
import Trainee from './Pages/Registration/Trainee/Trainee'
import Trainer from './Pages/Registration/Trainer/Trainer'
import './app.css'
import Home from './Pages/Home/Home'
import Batch from './Pages/Batch/Batch/Batch'
import TaskCreate from './Pages/Task/TaskCreate'
import ViewTasks from './Pages/Task/ViewTasks'
import TaskEvaluation from './Pages/Evaluation/TaskEvaluation/TaskEvaluation'
import ManagerEvaluation from './Pages/Evaluation/ManagerEvaluation/ManagerEvaluation'
import CEOEvaluation from './Pages/Evaluation/CEOEvaluation/CEOEvaluation'
import TraineeProfile from './Pages/Trainee/TraineeProfile/TraineeProfile'
import ViewTrainer from './Pages/Registration/Trainer/ViewTrainer'
import ShowMarks from './Pages/Evaluation/ShowMarks/ShowMarks'
import ViewManagerEvaluationMarks from './Pages/Evaluation/ViewManagerEvaluationMarks/ViewManagerEvaluationMarks'
import ViewCEOEvaluationMarks from './Pages/Evaluation/ViewCEOEvaluationMarks/ViewCEOEvaluationMarks'
import FinalScore from './Pages/Evaluation/FinalScore/FinalScore'
import Logout from './Pages/LogOut/LogOut'
import ViewSubmission from './Pages/ViewSubmission/ViewSubmission'
import ViewFinalScore from './Pages/Evaluation/ViewFinalScore/ViewFinalScore'
import PersonalProfile from './Pages/PersonalProfile/PersonalProfile'




export function App() {
  const fullName = localStorage.getItem('fullName');
  const role = localStorage.getItem('role');
  
  const profileId = localStorage.getItem('id');
  

  
  return (
    <>
    

     <Header fullName={fullName} role={role} profileId={profileId}/>
         <Routes >
            <Route path="/login" element={<UserLogin></UserLogin>}></Route>
            <Route path="/home" element={<Home></Home>}></Route>
            <Route path="/createBatch" element={<BatchCreate></BatchCreate>}></Route>
            <Route path="/allBatch" element={<AllBatch traineeId={profileId} role={role}/>}></Route>
            <Route path="/batch/:batchId" element={<Batch role={role}/>}></Route>
            <Route path="/traineeRegister" element={<Trainee></Trainee>}></Route>
            <Route path="/trainerRegister" element={<Trainer></Trainer>}></Route>
            <Route path="/traineeProfile/:traineeId" element={<TraineeProfile></TraineeProfile>}></Route>
            <Route path="/task" element={<TaskCreate></TaskCreate>}></Route>
            <Route path="/viewTask/:batchId" element={<ViewTasks fullName={fullName} traineeId={profileId} role={role}/>}></Route>
            <Route path="/taskEvaluation" element={<TaskEvaluation />} />
            <Route path="/taskEvaluation/:batchId" element={<TaskEvaluation />} />
            <Route path="/managerEvaluation" element={<ManagerEvaluation></ManagerEvaluation>}></Route>
            <Route path="/managerEvaluation/:batchId" element={<ManagerEvaluation></ManagerEvaluation>}></Route>
            <Route path="/CEOEvaluation" element={<CEOEvaluation></CEOEvaluation>}></Route>
            <Route path="/CEOEvaluation/:batchId" element={<CEOEvaluation></CEOEvaluation>}></Route>
            <Route path="/allTrainer" element={<ViewTrainer></ViewTrainer>}></Route>
            <Route path="/showMarks" element={<ShowMarks></ShowMarks>}></Route>
            <Route path="/managerEvaluationAllMarks" element={<ViewManagerEvaluationMarks></ViewManagerEvaluationMarks>}></Route>
            <Route path="/CEOEvaluationAllMarks" element={<ViewCEOEvaluationMarks></ViewCEOEvaluationMarks>}></Route>
            <Route path="/finalScoreCreate" element={<FinalScore></FinalScore>}></Route>
            <Route path="/logout" element={<Logout></Logout>}></Route>
            <Route path="/viewSubmissions" element={<ViewSubmission></ViewSubmission>}></Route>
            <Route path="/showFinalScore" element={<ViewFinalScore></ViewFinalScore>}></Route>
            <Route path="/personalProfile" element={<PersonalProfile role={role} profileId={profileId}/>}></Route>

          </Routes>
      



    </>
  )
}

