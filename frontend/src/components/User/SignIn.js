import React, { useState, useReducer, useEffect } from 'react';
import { useLoginUserMutation } from '../../services/auth'

// const clientSignIn = async (formData) => {
//   const response = await Axios.get(`http://localhost:8081/api/Account/SignIn`, {
//     params: {
//       username: formData.username,
//       password: formData.password
//     }
//   })
//   console.log(response)
//   console.log(response.data)
// };

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
  const [loginUser, {data, isLoading}] = useLoginUserMutation() // RTK Query

  const [submitting, setSubmitting] = useState(false);
  const [formData, setFormData] = useReducer(formReducer, {
    username: "",
    password: "",
  });

  const [filled, setFilled] = useState(false);
  useEffect(() => {
    setFilled(formData.username.length > 0 && formData.password.length > 0)
  }, [formData.username, formData.password]);

  const handleChange = event => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    });
  }
  
  const handleSubmit = event => {
    event.preventDefault();
    if (filled) {
      setSubmitting(true);
      
      loginUser({
        username: formData.username,
        password: formData.password,
      })
      .then((response) => {
        console.log('response')
        console.log(response)
      })

      setTimeout(() => {
        setSubmitting(false);
      }, 3000);

      setFormData({
        reset: true
      })
    }
  }

  return (
    <div className="sign-in">
      <p className="sign-in-title">登陆</p>

      <label style={{ color: '#ff6765' }}>{filled ? ' ' : '请填写所有必填信息'}</label><br/>
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

      { submitting &&
        <div>
          <h2>You are submitting...</h2>
          <p>Username: {formData.username}</p>
          <p>Password: {formData.password}</p>
        </div>
      }

      { /*{(!filled) &&
        <div>
          <p style={{ color: 'red' }}>请填写所有必填信息。</p>
        </div>
      } */}

      {/* <form onSubmit={handleSubmit}>
        <fieldset disabled={submitting}>
          <label>
            <p>用户名*</p>
            <input name="username" onChange={handleChange} value={formData.username}/>
          </label>

          <label>
            <p>密码*</p>
            <input name="password" onChange={handleChange} value={formData.password}/>
          </label>
        </fieldset>
        <button type="submit" disabled={submitting}>Sign In</button>
      </form> */}
    </div>
  )
}

export default SignIn;