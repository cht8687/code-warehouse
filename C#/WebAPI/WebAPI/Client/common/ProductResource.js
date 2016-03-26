(function () {

    "using strict";

    angular
          .module("common.services",
                     ["ngResources"])
          .factory("productResource",
                    ["$resource", "appSetings", productResource])

    function productResource($resource, appSettings) {
        return $resource(appSettings.serverPath + "/api/products/:id");
    }




}());