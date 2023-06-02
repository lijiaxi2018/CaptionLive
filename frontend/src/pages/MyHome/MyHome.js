import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { updateUserId, updateAccessToken } from '../../redux/userSlice'
import { useGetUserQuery, 
  useGetAllUsersQuery, 
  usePostUserMutation,
  usePutUserMutation,
  useDeleteUserMutation 
} from '../../services/user'

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
  } = useGetUserQuery(1)
  let user2Content = JSON.stringify(user)

  // RTK Post, Put, and Delete
  const [postUser] = usePostUserMutation()
  const [putUser] = usePutUserMutation()
  const [deleteUser] = useDeleteUserMutation()

  return (
    <div className='myhome'>
      <div>
        <h3>
          UserId: {myUserId}
        </h3>
        <h3>
          AccessToken: {myAccessToken}
        </h3>
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
      <h3>
        All Users:
        {usersContent}
      </h3>
      <h3>
        <br/>
        One User 2:
        {user2Content}
      </h3>

      <button
        aria-label="Post user"
        onClick={() => postUser({ 
          permission: 0,
          username: "new",
          password: "new",
          qq: "new",
          email: "new",
        })}
      >
        Post
      </button>

      <button
        aria-label="Put user"
        onClick={() => putUser({ 
          userId: 19,
          permission: 0,
          username: "modified",
          password: "modified",
          qq: "modified",
          email: "modified",
        })}
      >
        Put
      </button>

      <button
        aria-label="Delete user"
        onClick={() => deleteUser(19)}
      >
        Delete
      </button>
    </div>
  );
}

export default MyHome;