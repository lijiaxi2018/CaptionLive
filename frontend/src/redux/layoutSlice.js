import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
  inEditUser: false,
}

export const layoutSlice = createSlice({
  name: 'layout',
  initialState,
  reducers: {
    toggleSignInOnWindow: (state) => {
      state.openSignInOnWindow = !state.openSignInOnWindow;
    },

    toggleSignInOnPage: (state) => {
      state.inSignIn = !state.inSignIn;
    },

    toggleInEditUser: (state) => {
      state.inEditUser = !state.inEditUser;
    },
  },
})

export const { toggleSignInOnWindow, toggleSignInOnPage, toggleInEditUser } = layoutSlice.actions

export default layoutSlice.reducer