'use strict';

angular.module('misApp')
    .controller('LessonController', function ($scope, $state, Lesson) {

        $scope.lessons = [];
        $scope.loadAll = function() {
            Lesson.query(function(result) {
               $scope.lessons = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lesson = {
                start: null,
                duration: null,
                room: null,
                id: null
            };
        };
    });
