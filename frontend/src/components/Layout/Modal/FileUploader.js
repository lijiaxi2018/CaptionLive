import React, { useCallback } from "react";
import { useSelector, useDispatch } from 'react-redux'
import { useDropzone } from 'react-dropzone'
import { Icon } from '@rsuite/icons';
import { AiOutlineUpload } from 'react-icons/ai';

import { usePostFilesMutation } from "../../../services/file";
import { useAssignFileToTaskMutation } from '../../../services/organization';
import { closeUploaderWindow } from '../../../redux/fileSlice';

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

    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length > 0) {
            
            const acceptedFile = acceptedFiles[0];
            var formData = new FormData();
            formData.append('file', acceptedFile)
            postFiles(formData).then((response) => {
                const myFileRecordId = response.data.data;
                
                if (myCurrentUploadType === 3) {
                    assignFileToTaskMutation({
                        taskId: myCurrentIdToUpload,
                        fileRecordId: myFileRecordId,
                    })
                    .then((response) => {
                    // TODO: Deal with other return messages
                    })
                }
            }).catch(error => {
                console.log(error);
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