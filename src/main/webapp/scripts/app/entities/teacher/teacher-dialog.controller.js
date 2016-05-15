'use strict';

angular.module('misApp').controller('TeacherDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Teacher', 'User', 'Subject',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Teacher, User, Subject) {

        $scope.teacher = entity;
        $scope.users = User.query();
        $scope.subjects = Subject.query();
        $scope.load = function(id) {
            Teacher.get({id : id}, function(result) {
                $scope.teacher = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('misApp:teacherUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.teacher.id != null) {
                Teacher.update($scope.teacher, onSaveSuccess, onSaveError);
            } else {
                Teacher.save($scope.teacher, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
