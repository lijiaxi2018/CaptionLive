import { createSlice } from '@reduxjs/toolkit'

const localUserId = localStorage.getItem("clUserId") !== null ? JSON.parse(localStorage.getItem("clUserId")) : -1;
const localAccessToken = localStorage.getItem("clAccessToken") !== null ? JSON.parse(localStorage.getItem("clAccessToken")) : "";

const initialState = {
  userId: localUserId,
  accessToken: localAccessToken,
}

export const userSlice = createSlice({
  name: 'userAuth',
  initialState,
  reducers: {

    updateUserId: (state, action) => {
      state.userId = action.payload;
      localStorage.setItem("clUserId", JSON.stringify(state.userId));
    },

    updateAccessToken: (state, action) => {
      state.accessToken = action.payload;
      localStorage.setItem("clAccessToken", JSON.stringify(state.accessToken));
    },
  },
})

export const { updateUserId, updateAccessToken } = userSlice.actions

export default userSlice.reducer