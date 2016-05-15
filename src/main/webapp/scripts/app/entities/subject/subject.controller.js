'use strict';

angular.module('misApp')
    .controller('SubjectController', function ($scope, $state, Subject) {

        $scope.subjects = [];
        $scope.loadAll = function() {
            Subject.query(function(result) {
               $scope.subjects = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.subject = {
                name: null,
                type: null,
                id: null
            };
        };
    });
