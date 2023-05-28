import React, { useState } from 'react';
import { Icon } from '@rsuite/icons';
import { ImSphere } from 'react-icons/im';
import { AiOutlineHome, AiOutlineMail, AiOutlineProject } from 'react-icons/ai';
import { VscOrganization } from 'react-icons/vsc';
import { Link } from 'react-router-dom'
import { SidebarData } from './SidebarData';
import './Sidebar.css';
import { IconContext } from 'react-icons';
import { Sidenav, Nav, Toggle } from 'rsuite';

function Sidebar() {
  // const [sidebar, setSidebar] = useState(true); //whether sidebar is visibe
  // // const [expand, setExpand] = useState(true); //whether sidebar is expanded
  // const showSidebar = () => setSidebar(!sidebar); //open or close sidebar
  const [expanded, setExpanded] = React.useState(false);
  const [activeKey, setActiveKey] = React.useState('1');
  return (
    <div className='sidebar'>
      <IconContext.Provider value={{ color: '#4c4747' }}>
        <Sidenav expanded={expanded}>
          <Sidenav.Toggle onToggle={expanded => setExpanded(expanded)} />
          <Sidenav.Body>
            <Nav activeKey={activeKey} onSelect={setActiveKey}>
              <Nav.Item href='/myhome' eventKey="1" icon={<Icon as={AiOutlineHome} size="1em"/>}>
                我的主页
              </Nav.Item>
              <Nav.Menu eventKey="2" title="我的字幕组" icon={<Icon as={VscOrganization} size="1em"/>}>
                <Nav.Item href='/myorganizations' eventKey="2-1">
                  <a href='/myorganizations'>烤兔摊字幕组</a>
                </Nav.Item>
                <Nav.Item href='/myorganizations' eventKey="2-1">
                  <a href='/myorganizations'>烤鸭摊字幕组</a>
                </Nav.Item>
                <Nav.Item href='/myorganizations' eventKey="2-1">
                  <a href='/myorganizations'>烤鱼摊字幕组</a>
                </Nav.Item>
                <Nav.Item href='/myorganizations' eventKey="2-1">
                  <a href='/myorganizations'>烤鸡摊字幕组</a>
                </Nav.Item>
              </Nav.Menu>
              <Nav.Item href='/myprojects' eventKey="3" icon={<Icon as={AiOutlineProject} size="1em"/>}>
                我的项目
              </Nav.Item>
              <Nav.Item href='/allorganizations' eventKey="4" icon={<Icon as={ImSphere} size="1em"/>}>
                所有字幕组
              </Nav.Item>
              <Nav.Item href='/mail' eventKey="5" icon={<Icon as={AiOutlineMail} size="1em"/>}>
                信箱
              </Nav.Item>
            </Nav>
          </Sidenav.Body>
        </Sidenav>
      </IconContext.Provider>
    </div>
  );
}

export default Sidebar