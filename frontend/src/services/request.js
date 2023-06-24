import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const requestApi = createApi({
  reducerPath: 'requestApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

  tagTypes: ['Requests'],
  endpoints: (builder) => ({
    getRequestsForUser: builder.query({
      query: (userId) => `/requests/getAllRequests/${userId}`,
      providesTags: ['Requests']
    }),

    postRequest: builder.mutation({
      query: (request) => ({
          url: '/requests',
          method: 'POST',
          body: request
      }),
      invalidatesTags: ['Requests']
    }),

    postMessage: builder.mutation({
      query: (message) => ({
          url: '/messages',
          method: 'POST',
          body: message
      }),
      invalidatesTags: ['Requests']
    }),

    getMessagesForRequest: builder.query({
      query: (requestId) => `/messages/getAllMessages/${requestId}`,
      providesTags: ['Requests']
    }),

    getRequest: builder.query({
      query: (requestId) => `/requests/${requestId}`,
      providesTags: ['Requests']
    }),

    readRequest: builder.mutation({
      query: (relationship) => ({
          url: `/requests/read/${relationship.requestId}/${relationship.userId}`,
          method: 'PUT',
      }),
      invalidatesTags: ['Requests']
    }),

    unreadRequest: builder.mutation({
      query: (relationship) => ({
          url: `/requests/unread/${relationship.requestId}/${relationship.userId}`,
          method: 'PUT',
      }),
      invalidatesTags: ['Requests']
    }),

    approveRequest: builder.mutation({
      query: (request) => ({
          url: `/requests/approve/${request.requestId}`,
          method: 'PUT',
      }),
      invalidatesTags: ['Requests']
    }),

    rejectRequest: builder.mutation({
      query: (request) => ({
          url: `/requests/reject/${request.requestId}`,
          method: 'PUT',
      }),
      invalidatesTags: ['Requests']
    }),
  }),
})

export const { 
  useGetRequestsForUserQuery, 
  usePostRequestMutation, 
  usePostMessageMutation, 
  useGetMessagesForRequestQuery, 
  useGetRequestQuery, 
  useReadRequestMutation, 
  useUnreadRequestMutation,
  useApproveRequestMutation, 
  useRejectRequestMutation,
} = requestApi