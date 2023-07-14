import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux'
import SignInUpContainer from '../../components/User/SignInUpContainer';
import AddOrganization from '../../components/Organization/AddOrganization';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import Prompt from '../../components/InfoCard/Prompt';
import { openAddOrganization } from '../../redux/layoutSlice';
import { useGetAllOrganization } from '../../api/organization';
import { ImSphere } from 'react-icons/im';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';

function AllOrganizations() {
  function filterByKeyword(keyword, organization) {
    if (keyword === "" || organization.name.includes(keyword)) {
      return (<EntityInfo userId = {organization.organizationId} type={1} access={organization.leaderIds.includes(myUserId)} apply={!organization.memberIds.includes(myUserId)} applicant={myUserId}/>);
    }
  }

  const dispatch = useDispatch();

  const isOpenAddOrganization = useSelector((state) => state.layout.inAddOrganization);
  const myUserId = useSelector((state) => state.userAuth.userId);
  const language = useSelector((state) => state.layout.language);

  const [fetched, allOrganizationData] = useGetAllOrganization();

  const [keyword, setKeyword] = useState("");

  return (
    <div>
      <div className="header-title">
        <Icon as={ImSphere} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{languagedata[language]['allOrganizations']}</label>
      </div>

      <div className='general-page-container'>
        <SignInUpContainer />

        <Prompt />

        { isOpenAddOrganization &&
          <AddOrganization />
        }

        <div className='general-page-container-reduced'>
        { (myUserId !== -1 && fetched) &&
          <div>

            <div className='general-row-align'>
              <input name="keyword" className="general-search-bar" 
                placeholder={languagedata[language]['searchProject']}
                onChange={(e) => setKeyword(e.target.value)}
              />
              <button className='general-button-grey' onClick={() => dispatch(openAddOrganization())}>
                {languagedata[language]['newOrganization']}
              </button>
            </div>

            <div>
              {allOrganizationData.map((organization) =>
                <div key={organization.organizationId}>
                  {filterByKeyword(keyword, organization)}
                </div>
              )}
            </div>
          </div>
        }
        </div>
      </div>
    </div>
  );
}

export default AllOrganizations;