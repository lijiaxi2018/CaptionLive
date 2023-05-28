import React, { useState } from 'react';
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
                            <Nav.Item href='/myorganizations/projects' eventKey="1">
                                工作表
                            </Nav.Item>
                            <Nav.Item href='/myorganizations/glossaries' eventKey="2">
                                词汇表
                            </Nav.Item>
                            <Nav.Item href='/myorganizations/aboutorganization' eventKey="3">
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