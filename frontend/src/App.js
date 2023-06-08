import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Sidebar from './components/Layout/Sidebar/Sidebar';

import MyHome from './pages/MyHome/MyHome'

import Projects from './pages/MyOrganizations/Projects';
import Glossaries from './pages/MyOrganizations/Glossaries';
import AboutOrganization from './pages/MyOrganizations/AboutOrganization';

import CommittedProjects from './pages/MyProjects/CommittedProjects';
import AccessibleProjects from './pages/MyProjects/AccessibleProjects';
import AllProjects from './pages/MyProjects/AllProjects';

import AllOrganizations from './pages/AllOrganizations/AllOrganizations';

import AllMails from './pages/Mail/AllMails';
import UnreadMails from './pages/Mail/UnreadMails';
import SentMails from './pages/Mail/SentMails';

function App() {
  return (
    <>
      <Router>
        <Sidebar />
        <Routes>
          <Route path="/" element={<></>}/>

          <Route path="/myhome" element={<MyHome/>}/>

          <Route path="/myorganizations/:id" element={<Projects/>}/>
          <Route path="/myorganizations/:id/projects" element={<Projects/>}/>
          <Route path="/myorganizations/:id/glossaries" element={<Glossaries/>}/>
          <Route path="/myorganizations/:id/aboutorganization" element={<AboutOrganization/>}/>

          <Route path="/myprojects" element={<CommittedProjects/>}/>
          <Route path="/myprojects/committedprojects" element={<CommittedProjects/>}/>
          <Route path="/myprojects/accessibleprojects" element={<AccessibleProjects/>}/>
          <Route path="/myprojects/allprojects" element={<AllProjects/>}/>

          <Route path="/allorganizations" element={<AllOrganizations/>}/>

          <Route path="/mail" element={<AllMails/>}/>
          <Route path="/mail/allmails" element={<AllMails/>}/>
          <Route path="/mail/unreadmails" element={<UnreadMails/>}/>
          <Route path="/mail/sentmails" element={<SentMails/>}/>
        </Routes>
      </Router>
    </>
  )  
}

export default App;