import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const glossaryApi = createApi({
    reducerPath: 'glossaryApi',
    baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

    tagTypes: ['Glossaries'],
    endpoints: (builder) => ({
        getAllGlossaries: builder.query({
            query: (id) => `/glossaries/getAllGlossaries/${id}`,
            providesTags: ['Glossaries']
        }),
        
        getGlossaries: builder.query({
            query: () => `/glossaries`,
            providesTags: ['Glossaries']
        }),

        postGlossaries: builder.mutation({
            query: (body) => ({
                url: 'glossaries',
                method: 'POST',
                body,
            })
        })
    }),
})

export const {
    useGetAllGlossariesQuery,
    useGetGlossariesQuery,
    usePostGlossariesMutation,
} = glossaryApi