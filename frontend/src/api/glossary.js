import { useGetAllGlossariesQuery } from '../services/glossary';
import { updateUserId } from '../redux/userSlice';
import { openSignInOnWindow } from '../redux/layoutSlice';
import { useDispatch } from 'react-redux';

export function useGetAllGlossaries(organizationId) {
  const dispatch = useDispatch();

  const glossaryData = useGetAllGlossariesQuery(organizationId);
  
  if (glossaryData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !glossaryData.isFetching && !glossaryData.isError;
  
  return fetched ? [fetched, glossaryData.data.data] : [fetched, glossaryData];
}