import React from 'react';
import { useSelector } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import Worksheet from '../../components/Project/Worksheet';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization, useGetOrganizationProjects } from '../../api/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';

function Projects() {
  const organizationId = useParams().id;
  const myUserId = useSelector((state) => state.userAuth.userId);

  const [organizationFetched, organizationData] = useGetOrganization(organizationId);
  const [projectsFetched, orgProjectsResults] = useGetOrganizationProjects(organizationId);

  const fetched = (organizationFetched && projectsFetched) ? true : false;

  return (
    <div>
      { fetched &&
        <div className='general-page-container'>
          <Header title={organizationData.name} icon = {VscOrganization} />
          <Sidebarlvl2 
            prefix={`/myorganizations/${organizationId}/`}
            data={myorgnizationSideBar}
            type='myorgnization'
          />
          
          { myUserId !== -1 &&
            <div className='general-page-container-reduced'>
              {orgProjectsResults.map((project) =>
                <div key={project.projectId}>
                  <Worksheet data={project}/>
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