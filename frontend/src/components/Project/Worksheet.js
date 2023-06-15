import React from 'react';
import Segment from './Segment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';

function Worksheet() {
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);
  const segments = orgProjectsResults.isFetching ? null : orgProjectsResults.data.data[0].segmentInfos;

  function parseSegment(segment) {
    return (<Segment data={segment}/>);
  }
  
  return (
    <div>
      { !orgProjectsResults.isFetching &&
      <div>
        {segments.map((segment) =>
          <div key={segment.segmentId}>
            {parseSegment(segment)}
          </div>
        )}
      </div>
      }
    </div>

    
  );
}

export default Worksheet;