'use strict';

angular.module('misApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studentGroup', {
                parent: 'entity',
                url: '/studentGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'misApp.studentGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroups.html',
                        controller: 'StudentGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('studentGroup.timetable', {
                parent: 'entity',
                url: '/studentGroup/{id}/timetable',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'misApp.studentGroup.timetable.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroup-timetable.html',
                        controller: 'StudentGroupTimetableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentGroup', function($stateParams, StudentGroup) {
                        return StudentGroup.timetable({id : $stateParams.id}, {});
                    }]
                }
            })
            .state('studentGroup.detail', {
                parent: 'entity',
                url: '/studentGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'misApp.studentGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroup-detail.html',
                        controller: 'StudentGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentGroup', function($stateParams, StudentGroup) {
                        return StudentGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('studentGroup.new', {
                parent: 'studentGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroup-dialog.html',
                        controller: 'StudentGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('studentGroup', null, { reload: true });
                    }, function() {
                        $state.go('studentGroup');
                    })
                }]
            })
            .state('studentGroup.edit', {
                parent: 'studentGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroup-dialog.html',
                        controller: 'StudentGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StudentGroup', function(StudentGroup) {
                                return StudentGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('studentGroup.delete', {
                parent: 'studentGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentGroup/studentGroup-delete-dialog.html',
                        controller: 'StudentGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StudentGroup', function(StudentGroup) {
                                return StudentGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
