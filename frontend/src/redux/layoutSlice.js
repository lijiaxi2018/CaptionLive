import { createSlice } from '@reduxjs/toolkit'

const sessionSelectedOrgId = sessionStorage.getItem("clSelectedOrgId") !== null ? JSON.parse(sessionStorage.getItem("clSelectedOrgId")) : -1;
const sessionSelectedLvl2Id = sessionStorage.getItem("clSelectedLvl2Id") !== null ? JSON.parse(sessionStorage.getItem("clSelectedLvl2Id")) : 0;

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
  inEditUser: false,
  selectedOrgId: sessionSelectedOrgId,
  selectedLvl2Id: sessionSelectedLvl2Id
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

    updateSelectedLvl2Id: (state, action) => {
      state.selectedLvl2Id = action.payload;
      sessionStorage.setItem("clSelectedLvl2Id", JSON.stringify(state.selectedLvl2Id));
    },
  },
})

export const { 
  toggleSignInOnWindow, 
  toggleSignInOnPage, 
  toggleInEditUser, 
  updateSelectedOrgId,
  updateSelectedLvl2Id, 
} = layoutSlice.actions

export default layoutSlice.reducer