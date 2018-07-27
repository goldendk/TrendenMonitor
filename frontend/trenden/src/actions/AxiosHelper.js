import axios from "axios/index";

export function buildGet(url, withCredentials){
    const request = axios.get(url, {
        headers: {
            'Accept': "application/json"
        }
        , withCredentials:  withCredentials | false
    });
    return request;
}
