import { createSlice } from '@reduxjs/toolkit'

const localUserId = localStorage.getItem("userId") !== null ? JSON.parse(localStorage.getItem("userId")) : -1;
const localAccessToken = localStorage.getItem("accessToken") !== null ? JSON.parse(localStorage.getItem("accessToken")) : "";

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
      localStorage.setItem("userId", JSON.stringify(state.userId));
    },

    updateAccessToken: (state, action) => {
      state.accessToken = action.payload;
      localStorage.setItem("accessToken", JSON.stringify(state.accessToken));
    },
  },
})

export const { updateUserId, updateAccessToken } = userSlice.actions

export default userSlice.reducer