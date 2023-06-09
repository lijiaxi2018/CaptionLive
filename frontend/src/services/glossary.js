import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { configuration } from '../config/config';

export const glossaryApi = createApi({
    reducerPath: 'glossaryApi',
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

    tagTypes: ['Glossaries'],
    endpoints: (builder) => ({
        getAllGlossaries: builder.query({
            query: (id) => `/glossaries/getAllGlossaries/${id}`,
            providesTags: ['Glossaries'],
        }),

        deleteGlossary: builder.mutation({
            query: (id) => ({
                url: `/glossaries/${id}`,
                method: 'DELETE',
            }),
            invalidatesTags: ['Glossaries'],
        }),
        
        getGlossaries: builder.query({
            query: () => `/glossaries`,
            providesTags: ['Glossaries'],
        }),

        postGlossaries: builder.mutation({
            query: (body) => ({
                url: 'glossaries',
                method: 'POST',
                body,
            }),
            invalidatesTags: ['Glossaries'],
        })
    }),

})

export const {
    useGetAllGlossariesQuery,
    useGetGlossariesQuery,
    usePostGlossariesMutation,
    useDeleteGlossaryMutation,
} = glossaryApi