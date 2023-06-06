import React, { useCallback } from "react";
import { useDropzone } from 'react-dropzone'
import { usePostFilesMutation } from "../../../services/file";

// Reference: https://www.npmjs.com/package/react-dropzone
const FileUpload = () => {
    const [postFiles] = usePostFilesMutation();

    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length > 0) {
            
            const acceptedFile = acceptedFiles[0];
            var formData = new FormData();
            formData.append('file', acceptedFile)
            postFiles(formData).then((response) => {
                alert('success');
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

export default FileUpload;