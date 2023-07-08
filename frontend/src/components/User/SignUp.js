import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch } from 'react-redux'
import { useSignUpUserMutation } from '../../services/auth';
import { toggleSignInOnWindow, toggleSignInOnPage } from '../../redux/layoutSlice'
import { sha256 } from 'js-sha256';

const isValidPasswordChar = str => {
  const regex = /^[~`!@#$%^&*()_+=[\]\{}|;':",.\/<>?a-zA-Z0-9-]+$/;
  return regex.test(str)
};

const formReducer = (state, event) => {
  if (event.reset) {
    return {
      username: "",
      password: "",
      confirm: "",
      qq: "",
      email: "",
      nickname: "",
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
  const [signUpUserMutation] = useSignUpUserMutation() // RTK Query

  const [submitting, setSubmitting] = useState(false); // If is currently submitting the from
  
  const [formData, setFormData] = useReducer(formReducer, {
    username: "",
    password: "",
    confirm: "",
    qq: "",
    email: "",
    nickname: "",
  });

  const [filled, setFilled] = useState(false); // If all required fields are fulfilled
  useEffect(() => {
    setFilled(formData.username.length > 0 && formData.password.length > 0 && formData.confirm.length > 0 && formData.nickname.length > 0)
  }, [formData.username, formData.password, formData.confirm, formData.nickname]);

  const [matching, setMatching] = useState(true); // If password matches with confirm
  useEffect(() => {
    setMatching(formData.password === formData.confirm)
  }, [formData.password, formData.confirm]);

  const [validUsername, setValidUsername] = useState(false);
  useEffect(() => {
    setValidUsername(isValidPasswordChar(formData.username));
  }, [formData.username]);

  const [prompt, setPrompt] = useState(""); // Prompt message
  useEffect(() => {
    if (!submitting) {
      let message = '';
      if (!filled) {
        message = message + '请填写所有必填信息; ';
      } 
      
      if (!matching) {
        message = message + '请输入相同密码; ';
      }

      if (!validUsername) {
        message = message + '用户名只能由英文字母与数字组成; ';
      }

      setPrompt(message);
    }
  }, [filled, matching, submitting, validUsername]);

  const handleChange = event => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    });
  }

  const userSignUp = () => {
    const encrypted = sha256(formData.password);
    signUpUserMutation({ 
      permission: 0,
      username: formData.username,
      password: encrypted,
      qq: formData.qq,
      email: formData.email,
      nickname: formData.nickname,
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
    if (filled && matching && validUsername) {
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
      <input name="nickname" className="sign-in-up-input" placeholder="请输入昵称" onChange={handleChange} value={formData.nickname}/>
      <label className="star-mark">*</label>
      <br/>

      <input type="password" name="password" className="sign-in-up-input" placeholder="请输入密码" onChange={handleChange} value={formData.password}/>
      <label className="star-mark">*</label>
      <input type="password" name="confirm" className="sign-in-up-input" placeholder="请确认密码" onChange={handleChange} value={formData.confirm}/>
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