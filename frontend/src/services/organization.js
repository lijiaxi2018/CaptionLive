import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { configuration } from '../config/config';

export const organizationApi = createApi({
  reducerPath: 'organizationApi',
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

    deleteSegment: builder.mutation({
      query: (segmentId) => ({
        url: `/segments/${segmentId}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Organizations']
    }),

    createSegment: builder.mutation({
      query: (segmentInfo) => ({
        url: `/segments`,
        method: 'POST',
        body: segmentInfo,
      }),
      invalidatesTags: ['Organizations']
    }),

    putOrganizationDescription: builder.mutation({
      query: (organization) => ({
        url: `/organizations/${organization.organizationId}/description`,
        method: 'PUT',
        body: organization
      }),
      invalidatesTags: ['Organizations']
    }),

    putOrganizationAvatarId: builder.mutation({
      query: (organization) => ({
        url: `/organizations/${organization.organizationId}/avatar`,
        method: 'PUT',
        body: organization
      }),
      invalidatesTags: ['Organizations']
    }),

    addOrganization: builder.mutation({
      query: (orgInfo) => ({
        url: `/organizations`,
        method: 'POST',
        body: orgInfo,
      }),
      invalidatesTags: ['Organizations']
    }),

    getAccessibleProjects: builder.query({
      query: (id) => `/users/${id}/accessibleProjects`,
      providesTags: ['Organizations']
    }),

    addProject: builder.mutation({
      query: (projectInfo) => ({
        url: `/projects`,
        method: 'POST',
        body: projectInfo,
      }),
      invalidatesTags: ['Organizations']
    }),

    getSharedUsers: builder.query({
      query: (id) => `/projects/${id}/shareInfo/users`,
      providesTags: ['Organizations']
    }),

    getSharedOrgs: builder.query({
      query: (id) => `/projects/${id}/shareInfo/organizations`,
      providesTags: ['Organizations']
    }),

    shareProjectToUser: builder.mutation({
      query: (share) => ({
        url: `/projects/${share.projectId}/users/${share.userId}`,
        method: 'PUT',
      }),
      invalidatesTags: ['Organizations']
    }),

    shareProjectToOrg: builder.mutation({
      query: (share) => ({
        url: `/projects/${share.projectId}/organizations/${share.organizationId}`,
        method: 'PUT',
      }),
      invalidatesTags: ['Organizations']
    }),

    deleteProject: builder.mutation({
      query: (projectId) => ({
        url: `/projects/${projectId}`,
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
  useDeleteSegmentMutation, 
  useCreateSegmentMutation,
  usePutOrganizationDescriptionMutation,
  usePutOrganizationAvatarIdMutation,
  useAddOrganizationMutation,
  useGetAccessibleProjectsQuery, 
  useAddProjectMutation,
  useGetSharedUsersQuery, 
  useGetSharedOrgsQuery, 
  useShareProjectToUserMutation,
  useShareProjectToOrgMutation,
  useDeleteProjectMutation, 
} = organizationApi
