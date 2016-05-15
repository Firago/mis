'use strict';

angular.module('misApp')
	.controller('StudentGroupDeleteController', function($scope, $uibModalInstance, entity, StudentGroup) {

        $scope.studentGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StudentGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
