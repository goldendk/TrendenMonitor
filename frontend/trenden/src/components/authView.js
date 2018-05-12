import React from 'react'
import {GoogleLogin} from 'react-google-login'
import {axios} from 'axios'


const GOOGLE_API_CLIENT_ID = '1069165987697-c8c4rvl7nc0jpbbuoom8uodjtn4n0nua.apps.googleusercontent.com';

export default class AuthView extends React.Component {

    onSuccess(data) {
        console.log(data);
        console.log("Google token ID: " + data.tokenId;
        axios.get("/rest/applicaton/auth/google?idToken=" + data.tokenId);
    }

    onFailure(data) {
        console.log(data);
    }


    render() {
        return (
            <GoogleLogin onSuccess={this.onSuccess.bind(this)}
                         onFailure={this.onFailure.bind(this)}
                         clientId={GOOGLE_API_CLIENT_ID}/>
        );
    }


}