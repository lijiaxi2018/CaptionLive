import React, { useState, useReducer, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import { useLoginUserMutation } from '../../services/auth';
import { updateUserId, updateAccessToken } from '../../redux/userSlice';
import { toggleSignInOnWindow, toggleSignInOnPage } from '../../redux/layoutSlice'
import { sha256 } from 'js-sha256';
import { languagedata } from '../../assets/language';

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
  
  const language = useSelector((state) => state.layout.language);

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
        setPrompt(languagedata[language]['pleaseFillNecessaryInformation'])
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
    const encrypted = sha256(formData.password);
    loginUser({
      username: formData.username,
      password: encrypted,
    })
    .then((response) => {
      let message = response.data.message;
      if (message === "success") {
        setPrompt(languagedata[language]['loginSuccess']);
        dispatch(updateUserId(response.data.token.id));
        dispatch(updateAccessToken(response.data.token.token));
        dispatch(toggleSignInOnWindow());
      } else if (message.startsWith("invalid")) {
        setPrompt(languagedata[language]['passwordIncorrect']);
      } else if (message.startsWith("User")) {
        setPrompt(languagedata[language]['userNotFound']);
      }
    })
  }
  
  const handleLogIn = event => {
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

  const handleSignUp = () => {
    dispatch(toggleSignInOnPage());
  }

  const handleCancel = () => {
    dispatch(toggleSignInOnWindow());
  }

  return (
    <div className="sign-in-window">
      <p className="sign-in-up-title">{languagedata[language]['login']}</p>

      <label style={{ color: '#ff6765' }}>{prompt}</label><br/>
      <input name="username" className="sign-in-up-input" 
        placeholder={languagedata[language]['pleaseFillUsername']} 
        onChange={handleChange} value={formData.username}
      />
      <label className="star-mark">*</label>
      <br/>

      <input type="password" name="password" className="sign-in-up-input" 
        placeholder={languagedata[language]['pleaseFillPassword']}
        onChange={handleChange} value={formData.password}
      />
      <label className="star-mark">*</label>
      <br/>

      <div className="sign-in-up-button-list">
        <button className="general-button-grey" onClick={handleSignUp}>
          {languagedata[language]['register']}
        </button>
        <button className="general-button-red" onClick={handleCancel}>
          {languagedata[language]['cancel']}
        </button>
        <button className="general-button-green" onClick={handleLogIn}>
          {languagedata[language]['login']}
        </button>
      </div>
    </div>
  )
}

export default SignIn;