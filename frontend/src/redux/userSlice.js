import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  userId: -1,
  accessToken: ''
}

export const userSlice = createSlice({
  name: 'userAuth',
  initialState,
  reducers: {
    updateUserId: (state, action) => {
      state.userId = action.payload
    },
    updateAccessToken: (state, action) => {
      state.accessToken = action.payload
    },
  },
})

export const { updateUserId, updateAccessToken } = userSlice.actions

export default userSlice.reducer