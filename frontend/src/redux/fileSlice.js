import { createSlice } from '@reduxjs/toolkit'

const sessionCurrentIdToUpload = sessionStorage.getItem("clCurrentIdToUpload") !== null ? JSON.parse(sessionStorage.getItem("clCurrentIdToUpload")) : -1;
const sessionCurrentUploadType = sessionStorage.getItem("clCurrentUploadType") !== null ? JSON.parse(sessionStorage.getItem("clCurrentUploadType")) : -1;

const initialState = {
  openUploaderWindow: 0,
  currentProjectIdToAddSegment: -1,
  currentIdToUpload: sessionCurrentIdToUpload,
  currentUploadType: sessionCurrentUploadType,
}

export const fileSlice = createSlice({
  name: 'file',
  initialState,
  reducers: {
    openUploaderWindow: (state) => {
      state.openUploaderWindow = 1;
    },

    closeUploaderWindow: (state) => {
      state.openUploaderWindow = 0;
    },

    updateCurrentIdToUpload: (state, action) => {
      state.currentIdToUpload = action.payload;
      sessionStorage.setItem("clCurrentIdToUpload", JSON.stringify(state.currentIdToUpload));
    },

    updateCurrentUploadType: (state, action) => {
      state.currentUploadType = action.payload;
      sessionStorage.setItem("clCurrentUploadType", JSON.stringify(state.currentUploadType));
    },

    updateCurrentProjectIdToAddSegment: (state, action) => {
      state.currentProjectIdToAddSegment = action.payload;
    },
  },
})

export const { 
  openUploaderWindow,
  closeUploaderWindow,
  updateCurrentIdToUpload,
  updateCurrentUploadType,
  updateCurrentProjectIdToAddSegment,
} = fileSlice.actions

export default fileSlice.reducer