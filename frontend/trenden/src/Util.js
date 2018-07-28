export function formToQueryString(formDom) {
    var formData = new FormData(formDom);
    let parameters = [...formData.entries()] // expand the elements from the .entries() iterator into an actual array
        .map(e => encodeURIComponent(e[0]) + "=" + encodeURIComponent(e[1]))
        .join("&");  // transform the elements into encoded key-value-pairs
    return parameters;
}