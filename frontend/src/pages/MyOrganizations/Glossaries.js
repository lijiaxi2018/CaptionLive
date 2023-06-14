import React, { useState } from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useGetAllGlossariesQuery } from '../../services/glossary';
import { useParams } from 'react-router';
import GlossariesTable from './GlossariesTable';
import GlossariesForm from './GlossariesForm';


function Glossaries() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;

  const glossariesData = useGetAllGlossariesQuery(organizationId, {
    refetchOnMountOrArgChange: true,
  })
  // console.log(glossariesData)
  const glossaries = glossariesData.isFetching ? [] : glossariesData.data.data;
  // console.log(glossaries);

  const [formOpen, setFormOpen] = useState(false); // whether open the form for add new glossary
  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <Sidebarlvl2 />
        
        <div className='general-page-container-reduced'>
          <h1>Glossaries</h1>
          <button onClick={() => setFormOpen(!formOpen)}>add</button>
          {formOpen && (<GlossariesForm organizationId={organizationId}/>)}
          <GlossariesTable
            glossaries={glossaries} 
          />
        </div>
      
      </div>
    </div>  
  );
}

export default Glossaries;