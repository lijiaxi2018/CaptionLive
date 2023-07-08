import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Worksheet from '../../components/Project/Worksheet';
import AddProject from '../../components/Project/AddProject';
import { openAddProject } from '../../redux/layoutSlice';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization, useGetOrganizationProjects } from '../../api/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';

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

  const [organizationFetched, organizationData] = useGetOrganization(organizationId);
  const [projectsFetched, orgProjectsResults] = useGetOrganizationProjects(organizationId);

  const fetched = (organizationFetched && projectsFetched) ? true : false;

  const [keyword, setKeyword] = useState("");

  return (
    <div>
      { fetched &&
        <div className='general-page-container'>
          <Header title={organizationData.name} icon = {VscOrganization} />

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
                <input name="keyword" className="general-search-bar" placeholder="搜索项目" onChange={(e) => setKeyword(e.target.value)}/>
                <button className='general-button-grey' onClick={() => dispatch(openAddProject())}>新建项目</button>
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