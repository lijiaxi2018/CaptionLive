import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { configuration } from '../config/config';

export const authApi = createApi({
  reducerPath: 'authApi',
  baseQuery: fetchBaseQuery({ baseUrl: `http://${configuration.HOSTNAME}:8080/api/`, }),

  tagTypes: ['Auth'],
  endpoints: (builder) => ({

    loginUser: builder.mutation({
      query: (signInInfo) => ({
          url: '/login',
          method: 'POST',
          body: signInInfo
      }),
      invalidatesTags: ['Auth']
    }),

    signUpUser: builder.mutation({
      query: (signUpInfo) => ({
          url: '/signUp',
          method: 'POST',
          body: signUpInfo
      }),
      invalidatesTags: ['Auth']
    }),

  }),
})

export const { 
  useLoginUserMutation,
  useSignUpUserMutation,
} = authApi