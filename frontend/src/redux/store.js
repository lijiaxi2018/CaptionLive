import { configureStore } from '@reduxjs/toolkit'
import userReducer from './userSlice'
import layoutReducer from './layoutSlice'
import fileReducer from './fileSlice'
import { userApi } from '../services/user'
import { authApi } from '../services/auth'
import { fileApi } from '../services/file'
import { organizationApi } from '../services/organization'
import { segmentApi } from '../services/segment'

export const store = configureStore({
  reducer: {
    userAuth: userReducer,
    layout: layoutReducer,
    file: fileReducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
    [fileApi.reducerPath]: fileApi.reducer,
    [organizationApi.reducerPath]: organizationApi.reducer,
    [segmentApi.reducerPath]: segmentApi.reducer,
  },

  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat([
      userApi.middleware,
      authApi.middleware,
      fileApi.middleware,
      organizationApi.middleware,
      segmentApi.middleware,
    ]),
})