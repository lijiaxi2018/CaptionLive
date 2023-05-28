import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Sidebar from './components/Layout/Sidebar/Sidebar';

import MyHome from './pages/MyHome/MyHome';

import MyProjects from './pages/MyProjects/MyProjects';

import Projects from './pages/MyOrganizations/Projects';
import Glossaries from './pages/MyOrganizations/Glossaries';
import AboutOrganization from './pages/MyOrganizations/AboutOrganization';

import AllOrganizations from './pages/AllOrganizations/AllOrganizations';

import Mail from './pages/Mail/Mail';

function App() {
  return (
    <>
      <Router>
        <Sidebar />
        <Routes>
          <Route path="/" element={<></>}/>

          <Route path="/myhome" element={<MyHome/>}/>

          <Route path="/myorganizations" element={<Projects/>}/>
          <Route path="/myorganizations/projects" element={<Projects/>}/>
          <Route path="/myorganizations/glossaries" element={<Glossaries/>}/>
          <Route path="/myorganizations/aboutorganization" element={<AboutOrganization/>}/>

          <Route path="/myprojects" element={<MyProjects/>}/>

          <Route path="/allorganizations" element={<AllOrganizations/>}/>

          <Route path="/mail" element={<Mail/>}/>
        </Routes>
      </Router>
    </>
  )  
}

export default App;