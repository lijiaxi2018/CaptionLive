import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { updateUserId, updateAccessToken } from '../redux/userSlice'
import { useGetUserQuery, 
  useGetAllUsersQuery, 
  usePostUserMutation,
  usePutUserMutation,
  useDeleteUserMutation 
} from '../services/user'

function MyHome() {
  
  // Redux
  const myUserId = useSelector((state) => state.userAuth.userId)
  const myAccessToken = useSelector((state) => state.userAuth.accessToken)
  const dispatch = useDispatch()

  // RTK Query Get 1
  const {
    data: users,
    isLoading1,
    isSuccess1,
    isError1,
    error1
  } = useGetAllUsersQuery()

  let usersContent = JSON.stringify(users)

  // RTK Query Get 2
  const {
    data: user,
    isLoading2,
    isSuccess2,
    isError2,
    error2
  } = useGetUserQuery(2)

  let userContent = JSON.stringify(user)

  // RTK Post
  const [postUser] = usePostUserMutation()

  // RTK Put
  const [putUser] = usePutUserMutation()

  // RTK Delete
  const [deleteUser] = useDeleteUserMutation()

  return (
    <div>
      <div>
        <h1>
          UserId: {myUserId}
        </h1>
        <h1>
          AccessToken: {myAccessToken}
        </h1>
        <button
          aria-label="Increment value"
          onClick={() => dispatch(updateUserId(0))}
        >
          Modify UserId
        </button>
        <button
          aria-label="Decrement value"
          onClick={() => dispatch(updateAccessToken('New Access Token'))}
        >
          Modify AccessToken
        </button>
      </div>
      <h1>
        All Users:
        {usersContent}
      </h1>
      <h1>
        <br/>
        One User 2:
        {userContent}
      </h1>

      <button
        aria-label="Post user"
        onClick={() => postUser({ 
          permission: 0,
          username: "test",
          password: "password",
          qq: "qq",
          avatar: "",
          email: "yijunlin2018",
          lastUpdatedTime: "2023-05-27T00:04:58.950Z",
          createdTime: "2023-05-27T00:04:58.950Z"
        })}
      >
        Post
      </button>

      <button
        aria-label="Put user"
        onClick={() => putUser({ 
          userId: 2,
          permission: 0,
          username: "haixing",
          password: "md",
          qq: "qq",
          avatar: "",
          email: "yijunlin2018",
          lastUpdatedTime: "2023-05-27T00:04:58.950Z",
          createdTime: "2023-05-27T00:04:58.950Z"
        })}
      >
        Put
      </button>

      <button
        aria-label="Delete user"
        onClick={() => deleteUser(13)}
      >
        Delete
      </button>
    </div>
  );
}

export default MyHome;