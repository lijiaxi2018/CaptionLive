import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
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
  },
})

export const { toggleSignInOnWindow, toggleSignInOnPage } = layoutSlice.actions

export default layoutSlice.reducer