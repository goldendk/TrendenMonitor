import React from 'react';
import {GoogleLogin, GoogleLogout} from 'react-google-login';
import {googleLoginAction, googleLogoutAction} from '../actions/index';

// redux stuff.
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

export const GOOGLE_API_CLIENT_ID = '1069165987697-c8c4rvl7nc0jpbbuoom8uodjtn4n0nua.apps.googleusercontent.com';

class AuthView extends React.Component {

    onSuccess(data) {
        console.log(data);
        console.log("Google token ID: " + data.tokenId);
        this.props.googleLoginAction(data.tokenId);
    }

    onFailure(data) {

        console.log("Failed Google login", data);
        alert('Google login failure: ' + data);
    }

    onLogoutSuccess(){
        console.log("Google logout success");
        this.props.googleLogoutAction();
    }

    render() {
        const authenticated = this.props.authenticated;

        if (!authenticated) {
            return (<GoogleLogin onSuccess={this.onSuccess.bind(this)}
                                 onFailure={this.onFailure.bind(this)}
                                 clientId={GOOGLE_API_CLIENT_ID}/>);

        }
        else {
            return (
                <div>

                    <div>
                        <GoogleLogout onLogoutSuccess={this.onLogoutSuccess.bind(this)}/>
                    </div>

                    <ul>
                        <li>{this.props.user.id}</li>
                        <li>{this.props.user.username}</li>
                        <li>{this.props.user.firstName + " " + this.props.user.lastName}</li>
                        <li>{this.props.user.email}</li>
                        <li><img src={this.props.user.pictureUrl}/></li>


                    </ul>

                </div>


            );

        }
    }


}

function mapStateToProps({userState}, ownProps) {
    console.log("authView.js, mapStateToProps", userState);
    return {
        ...userState
    }
}

export default connect(mapStateToProps, {googleLoginAction, googleLogoutAction})(AuthView);