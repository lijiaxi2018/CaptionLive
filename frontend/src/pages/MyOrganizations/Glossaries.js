import React, { useState } from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization } from '../../api/organization';
import { useGetAllGlossaries } from '../../api/glossary';
import { useParams } from 'react-router';
import GlossariesTable from '../../components/Glossary/GlossariesTable';
import GlossaryForm from '../../components/Glossary/GlossaryForm';
import { myorgnizationSideBar } from '../../assets/sidebar';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';
import { useSelector } from 'react-redux'

function Glossaries() {
  const organizationId = useParams().id;
  
  const [organizationFetched, organizationData] = useGetOrganization(organizationId);
  const [glossaryFetched, glossaryData] = useGetAllGlossaries(organizationId);
  const fetched = (organizationFetched && glossaryFetched) ? true : false;

  const language = useSelector((state) => state.layout.language);

  const organizationName = fetched ? organizationData.name : languagedata[language]['loading'];
  
  const glossaries = fetched ? glossaryData : [];

  const [formOpen, setFormOpen] = useState(false);
  const [keyword, setKeyword] = useState("");
  return (
    <div>
      <div className="header-title">
        <Icon as={VscOrganization} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{organizationName}</label>
      </div>

      <div className='general-page-container'>
        <SignInUpContainer />
        <Sidebarlvl2 
          prefix={`/myorganizations/${organizationId}/`}
          data={myorgnizationSideBar}
          type='myorgnization'
        />
        
        <div className='general-page-container-reduced'>
          <div className='general-row-align'>
            <input name="keyword" className="general-search-bar" 
              placeholder={languagedata[language]['loading']}
              onChange={(e) => setKeyword(e.target.value)}
            />
            <button className='general-button-grey' onClick={() => setFormOpen(!formOpen)}>
              {languagedata[language]['newGlossary']}
            </button>
          </div>
          
          {formOpen && (
            <GlossaryForm 
              organizationId={organizationId}
              onClose={() => setFormOpen(false)}
            />)
          }
          <GlossariesTable glossaries={glossaries} keyword={keyword} />
        </div>
      
      </div>
    </div>  
  );
}

export default Glossaries;