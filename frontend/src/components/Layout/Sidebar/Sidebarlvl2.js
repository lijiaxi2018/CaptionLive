import React, { useState } from 'react';
// import * as FaIcons from 'react-icons/fa';
// import * as ImIcons from 'react-icons/im';
// import * as AiIcons from 'react-icons/ai';
// import * as VscIcons from 'react-icons/vsc';
import { Link } from 'react-router-dom'
import { SidebarData } from './SidebarData';
import './Sidebarlvl2.css';
import { IconContext } from 'react-icons';
import { Sidenav, Nav } from 'rsuite';

function Sidebarlvl2() {  
    return (
      <div className='sidebar-lvl-2' >
        <IconContext.Provider value={{ color: '#fff' }}>
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
        </IconContext.Provider>
      </div>
    );
  }
  
  export default Sidebarlvl2;