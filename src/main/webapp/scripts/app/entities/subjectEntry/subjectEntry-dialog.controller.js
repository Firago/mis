'use strict';

angular.module('misApp').controller('SubjectEntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubjectEntry', 'Subject', 'Teacher', 'StudentGroup',
        function($scope, $stateParams, $uibModalInstance, entity, SubjectEntry, Subject, Teacher, StudentGroup) {

        $scope.subjectEntry = entity;
        $scope.subjects = Subject.query();
        $scope.teachers = Teacher.query();
        $scope.studentgroups = StudentGroup.query();
        $scope.load = function(id) {
            SubjectEntry.get({id : id}, function(result) {
                $scope.subjectEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('misApp:subjectEntryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subjectEntry.id != null) {
                SubjectEntry.update($scope.subjectEntry, onSaveSuccess, onSaveError);
            } else {
                SubjectEntry.save($scope.subjectEntry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
