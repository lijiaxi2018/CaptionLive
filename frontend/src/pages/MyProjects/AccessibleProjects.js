import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Worksheet from '../../components/Project/Worksheet';
import AddProject from '../../components/Project/AddProject';
import { AiOutlineProject } from 'react-icons/ai';
import { useGetAccessibleProjects } from '../../api/organization';
import { openAddProject } from '../../redux/layoutSlice';
import { myprojectSideBar } from '../../assets/sidebar';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';


function AccessibleProjects() {
  function filterByKeyword(keyword, project) {
    if (keyword === "" || project.name.includes(keyword)) {
      return (<Worksheet data={project}/>);
    }
  }

  const dispatch = useDispatch();

  const myUserId = useSelector((state) => state.userAuth.userId);

  const isOpenAddProject = useSelector((state) => state.layout.inAddProject);

  const [fetched, myAccessibleProjects] = useGetAccessibleProjects(myUserId);

  const [keyword, setKeyword] = useState("");

  return (
    <div className='general-page-container'>
      <Header title="所有项目" icon = {AiOutlineProject} />
      
      <SignInUpContainer />

      { isOpenAddProject &&
        <AddProject />
      }
      <Sidebarlvl2 
        prefix={`/myprojects/`}
        data={myprojectSideBar}
        type='myproject'
      />
      { fetched && myUserId !== -1 &&
        <div className='general-page-container-reduced'>

          <div className='general-row-align'>
            <input name="keyword" className="general-search-bar" placeholder="搜索项目" onChange={(e) => setKeyword(e.target.value)}/>
            <button className='general-button-grey' onClick={() => dispatch(openAddProject())}>新建项目</button>
          </div>

          <div style={{'marginTop' : '40px'}}></div>

          {myAccessibleProjects.map((project) =>
            <div key={project.projectId}>
              {filterByKeyword(keyword, project)}
            </div>
          )}
        </div>
      }
      
    </div>
  );
}

export default AccessibleProjects;