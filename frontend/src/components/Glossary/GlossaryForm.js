import React, { useState, useReducer, useEffect } from 'react';
import { usePostGlossariesMutation } from '../../services/glossary';
import { useSelector } from 'react-redux';
import { languagedata } from '../../assets/language';

function GlossaryForm({organizationId, onClose}) {
  const [postGlossary] = usePostGlossariesMutation();

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  const language = useSelector((state) => state.layout.language);

  const formReducer = (state, event) => {
    if (event.reset) {
      return {
        organizationId: organizationId,
        source: "",
        romanization: "",
        term: "",
        explanation: "",
        remark: "",
        category: "",
      }
    } else {
      return {
        ...state,
        [event.name]: event.value
      }
    }
  }
  
  const [formData, setFormData] = useReducer(formReducer, {
    organizationId: organizationId,
    source: "",
    romanization: "",
    term: "",
    explanation: "",
    remark: "",
    category: "",
  });

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(
      formData.term.length > 0 && formData.explanation.length > 0
    )
  }, [formData.term, formData.explanation]);

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

  const addGlossary = () => {
    postGlossary(formData)
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

      addGlossary();
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      });

      onClose();
    }
  }

  return (
    <div className="form-container">
      <p className="sign-in-up-title">{languagedata[language]['newGlossary']}</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      
      <input name="term" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillOriginal']}
        onChange={handleFormChange} value={formData.term}
      />
      <label className="star-mark">*</label>
      <br/>

      <input name="explanation" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillParaphrase']} 
        onChange={handleFormChange} value={formData.explanation}
      />
      <label className="star-mark">*</label>
      <br/>

      <input name="romanization" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillRomanization']} 
        onChange={handleFormChange} value={formData.romanization}
      />
      <br/>

      <input name="source" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillSource']}
        onChange={handleFormChange} 
        value={formData.source}
      />
      <br/>

      <input name="category" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillCategory']}
        onChange={handleFormChange} value={formData.category}
      />
      <br/>

      <input name="remark" className="general-input-single-line-long" 
        placeholder={languagedata[language]['pleaseFillRemark']}
        onChange={handleFormChange} value={formData.remark}
      />
      <br/>



      <div className="sign-in-up-button-list">
        <button className="general-button-red" onClick={onClose}>
          {languagedata[language]['cancel']}
        </button>
        <button className="general-button-green" onClick={handleComplete}>
          {languagedata[language]['complete']}
        </button>
      </div>
      
    </div>
  )
}

export default GlossaryForm;