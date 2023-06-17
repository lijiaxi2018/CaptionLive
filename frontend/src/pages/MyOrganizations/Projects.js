import React from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import Worksheet from '../../components/Project/Worksheet';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery, useGetOrganizationProjectsQuery } from '../../services/organization';
import { useParams } from 'react-router';

function Projects() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;
  
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);
  const projectData = orgProjectsResults.isFetching ? null : orgProjectsResults.data.data[0];
  
  // console.log(orgProjectsResults);

  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <Sidebarlvl2 />
        
        <div className='general-page-container-reduced'>
          { !orgProjectsResults.isFetching &&
            <Worksheet data={projectData}/>
          }
        </div>
      
      </div>
    </div>  
  );
}

export default Projects;