'use strict';

angular.module('misApp')
    .controller('StudentDetailController', function ($scope, $rootScope, $stateParams, entity, Student, User, StudentGroup) {
        $scope.student = entity;
        $scope.load = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:studentUpdate', function(event, result) {
            $scope.student = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
