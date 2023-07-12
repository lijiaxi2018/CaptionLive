import React, { useState } from 'react';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { VscOrganization } from 'react-icons/vsc';
import { useGetOrganization } from '../../api/organization';
import { useGetAllGlossaries } from '../../api/glossary';
import { useParams } from 'react-router';
import GlossariesTable from '../../components/Glossary/GlossariesTable';
import GlossaryForm from '../../components/Glossary/GlossaryForm';
import { myorgnizationSideBar } from '../../assets/sidebar';

function Glossaries() {
  const organizationId = useParams().id;
  
  const [organizationFetched, organizationData] = useGetOrganization(organizationId);
  const [glossaryFetched, glossaryData] = useGetAllGlossaries(organizationId);
  const fetched = (organizationFetched && glossaryFetched) ? true : false;

  const organizationName = fetched ? organizationData.name : "获取中...";
  
  const glossaries = fetched ? glossaryData : [];

  const [formOpen, setFormOpen] = useState(false);
  const [keyword, setKeyword] = useState("");
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
          <div className='general-row-align'>
            <input name="keyword" className="general-search-bar" placeholder="搜索词汇" onChange={(e) => setKeyword(e.target.value)}/>
            <button className='general-button-grey' onClick={() => setFormOpen(!formOpen)}>新增词汇</button>
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