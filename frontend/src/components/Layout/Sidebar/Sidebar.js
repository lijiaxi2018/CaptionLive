import { useEffect, useState } from 'react';
import { Icon } from '@rsuite/icons';
import { ImSphere } from 'react-icons/im';
import { AiOutlineHome, AiOutlineMail, AiOutlineProject } from 'react-icons/ai';
import { VscOrganization } from 'react-icons/vsc';
// import { Link } from 'react-router-dom'
// import { SidebarData } from './SidebarData';
import "rsuite/dist/rsuite.css"
import './Sidebar.css';
import { IconContext } from 'react-icons';
import { Sidenav, Nav, Toggle } from 'rsuite';
import { useGetOrganizationsByUserQuery } from '../../../services/user';
import { useSelector, useDispatch } from 'react-redux';
import { updateSelectedOrgId } from '../../../redux/layoutSlice';
import SidebarElement from './SidebarElement';

function Sidebar() {
  const dispatch = useDispatch()
  const [expanded, setExpanded] = useState(false);
  const [activeKey, setActiveKey] = useState('1');
  const [myorganizations, setMyorganizations] = useState([]);
  const myUserId = useSelector((state) => state.userAuth.userId);
  // const organizations = useGetOrganizationsByUserQuery(myUserId);

  const organizations= useGetOrganizationsByUserQuery(myUserId, {
    refetchOnMountOrArgChange: true,
  });

  useEffect(() => {
    /** user login or logout will change myUserId */
  }, [myUserId])
  
  useEffect(() => {
    if (organizations.isLoading) {
      /** it is fetching or loading data, simply wait */
      setMyorganizations([]);
    } else if (!organizations.isSuccess) {
      /** has error */
      // console.log(`error: ${organizations.isError}`);
      // console.log(organizations.error);
      // console.log(organizations);
      setMyorganizations([]);
    } else {
      const message = organizations.data.message;
      if (message === 'success') {
        setMyorganizations(organizations.data.data);
      } else {
        setMyorganizations([]);
      }
      
    }
  }, [organizations])
  
  const handleSelectOrgId = (newId) => {
    // console.log(`set orgId ${newId}`);
    dispatch(updateSelectedOrgId(newId));
  }

  return (
    <div className='sidebar'>
      <IconContext.Provider value={{ color: '#4c4747' }}>
        <Sidenav expanded={expanded}>
          <Sidenav.Toggle style={{'paddingLeft':'19px'}} onToggle={expanded => setExpanded(expanded)} />
          <Sidenav.Body>
            <Nav activeKey={activeKey} onSelect={setActiveKey}>
              
              <Nav.Item href='/myhome' eventKey="1" icon={<Icon as={AiOutlineHome} size="1em"/>}>
                个人信息
              </Nav.Item>

              <Nav.Menu eventKey="2" title="相关群组" icon={<Icon as={VscOrganization} size="1em"/>}>
                {myorganizations.map((org) => {
                  const id = org.organizationId
                  return (
                    <SidebarElement 
                      key={id}
                      id={id}
                      eventKey={'2-1'}
                      href={`/myorganizations/${id}`}
                      name={org.name}
                      onClick={() => handleSelectOrgId(id)}
                    />
                  )
                })}
              </Nav.Menu>
              
              <Nav.Item href='/myprojects/accessibleprojects' eventKey="3" icon={<Icon as={AiOutlineProject} size="1em"/>}>
                项目一览
              </Nav.Item>        

              <Nav.Item href='/allorganizations' eventKey="4" icon={<Icon as={ImSphere} size="1em"/>}>
                群组一览
              </Nav.Item>
              
              <Nav.Item href='/mail/allmails' eventKey="5" icon={<Icon as={AiOutlineMail} size="1em"/>}>
                消息
              </Nav.Item>

            </Nav>
          </Sidenav.Body>
        </Sidenav>
      </IconContext.Provider>
    </div>
  );
}

export default Sidebar