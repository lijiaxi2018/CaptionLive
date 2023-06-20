import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch } from 'react-redux'
import { generalWorkflow } from '../../assets/workflows';
import { parseWorkflowCode } from '../../utils/segment';
import { useCreateSegmentMutation } from '../../services/organization';
import { closeAddSegment } from '../../redux/layoutSlice'

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      summary: "",
      start: "",
      end: "",
      workflow: 0,
    }
  } else {
    return {
      ...state,
      [event.name]: event.value
    }
  }
}

function AddSegment({projectId}) {
  const dispatch = useDispatch() // Redux
  const [createSegment] = useCreateSegmentMutation() // RTK QUery

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    summary: "",
    start: "",
    end: "",
    workflow: 0,
  });

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(formData.summary.length > 0 && formData.start.length > 0 && formData.end.length > 0)
  }, [formData.summary, formData.start, formData.end]);

  const [prompt, setPrompt] = useState(""); // Prompt message
  useEffect(() => {
    if (!submitting) {
      if (!filled) {
        setPrompt('请填写所有必填信息')
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

  const userCreateSegment = () => {
    createSegment({ 
      projectId: projectId.toString(),
      summary: formData.summary,
      scope: formData.start + ':' + formData.end,
      workflows: parseWorkflowCode(formData.workflow, generalWorkflow),
    })
    .then((response) => {
      let message = response.data.message;
      if (message === "success") {
        setPrompt("创建段落成功");
      }
    })
  }
  
  const handleComplete = event => {
    event.preventDefault();
    if (filled) {
      setSubmitting(true);

      userCreateSegment();
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      });

      for (let i = 0; i < generalWorkflow.length; i++) {
        document.getElementById(generalWorkflow[i].key).checked = false;
      }

      dispatch(closeAddSegment());
    }
  }

  const handleCancel = () => {
    dispatch(closeAddSegment());
  }

  return (
    <div className="add-segment-container">
      <p className="sign-in-up-title">新建段落</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      
      <input name="summary" className="sign-in-up-input" placeholder="请输入段落简介" onChange={handleFormChange} value={formData.summary}/>
      <label className="star-mark">*</label>
      <br/>

      <input name="start" className="sign-in-up-input" placeholder="请输入开始时间点" onChange={handleFormChange} value={formData.start}/>
      <label className="star-mark">*</label>
      <input name="end" className="sign-in-up-input" placeholder="请输入结束时间点" onChange={handleFormChange} value={formData.end}/>
      <label className="star-mark">*</label>
      <br/>

      <div style={{ marginTop: '10px' }}></div>

      <div className='general-flex-wrap'>
        {generalWorkflow.map((task) =>
          <div key={task.key} className='general-row-align'>
            <label className='general-font-tiny' style={{ marginRight: '10px' }}>{task.taskName}</label>
            <input className='general-checkbox' style={{ marginRight: '30px' }} type="checkbox" id={task.key} onChange={handleWorkflowChange}/>
          </div>
        )}
      </div>

      <div className="sign-in-up-button-list">
        <button className="general-button-red" onClick={handleCancel}>取消</button>
        <button className="general-button-green" onClick={handleComplete}>完成</button>
      </div>
      
    </div>
  )
}

export default AddSegment;