'use strict';

angular.module('misApp')
    .controller('SubjectEntryDetailController', function ($scope, $rootScope, $stateParams, entity, SubjectEntry, Subject, Teacher, StudentGroup) {
        $scope.subjectEntry = entity;
        $scope.load = function (id) {
            SubjectEntry.get({id: id}, function(result) {
                $scope.subjectEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:subjectEntryUpdate', function(event, result) {
            $scope.subjectEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
