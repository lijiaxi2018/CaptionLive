import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import AddOrganization from '../../components/Organization/AddOrganization';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import { openAddOrganization } from '../../redux/layoutSlice';
import { useGetAllOrganizationsQuery } from '../../services/organization';
import { ImSphere } from 'react-icons/im';

function AllOrganizations() {
  const dispatch = useDispatch();

  const isOpenAddOrganization = useSelector((state) => state.layout.inAddOrganization);
  const myUserId = useSelector((state) => state.userAuth.userId);

  const allOrganizationData = useGetAllOrganizationsQuery();

  return (
    <div className='general-page-container'>

      <Header title="所有字幕组" icon = {ImSphere} />

      <SignInUpContainer />

      { isOpenAddOrganization &&
        <AddOrganization />
      }

      <div className='general-page-container-reduced'>
      { (myUserId !== -1 && !allOrganizationData.isFetching) &&
        <div>
          <div className='general-row-align'>
            <button className='general-button-grey' style={{"marginLeft" : "auto"}} onClick={() => dispatch(openAddOrganization())}>新建字幕组</button>
          </div>

          <div>
            {allOrganizationData.data.data.map((organization) =>
              <div key={organization.organizationId}>
                <EntityInfo userId = {organization.organizationId} type={1} access={organization.leaderIds.includes(myUserId)} apply={!organization.memberIds.includes(myUserId)} applicant={myUserId}/>
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