'use strict';

angular.module('misApp').controller('SubjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subject',
        function($scope, $stateParams, $uibModalInstance, entity, Subject) {

        $scope.subject = entity;
        $scope.load = function(id) {
            Subject.get({id : id}, function(result) {
                $scope.subject = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('misApp:subjectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subject.id != null) {
                Subject.update($scope.subject, onSaveSuccess, onSaveError);
            } else {
                Subject.save($scope.subject, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
