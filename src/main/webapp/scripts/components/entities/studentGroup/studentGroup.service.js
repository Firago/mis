'use strict';

angular.module('misApp')
    .factory('StudentGroup', function ($resource, DateUtils) {
        return $resource('api/studentGroups/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'timetable': {
                'method': 'POST',
                'url': 'api/studentGroups/:id/timetable',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
