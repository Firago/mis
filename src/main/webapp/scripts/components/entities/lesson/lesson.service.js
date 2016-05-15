'use strict';

angular.module('misApp')
    .factory('Lesson', function ($resource, DateUtils) {
        return $resource('api/lessons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.start = DateUtils.convertDateTimeFromServer(data.start);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
