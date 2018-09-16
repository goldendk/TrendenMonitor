import axios from "axios/index";

const DEFAULT_HEADERS = {
    'Content-Type': 'application/json',
        'accept': "application/json",
        'X-Custom-Header': 'foobar'
};
export function buildGet(url, withCredentials){
    const request = axios.get(url, {
        headers: DEFAULT_HEADERS
        , withCredentials:  withCredentials | true
    });
    return request;
}

export function buildPost(url, data, withCredentials){
    const request = axios.post(url, data, {
        headers: {
            'Content-Type': 'application/json',
            'accept': "application/json",
            'X-Custom-Header': 'foobar'
        }
        , withCredentials:  withCredentials | true,

    });
    return request;
}
export function buildDelete(url, withCredentials){
    const request = axios.delete(url, {
        headers: DEFAULT_HEADERS,
        withCredentials:   withCredentials | true
    })
    return request;
}