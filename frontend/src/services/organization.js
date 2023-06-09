import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const organizationApi = createApi({
  reducerPath: 'organizationApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

  tagTypes: ['Organizations'],
  endpoints: (builder) => ({
    getOrganization: builder.query({
      query: (id) => `/organizations/${id}`,
      providesTags: ['Organizations']
    }),

    getAllOrganizations: builder.query({
      query: () => '/organizations',
      providesTags: ['Organizations']
    }),

    getOrganizationProjects: builder.query({
      query: (projectid) => `/organizations/${projectid}/projects`,
      params: { page: '0', size: '10', sortBy: 'createdTime', sortOrder: 'asc',  },
      providesTags: ['Organizations']
    }),
  }),
})

export const { 
  useGetOrganizationQuery, 
  useGetAllOrganizationsQuery,
  useGetOrganizationProjectsQuery,
} = organizationApi