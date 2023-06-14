import React from 'react';
import Segment from './Segment';
import GlobalSegment from './GlobalSegment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';

function Worksheet() {
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);

  const globalSegment = orgProjectsResults.isFetching ? null : orgProjectsResults.data.data[0].taskInfos;
  const segments = orgProjectsResults.isFetching ? null : orgProjectsResults.data.data[0].segmentInfos;

  console.log(segments);
  
  return (
    <div>
      { !orgProjectsResults.isFetching &&
      <div>
        <GlobalSegment data={globalSegment}/>
        {segments.map((segments) =>
          <div key={segments.segmentId}>
            <Segment data={segments}/>
          </div>
        )}
      </div>
      }
    </div>

    
  );
}

export default Worksheet;