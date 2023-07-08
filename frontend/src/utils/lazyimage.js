import { lazy, Suspense, Component } from 'react';
import { useState } from 'react';
import { configuration } from '../config/config';
import ReactDOM from 'react-dom';

// class BlobManager {
//     constructor() {
//         this.blobMap = new Map();
//     }

//     static setBlob(id, blob) {
//         this.blobMap.set(id, blob);
//     }

//     static getBlob(id) {
//         if (this.blobMap.has(id)) {
//             return this.blobMap.get(id);
//         } else {
//             return null;
//         }
//     }
// }


// export default function LazyImage({imageFileId, userToken, stylesheet}) {
//     const curElementId = Symbol(imageFileId).toString();
//     // const prevBlob = BlobManager.getBlob(imageFileId);
//     console.log(this);
//     // if (blobMap.has(imageFileId)) {
//     //     // reuse blob
//     //     console.log('found')
//     //     console.log(blobMap);
//     //     return (
//     //         <img style={stylesheet} src={blobMap.get(imageFileId)}></img>
//     //     )
//     // } else {
//     fetch(`http://${configuration.HOSTNAME}:8080/api/files/${imageFileId}`, {headers:{'Authorization': userToken}}).then(
//         (response) => response.blob()
//     ).then((blob) => {
//         const blo = URL.createObjectURL(blob);
//         // blobMap.set(imageFileId, blo);
//         document.getElementById(curElementId).src = blo;
//         // console.log('notfound')
//         // console.log(blobMap);
//     })
//     return (
//         <img id={curElementId} style={stylesheet}></img>
//     )
//     // }
// }

class LazyImage extends Component {
    constructor(props) {
        super(props);
        // console.log(props);
        // this.setState({blo: null});
    }
    
    componentDidMount() {
        const dom = ReactDOM.findDOMNode(this);
        fetch(`http://${configuration.HOSTNAME}:8080/api/files/${this.props.imageFileId}`, {headers:{'Authorization': this.props.userToken}}).then(
            (response) => response.blob()
        ).then((blob) => {
            const blo = URL.createObjectURL(blob);
            dom.src = blo;
            // this.setState({blo: blo});
        })
    }

    // componentWillUnmount() {
        
    // }

    render() {
        return (
            <img style={this.props.stylesheet}></img>
        )
    }
}

export default LazyImage