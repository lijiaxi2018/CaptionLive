import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import layoutReducer from './layoutSlice'
import { userApi } from '../services/user'
import { authApi } from '../services/auth'

export const store = configureStore({
  reducer: {
    userAuth: userReducer,
    layout: layoutReducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
  },

  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat([
      userApi.middleware,
      authApi.middleware,
    ]),
})