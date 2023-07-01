import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const projectApi = createApi({
  reducerPath: 'projectApi',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://13.59.233.173:8080/api/',
    prepareHeaders: (headers) => {
      const userToken = JSON.parse(localStorage.getItem("clAccessToken"));
      if (userToken) {
        headers.set('Authorization', userToken);
      }
      return headers
    },
  }),

  tagTypes: ['Projects'],
  endpoints: (builder) => ({
    getProject: builder.query({
      query: (id) => `/projects/${id}`,
      providesTags: ['Projects']
    }),

    putProjectCoverId: builder.mutation({
      query: (coverInfo) => ({
          url: `/projects/${coverInfo.projectId}/cover/${coverInfo.coverId}`,
          method: 'PUT',
      }),
      invalidatesTags: ['Projects']
    }),
  }),
})

export const { 
  useGetProjectQuery, 
  usePutProjectCoverIdMutation,
} = projectApi