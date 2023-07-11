import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { globalWorkflow } from '../../assets/workflows';
import { parseWorkflowCode } from '../../utils/segment';
import { useAddProjectMutation } from '../../services/organization';
import { closeAddProject } from '../../redux/layoutSlice';
import { languagedata } from '../../assets/language';

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      name: "",
      isVideo: true,
      workflow: 0,
    }
  } else {
    return {
      ...state,
      [event.name]: event.value
    }
  }
}

function AddProject() {
  const dispatch = useDispatch() // Redux

  const [addProjectMutation] = useAddProjectMutation();

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    name: "",
    isVideo: true,
    workflow: 0,
  });

  const language = useSelector((state) => state.layout.language)

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(formData.name.length > 0)
  }, [formData.name]);

  const [prompt, setPrompt] = useState(""); // Prompt message
  useEffect(() => {
    if (!submitting) {
      if (!filled) {
        setPrompt(languagedata[language]['pleaseFillNecessaryInformation'])
      } else {
        setPrompt('')
      }
    }
  }, [filled, submitting]);

  const handleFormChange = event => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    });
  }

  const addProject = () => {
    addProjectMutation({
      name: formData.name,
      type: formData.isVideo ? "AUDIO_AND_VIDEO" : "TXT",
      workflows: parseWorkflowCode(formData.workflow, globalWorkflow),
    })
    .then((secondResponse) => {
      let message = secondResponse.data.message;
      if (message === "success") {
        setPrompt(languagedata[language]['successfullyCreated']);
      } else {
        setPrompt(languagedata[language]['unknownError']);
      }
    })
  }
  
  const handleComplete = event => {
    event.preventDefault();
    if (filled) {
      setSubmitting(true);

      addProject();
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      });

      for (let i = 0; i < globalWorkflow.length; i++) {
        document.getElementById(globalWorkflow[i].key).checked = false;
      }

      dispatch(closeAddProject());
    }
  }

  const handleCancel = () => {
    dispatch(closeAddProject());
  }

  const handleWorkflowChange = (e) => {
    if (e.target.checked) {
      setFormData({
        name: 'workflow',
        value: formData.workflow + 2 ** parseInt(e.target.id),
      });
    } else {
      setFormData({
        name: 'workflow',
        value: formData.workflow - 2 ** parseInt(e.target.id),
      });
    }
  }

  const handleVideoChecked = () => {
    setFormData({
      name: 'isVideo',
      value: true,
    });
    document.getElementById("text-checkbox").checked = false;
  }

  const handleTextChecked = () => {
    setFormData({
      name: 'isVideo',
      value: false,
    });
    document.getElementById("video-checkbox").checked = false;
  }

  return (
    <div className="add-project-container">
      <p className="sign-in-up-title">{languagedata[language]['newProject']}</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>

      <div className='general-row-align'>
        <input name="name" className="sign-in-up-input" 
          placeholder={languagedata[language]['pleaseFillProjectName']} 
          onChange={handleFormChange} value={formData.name}
        />
        <label className="star-mark">*</label>

        <div className='general-flex-wrap' style={{ marginLeft: '20px' }}>
          <div className='general-row-align'>
            <label className='general-font-tiny' style={{ marginRight: '10px' }}>
              {languagedata[language]['audioVideo']}
            </label>
            <input className='general-checkbox' style={{ marginRight: '30px' }} type="checkbox" id="video-checkbox" checked={formData.isVideo} onChange={handleVideoChecked}/>

            <label className='general-font-tiny' style={{ marginRight: '10px' }}>
              {languagedata[language]['text']}
            </label>
            <input className='general-checkbox' style={{ marginRight: '30px' }} type="checkbox" id="text-checkbox" checked={!formData.isVideo} onChange={handleTextChecked}/>
          </div>
          <label className="star-mark-global-task-checkbox">*</label>
        </div>
      </div>

      <div className='general-flex-wrap' style={{ marginTop: '10px' }}>
        {globalWorkflow.map((task) =>
          <div key={task.key} className='general-row-align'>
            <label className='general-font-tiny' style={{ marginRight: '10px' }}>{task.taskName}</label>
            <input className='general-checkbox' style={{ marginRight: '30px' }} type="checkbox" id={task.key} onChange={handleWorkflowChange}/>
          </div>
        )}
      </div>

      <div className="sign-in-up-button-list">
        <button className="general-button-red" onClick={handleCancel}>
          {languagedata[language]['cancel']}
        </button>
        <button className="general-button-green" onClick={handleComplete}>
          {languagedata[language]['complete']}
        </button>
      </div>
      
    </div>
  )
}

export default AddProject;