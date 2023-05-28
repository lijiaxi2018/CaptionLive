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
                <Nav.Item href='/myorganizations' eventKey="2-2">
                  <a href='/myorganizations'>烤鸭摊字幕组</a>
                </Nav.Item>
                <Nav.Item href='/myorganizations' eventKey="2-3">
                  <a href='/myorganizations'>烤鱼摊字幕组</a>
                </Nav.Item>
                <Nav.Item href='/myorganizations' eventKey="2-4">
                  <a href='/myorganizations'>烤鸡摊字幕组</a>
                </Nav.Item>
              </Nav.Menu>

              <Nav.Menu eventKey="3" title="我的项目" icon={<Icon as={AiOutlineProject} size="1em"/>}>
                <Nav.Item href='/myprojects/committedprojects' eventKey="3-1">
                  <a href='/myprojects/committedprojects'>参与的项目</a>
                </Nav.Item>
                <Nav.Item href='/myprojects/accessibleprojects' eventKey="3-2">
                  <a href='/myprojects/accessibleprojects'>可访问的项目</a>
                </Nav.Item>
                <Nav.Item href='/myprojects/allprojects' eventKey="3-3">
                  <a href='/myprojects/allprojects'>所有项目</a>
                </Nav.Item>
              </Nav.Menu>

              <Nav.Item href='/allorganizations' eventKey="4" icon={<Icon as={ImSphere} size="1em"/>}>
                所有字幕组
              </Nav.Item>

              <Nav.Menu eventKey="5" title="信箱" icon={<Icon as={AiOutlineMail} size="1em"/>}>
                <Nav.Item href='/mail/allmails' eventKey="5-1">
                  <a href='/mail/allmails'>所有</a>
                </Nav.Item>
                <Nav.Item href='/mail/unreadmails' eventKey="5-2">
                  <a href='/mail/unreadmails'>未读</a>
                </Nav.Item>
                <Nav.Item href='/mail/sentmails' eventKey="5-3">
                  <a href='/mail/sentmails'>已发送</a>
                </Nav.Item>
              </Nav.Menu>

            </Nav>
          </Sidenav.Body>
        </Sidenav>
      </IconContext.Provider>
    </div>
  );
}

export default Sidebar