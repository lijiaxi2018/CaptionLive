import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux'
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import AddOrganization from '../../components/Organization/AddOrganization';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import Prompt from '../../components/InfoCard/Prompt';
import { openAddOrganization } from '../../redux/layoutSlice';
import { useGetAllOrganization } from '../../api/organization';
import { ImSphere } from 'react-icons/im';

function AllOrganizations() {
  function filterByKeyword(keyword, organization) {
    if (keyword === "" || organization.name.includes(keyword)) {
      return (<EntityInfo userId = {organization.organizationId} type={1} access={organization.leaderIds.includes(myUserId)} apply={!organization.memberIds.includes(myUserId)} applicant={myUserId}/>);
    }
  }

  const dispatch = useDispatch();

  const isOpenAddOrganization = useSelector((state) => state.layout.inAddOrganization);
  const myUserId = useSelector((state) => state.userAuth.userId);

  const [fetched, allOrganizationData] = useGetAllOrganization();

  const [keyword, setKeyword] = useState("");

  return (
    <div className='general-page-container'>

      <Header title="所有字幕组" icon = {ImSphere} />

      <SignInUpContainer />

      <Prompt />

      { isOpenAddOrganization &&
        <AddOrganization />
      }

      <div className='general-page-container-reduced'>
      { (myUserId !== -1 && fetched) &&
        <div>

          <div className='general-row-align'>
            <input name="keyword" className="general-search-bar" placeholder="搜索项目" onChange={(e) => setKeyword(e.target.value)}/>
            <button className='general-button-grey' onClick={() => dispatch(openAddOrganization())}>新建字幕组</button>
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
  );
}

export default AllOrganizations;