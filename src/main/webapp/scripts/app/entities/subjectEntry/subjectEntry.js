'use strict';

angular.module('misApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subjectEntry', {
                parent: 'entity',
                url: '/subjectEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'misApp.subjectEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subjectEntry/subjectEntrys.html',
                        controller: 'SubjectEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subjectEntry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('subjectEntry.detail', {
                parent: 'entity',
                url: '/subjectEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'misApp.subjectEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subjectEntry/subjectEntry-detail.html',
                        controller: 'SubjectEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subjectEntry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SubjectEntry', function($stateParams, SubjectEntry) {
                        return SubjectEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('subjectEntry.new', {
                parent: 'subjectEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectEntry/subjectEntry-dialog.html',
                        controller: 'SubjectEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('subjectEntry', null, { reload: true });
                    }, function() {
                        $state.go('subjectEntry');
                    })
                }]
            })
            .state('subjectEntry.edit', {
                parent: 'subjectEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectEntry/subjectEntry-dialog.html',
                        controller: 'SubjectEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SubjectEntry', function(SubjectEntry) {
                                return SubjectEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subjectEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('subjectEntry.delete', {
                parent: 'subjectEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectEntry/subjectEntry-delete-dialog.html',
                        controller: 'SubjectEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SubjectEntry', function(SubjectEntry) {
                                return SubjectEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subjectEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
