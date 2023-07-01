import React from 'react';
import { useSelector } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';

function AboutOrganization() {
  const currentUserId = useSelector((state) => state.userAuth.userId);

  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;
  
  const myUserId = useSelector((state) => state.userAuth.userId);
  console.log('myorg')
  // console.log(myorgnizationSideBar);
  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <Sidebarlvl2 
          prefix={`/myorganizations/${organizationId}/`}
          data={myorgnizationSideBar}
          type='myorgnization'
        />

        { (myUserId !== -1 && !organizationData.isFetching) &&
          <div className='general-page-container-reduced'>
            <EntityInfo userId = {organizationId} type={1} access={organizationData.data.data.leaderIds.includes(currentUserId)}/>
          </div>
        }
      </div>
    </div>  
  );
}

export default AboutOrganization;