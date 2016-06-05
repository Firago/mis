'use strict';

angular.module('misApp')
    .factory('Teacher', function ($resource, DateUtils) {
        return $resource('api/teachers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'timetable': {
                'method': 'POST',
                'url': 'api/teachers/:id/timetable',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
