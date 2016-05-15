'use strict';

angular.module('misApp')
	.controller('SubjectDeleteController', function($scope, $uibModalInstance, entity, Subject) {

        $scope.subject = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Subject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
