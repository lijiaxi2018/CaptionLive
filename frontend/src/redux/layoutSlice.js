import { createSlice } from '@reduxjs/toolkit'

const sessionSelectedOrgId = sessionStorage.getItem("clSelectedOrgId") !== null ? JSON.parse(sessionStorage.getItem("clSelectedOrgId")) : -1;

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
  inEditUser: false,
  selectedOrgId: sessionSelectedOrgId,
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

    updateSelectedOrgId: (state, action) => {
      state.selectedOrgId = action.payload;
      sessionStorage.setItem("clSelectedOrgId", JSON.stringify(state.selectedOrgId));
    },
  },
})

export const { toggleSignInOnWindow, toggleSignInOnPage, toggleInEditUser, updateSelectedOrgId } = layoutSlice.actions

export default layoutSlice.reducer