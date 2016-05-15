'use strict';

angular.module('misApp')
    .controller('LessonDetailController', function ($scope, $rootScope, $stateParams, entity, Lesson, SubjectEntry, Teacher, StudentGroup) {
        $scope.lesson = entity;
        $scope.load = function (id) {
            Lesson.get({id: id}, function(result) {
                $scope.lesson = result;
            });
        };
        var unsubscribe = $rootScope.$on('misApp:lessonUpdate', function(event, result) {
            $scope.lesson = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
