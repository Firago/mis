'use strict';

angular.module('misApp')
    .controller('StudentGroupDetailController', function ($scope, $rootScope, $stateParams, entity, StudentGroup) {
        $scope.studentGroup = entity;
        $scope.load = function (id) {
            StudentGroup.get({id: id}, function(result) {
                $scope.studentGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:studentGroupUpdate', function(event, result) {
            $scope.studentGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
