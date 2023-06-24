// DEPRECATED

import React, { useCallback } from "react";
import { useDropzone } from 'react-dropzone'
import { usePostFilesMutation } from "../../../services/file";
import { usePutUserAvatarIdMutation } from '../../../services/user';

// Reference: https://www.npmjs.com/package/react-dropzone
const FileDropZone = ({id, type}) => {
    const [postFiles] = usePostFilesMutation();
    const [putUserAvatarId] = usePutUserAvatarIdMutation();

    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length > 0) {
            
            const acceptedFile = acceptedFiles[0];
            var formData = new FormData();
            formData.append('file', acceptedFile)
            postFiles(formData).then((response) => {
                const fileRecordId = response.data.data;

                putUserAvatarId({
                  userId: id,
                  avatarId: fileRecordId,
                })
                .then((response) => {
                  // TODO: Deal with other return messages
                })
            }).catch(error => {
                console.log(error);
            })
        }
        
      }, [postFiles])

    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop});

    return (
        <div className="file-drop-zone" {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                <p>将文件拖拽于此</p> :
                <p>点此上传</p>
            }
        </div>
        
    );
}

export default FileDropZone;