import Data from './Data';

export default class {
    loadOpenRecommendations = function(callback){
        var data = new Data();
        callback.call(this, data.openArray)
    };


   loadCompanies(callback){

       var data = new Data()
       callback.call(this, {
           data: data.companies
       });

   }

}