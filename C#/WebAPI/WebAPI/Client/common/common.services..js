(function () {

    "using strict";

    angular
          .module("common.services",
                     ["ngResource"])
          .constant("appSettings",
          {
              serverPath: "http://localhost:48828"
          });




}());