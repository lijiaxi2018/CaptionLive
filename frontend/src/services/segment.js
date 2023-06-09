import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const segmentApi = createApi({
  reducerPath: 'segmentApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

  tagTypes: ['Segments'],
  endpoints: (builder) => ({
    getSegment: builder.query({
      query: (id) => `/segments/${id}`,
      providesTags: ['Segments']
    }),
  }),
})

export const { 
  useGetSegmentQuery, 
} = segmentApi