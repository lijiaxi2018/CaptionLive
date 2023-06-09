import React from 'react';
import Segment from './Segment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';

function Worksheet() {
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);

  const segmentData = orgProjectsResults.isFetching ? {} : orgProjectsResults.data.data[0].segmentInfos[0];
  console.log(segmentData);

  // const orgProjectsFetched = 
  //   orgId === -1 ? false : 
  //   orgProjectsResults.isFetching ? false : 
  //   true;
  
  return (
    <div>
      { segmentData !== {} &&
        <Segment data={segmentData}/>
      }
    </div>

    
  );
}

export default Worksheet;