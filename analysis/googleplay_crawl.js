"use strict";

var gplay = require('google-play-scraper');

//Get 500 appIDs from google play store.
//This is the list of top free apps in tools category.
//Basically, do this 5 times

var index = 0;
var arr = {
   table: []
};
var fs = require('fs');

while (index < 500){
  gplay.list({
      category: gplay.category.TOOLS,
      collection: gplay.collection.TOP_FREE,
      num: 100,
      start: index
  })
  .then(function(value){
      console.log(value);
      fs.readFile('data.json', 'utf8', function readFileCallback(err, data){
        if (err){
          console.log(err);
        } else {
          var obj = JSON.parse(data); //now it an object
          for (var i=0;i<value.length;i++){
            obj.table.push(value[i]); //add some data
          }
          var json = JSON.stringify(obj); //convert it back to json
          fs.writeFile('data.json', json, 'utf8', function(err) {
            if (err) {
              console.log("error")
            }
          }); // write it back
        }
      });
  });
  index += 100;
}
