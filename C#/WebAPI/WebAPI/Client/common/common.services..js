(function () {

    "using strict";

    angular
          .module("common.services",
                     ["ngResources"])
          .constant("appSettings",
          {
              serverPath: "http://localhost:48828"
          });




}());