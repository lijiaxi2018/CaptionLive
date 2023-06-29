import React from 'react';
import { useSelector } from 'react-redux';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import Worksheet from '../../components/Project/Worksheet';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery, useGetOrganizationProjectsQuery } from '../../services/organization';
import { useParams } from 'react-router';

function Projects() {
  const organizationId = useParams().id;
  const myUserId = useSelector((state) => state.userAuth.userId);

  const organizationData = useGetOrganizationQuery(organizationId);
  const orgProjectsResults = useGetOrganizationProjectsQuery(organizationId);

  const fetched = (orgProjectsResults.isFetching || organizationData.isFetching) ? false : true;

  return (
    <div>
      { fetched &&
        <div className='general-page-container'>
          <Header title={organizationData.data.data.name} icon = {VscOrganization} />
          <Sidebarlvl2 />
          
          { myUserId !== -1 &&
            <div className='general-page-container-reduced'>
              {orgProjectsResults.data.data.map((project) =>
                <div key={project.projectId}>
                  <Worksheet data={project}/>
                </div>
              )}
            </div>
          }

        </div>
      }
    </div>  
  );
}

export default Projects;