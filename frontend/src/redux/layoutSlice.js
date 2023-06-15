import { createSlice } from '@reduxjs/toolkit'

const sessionSelectedOrgId = sessionStorage.getItem("clSelectedOrgId") !== null ? JSON.parse(sessionStorage.getItem("clSelectedOrgId")) : -1;
const sessionSelectedLvl2Id = sessionStorage.getItem("clSelectedLvl2Id") !== null ? JSON.parse(sessionStorage.getItem("clSelectedLvl2Id")) : 0;
const sessionOpenedSegmentIds = sessionStorage.getItem("clOpenedSegmentIds") !== null ? JSON.parse(sessionStorage.getItem("clOpenedSegmentIds")) : [];

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
  inEditUser: false,
  selectedOrgId: sessionSelectedOrgId,
  selectedLvl2Id: sessionSelectedLvl2Id,
  openedSegmentIds: sessionOpenedSegmentIds,
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

    openSegment: (state, action) => {
      state.openedSegmentIds.push(action.payload);
      sessionStorage.setItem("clOpenedSegmentIds", JSON.stringify(state.openedSegmentIds));
    },

    closeSegment: (state, action) => {
      let index = state.openedSegmentIds.indexOf(action.payload);
      if (index !== -1) {
        state.openedSegmentIds.splice(index, 1);
      }
      sessionStorage.setItem("clOpenedSegmentIds", JSON.stringify(state.openedSegmentIds));
    },
  },
})

export const { 
  toggleSignInOnWindow, 
  toggleSignInOnPage, 
  toggleInEditUser, 
  updateSelectedOrgId,
  updateSelectedLvl2Id, 
  openSegment,
  closeSegment, 
} = layoutSlice.actions

export default layoutSlice.reducer