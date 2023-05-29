import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import { userApi } from '../services/user'

export const store = configureStore({
  reducer: {
    userAuth: userReducer,
    [userApi.reducerPath]: userApi.reducer,
  },

  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(userApi.middleware),
})