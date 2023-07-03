import { createSlice } from '@reduxjs/toolkit'

const sessionSelectedOrgId = sessionStorage.getItem("clSelectedOrgId") !== null ? JSON.parse(sessionStorage.getItem("clSelectedOrgId")) : -1;
const sessionSelectedLvl2Id = sessionStorage.getItem("clSelectedLvl2Id") !== null ? JSON.parse(sessionStorage.getItem("clSelectedLvl2Id")) : 0;
const sessionOpenedSegmentIds = sessionStorage.getItem("clOpenedSegmentIds") !== null ? JSON.parse(sessionStorage.getItem("clOpenedSegmentIds")) : [];

const initialState = {
  openSignInOnWindow: false,
  inSignIn: false,
  inAddSegment: false,
  inAddOrganization: false,
  inMessage: false,
  inAddProject: false,
  selectedRequestId: -1,
  selectedProjectId: -1,
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

    openAddSegment: (state) => {
      state.inAddSegment = true;
    },

    closeAddSegment: (state) => {
      state.inAddSegment = false;
    },

    openAddOrganization: (state) => {
      state.inAddOrganization = true;
    },

    closeAddOrganization: (state) => {
      state.inAddOrganization = false;
    },

    openMessage: (state) => {
      state.inMessage = true;
    },

    closeMessage: (state) => {
      state.inMessage = false;
    },

    updateSelectedRequestId: (state, action) => {
      state.selectedRequestId = action.payload;
    },

    openAddProject: (state) => {
      state.inAddProject = true;
    },

    closeAddProject: (state) => {
      state.inAddProject = false;
    },

    updateSelectedProjectId: (state, action) => {
      state.selectedProjectId = action.payload;
    },
  },
})

export const { 
  toggleSignInOnWindow, 
  toggleSignInOnPage, 
  updateSelectedOrgId,
  updateSelectedLvl2Id, 
  openSegment,
  closeSegment, 
  openAddSegment, 
  closeAddSegment, 
  openAddOrganization,
  closeAddOrganization,
  openMessage,
  closeMessage,
  updateSelectedRequestId,
  openAddProject,
  closeAddProject,
  updateSelectedProjectId,
} = layoutSlice.actions

export default layoutSlice.reducer