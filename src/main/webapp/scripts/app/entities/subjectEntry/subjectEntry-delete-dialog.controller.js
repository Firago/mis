'use strict';

angular.module('misApp')
	.controller('SubjectEntryDeleteController', function($scope, $uibModalInstance, entity, SubjectEntry) {

        $scope.subjectEntry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SubjectEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
