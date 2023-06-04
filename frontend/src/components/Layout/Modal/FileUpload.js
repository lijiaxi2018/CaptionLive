import React, { useCallback, useState, useRef } from "react";
import Dropzone from 'react-dropzone'
import { useDropzone } from 'react-dropzone'
import { usePostFilesMutation } from "../../../services/file";
import axios from 'axios';

const DEFAULT_MAX_BYTES = 1024*50;

/** reference:https://www.npmjs.com/package/react-dropzone */
const headers = {
    "Content-Type": "multipart/form-data",
};
const FileUpload = () => {
    const [postFiles] = usePostFilesMutation();
    // const [curFile, setCurFile] = useState(null);

    const onDrop = useCallback(acceptedFiles => {
        // Do something with the files
        // alert("recieved files");
        // console.log(acceptedFiles);
        if (acceptedFiles.length > 0) {
            
            const acceptedFile = acceptedFiles[0];
            var formData = new FormData();
            formData.append('file', acceptedFile)
            postFiles(formData).then((response) => {
                alert('success');
            }).catch(error => {
                console.log(error);
            })
            // axios.post('http://localhost:8080/api/files', formData, headers).then(response => {
            //     if (response.status === 200) {
            //         alert('file upload successfully');
            //     } else {
            //         alert('file upload failed');
            //     }
            // }).catch(error => {
            //     console.log(error);
            // });
        }
        
      }, [])

    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop});

    // const handleSubmitUpload = () => {
    //     if (curFile !== null) {

    //     } else {
    //         alert('please first upload')
    //     }
    // }
    return (
        <div className="file-drop-zone" {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                <p>Drop the files here ...</p> :
                <p>Drag 'n' drop some files here, or click to select files</p>
            }
        </div>
        
    );
}

export default FileUpload;