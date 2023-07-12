import { useGetOrganizationQuery, useGetAllOrganizationsQuery, useGetOrganizationProjectsQuery, useGetAccessibleProjectsQuery } from '../services/organization';
import { updateUserId } from '../redux/userSlice';
import { openSignInOnWindow } from '../redux/layoutSlice';
import { useDispatch } from 'react-redux';

export function useGetOrganization(organizationId) {
  const dispatch = useDispatch();

  const organizationData = useGetOrganizationQuery(organizationId);
  
  if (organizationData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !organizationData.isFetching && !organizationData.isError;
  
  return fetched ? [fetched, organizationData.data.data] : [fetched, organizationData];
}

export function useGetAllOrganization() {
  const dispatch = useDispatch();

  const organizationData = useGetAllOrganizationsQuery();
  
  if (organizationData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !organizationData.isFetching && !organizationData.isError;
  
  return fetched ? [fetched, organizationData.data.data] : [fetched, organizationData];
}

export function useGetOrganizationProjects(organizationId) {
  const dispatch = useDispatch();

  const projectData = useGetOrganizationProjectsQuery(organizationId);
  
  if (projectData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !projectData.isFetching && !projectData.isError;
  
  return fetched ? [fetched, projectData.data.data] : [fetched, projectData];
}

export function useGetAccessibleProjects(userId) {
  const dispatch = useDispatch();

  const projectData = useGetAccessibleProjectsQuery(userId);
  
  if (projectData.isError) {
    dispatch(updateUserId(-1));
    dispatch(openSignInOnWindow());
  }

  const fetched = !projectData.isFetching && !projectData.isError;
  
  return fetched ? [fetched, projectData.data.data] : [fetched, projectData];
}