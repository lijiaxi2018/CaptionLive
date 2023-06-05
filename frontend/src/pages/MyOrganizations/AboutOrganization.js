import React from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useParams } from 'react-router';

function AboutOrganization() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;

  return (
    <div>
      <Sidebarlvl2 />
      <div className='general-page-container-reduced'>
        <Header title={organizationName} icon = {VscOrganization} />
        <h1>About Organization</h1>
      </div>
    </div>  
  );
}

export default AboutOrganization;