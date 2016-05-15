'use strict';

angular.module('misApp')
    .controller('SubjectDetailController', function ($scope, $rootScope, $stateParams, entity, Subject) {
        $scope.subject = entity;
        $scope.load = function (id) {
            Subject.get({id: id}, function(result) {
                $scope.subject = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:subjectUpdate', function(event, result) {
            $scope.subject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
