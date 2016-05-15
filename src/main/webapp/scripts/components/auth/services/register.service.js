'use strict';

angular.module('misApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


