const retrievePromise = googleyolo.retrieve({
    supportedAuthMethods: [
        "https://accounts.google.com",
        "googleyolo://id-and-password"
    ],
    supportedIdTokenProviders: [
        {
            uri: "https://accounts.google.com",
            clientId: "1069165987697-2bgck91g7v9tagd0qimq7nqvhshbumqg.apps.googleusercontent.com"
        }
    ],
    context: 'signin'
}).then((credential) => {
        if (credential.idToken) {
            // Send the token to your auth backend.
            useGoogleIdTokenForAuth(credential.idToken);
        }
    },
    (error) => {
        switch (error.type) {
            case "userCanceled":
                // The user closed the hint selector. Depending on the desired UX,
                // request manual sign up or do nothing.
                break;
            case "noCredentialsAvailable":
                // No hint available for the session. Depending on the desired UX,
                // request manual sign up or do nothing.
                break;
            case "requestFailed":
                // The request failed, most likely because of a timeout.
                // You can retry another time if necessary.
                break;
            case "operationCanceled":
                // The operation was programmatically canceled, do nothing.
                break;
            case "illegalConcurrentRequest":
                // Another operation is pending, this one was aborted.
                break;
            case "initializationError":
                // Failed to initialize. Refer to error.message for debugging.
                break;
            case "configurationError":
                // Configuration error. Refer to error.message for debugging.
                break;
            default:
            // Unknown error, do nothing.
        }
    }
);
useGoogleIdTokenForAuth = function(token){
    window.location = "/GoogleAuth?idToken=" + token;
};