import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Sidebar from './components/Layout/Sidebar/Sidebar';
import MyHome from './pages/MyHome';
import MyOrganizations from './pages/MyOrganizations';
import MyProjects from './pages/MyProjects';
import AllOrganizations from './pages/AllOrganizations';
import Mail from './pages/Mail';

function App() {
  return (
    <>
      <Router>
        <Sidebar />
        <Routes>
          <Route path="/" element={<></>}/>
          <Route path="/myhome" element={<MyHome/>}/>
          <Route path="/myorganizations" element={<MyOrganizations/>}/>
          <Route path="/myprojects" element={<MyProjects/>}/>
          <Route path="/allorganizations" element={<AllOrganizations/>}/>
          <Route path="/mail" element={<Mail/>}/>
        </Routes>
      </Router>
    </>
  )  
}

export default App;