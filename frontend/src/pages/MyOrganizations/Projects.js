import React from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import Segment from '../../components/Project/Segment';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useParams } from 'react-router';

function Projects() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;

  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <Sidebarlvl2 />
        
        <div className='general-page-container-reduced'>
        <Segment segmentId={2} type={0} />
        </div>
      
      </div>
    </div>  
  );
}

export default Projects;