import { useGetUserQuery } from '../services/user';
import { updateUserId } from '../redux/userSlice';
import { openSignInOnWindow } from '../redux/layoutSlice';
import { useDispatch } from 'react-redux';

export function useGetUser(userId) {
  const dispatch = useDispatch();

  const userData = useGetUserQuery(userId);
  
  if (userData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !userData.isFetching && !userData.isError;
  
  return fetched ? [fetched, userData.data.data] : [fetched, userData];
}