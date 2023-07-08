import React, { useState } from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganizationQuery } from '../../services/organization';
import { useGetAllGlossariesQuery } from '../../services/glossary';
import { useParams } from 'react-router';
import GlossariesTable from './GlossariesTable';
import GlossariesForm from './GlossariesForm';
import { myorgnizationSideBar } from '../../assets/sidebar';

function Glossaries() {
  const organizationId = useParams().id;
  const organizationData = useGetOrganizationQuery(organizationId);
  const organizationName = organizationData.isFetching ? "获取中..." 
    : organizationData.data.message.startsWith("Organization not") ? "" 
    : organizationData.data.data.name;

  const glossariesData = useGetAllGlossariesQuery(organizationId, {
    refetchOnMountOrArgChange: true,
  })
  
  const glossaries = glossariesData.isFetching ? [] : glossariesData.data.data;
  

  const [formOpen, setFormOpen] = useState(false); // whether open the form for add new glossary
  return (
    <div>
      <div className='general-page-container'>
        <Header title={organizationName} icon = {VscOrganization} />
        <SignInUpContainer />
        <Sidebarlvl2 
          prefix={`/myorganizations/${organizationId}/`}
          data={myorgnizationSideBar}
          type='myorgnization'
        />
        
        <div className='general-page-container-reduced'>
          {/* <h1>Glossaries</h1> */}
          <div className='glossary-button-group'>
            <button
              className='glossary-button-modal' 
              onClick={() => setFormOpen(!formOpen)}
            >
                新增单词
            </button>
          </div>
          
          {formOpen && (
            <GlossariesForm 
              organizationId={organizationId}
              onClose={() => setFormOpen(false)}
            />)}
          <GlossariesTable glossaries={glossaries} />
        </div>
      
      </div>
    </div>  
  );
}

export default Glossaries;