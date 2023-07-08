import React from 'react';
import { useSelector } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization } from '../../api/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';

function AboutOrganization() {
  const currentUserId = useSelector((state) => state.userAuth.userId);

  const organizationId = useParams().id;

  const [fetched, organizationData] = useGetOrganization(organizationId);

  const organizationName = fetched ? organizationData.name : "获取中...";
  
  const myUserId = useSelector((state) => state.userAuth.userId);
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

        { (myUserId !== -1 && fetched) &&
          <div className='general-page-container-reduced'>
            <EntityInfo userId = {organizationId} type={1} access={organizationData.leaderIds.includes(currentUserId)}/>
          </div>
        }
      </div>
    </div>  
  );
}

export default AboutOrganization;