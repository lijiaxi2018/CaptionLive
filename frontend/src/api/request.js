import { useGetRequestsForUserQuery } from '../services/request';
import { updateUserId } from '../redux/userSlice';
import { openSignInOnWindow } from '../redux/layoutSlice';
import { useDispatch } from 'react-redux';

export function useGetRequestsForUser(userId) {
  const dispatch = useDispatch();

  const requestData = useGetRequestsForUserQuery(userId);
  
  if (requestData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !requestData.isFetching && !requestData.isError;
  
  return fetched ? [fetched, requestData.data.data] : [fetched, requestData];
}