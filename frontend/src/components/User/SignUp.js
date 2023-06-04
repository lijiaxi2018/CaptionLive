import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch } from 'react-redux'
import { usePostUserMutation } from '../../services/user';
import { toggleSignInOnWindow, toggleSignInOnPage } from '../../redux/layoutSlice'

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      username: "",
      password: "",
      confirm: "",
      qq: "",
      email: "",
    }
  } else {
    return {
      ...state,
      [event.name]: event.value
    }
  }
}

function SignUp() {
  const dispatch = useDispatch() // Redux
  const [postUser] = usePostUserMutation() // RTK QUery

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    username: "",
    password: "",
    confirm: "",
    qq: "",
    email: "",
  });

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(formData.username.length > 0 && formData.password.length > 0 && formData.confirm.length > 0)
  }, [formData.username, formData.password, formData.confirm]);

  const [matching, setMatching] = useState(true); // If password matches with confirm
  useEffect(() => {
    setMatching(formData.password === formData.confirm)
  }, [formData.password, formData.confirm]);

  const [prompt, setPrompt] = useState(""); // Prompt message
  useEffect(() => {
    if (!submitting) {
      if (!filled && !matching) {
        setPrompt('请填写所有必填信息; 请输入相同密码')
      } else if (!filled) {
        setPrompt('请填写所有必填信息')
      } else if (!matching) {
        setPrompt('请输入相同密码')
      } else {
        setPrompt('')
      }
    }
  }, [filled, matching, submitting]);

  const handleChange = event => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    });
  }

  const userSignUp = () => {
    postUser({ 
      permission: 0,
      username: formData.username,
      password: formData.password,
      qq: formData.qq,
      email: formData.email,
    })
    .then((response) => {
      let message = response.data.message;
      if (message === "success") {
        setPrompt("注册成功，请登录");
      // } else if (message.startsWith("invalid")) {
      //   setPrompt("密码错误");
      // } else if (message.startsWith("User")) {
      //   setPrompt("用户不存在");
      }
    })
  }
  
  const handleSignUp = event => {
    event.preventDefault();
    if (filled && matching) {
      setSubmitting(true);

      userSignUp();
      
      setTimeout(() => {
        setSubmitting(false);
      }, 1000);

      setFormData({
        reset: true
      })
    }
  }

  const handleLoginIn = () => {
    dispatch(toggleSignInOnPage());
  }

  const handleCancel = () => {
    dispatch(toggleSignInOnWindow());
  }

  return (
    <div className="sign-up-window">
      <p className="sign-in-up-title">注册账号</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      <input name="username" className="sign-in-up-input" placeholder="请输入用户名" onChange={handleChange} value={formData.username}/>
      <label className="star-mark">*</label>
      <br/>

      <input name="password" className="sign-in-up-input" placeholder="请输入密码" onChange={handleChange} value={formData.password}/>
      <label className="star-mark">*</label>
      <input name="confirm" className="sign-in-up-input" placeholder="请确认密码" onChange={handleChange} value={formData.confirm}/>
      <label className="star-mark">*</label>
      <br/>

      <input name="qq" className="sign-in-up-input-non-required" placeholder="请输入QQ" onChange={handleChange} value={formData.qq}/>
      <input name="email" className="sign-in-up-input-non-required" placeholder="请输入邮箱" onChange={handleChange} value={formData.email}/>
      <br/>

      <div className="sign-in-up-button-list">
        <button className="general-button-grey" onClick={handleLoginIn}>前往登陆</button>
        <button className="general-button-red" onClick={handleCancel}>取消</button>
        <button className="general-button-green" onClick={handleSignUp}>注册</button>
      </div>
    </div>
  )
}

export default SignUp;