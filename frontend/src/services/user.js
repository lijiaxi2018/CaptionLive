import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const userApi = createApi({
  reducerPath: 'userApi',
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

  tagTypes: ['Users'],
  endpoints: (builder) => ({
    getUser: builder.query({
      query: (id) => `/users/${id}`,
      providesTags: ['Users']
    }),

    getAllUsers: builder.query({
      query: () => '/users',
      providesTags: ['Users']
    }),

    postUser: builder.mutation({
      query: (user) => ({
          url: '/users',
          method: 'POST',
          body: user
      }),
      invalidatesTags: ['Users']
    }),

    putUser: builder.mutation({
      query: (user) => ({
          url: `/users/${user.userId}`,
          method: 'PUT',
          body: user
      }),
      invalidatesTags: ['Users']
    }),

    putUserDescription: builder.mutation({
      query: (user) => ({
          url: `/users/${user.userId}/description`,
          method: 'PUT',
          body: user
      }),
      invalidatesTags: ['Users']
    }),

    putUserAvatarId: builder.mutation({
      query: (user) => ({
          url: `/users/${user.userId}/avatar`,
          method: 'PUT',
          body: user
      }),
      invalidatesTags: ['Users']
    }),

    deleteUser: builder.mutation({
      query: (id) => ({
          url: `/users/${id}`,
          method: 'DELETE',
      }),
      invalidatesTags: ['Users']
    }),

    getOrganizationsByUser: builder.query({
      query: (id) => `/users/${id}/organizations`,
      providesTags: ['Users']
    }),
  }),
})

export const { 
  useGetUserQuery, 
  useGetAllUsersQuery, 
  usePostUserMutation,
  usePutUserMutation,
  usePutUserDescriptionMutation,
  usePutUserAvatarIdMutation,
  useDeleteUserMutation,
  useGetOrganizationsByUserQuery,
} = userApi