import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const authApi = createApi({
  reducerPath: 'authApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://13.59.233.173:8080/api/' }),

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

  }),
})

export const { 
  useLoginUserMutation
} = authApi