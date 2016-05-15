'use strict';

angular.module('misApp')
	.controller('TeacherDeleteController', function($scope, $uibModalInstance, entity, Teacher) {

        $scope.teacher = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Teacher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
