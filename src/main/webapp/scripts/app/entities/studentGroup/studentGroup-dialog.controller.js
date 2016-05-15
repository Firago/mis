'use strict';

angular.module('misApp').controller('StudentGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentGroup',
        function($scope, $stateParams, $uibModalInstance, entity, StudentGroup) {

        $scope.studentGroup = entity;
        $scope.load = function(id) {
            StudentGroup.get({id : id}, function(result) {
                $scope.studentGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('misApp:studentGroupUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.studentGroup.id != null) {
                StudentGroup.update($scope.studentGroup, onSaveSuccess, onSaveError);
            } else {
                StudentGroup.save($scope.studentGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
