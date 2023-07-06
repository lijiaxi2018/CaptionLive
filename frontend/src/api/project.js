import { useGetProjectQuery } from '../services/project';
import { updateUserId } from '../redux/userSlice';
import { openSignInOnWindow } from '../redux/layoutSlice';
import { useDispatch } from 'react-redux';

export function useGetProject(projectId) {
  const dispatch = useDispatch();

  const projectData = useGetProjectQuery(projectId);
  
  if (projectData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !projectData.isFetching && !projectData.isError;
  
  return fetched ? [fetched, projectData.data.data] : [fetched, projectData];
}