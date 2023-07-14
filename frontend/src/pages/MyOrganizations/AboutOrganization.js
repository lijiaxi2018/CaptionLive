import React from 'react';
import { useSelector } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization } from '../../api/organization';
import { useParams } from 'react-router';
import { myorgnizationSideBar } from '../../assets/sidebar';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';

function AboutOrganization() {
  const currentUserId = useSelector((state) => state.userAuth.userId);

  const organizationId = useParams().id;

  const [fetched, organizationData] = useGetOrganization(organizationId);

  const myUserId = useSelector((state) => state.userAuth.userId);
  const language = useSelector((state) => state.layout.language);

  const organizationName = fetched ? organizationData.name : languagedata[language]['loading'];
  
  
  return (
    <div>
      <div className="header-title">
        <Icon as={VscOrganization} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{organizationName}</label>
      </div>

      <div className='general-page-container'>
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