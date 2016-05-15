'use strict';

angular.module('misApp')
    .factory('SubjectEntry', function ($resource, DateUtils) {
        return $resource('api/subjectEntrys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
