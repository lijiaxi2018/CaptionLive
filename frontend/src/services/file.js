import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'


export const fileApi = createApi({
    reducerPath: 'fileApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

    tagTypes: ['Files'],
    endpoints: (builder) => ({
        postFiles: builder.mutation({
          query: (body) => ({
            // return {
              url: 'files',
              method: 'POST',
              body,
              formData: true,
            // };
          }),
        }),

    }),
})

export const { usePostFilesMutation } = fileApi