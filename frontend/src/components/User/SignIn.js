import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch } from 'react-redux'
import { useLoginUserMutation } from '../../services/auth';
import { updateUserId, updateAccessToken } from '../../redux/userSlice';

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      username: "",
      password: "",
    }
  } else {
    return {
      ...state,
      [event.name]: event.value
    }
  }
}

function SignIn() {
  const dispatch = useDispatch() // Redux
  const [loginUser] = useLoginUserMutation() // RTK QUery

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    username: "",
    password: "",
  });

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(formData.username.length > 0 && formData.password.length > 0)
  }, [formData.username, formData.password]);

  const [prompt, setPrompt] = useState(""); // Prompt message
  useEffect(() => {
    if (!submitting) {
      if (filled) {
        setPrompt('')
      } else {
        setPrompt('请填写所有必填信息')
      }
    }
  }, [filled, submitting]);

  const handleChange = event => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    });
  }

  const userLogin = () => {
    loginUser({
      username: formData.username,
      password: formData.password,
    })
    .then((response) => {
      let message = response.data.message
      if (message === "success") {
        setPrompt("登录成功")
        // dispatch(updateUserId(0))
        dispatch(updateAccessToken(response.data.data.token))
      } else if (message.startsWith("invalid")) {
        setPrompt("密码错误")
      } else if (message.startsWith("User")) {
        setPrompt("用户不存在")
      }
    })
  }
  
  const handleSubmit = event => {
    event.preventDefault();
    if (filled) {
      setSubmitting(true);

      userLogin()
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      })
    }
  }

  return (
    <div className="sign-in">
      <p className="sign-in-title">登陆</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      <input name="username" className="sign-in-input" placeholder="请输入用户名" onChange={handleChange} value={formData.username}/>
      <label className="star-mark">*</label>
      <br/>

      <input name="password" className="sign-in-input" placeholder="请输入密码" onChange={handleChange} value={formData.password}/>
      <label className="star-mark">*</label>
      <br/>

      <div className="sign-in-button-list">
        <button style={{ 'backgroundColor': '#ff6765' }} className="sign-in-button">取消</button>
        <button style={{ 'backgroundColor': '#7f7f7f' }} className="sign-in-button">注册</button>
        <button style={{ 'backgroundColor': '#5bc96d' }} className="sign-in-button" onClick={handleSubmit}>登陆</button>
      </div>
    </div>
  )
}

export default SignIn;