import React from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';

function Glossaries() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;

  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <SignInUpContainer />
        <Sidebarlvl2 
          prefix={`/myorganizations/${organizationId}/`}
          data={myorgnizationSideBar}
          type='myorgnization'
        />
        
        <div className='general-page-container-reduced'>
          <h1>Glossaries</h1>
        </div>
      
      </div>
    </div>  
  );
}

export default Glossaries;