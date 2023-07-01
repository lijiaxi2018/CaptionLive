import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { configuration } from '../config/config';

export const fileApi = createApi({
  reducerPath: 'fileApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `http://${configuration.HOSTNAME}:8080/api/`,
    prepareHeaders: (headers) => {
      const userToken = JSON.parse(localStorage.getItem("clAccessToken"));
      if (userToken) {
        headers.set('Authorization', userToken);
      }
      return headers
    },
  }),

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