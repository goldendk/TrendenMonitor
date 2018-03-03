var axios = require('axios');

export default class {
        loadOpenRecommendations = function(callback){
            axios.get('/rest/recommendation-periods/list/open')
                .then(function (response) {
                    callback.apply(this, response.data)
                })
                .catch(function (error) {
                    console.log(error);
                });
        };

    }

