import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const fileApi = createApi({
  reducerPath: 'fileApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

  tagTypes: ['Files'],
  endpoints: (builder) => ({
    getFiles: builder.query({
      query: (id) => `files/${id}`,
      providesTags: ['Files']
    }),

    postFiles: builder.mutation({
      query: (body) => ({
          url: 'files',
          method: 'POST',
          body,
          formData: true,
        }),
      }),
      invalidatesTags: ['Files']
  }),
})

export const {
  usePostFilesMutation,
  useGetFilesQuery,
} = fileApi