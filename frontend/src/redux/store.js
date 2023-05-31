import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import { userApi } from '../services/user'
import { authApi } from '../services/auth'

export const store = configureStore({
  reducer: {
    userAuth: userReducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
  },

  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat([
      userApi.middleware,
      authApi.middleware,
    ]),
})