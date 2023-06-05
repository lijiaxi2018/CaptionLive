import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import layoutReducer from './layoutSlice'
import { userApi } from '../services/user'
import { authApi } from '../services/auth'
import { fileApi } from '../services/file'
import { organizationApi } from '../services/organization'

export const store = configureStore({
  reducer: {
    userAuth: userReducer,
    layout: layoutReducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
    [fileApi.reducerPath]: fileApi.reducer,
    [organizationApi.reducerPath]: organizationApi.reducer,
  },

  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat([
      userApi.middleware,
      authApi.middleware,
      fileApi.middleware,
      organizationApi.middleware,
    ]),
})