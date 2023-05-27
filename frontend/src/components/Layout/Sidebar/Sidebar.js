import React, { useState } from 'react';
import * as FaIcons from 'react-icons/fa';
import * as ImIcons from 'react-icons/im';
import * as AiIcons from 'react-icons/ai';
import * as VscIcons from 'react-icons/vsc';
import { Link } from 'react-router-dom'
import { SidebarData } from './SidebarData';
import './Sidebar.css';
import { IconContext } from 'react-icons';
import { Sidenav, Nav } from 'rsuite';


function Sidebar() {
  const [sidebar, setSidebar] = useState(false); //whether sidebar is visibe

  const showSidebar = () => setSidebar(!sidebar); //open or close sidebar

  return (
    <>
      <IconContext.Provider value={{ color: '#fff' }}>
        <div className='navbar'>
          <Link to='#' className='menu-bars'>
            <FaIcons.FaBars onClick={showSidebar} />
          </Link>
        </div>
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
          <ul className='nav-menu-items' >
            <li className='navbar-toggle'>
              <Link to='#' className='menu-bars' onClick={showSidebar}>
                <AiIcons.AiOutlineClose />
              </Link>
            </li>
            <li className='sidebar-lvl-1'>
              <Sidenav >
                <Sidenav.Body>
                  <Nav activeKey="1">
                    <Nav.Item href='/myhome' eventKey="1" icon={<AiIcons.AiOutlineHome />}>
                      我的主页
                    </Nav.Item>
                    <Nav.Menu eventKey="2" title="我的字幕组" icon={<VscIcons.VscOrganization />}>
                      <Nav.Item href='/myorganizations' eventKey="2-1">烤兔摊字幕组</Nav.Item>
                      <Nav.Item href='/myorganizations' eventKey="2-2">烤鸭摊字幕组</Nav.Item>
                      <Nav.Item href='/myorganizations' eventKey="2-3">烤鱼摊字幕组</Nav.Item>
                      <Nav.Item href='/myorganizations' eventKey="2-4">烤鸡摊字幕组</Nav.Item>
                    </Nav.Menu>
                    <Nav.Item href='/myprojects' eventKey="3" icon={<AiIcons.AiOutlineProject />}>
                      我的项目
                    </Nav.Item>
                    <Nav.Item href='/allorganizations' eventKey="4" icon={<ImIcons.ImSphere />}>
                      所有字幕组
                    </Nav.Item>
                    <Nav.Item href='/mail' eventKey="5" icon={<AiIcons.AiOutlineMail />}>
                      信箱
                    </Nav.Item>
                  </Nav>
                </Sidenav.Body>
              </Sidenav>
            </li>
          </ul>
            
        </nav>
      </IconContext.Provider>
    </>
  );
}

export default Sidebar