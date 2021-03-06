'use strict';

angular.module('misApp')
    .controller('LessonDialogController',
        ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lesson', 'SubjectEntry', 'Teacher', 'StudentGroup',
            function ($scope, $stateParams, $uibModalInstance, entity, Lesson, SubjectEntry, Teacher, StudentGroup) {

                $scope.lesson = entity;
                $scope.subjectentrys = SubjectEntry.query();
                $scope.teachers = Teacher.query();
                $scope.studentgroups = StudentGroup.query();
                $scope.load = function (id) {
                    Lesson.get({id: id}, function (result) {
                        $scope.lesson = result;
                    });
                };

                var onSaveSuccess = function (result) {
                    $scope.$emit('misApp:lessonUpdate', result);
                    $uibModalInstance.close(result);
                    $scope.isSaving = false;
                };

                var onSaveError = function (result) {
                    $scope.isSaving = false;
                };

                $scope.save = function () {
                    $scope.lesson.start = moment($scope.lesson.start)
                        .format('YYYY-MM-DDTHH:mm:ssZ');
                    $scope.isSaving = true;
                    if ($scope.lesson.id != null) {
                        Lesson.update($scope.lesson, onSaveSuccess, onSaveError);
                    } else {
                        Lesson.save($scope.lesson, onSaveSuccess, onSaveError);
                    }
                };

                $scope.clear = function () {
                    $uibModalInstance.dismiss('cancel');
                };

            }]);
