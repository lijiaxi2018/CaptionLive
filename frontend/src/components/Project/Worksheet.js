import React from 'react';
import Segment from './Segment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';

function Worksheet() {
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);

  const segmentData = orgProjectsResults.isFetching ? null : orgProjectsResults.data.data[0].segmentInfos[0];
  console.log(segmentData);
  
  return (
    <div>
      { segmentData !== null &&
        <Segment data={segmentData}/>
      }
    </div>

    
  );
}

export default Worksheet;