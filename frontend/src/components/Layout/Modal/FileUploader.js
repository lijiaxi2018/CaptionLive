import React, { useCallback } from "react";
import { useSelector, useDispatch } from 'react-redux'
import { useDropzone } from 'react-dropzone'
import { Icon } from '@rsuite/icons';
import { AiOutlineUpload } from 'react-icons/ai';

import { usePostFilesMutation } from "../../../services/file";
import { useAssignFileToTaskMutation, usePutOrganizationAvatarIdMutation } from '../../../services/organization';
import { usePutProjectCoverIdMutation } from '../../../services/project';
import { usePutUserAvatarIdMutation } from '../../../services/user';
import { closeUploaderWindow, updateCurrentIdToUpload, updateCurrentUploadType } from '../../../redux/fileSlice';

// Reference: https://www.npmjs.com/package/react-dropzone
const FileUploader = () => {
    const dispatch = useDispatch();

    const myCurrentIdToUpload = useSelector((state) => state.file.currentIdToUpload);
    const myCurrentUploadType = useSelector((state) => state.file.currentUploadType);

    function handleCancel() {
        dispatch(closeUploaderWindow());
    }

    const [postFiles] = usePostFilesMutation();
    const [assignFileToTaskMutation] = useAssignFileToTaskMutation();
    const [putProjectCoverIdMutation] = usePutProjectCoverIdMutation();
    const [putUserAvatarId] = usePutUserAvatarIdMutation();
    const [putOrganizationAvatarIdMutation] = usePutOrganizationAvatarIdMutation();

    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length > 0) {
            
            const acceptedFile = acceptedFiles[0];
            var formData = new FormData();
            formData.append('file', acceptedFile)
            postFiles(formData).then((response) => {
                const myFileRecordId = response.data.data;
                // UploadType === 3 : Upload File to Task
                // UploadType === 2 : Upload Project Cover
                // UploadType === 1 : Upload User Avatar
                // UploadType === 4 : Upload Organization Avatar
                if (myCurrentUploadType === 3) {
                    assignFileToTaskMutation({
                        taskId: myCurrentIdToUpload,
                        fileRecordId: myFileRecordId,
                    })
                    .then((response) => {
                        dispatch(updateCurrentIdToUpload(-1));
                        dispatch(updateCurrentUploadType(-1));
                        dispatch(closeUploaderWindow());
                    // TODO: Deal with other return messages
                    })
                } else if (myCurrentUploadType === 2) {
                    putProjectCoverIdMutation({
                        projectId: myCurrentIdToUpload,
                        coverId: myFileRecordId,
                    })
                    .then((response) => {
                        dispatch(updateCurrentIdToUpload(-1));
                        dispatch(updateCurrentUploadType(-1));
                        dispatch(closeUploaderWindow());
                    // TODO: Deal with other return messages
                    })
                } else if (myCurrentUploadType === 1) {
                    putUserAvatarId({
                        userId: myCurrentIdToUpload,
                        avatarId: myFileRecordId,
                    })
                    .then((response) => {
                        dispatch(updateCurrentIdToUpload(-1));
                        dispatch(updateCurrentUploadType(-1));
                        dispatch(closeUploaderWindow());
                    // TODO: Deal with other return messages
                    })
                } else if (myCurrentUploadType === 4) {
                    putOrganizationAvatarIdMutation({
                        organizationId: myCurrentIdToUpload,
                        avatarId: myFileRecordId,
                    })
                    .then((response) => {
                        dispatch(updateCurrentIdToUpload(-1));
                        dispatch(updateCurrentUploadType(-1));
                        dispatch(closeUploaderWindow());
                    // TODO: Deal with other return messages
                    })
                }
            }).catch(error => {
            })
        }
      }, [postFiles])

    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop});

    return (
        <div className="file-uploader-container">
            <div className="file-uploader-icon" {...getRootProps()}>
                <input {...getInputProps()} />
                <Icon as={AiOutlineUpload} size="10em"/>
                <p>点此上传文件</p>
                <p>或将文件拖拽于此</p>
            </div>
            
            <button className="general-button-red" style={{ marginTop: '20px', marginLeft: '320px' }} onClick={() => handleCancel()}>取消</button>
        </div>
        
        
    );
}

export default FileUploader;