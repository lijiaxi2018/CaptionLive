import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'

export const userApi = createApi({
  reducerPath: 'userApi',
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),

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

    deleteUser: builder.mutation({
      query: (id) => ({
          url: `/users/${id}`,
          method: 'DELETE',
      }),
      invalidatesTags: ['Users']
    }),
  }),
})

export const { 
  useGetUserQuery, 
  useGetAllUsersQuery, 
  usePostUserMutation,
  usePutUserMutation,
  useDeleteUserMutation 
} = userApi