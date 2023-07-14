import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Worksheet from '../../components/Project/Worksheet';
import AddProject from '../../components/Project/AddProject';
import { openAddProject } from '../../redux/layoutSlice';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization, useGetOrganizationProjects } from '../../api/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';

function Projects() {
  function filterByKeyword(keyword, project) {
    if (keyword === "" || project.name.includes(keyword)) {
      return (<Worksheet data={project}/>);
    }
  }

  const dispatch = useDispatch();

  const organizationId = useParams().id;
  const myUserId = useSelector((state) => state.userAuth.userId);
  const isOpenAddProject = useSelector((state) => state.layout.inAddProject);
  const language = useSelector((state) => state.layout.language);

  const [organizationFetched, organizationData] = useGetOrganization(organizationId);
  const [projectsFetched, orgProjectsResults] = useGetOrganizationProjects(organizationId);

  const fetched = (organizationFetched && projectsFetched) ? true : false;

  const [keyword, setKeyword] = useState("");

  return (
    <div>
      <div className="header-title">
        <Icon as={VscOrganization} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{organizationData.name}</label>
      </div>

      { fetched &&
        <div className='general-page-container'>
          <SignInUpContainer />

          { isOpenAddProject &&
            <AddProject />
          }

          <Sidebarlvl2 
            prefix={`/myorganizations/${organizationId}/`}
            data={myorgnizationSideBar}
            type='myorgnization'
          />
          
          { myUserId !== -1 &&
            <div className='general-page-container-reduced'>

              <div className='general-row-align'>
                <input name="keyword" className="general-search-bar" 
                  placeholder={languagedata[language]['searchProject']}
                  onChange={(e) => setKeyword(e.target.value)}
                />
                <button className='general-button-grey' onClick={() => dispatch(openAddProject())}>
                  {languagedata[language]['newProject']}
                </button>
              </div>

              <div style={{'marginTop' : '40px'}}></div>

              {orgProjectsResults.map((project) =>
                <div key={project.projectId}>
                  {filterByKeyword(keyword, project)}
                </div>
              )}
            </div>
          }

        </div>
      }
    </div>  
  );
}

export default Projects;