'use strict';

angular.module('misApp')
    .controller('TeacherDetailController', function ($scope, $rootScope, $stateParams, entity, Teacher, User, Subject) {
        $scope.teacher = entity;
        $scope.load = function (id) {
            Teacher.get({id: id}, function(result) {
                $scope.teacher = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:teacherUpdate', function(event, result) {
            $scope.teacher = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
