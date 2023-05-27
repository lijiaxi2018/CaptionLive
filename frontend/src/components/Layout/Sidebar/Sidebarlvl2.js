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

function Sidebarlvl2() {
    const [sidebar, setSidebar] = useState(false); //whether sidebar is visibe
  
    const showSidebar = () => setSidebar(!sidebar); //open or close sidebar
  
    return (
      <>
        <IconContext.Provider value={{ color: '#fff' }}>
            <div style={{ width: 240 }}>
                <Sidenav>
                    <Sidenav.Body>
                        <Nav>
                            <Nav.Item href='/myorganizations' eventKey="1">
                                工作表
                            </Nav.Item>
                            <Nav.Item href='/myorganizations' eventKey="1">
                                笔记
                            </Nav.Item>
                            <Nav.Item href='/myorganizations' eventKey="1">
                                论坛
                            </Nav.Item>
                            <Nav.Item href='/myorganizations' eventKey="1">
                                词汇表
                            </Nav.Item>
                            <Nav.Item href='/myorganizations' eventKey="1">
                                关于
                            </Nav.Item>
                        </Nav>
                    </Sidenav.Body>
                </Sidenav>
            </div>
            
        </IconContext.Provider>
      </>
    );
  }
  
  export default Sidebarlvl2;