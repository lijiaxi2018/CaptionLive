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
      providesTags: ['Organizations']
    }),

    assignTask: builder.mutation({
      query: (commitment) => ({
        url: `/tasks/${commitment.taskId}/assign/${commitment.userId}`,
        method: 'PUT',
      }),
      invalidatesTags: ['Organizations']
    }),

    assignFileToTask: builder.mutation({
      query: (ownership) => ({
        url: `/tasks/${ownership.taskId}/file/${ownership.fileRecordId}`,
        method: 'PUT',
      }),
      invalidatesTags: ['Organizations']
    }),

    withdrawTask: builder.mutation({
      query: (taskId) => ({
        url: `/tasks/${taskId}/assign`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Organizations']
    }),
  }),
})

export const { 
  useGetOrganizationQuery, 
  useGetAllOrganizationsQuery,
  useGetOrganizationProjectsQuery,
  useAssignTaskMutation, 
  useAssignFileToTaskMutation, 
  useWithdrawTaskMutation, 
} = organizationApi