import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'
import { SidebarData } from './SidebarData';
import { IconContext } from 'react-icons';
import { Sidenav, Nav } from 'rsuite';
import SidebarElement from './SidebarElement';
import { useSelector, useDispatch } from 'react-redux';
import { updateSelectedLvl2Id } from '../../../redux/layoutSlice';
import "rsuite/dist/rsuite.css"
import './Sidebarlvl2.css';

const Sidebarlvl2 = ({
    prefix,
    type,
    data=[]
}) => {  
    const dispatch = useDispatch()
    const selectedOrgId = useSelector((state) => state.layout.selectedOrgId);
    const selectedLvl2Id = useSelector((state) => state.layout.selectedLvl2Id);
    // console.log(data);
    // const data = [
    //     {path:'projects', name:'工作表'},
    //     {path:'glossaries', name:'词汇表'},
    //     {path:'aboutorganization', name:'关于'}
    // ]

    const handleSelectLvl2 = (newId) => {
        dispatch(updateSelectedLvl2Id(newId));
    }

    return (
      <div className='sidebar-lvl-2' >
        <IconContext.Provider value={{ color: '#fff' }}>
                <Sidenav id='sidenav-out'>
                    <Sidenav.Body id='sidenav-body'>
                        <Nav>
                            {data.map((obj, key) => {
                                // console.log(key);
                                // console.log(obj);
                                if (key === selectedLvl2Id) {
                                    /** current tab is selected */
                                    return (
                                        <Nav.Item 
                                            key={key} 
                                            href={`${prefix}${obj.path}`} 
                                            eventKey={`${key}`}
                                            id='sidebar-item-lvl2'
                                            className='selected-lvl2'
                                            onClick={() => handleSelectLvl2(key)}
                                            // style={{'background-color': '#ffffff'}}
                                        >
                                            {`| ${obj.name}`}
                                        </Nav.Item>
                                    );
                                } else {
                                    return (
                                        <Nav.Item
                                            key={key} 
                                            href={`${prefix}${obj.path}`} 
                                            eventKey={`${key}`}
                                            id='sidebar-item-lvl2'
                                            // className='sidebar-item-lvl2'
                                            onClick={() => handleSelectLvl2(key)}
                                            // style={{'background-color': '#ffffff'}}
                                        >
                                            {obj.name}
                                        </Nav.Item>
                                    );
                                }
                                
                            })}
                        </Nav>
                    </Sidenav.Body>
                </Sidenav>
        </IconContext.Provider>
      </div>
    );
  }
  
  export default Sidebarlvl2;