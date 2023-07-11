import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import { useAddOrganizationMutation } from '../../services/organization';
import { closeAddOrganization } from '../../redux/layoutSlice';
import { languagedata } from '../../assets/language';

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      name: "",
      description: "",
    }
  } else {
    return {
      ...state,
      [event.name]: event.value
    }
  }
}

function AddOrganization() {
  const dispatch = useDispatch() // Redux

  const [addOrganizationMutation] = useAddOrganizationMutation();

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    name: "",
    description: "",
  });

  const language = useSelector((state) => state.layout.language);

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

  const addOrganization = (orgName, orgDescription) => {
    addOrganizationMutation({
      name: orgName,
      description: orgDescription,
      avatarId: 0,
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

      addOrganization(formData.name, formData.description);
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      });

      dispatch(closeAddOrganization());
    }
  }

  const handleCancel = () => {
    dispatch(closeAddOrganization());
  }

  return (
    <div className="add-organization-container">
      <p className="sign-in-up-title">{languagedata[language]['newOrganization']}</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      
      <input name="name" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillOrganizationName']} 
        onChange={handleFormChange} value={formData.summary}
      />
      <label className="star-mark">*</label>
      <br/>

      <textarea 
        name="description" 
        style={{ 'resize': 'none' }} 
        className='general-input-multiple-lines-long' 
        placeholder={languagedata[language]['pleaseFillOrganizationIntroduction']}
        onChange={handleFormChange} 
        value={formData.description}
      >
      </textarea>

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

export default AddOrganization;